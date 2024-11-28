package lk.ijse.bo.custom.impl;

import lk.ijse.DTO.PaymentDto;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.entity.Payment;
import lk.ijse.entity.Registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO<PaymentDto> {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public List<Registration> getRegistrationIds() {

        return paymentDAO.getRegistrationIds();
    }

    @Override
    public double getPaidAmountByRegistrationId(Registration selectedRegistrationId) {

        return paymentDAO.getPaidAmountByRegistrationId(selectedRegistrationId);
    }

    @Override
    public double getFullFeeRegistrationId(Registration selectedRegistrationId) {

        return paymentDAO.getFullFeeByRegistrationId(selectedRegistrationId);
    }

    @Override
    public double getAmounteRegistrationId(Registration selectedRegistrationId) {

        return paymentDAO.getAmountRegistrationId(selectedRegistrationId);
    }

    @Override
    public boolean save(PaymentDto paymentDto) throws IOException {

        Payment payment = new Payment(
                paymentDto.getPaymentId(),
                paymentDto.getAmount(),
                paymentDto.getPaidAmount(),
                paymentDto.getFullPayment(),
                paymentDto.getPay(),
                paymentDto.getBalance(),
                paymentDto.getRegistration()
        );
        return paymentDAO.add(payment);
    }

    @Override
    public List<PaymentDto> getAll() throws IOException {
        // Get all payments from DAO and convert them into DTOs
        List<PaymentDto> paymentDtos = new ArrayList<>();
        List<Payment> allPayments = paymentDAO.getAll();
        for (Payment payment : allPayments) {
            paymentDtos.add(new PaymentDto(
                    payment.getPaymentId(),
                    payment.getAmount(),
                    payment.getPaidAmount(),
                    payment.getFullPayment(),
                    payment.getPay(),
                    payment.getBalance(),
                    payment.getRegistration()
            ));
        }
        return paymentDtos;
    }

    @Override
    public String getCurrentId() throws IOException {
        // Get the current payment ID
        return paymentDAO.generateNewID();
    }
}
