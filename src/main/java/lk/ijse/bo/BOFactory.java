package lk.ijse.bo;


import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        STUDENT, PROGRAMME, USER, REGISTRATION, PAYMENT,LOGIN

    }

    public SuperBO getBO(BOTypes boTypes) {

        switch (boTypes) {
            case STUDENT:
                return new StudentBOImpl();
            case PROGRAMME:
                return new ProgrammeBOImpl();
            case USER:
                return new UserBOImpl();
            case REGISTRATION:
                return new RegistrationBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            default:
                return null;
        }
    }
}
