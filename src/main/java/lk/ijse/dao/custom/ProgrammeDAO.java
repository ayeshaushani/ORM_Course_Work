package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Programme;

import java.io.IOException;
import java.util.List;

public interface ProgrammeDAO extends CrudDAO<Programme> {
    List<Programme> SearchCID(String cid) throws IOException;

    Programme searchByCId(String id) throws IOException;
}
