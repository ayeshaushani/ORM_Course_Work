package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.io.IOException;
import java.util.List;

public interface UserBO<T> extends SuperBO {

    public boolean addUser(T entity) throws IOException;

    public String generateNewUserID() throws IOException;

    public List<T> getAllusers() throws IOException;

    public boolean updateUser(T entity) throws IOException;

    public boolean deleteUser(String id) throws IOException;

   // public boolean validUser(T id) throws IOException;
}
