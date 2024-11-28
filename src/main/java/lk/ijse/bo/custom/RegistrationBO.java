package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Programme;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface RegistrationBO extends SuperBO {

    boolean saveCourse(Programme entity) throws IOException;

    boolean updateCourse(Programme entity) throws IOException;

    boolean deleteCourse(String id) throws IOException;

    List<Programme> getAllCourse() throws IOException;

    List<Programme> SearchCID(String cid) throws IOException;


    boolean saveStudent(Student entity) throws IOException;

    boolean updateStudent(Student entity) throws IOException;

    boolean deleteStudent(String id) throws IOException;

    List<Student> getAllStudent() throws IOException;

    List<Student> SearchSID(String sid) throws IOException;

    Student serachbyIDS(String cid) throws SQLException, ClassNotFoundException, IOException;


    Programme serachbyCIDs(String cid) throws SQLException, ClassNotFoundException, IOException;

    boolean saveRegistration(Registration entity) throws IOException;

    List<Registration> getAllRegistrationDetails() throws IOException;

    public String generateNewRegistrationID() throws IOException;

}
