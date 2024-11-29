package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.DTO.StudentDTO;
import lk.ijse.DTO.tm.StudentTM;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.entity.User;
import lk.ijse.util.regex.RegExFactory;
import lk.ijse.util.regex.RegExType;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {

    public JFXButton btnRegister;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private DatePicker cmbDob;

    @FXML
    private ComboBox<User> cmb_Codinator_ID;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCoordinatorId;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private AnchorPane rootStudent;

    @FXML
    private TableView<StudentTM> tblStudents;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    ObservableList<StudentTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setStudentID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            getAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        setComboUser();
       // tblStudentOnMouseClicked();
    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCoordinatorId.setCellValueFactory(new PropertyValueFactory<>("useId"));

    }

    private void getAll() throws IOException {
        // Clear the ObservableList to prevent duplicate entries.
        obList.clear();

        List<StudentDTO> allStudents = studentBO.getAllStudents();
        if (!allStudents.isEmpty()) {
            for (StudentDTO studentDTO : allStudents) {
                obList.add(new StudentTM(
                        studentDTO.getStudent_id(),
                        studentDTO.getName(),
                        studentDTO.getAddress(),
                        studentDTO.getContact(),
                        studentDTO.getUserId(),
                        studentDTO.getDate()
                ));
            }
            tblStudents.setItems(obList);
        } else {
            new Alert(Alert.AlertType.ERROR, "No students found.").show();
        }

        // Refresh the table view to ensure it's displaying the latest data.
        tblStudents.refresh();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {

        StudentTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();

        try {
            if (selectedItem != null) {
                studentBO.deleteStudent(selectedItem.getStudent_id());
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted").show();
                getAll();
                clearAll();

            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student first!").show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
    }

    private void setComboUser() {
        List<User> users = studentBO.getUserIds(); // Adjust this method to return List<User>
        cmb_Codinator_ID.getItems().addAll(users);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        User selectedCoordinator = (User) this.cmb_Codinator_ID.getSelectionModel().getSelectedItem();

        if (validity()){
            if (checkRegex()) {
                boolean isSaved = studentBO.addStudent(new StudentDTO(txtId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(), selectedCoordinator, cmbDob.getValue()));
                if (isSaved) {
                    setComboUser();
                    new Alert(Alert.AlertType.CONFIRMATION, "Student save successfully....!!! :)").show();
                    getAll();
                    clearAll();


                    // Set the popup window to be modal (block input to other windows)
                    //  popupStage.initModality(Modality.APPLICATION_MODAL);

                    // Center the popup on the screen
                    //   popupStage.centerOnScreen();

                    // Show the popup
                    //   popupStage.showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student save unsuccessfully....!!! :(").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid types !!!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields :( !!!").show();
        }
    }

    private void setStudentID() throws IOException {
        String studentID = studentBO.generateNewStudentID();

        if (studentID == null) {
            txtId.setText("ST000001");
        } else {
            // Check if the studentID starts with "ST"
            if (studentID.startsWith("ST")) {
                // Extract the numeric part after "ST"
                String numericPart = studentID.substring(2);

                try {
                    int lastDigits = Integer.parseInt(numericPart);
                    lastDigits++;
                    String newID = String.format("ST%06d", lastDigits);
                    txtId.setText(newID);
                } catch (NumberFormatException e) {
                    // Handle case if the numeric part is not a valid number
                    txtId.setText("ST000001");
                }
            } else {
                // In case the studentID does not follow the expected format
                txtId.setText("ST000001");
            }
        }
    }

    private void clearAll() {
        txtContact.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
        txtId.clear();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        User selectedCoordinator = (User) this.cmb_Codinator_ID.getSelectionModel().getSelectedItem();

        boolean isUpdate = studentBO.updateStudent(new StudentDTO(txtId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(), selectedCoordinator, cmbDob.getValue()));
        if(isUpdate){
            getAll();
            setCellValueFactory();
            tblStudents.refresh();
            clearAll();
            setComboUser();
            new Alert(Alert.AlertType.CONFIRMATION,"Student update successfully....!!! :)").show();

        }else {
            new Alert(Alert.AlertType.ERROR,"Student update unsuccessfully....!!! :(").show();
        }
    }

    @FXML
    void cmb_Codinator_OnAction(ActionEvent event) {
        cmb_Codinator_ID.getValue();
        cmb_Codinator_ID.requestFocus();
    }

    @FXML
    void tblStudentOnMouseClicked(MouseEvent event) {
        StudentTM selectedItem = tblStudents.getSelectionModel().getSelectedItem();
        System.out.println("errorrrrr");
        try {
            if (selectedItem != null) {
              //  btnUpdate.setDisable(false);
               // btnDelete.setDisable(false);
              //  btnSave.setDisable(true);
                txtId.setText(selectedItem.getStudent_id());
                txtName.setText(selectedItem.getName());
                txtAddress.setText(selectedItem.getAddress());
                cmb_Codinator_ID.setValue(selectedItem.getUseId());
                txtContact.setText(selectedItem.getContact());
                cmbDob.setValue(selectedItem.getDate());
            } else {
                btnUpdate.setDisable(true);
            }
        } catch (RuntimeException e) {
          //  new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println(e);
        }
    }


    public void btnRegisterOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Register_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage popupStage = new Stage();
        popupStage.setScene(scene);
        popupStage.setTitle("Register Form");
    }

    private boolean checkRegex() {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtName.getText()).matches() &&
                RegExFactory.getInstance().getPattern(RegExType.CITY).matcher(txtAddress.getText()).matches() && cmb_Codinator_ID.getValue() != null &&
                RegExFactory.getInstance().getPattern(RegExType.MOBILE).matcher(txtContact.getText()).matches() && cmbDob.getValue() != null ;
    }

    private boolean validity() {

        return !txtId.getText().isEmpty() &&
                !txtName.getText().isEmpty() &&
                !txtAddress.getText().isEmpty() &&
                cmb_Codinator_ID.getValue() != null &&
                !txtContact.getText().isEmpty() &&
                cmbDob.getValue() != null;

    }
}
