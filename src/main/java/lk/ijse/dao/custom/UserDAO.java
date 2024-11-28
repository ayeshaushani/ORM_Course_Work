package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.io.IOException;

public interface UserDAO extends CrudDAO<User> {
    boolean valid(User user) throws IOException;
}
