package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Programme;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProgrammeBO<T> extends SuperBO {

    public boolean addProgramme(T entity) throws IOException;

   public String generateNewProgrammeID() throws IOException;

    public List<T> getAllPrograms() throws IOException;

    public boolean updateProgram(T entity) throws IOException;

    public boolean deleteProgram(String id) throws IOException;

    List<Programme> SearchCID(String cid) throws IOException;

    Programme serachbyCIDs(String cid) throws SQLException, ClassNotFoundException, IOException;
}
