package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.DTO.ProgrammeDTO;
import lk.ijse.DTO.tm.ProgrammeTM;
import lk.ijse.DTO.tm.StudentTM;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgrammeBO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProgrammeFormController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colProgrammeName;

    @FXML
    private AnchorPane rootStudent;

    @FXML
    private TableView<ProgrammeTM> tblProgramme;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtPId;

    @FXML
    private TextField txtProgrammeName;

    ProgrammeBO programmeBO = (ProgrammeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROGRAMME);
    ObservableList<ProgrammeTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setProgrammeID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            getAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
    }

    private void setProgrammeID() throws IOException {
        String programmeId = programmeBO.generateNewProgrammeID();

        if (programmeId == null) {
            txtPId.setText("PR000001");
        } else {
            String[] split = programmeId.split("[P][R]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("PR%06d", lastDigits);
            txtPId.setText(newID);
        }
    }

    private void refreshTable() throws IOException {
        obList.clear();
        getAll();
        setProgrammeID();
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("programmeId"));
        colProgrammeName.setCellValueFactory(new PropertyValueFactory<>("programmeName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void getAll() throws IOException {
        List<ProgrammeDTO> allPrograms = programmeBO.getAllPrograms();

        if (!(allPrograms.isEmpty())) {
            for (ProgrammeDTO programmeDTO : allPrograms) {
                obList.add(new ProgrammeTM(programmeDTO.getProgrammeId(),
                        programmeDTO.getProgrammeName(), programmeDTO.getDuration(),
                        programmeDTO.getFee()));

                tblProgramme.setItems(obList);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Empty Programmes :( !!!").show();
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {
        ProgrammeTM selectedItem = tblProgramme.getSelectionModel().getSelectedItem();

        if (selectedItem!= null) {
            programmeBO.deleteProgram(selectedItem.getProgrammeId());
            new Alert(Alert.AlertType.INFORMATION, "Programme Deleted").show();
            refreshTable();
            clearAll();
        } else {
            new Alert(Alert.AlertType.ERROR, "Please Select a Programme to Delete!").show();
        }
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String programmeId  = txtPId.getText();
        String programmeName = txtProgrammeName.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtFee.getText());

        ProgrammeDTO programmeDTO = new ProgrammeDTO();
        programmeDTO.setProgrammeId(programmeId);
        programmeDTO.setProgrammeName(programmeName);
        programmeDTO.setDuration(duration);
        programmeDTO.setFee(fee);

        boolean isAdded = programmeBO.addProgramme(programmeDTO);

        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Programme Added Successful :)!!!").show();
            refreshTable();
            clearAll();
        }else {
            new Alert(Alert.AlertType.ERROR, "programme not Added :( !!!").show();

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        String programmeId  = txtPId.getText();
        String programmeName = txtProgrammeName.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtFee.getText());

        ProgrammeDTO programmeDTO = new ProgrammeDTO();
        programmeDTO.setProgrammeId(programmeId);
        programmeDTO.setProgrammeName(programmeName);
        programmeDTO.setDuration(duration);
        programmeDTO.setFee(fee);

        boolean isUpdated = programmeBO.updateProgram(programmeDTO);

        if (isUpdated) {

            btnSave.setDisable(false);
            new Alert(Alert.AlertType.CONFIRMATION, "Programme Update Successful :)!!!").show();
            refreshTable();
            clearAll();
            clearAll();
        }else {
            new Alert(Alert.AlertType.ERROR, "Student not Updated :( !!!").show();
        }
    }

    @FXML
    void tblStudentOnMouseClicked(MouseEvent event) {
        ProgrammeTM selectedItem = tblProgramme.getSelectionModel().getSelectedItem();

        if (selectedItem!= null) {
            txtPId.setText(selectedItem.getProgrammeId());
            txtProgrammeName.setText(selectedItem.getProgrammeName());
            txtDuration.setText(selectedItem.getDuration());
            txtFee.setText(String.valueOf(selectedItem.getFee()));

        }else {
            btnUpdate.setDisable(true);
        }
    }

    private void clearAll(){
        txtPId.clear();
        txtProgrammeName.clear();
        txtDuration.clear();
        txtFee.clear();
    }
}
