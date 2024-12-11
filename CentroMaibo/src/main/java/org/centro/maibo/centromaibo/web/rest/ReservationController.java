package org.centro.maibo.centromaibo.web.rest;

import lombok.RequiredArgsConstructor;
import org.centro.maibo.centromaibo.dto.*;
import org.centro.maibo.centromaibo.exception.*;
import org.centro.maibo.centromaibo.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO getById(@PathVariable Long id) {
        try {
            return reservationService.getById(id);
        } catch (BoxNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getAll() {
        return reservationService.getAllReservationAdmin();
    }

    @PostMapping("reservation-box")
    @ResponseStatus(HttpStatus.OK)
    public ReservationDTO reserve(@RequestBody ReserveFormDTO reserveDTO) {
        try {
            if (reserveDTO.getFile() == null || reserveDTO.getFile().isEmpty()) {
                throw new IllegalArgumentException("Archivo Base64 vacío o nulo.");
            }
            reservationService.reserve(reserveDTO);
            return new ReservationDTO();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Base64 inválido: " + e.getMessage());
        } catch (BoxNotFoundException | RoleNotFoundException | ReservationConflictException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + e.getMessage());
        }
    }

    @PostMapping("accepted")
    @ResponseStatus(HttpStatus.OK)
    public void reservationAccepted(@RequestBody ReservationDTO dto) throws ReservationNotFoundException {
        reservationService.reservationAccepted(dto);
    }

    @PostMapping("rejected")
    @ResponseStatus(HttpStatus.OK)
    public void reservationRejected(@RequestBody ReservationDTO dto) throws ReservationNotFoundException {
        reservationService.reservationRejected(dto);
    }

    @PostMapping("schedule")
    @ResponseStatus(HttpStatus.OK)
    public BookAppointmentDTO scheduledAppointment(@RequestBody BookAppointmentDTO dto) {
        try {
            if (dto.getFile() == null || dto.getFile().isEmpty()) {
                throw new IllegalArgumentException("Archivo Base64 vacío o nulo.");
            }
            reservationService.scheduledAppointment(dto);
            return new BookAppointmentDTO();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Base64 inválido: " + e.getMessage());
        } catch (BoxNotFoundException | RoleNotFoundException | ReservationConflictException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + e.getMessage());
        }
    }

    @GetMapping("book-appointments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getAllReservation(@PathVariable Long id) {
        try {
            return reservationService.getAllBookAppointments(id);
        } catch (ReservationNotFoundException | UserNotFoundException | PsychologistNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getAllPatient(@PathVariable Long id) {
        try {
            return reservationService.getAllPatient(id);
        } catch (ReservationNotFoundException | UserNotFoundException | PsychologistNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("accepted-book-appointment")
    @ResponseStatus(HttpStatus.OK)
    public void reservationAcceptedBookAppointment(@RequestBody ReservationDTO dto) throws ReservationNotFoundException {
        reservationService.reservationAcceptedBookAppointments(dto);
    }

    @PostMapping("rejected-book-appointment")
    @ResponseStatus(HttpStatus.OK)
    public void reservationRejectedBookAppointment(@RequestBody ReservationDTO dto) throws ReservationNotFoundException {
        reservationService.reservationRejectedBookAppointments(dto);
    }

    @PutMapping("/{id}")
    public ReservationDTO updateReservation(
            @PathVariable Long id,
            @RequestBody ReservationUpdateRequestDTO updateRequest) {
        try {
            return reservationService.updateReservation(id, updateRequest);
        } catch (ReservationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReservation(@PathVariable Long id) {
        try {
            reservationService.cancelReservation(id);
        } catch (ReservationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada.");
        }
    }
}
