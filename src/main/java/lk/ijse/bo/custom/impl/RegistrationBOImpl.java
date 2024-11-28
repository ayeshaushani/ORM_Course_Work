package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgrammeDAO;
import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Programme;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);
    ProgrammeDAO programmeDAO = (ProgrammeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PROGRAMME);
    RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.REGISTRATION);


    @Override
    public boolean saveCourse(Programme entity) throws IOException {
        return programmeDAO.add(new Programme(entity.getProgrammeId(),entity.getProgrammeName(), entity.getDuration(), entity.getFee(), entity.getRegistrations()));
    }

    @Override
    public boolean updateCourse(Programme entity) throws IOException {
        return programmeDAO.update(new Programme(entity.getProgrammeId(),entity.getProgrammeName(), entity.getDuration(), entity.getFee(), entity.getRegistrations()));

    }

    @Override
    public boolean deleteCourse(String id) throws IOException {
        return programmeDAO.delete(id);
    }

    @Override
    public List<Programme> getAllCourse() throws IOException {
        List<Programme> allPrograms = programmeDAO.getAll();
        return allPrograms;
    }

    @Override
    public List<Programme> SearchCID(String cid) throws IOException {
        return programmeDAO.SearchCID(cid);
    }

    @Override
    public boolean saveStudent(Student entity) throws IOException {
        return studentDAO.add(new Student(entity.getStudent_id(), entity.getName(), entity.getAddress(), entity.getContact(), entity.getDate(), entity.getUser(), entity.getRegistrations()));
    }

    @Override
    public boolean updateStudent(Student entity) throws IOException {
        return studentDAO.update(new Student(entity.getStudent_id(), entity.getName(), entity.getAddress(), entity.getContact(), entity.getDate(), entity.getUser(), entity.getRegistrations()));

    }

    @Override
    public boolean deleteStudent(String id) throws IOException {
        return studentDAO.delete(id);
    }

    @Override
    public List<Student> getAllStudent() throws IOException {

        List<Student> allStudent = studentDAO.getAll();

        return allStudent;
    }

    @Override
    public List<Student> SearchSID(String sid) throws IOException {
        return studentDAO.SearchSID(sid);
    }

    @Override
    public Student serachbyIDS(String cid) throws SQLException, ClassNotFoundException, IOException {
        return studentDAO.searchById(cid);
    }

    @Override
    public Programme serachbyCIDs(String cid) throws SQLException, ClassNotFoundException, IOException {
        return programmeDAO.searchByCId(cid);
    }

    @Override
    public boolean saveRegistration(Registration entity) throws IOException {
        return registrationDAO.add(new Registration(entity.getRegiId(),entity.getEnrollmentDate(),entity.getPayment(),entity.getDueAmount(),entity.getStudentName(),entity.getProgramName(),entity.getDuration(),entity.getStudent(),entity.getProgramme()));

    }

    @Override
    public List<Registration> getAllRegistrationDetails() throws IOException {
        List<Registration> alldetails = registrationDAO.getAll();

        return alldetails;
    }

    @Override
    public String generateNewRegistrationID() throws IOException {
        return registrationDAO.generateNewID();
    }
}
