package org.centro.maibo.centromaibo.mapper.mappers;

import org.centro.maibo.centromaibo.domain.PaymentReceipt;
import org.centro.maibo.centromaibo.domain.Profile;
import org.centro.maibo.centromaibo.domain.StatesEnum;
import org.centro.maibo.centromaibo.dto.PaymentReceiptDTO;
import org.centro.maibo.centromaibo.dto.ProfileDTO;
import org.centro.maibo.centromaibo.mapper.Mapper;

import java.math.BigDecimal;

public class PaymentReceiptMapper implements Mapper<PaymentReceipt, PaymentReceiptDTO> {

    @Override
    public PaymentReceiptDTO map(PaymentReceipt entity) {
        PaymentReceiptDTO paymentReceiptDTO = new PaymentReceiptDTO();
        paymentReceiptDTO.setId(entity.getId());
        paymentReceiptDTO.setAttached(entity.getAttached());

        // Conversion de Integer a BigDecimal
        if (entity.getAmount() != null) {
            paymentReceiptDTO.setAmount(new BigDecimal(entity.getAmount()));
        }

        paymentReceiptDTO.setDate(entity.getDate());

        // Conversion de StatesEnum a Boolean
//        paymentReceiptDTO.setStatus(entity.getStatus());

        paymentReceiptDTO.setComment(entity.getComment());
        return paymentReceiptDTO;
    }
}
