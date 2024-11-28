package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        STUDENT, PROGRAMME, USER, REGISTRATION, PAYMENT,LOGIN
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {

        switch (daoTypes) {

            case STUDENT:
                return new StudentDAOImpl();
            case PROGRAMME:
                return new ProgrammeDAOImpl();
            case USER:
                return new UserDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            default:
                return null;


        }
    }
}
