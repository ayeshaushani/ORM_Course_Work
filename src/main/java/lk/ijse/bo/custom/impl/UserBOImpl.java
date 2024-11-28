package lk.ijse.bo.custom.impl;

import lk.ijse.DTO.UserDTO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO<UserDTO> {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);


    @Override
    public boolean addUser(UserDTO entity) throws IOException {
        User user = new User();
        user.setUserId(entity.getUserId());
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole());

        return userDAO.add(user);
    }

    @Override
    public String generateNewUserID() throws IOException {
        return userDAO.generateNewID();
    }

    @Override
    public List<UserDTO> getAllusers() throws IOException {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users){

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());

            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Override
    public boolean updateUser(UserDTO entity) throws IOException {
        User user = new User();

        user.setUserId(entity.getUserId());
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());
        user.setRole(entity.getRole());

        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String id) throws IOException {
        return userDAO.delete(id);
    }

   /* @Override
    public boolean validUser(UserDTO userDTO) throws IOException {
        return userDAO.valid(new User(userDTO.getUserId(), userDTO.getPassword(),userDTO.getUserName(), userDTO.getRole()));
    }*/
}
