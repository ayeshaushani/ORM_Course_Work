package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.DTO.LoginDTO;
import lk.ijse.entity.Login;

import java.io.IOException;
import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    LoginDAO loginDAO = (LoginDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public boolean checkCredential(LoginDTO login) throws SQLException, IOException, ClassNotFoundException {
        return loginDAO.checkCredential(new Login(login.getUserName(), login.getPassword()));
    }
}
