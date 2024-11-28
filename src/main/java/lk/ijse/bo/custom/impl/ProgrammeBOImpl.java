package lk.ijse.bo.custom.impl;

import lk.ijse.DTO.ProgrammeDTO;
import lk.ijse.bo.custom.ProgrammeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgrammeDAO;
import lk.ijse.entity.Programme;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammeBOImpl implements ProgrammeBO<ProgrammeDTO>{

    ProgrammeDAO programmeDAO = (ProgrammeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PROGRAMME);
    @Override
    public boolean addProgramme(ProgrammeDTO entity) throws IOException {
        Programme programme = new Programme();
        programme.setProgrammeId(entity.getProgrammeId());
        programme.setProgrammeName(entity.getProgrammeName());
        programme.setDuration(entity.getDuration());
        programme.setFee(entity.getFee());

        return programmeDAO.add(programme);
    }

    @Override
    public String generateNewProgrammeID() throws IOException {
        return programmeDAO.generateNewID();
    }

    @Override
    public List<ProgrammeDTO> getAllPrograms() throws IOException {
        List<Programme> programmes = programmeDAO.getAll();
        List<ProgrammeDTO> programmeDTOS = new ArrayList<>();

        for (Programme programme: programmes){
            ProgrammeDTO programmeDTO = new ProgrammeDTO();

            programmeDTO.setProgrammeId(programme.getProgrammeId());
            programmeDTO.setProgrammeName(programme.getProgrammeName());
            programmeDTO.setDuration(programme.getDuration());
            programmeDTO.setFee(programme.getFee());

            programmeDTOS.add(programmeDTO);
        }
        return programmeDTOS;
    }

    @Override
    public boolean updateProgram(ProgrammeDTO entity) throws IOException {
        Programme programme = new Programme();
        programme.setProgrammeId(entity.getProgrammeId());
        programme.setProgrammeName(entity.getProgrammeName());
        programme.setDuration(entity.getDuration());
        programme.setFee(entity.getFee());
        return programmeDAO.update(programme);
    }

    @Override
    public boolean deleteProgram(String id) throws IOException {
        return programmeDAO.delete(id);
    }

    @Override
    public List<Programme> SearchCID(String cid) throws IOException {
        return programmeDAO.SearchCID(cid);
    }

    @Override
    public Programme serachbyCIDs(String cid) throws SQLException, ClassNotFoundException, IOException {
        return programmeDAO.searchByCId(cid);
    }
}
