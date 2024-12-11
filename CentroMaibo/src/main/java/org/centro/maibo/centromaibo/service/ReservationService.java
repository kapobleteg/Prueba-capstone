package org.centro.maibo.centromaibo.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.config.MailService;
import org.centro.maibo.centromaibo.config.RandomUtil;
import org.centro.maibo.centromaibo.domain.*;
import org.centro.maibo.centromaibo.dto.*;
import org.centro.maibo.centromaibo.exception.*;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.properties.RoleProperties;
import org.centro.maibo.centromaibo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final MapperFactory<Reservation, ReservationDTO> reservationMapperFactory;
    private final BoxRepository boxRepository;
    private final MailService mailService;
    private final RoleRepository roleRepository;
    private final RoleProperties roleProperties;
    private final PaymentReceiptRepository paymentReceiptRepository;
    private final PsychologistRepository psychologistRepository;
    private final BankInfoRepository bankInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public ReservationDTO getById(Long id) throws BoxNotFoundException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new BoxNotFoundException(id));
        return reservationMapperFactory.createFull().map(reservation);
    }

    public List<ReservationDTO> getAllReservationAdmin() {
        List<Reservation> boxes = reservationRepository.findByPsychologistIsNull();
        Mapper<Reservation, ReservationDTO> mapper = reservationMapperFactory.createFull();
        return boxes.stream().map(mapper::map).toList();
    }

    @Transactional
    public ReservationDTO reserve(ReserveFormDTO reserveDTO)
            throws BoxNotFoundException, RoleNotFoundException, ReservationConflictException {

        Box box = boxRepository.findById(reserveDTO.getBoxId())
                .orElseThrow(() -> new BoxNotFoundException(reserveDTO.getBoxId()));

        User user = userRepository.findByEmail(reserveDTO.getEmail());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reserveDTO.getReservationDate());
        calendar.add(Calendar.HOUR, 1);
        Date endDate = calendar.getTime();

        boolean isReserved = reservationRepository.existsByBoxAndStartDateBetween(
                box, reserveDTO.getReservationDate(), endDate);

        if (isReserved) {
            throw new ReservationConflictException(
                    "El box ya está reservado en esa franja horaria. Seleccione otra fecha u hora.");
        }
        if (user == null) {
            Role role = roleRepository.findById(roleProperties.getClientRoleId())
                    .orElseThrow(() -> new RoleNotFoundException(
                            "El Rol #" + roleProperties.getClientRoleId() + " no existe en el sistema"));

            User newUser = new User();
            newUser.setEmail(reserveDTO.getEmail());
            newUser.setName(reserveDTO.getName());
            newUser.setPhone(reserveDTO.getPhone());
            newUser.setStatus(false);
            newUser.setRole(role);
            newUser.setActivationKey(RandomUtil.generateActivationKey());
            userRepository.save(newUser);

            Profile profile = new Profile();
            profile.setCertifications(reserveDTO.getCertifications().toArray(new String[0]));
            profile.setSpecialty(reserveDTO.getSpecialty());
            profile.setUser(newUser);
            profileRepository.save(profile);
            try {
                mailService.sendActivationEmail(newUser);
            } catch (Exception e) {
                System.err.println("Error al enviar el correo: " + e.getMessage());
            }

            user = newUser;
        }


        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBox(box);
        reservation.setStatus(StatesEnum.PENDING);
        reservation.setStartDate(reserveDTO.getReservationDate());
        reservation.setEndDate(endDate);

        PaymentReceipt paymentReceipt = new PaymentReceipt();
        paymentReceipt.setStatus(StatesEnum.PENDING);
        paymentReceipt.setAmount(box.getPrice());
        paymentReceipt.setDate(new Date());
        paymentReceipt.setAttached(reserveDTO.getFile());
        paymentReceiptRepository.save(paymentReceipt);

        reservation.setPaymentReceipt(paymentReceipt);
        reservationRepository.save(reservation);
        mailService.sendScheduledAppointmentEmail(user);

        return reservationMapperFactory.createFull().map(reservation);
    }

    @Transactional
    public ReservationDTO scheduledAppointment(BookAppointmentDTO dto)
            throws BoxNotFoundException, RoleNotFoundException, ReservationConflictException {
        User user = userRepository.findByEmail(dto.getEmail());
        Psychologist psychologist = psychologistRepository.findById(dto.getPsychologist().getId()).orElseThrow();
        BankInfo bankInfo = bankInfoRepository.findByUserId(psychologist.getUser().getId()).orElseThrow(() ->
                new BankInfoNotFoundException("La informacion del banco no se encuentra en el sistema"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getReservationDate());
        calendar.add(Calendar.HOUR, 1);
        Date endDate = calendar.getTime();

        boolean isReserved = reservationRepository.existsByPsychologistAndEndDateBetween(
                psychologist, dto.getReservationDate(), endDate);

        if (isReserved) {
            throw new ReservationConflictException(
                    "ya se encuntra una reservación en esa franja horaria. Seleccione otra fecha u hora.");
        }
        if (user == null) {
            Role role = roleRepository.findById(roleProperties.getPatientRoleId())
                    .orElseThrow(() -> new RoleNotFoundException(
                            "El Rol #" + roleProperties.getPatientRoleId() + " no existe en el sistema"));

            User newUser = new User();
            newUser.setEmail(dto.getEmail());
            newUser.setName(dto.getName());
            newUser.setPhone(dto.getPhone());
            newUser.setStatus(false);
            newUser.setActivationKey(RandomUtil.generateActivationKey());
            newUser.setRole(role);
            userRepository.save(newUser);

            Profile profile = new Profile();
            profile.setSpecialty("Paciente");
            profile.setUser(newUser);
            profileRepository.save(profile);

            try {
                mailService.sendActivationEmail(newUser);
            } catch (Exception e) {
                System.err.println("Error al enviar el correo: " + e.getMessage());
            }

            user = newUser;
        }


        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setStatus(StatesEnum.PENDING);
        reservation.setStartDate(dto.getReservationDate());
        reservation.setEndDate(endDate);
        reservation.setPsychologist(psychologist);

        PaymentReceipt paymentReceipt = new PaymentReceipt();
        paymentReceipt.setStatus(StatesEnum.PENDING);
        paymentReceipt.setAmount(bankInfo.getAmountTransfer());
        paymentReceipt.setDate(new Date());
        paymentReceipt.setAttached(dto.getFile());
        paymentReceiptRepository.save(paymentReceipt);

        reservation.setPaymentReceipt(paymentReceipt);
        reservationRepository.save(reservation);
        mailService.sendScheduledAppointmentEmail(user);

        return reservationMapperFactory.createFull().map(reservation);
    }

    @Transactional
    public void reservationAccepted(ReservationDTO reservationPatientDTO) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationPatientDTO.getId()).orElseThrow(() ->
                new ReservationNotFoundException("La reserva no se encuentra en el sistema"));
        reservation.setStatus(StatesEnum.APPROVED);
        reservation.getPaymentReceipt().setStatus(StatesEnum.APPROVED);
        Psychologist psychologist = new Psychologist();
        psychologist.setName(reservation.getUser().getName());
        psychologist.setUser(reservation.getUser());
        psychologistRepository.save(psychologist);
        mailService.sendReservationAcceptedEmail(reservation.getUser());
    }

    @Transactional
    public void reservationRejected(ReservationDTO reservationPatientDTO) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationPatientDTO.getId()).orElseThrow(() ->
                new ReservationNotFoundException("La reserva no se encuentra en el sistema"));
        reservation.setStatus(StatesEnum.REJECTED);
        reservation.getPaymentReceipt().setStatus(StatesEnum.REJECTED);
        mailService.sendReservationRejectedEmail(reservation.getUser());
    }

    @Transactional
    public List<ReservationDTO> getAllBookAppointments(Long id) throws ReservationNotFoundException,
            UserNotFoundException, PsychologistNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id));
        Psychologist psychologist = psychologistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new PsychologistNotFoundException("El psicologo no se encuentra en el sistema"));

        List<Reservation> reservations = reservationRepository.findByPsychologistId(psychologist.getId())
                .orElseThrow(() -> new PsychologistNotFoundException("La reserva no se encuentra en el sistema"));
        Mapper<Reservation, ReservationDTO> mapper = reservationMapperFactory.createFull();
        return reservations.stream().map(mapper::map).toList();

    }

    @Transactional
    public void reservationAcceptedBookAppointments(ReservationDTO reservationPatientDTO) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationPatientDTO.getId()).orElseThrow(() ->
                new ReservationNotFoundException("La reserva no se encuentra en el sistema"));
        reservation.setStatus(StatesEnum.APPROVED);
        reservation.getPaymentReceipt().setStatus(StatesEnum.APPROVED);
        mailService.sendReservationAcceptedEmail(reservation.getUser());
    }

    @Transactional
    public void reservationRejectedBookAppointments(ReservationDTO reservationPatientDTO) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationPatientDTO.getId()).orElseThrow(() ->
                new ReservationNotFoundException("La reserva no se encuentra en el sistema"));
        reservation.setStatus(StatesEnum.REJECTED);
        reservation.getPaymentReceipt().setStatus(StatesEnum.REJECTED);
        mailService.sendReservationRejectedEmail(reservation.getUser());
    }

    @Transactional
    public ReservationDTO updateReservation(Long id, ReservationUpdateRequestDTO updateRequest) throws ReservationNotFoundException {
        log.info("entra");

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reserva no encontrada con ID: " + id));
        Date now = new Date();
        if (updateRequest.getStartDate().before(now)) {
            throw new StartDateInPastException("La fecha de inicio no puede estar en el pasado.");
        }
        if (updateRequest.getEndDate().before(updateRequest.getStartDate())) {
            throw new EndDateBeforeStartDateException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }
        reservation.setStartDate(updateRequest.getStartDate());
        reservation.setEndDate(updateRequest.getEndDate());
        reservationRepository.save(reservation);

        mailService.sendUpdateReservation(reservation);
        return reservationMapperFactory.createFull().map(reservation);
    }

    public List<ReservationDTO> getAllPatient(Long id)  throws ReservationNotFoundException,
            UserNotFoundException, PsychologistNotFoundException {
        List<Reservation> reservations = reservationRepository.findByUserIdAndStatusIn(id, List.of(StatesEnum.PENDING,StatesEnum.APPROVED))
                .orElseThrow(() -> new PsychologistNotFoundException("La reserva no se encuentra en el sistema"));
        Mapper<Reservation, ReservationDTO> mapper = reservationMapperFactory.createFull();
        return reservations.stream().map(mapper::map).toList();

    }

    @Transactional
    public void cancelReservation(Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new ReservationNotFoundException("La reserva no se encuentra en el sistema"));
        reservation.setStatus(StatesEnum.REJECTED);
        reservation.getPaymentReceipt().setStatus(StatesEnum.REJECTED);
        mailService.sendReservationRejectedEmail(reservation.getUser());
    }
}