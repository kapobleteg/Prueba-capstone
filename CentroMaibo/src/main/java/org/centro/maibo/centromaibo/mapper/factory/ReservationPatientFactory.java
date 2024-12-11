package org.centro.maibo.centromaibo.mapper.factory;

import org.centro.maibo.centromaibo.domain.Reservation;
import org.centro.maibo.centromaibo.dto.ReservationDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;
import org.centro.maibo.centromaibo.mapper.MapperFactory;
import org.centro.maibo.centromaibo.mapper.mappers.PaymentReceiptMapper;
import org.centro.maibo.centromaibo.mapper.mappers.PsychologistMapper;
import org.centro.maibo.centromaibo.mapper.mappers.ReservationPatientMapper;
import org.springframework.stereotype.Service;

@Service
public class ReservationPatientFactory implements MapperFactory<Reservation, ReservationDTO> {

    @Override
    public Mapper<Reservation, ReservationDTO> createFull() {
        return new ReservationPatientMapper().withPaymentReceipt(new PaymentReceiptMapper())
                .withPsychologistReceipt(new PsychologistMapper());
    }

    @Override
    public Mapper<Reservation, ReservationDTO> createSimple() {
        return new ReservationPatientMapper();
    }
}
