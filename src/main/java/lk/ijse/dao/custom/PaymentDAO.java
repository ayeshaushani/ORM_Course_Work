package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Payment;
import lk.ijse.entity.Registration;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {

    List<Registration> getRegistrationIds();

    double getPaidAmountByRegistrationId(Registration registrationId);

    double getFullFeeByRegistrationId(Registration selectedRegistrationId);

    double getAmountRegistrationId(Registration selectedRegistrationId);

}
