package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.util.config.FactoryConfiguration;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.entity.*;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoginDAOImpl implements LoginDAO {

    @Override
    public boolean checkCredential(Login login) throws SQLException, IOException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Query<User> query = session.createQuery("FROM User u WHERE u.userName = :userName", User.class);
            query.setParameter("userName", login.getUserName());

            User user = query.uniqueResult();

            if (user != null) {
                if (BCrypt.checkpw(login.getPassword(), user.getPassword())) {

                    // Store userId and role in Session singleton
                    session = FactoryConfiguration.getInstance().getSession();
                    UserSession.getInstance().setUser(user.getUserId(), user.getRole());
                    return true;
                } else {
                    new Alert(Alert.AlertType.ERROR, "Sorry! Password is incorrect!").show();
                    return false;
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Sorry! User ID can't be found!").show();
                return false;
            }
        } finally {
            session.close();
        }
    }
}
