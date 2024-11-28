package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.DTO.UserDTO;
import lk.ijse.DTO.tm.UserTM;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import org.mindrot.jbcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    @FXML
    private TableView<UserTM> UserTable;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserPassword;

    @FXML
    private TableColumn<?, ?> colUserRole;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane rootStudent;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
    ObservableList<UserTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setUserID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setUserTypes();
        try {
            getAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));  //  password not show
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void getAll() throws IOException {
        List<UserDTO> allUsers = userBO.getAllusers();
        if (!(allUsers.isEmpty())){
            for (UserDTO userDTO : allUsers){
                obList.add(new UserTM(userDTO.getUserId(), userDTO.getUserName(),
                        userDTO.getPassword(),userDTO.getRole()));

                UserTable.setItems(obList);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Empty Users ").show();
        }
    }

    private void refreshTable() throws IOException {
        obList.clear();
        getAll();
        setUserID();
    }

    private void setUserTypes() {
        ObservableList<String> role = FXCollections.observableArrayList();
        //cmbType.setValue("Admin");

        role.add("Admin");
        role.add("Coordinator");

        cmbType.setItems(role);
    }

    private void setUserID() throws IOException {
        String userID = userBO.generateNewUserID();

        if (userID == null) {
            txtId.setText("U000001");
        } else {
            String[] split = userID.split("[U]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("U%06d", lastDigits);
            txtId.setText(newID);
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {
        UserTM selectItem = UserTable.getSelectionModel().getSelectedItem();

        if (selectItem!= null){
            userBO.deleteUser(selectItem.getUserId());
            new Alert(Alert.AlertType.INFORMATION, "User Deleted (:").show();
            refreshTable();
            clearAll();
        }else {
            new Alert(Alert.AlertType.ERROR, "Please select a user to delete ):").show();
        }
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String userId = txtId.getText();
        String userName = txtName.getText();
        String userPassword = txtPassword.getText();
        String role = (String) cmbType.getValue();

        // Hash the password with bcrypt
        String hashedPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName(userName);
        userDTO.setPassword(hashedPassword);
        userDTO.setRole(role);

        boolean isSaved = userBO.addUser(userDTO);

        if (isSaved){

            clearAll();
            new Alert(Alert.AlertType.CONFIRMATION, "User Added successful :) !!!").show();
            refreshTable();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION, "User  Added Unsuccessful :( !!!").show();

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        String id = txtId.getText();
        String userName = txtName.getText();
        String userPassword = txtPassword.getText();
        String role = (String) cmbType.getValue();

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(id);
        userDTO.setUserName(userName);
        userDTO.setPassword(userPassword);
        userDTO.setRole(role);

        boolean isUpdated = userBO.updateUser(userDTO);

        if (isUpdated){
            refreshTable();
            clearAll();
            new Alert(Alert.AlertType.CONFIRMATION, "User Updated successful").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "User Updated Unsuccessful").show();
        }
    }

    @FXML
    void tblUserOnMouseClicked(MouseEvent event) {
        UserTM selectItem = UserTable.getSelectionModel().getSelectedItem();

        if (selectItem != null){
            txtId.setText(selectItem.getUserId());
            txtName.setText(selectItem.getUserName());
            txtPassword.setText(selectItem.getPassword());
            cmbType.setValue(selectItem.getRole());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }else {
            btnUpdate.setDisable(true);
        }
    }

    private void clearAll(){
        txtId.clear();
        txtName.clear();
        txtPassword.clear();
        cmbType.setValue(null);
    }

}
