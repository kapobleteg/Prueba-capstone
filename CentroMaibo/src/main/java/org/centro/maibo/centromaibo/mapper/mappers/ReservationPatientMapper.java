package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.PaymentReceipt;
import org.centro.maibo.centromaibo.domain.Psychologist;
import org.centro.maibo.centromaibo.domain.Reservation;
import org.centro.maibo.centromaibo.dto.PaymentReceiptDTO;
import org.centro.maibo.centromaibo.dto.PsychologistDTO;
import org.centro.maibo.centromaibo.dto.ReservationDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

public class ReservationPatientMapper implements Mapper<Reservation, ReservationDTO> {


    private Mapper<PaymentReceipt, PaymentReceiptDTO> paymentReceiptMapper;

    public ReservationPatientMapper withPaymentReceipt(Mapper<PaymentReceipt, PaymentReceiptDTO> paymentReceiptMapper) {
        this.paymentReceiptMapper = paymentReceiptMapper;
        return this;
    }

    private Mapper<Psychologist, PsychologistDTO> psychologistMapper;

    public ReservationPatientMapper withPsychologistReceipt(Mapper<Psychologist, PsychologistDTO> psychologistMapper) {
        this.psychologistMapper = psychologistMapper;
        return this;
    }


    @Override
    public ReservationDTO map(Reservation entity) {
        ReservationDTO reservationPatientDTO = new ReservationDTO();
        reservationPatientDTO.setId(entity.getId());
        reservationPatientDTO.setBox(entity.getBox());
        reservationPatientDTO.setStartDate(entity.getStartDate());
        reservationPatientDTO.setEndDate(entity.getEndDate());
        reservationPatientDTO.setStatus(entity.getStatus());
        if (entity.getPaymentReceipt() != null) {
            reservationPatientDTO.setPaymentReceipt(paymentReceiptMapper.map(entity.getPaymentReceipt()));
        }
        reservationPatientDTO.setUser(entity.getUser());
        if (entity.getPsychologist() != null) {
            reservationPatientDTO.setPsychologist(psychologistMapper.map(entity.getPsychologist()));
        }
        return reservationPatientDTO;
    }


}
