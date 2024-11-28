package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentBO<T> extends SuperBO {

    public boolean addStudent(T entity) throws IOException;

    public String generateNewStudentID() throws IOException;

    public List<T> getAllStudents() throws IOException;

    public boolean updateStudent(T entity) throws IOException;

    public boolean deleteStudent(String id) throws IOException;

    List<Student> SearchSID(String sid) throws IOException;

    Student serachbyIDS(String sid) throws SQLException, ClassNotFoundException, IOException;

    List<User> getUserIds();
}
