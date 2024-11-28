package lk.ijse.bo.custom;

import lk.ijse.DTO.PaymentDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Registration;

import java.io.IOException;
import java.util.List;

public interface PaymentBO<T> extends SuperBO {
    List<Registration> getRegistrationIds();

    double getPaidAmountByRegistrationId(Registration selectedRegistrationId);

    double getFullFeeRegistrationId(Registration selectedRegistrationId);

    double getAmounteRegistrationId(Registration selectedRegistrationId);

    boolean save(PaymentDto paymentDto) throws IOException;

    List<PaymentDto> getAll() throws IOException;

    String getCurrentId() throws IOException;
}
