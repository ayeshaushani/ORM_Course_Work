package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.io.IOException;
import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    List<Student> SearchSID(String sid) throws IOException;

    Student searchById(String id) throws IOException;

    List<User> getid();
}
