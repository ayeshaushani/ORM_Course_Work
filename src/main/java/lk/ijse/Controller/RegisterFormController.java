package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.DTO.RegistrationDto;
import lk.ijse.DTO.tm.RegistrationTM;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.entity.Programme;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterFormController implements Initializable {


    public TextField txtId;
    public TableColumn<String, RegistrationTM> colRegId;
    @FXML
    private Text Amountduetxt;

    @FXML
    private Text CourseDuration;

    @FXML
    private TextField Paymenttxt;

    @FXML
    private ComboBox<String> StudentIDComboBox;

    @FXML
    private ComboBox<String> StudentIDComboCourseComboBox;

    @FXML
    private TableView<RegistrationTM> StudentTable;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<Double, RegistrationTM> colPayment;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<Double, RegistrationTM> coldueAmonut;

    @FXML
    private TableColumn<String, RegistrationTM> colduration;

    @FXML
    private TableColumn<String, RegistrationTM> colsid;

    @FXML
    private TableColumn<String, RegistrationTM> colsname;

    @FXML
    private Text courseName;

    @FXML
    private Text courseid;

    @FXML
    private DatePicker datecombo;

    @FXML
    private TableColumn<RegistrationTM, JFXButton> deleteBtn;

    @FXML
    private Text fee;

    @FXML
    private Text payment;

    @FXML
    private Text payment1;

    @FXML
    private AnchorPane rootStudent;

    @FXML
    private Text studentMobile;

    @FXML
    private Text studentName;

    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.REGISTRATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setregisterID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getProgramID();
        getStudentIds();
        try {
            loadallvalues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setValues();
    }

    private void setregisterID() throws IOException {
        String registerId = registrationBO.generateNewRegistrationID();

        if (registerId == null) {
            txtId.setText("RG000001");
        } else {
            String[] split = registerId.split("[R][G]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newID = String.format("RG%06d", lastDigits);
            txtId.setText(newID);
        }
    }

    private void setValues() {
        colRegId.setCellValueFactory(new PropertyValueFactory<>("regiId"));
        colsid.setCellValueFactory(new PropertyValueFactory<>("sid"));
        colsname.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colduration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        coldueAmonut.setCellValueFactory(new PropertyValueFactory<>("dueAmount"));
        deleteBtn.setCellValueFactory(new PropertyValueFactory<RegistrationTM, JFXButton>("Delete"));

    }

    private void loadallvalues() throws IOException {
        List<Registration> alldetails = registrationBO.getAllRegistrationDetails();

        ObservableList<RegistrationTM> observableList = FXCollections.observableArrayList();

        for (Registration registration : alldetails) {
            RegistrationTM registrationTM = new RegistrationTM(
                    registration.getRegiId(),  // Set the registerId
                    registration.getStudent().getStudent_id(),
                    registration.getStudentName(),
                    registration.getProgramme().getProgrammeName(),
                    registration.getEnrollmentDate(),
                    registration.getDuration(),
                    registration.getPayment(),
                    registration.getDueAmount(),
                    new JFXButton("delete")
            );

            observableList.add(registrationTM);
        }

        StudentTable.setItems(observableList);
    }

    private void getStudentIds() {
        try {
            List<Student> allstu = registrationBO.getAllStudent();
            for (Student s : allstu) {

                boolean b = StudentIDComboBox.getItems().add(s.getStudent_id());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getProgramID() {
        try {
            List<Programme> allprogramID = registrationBO.getAllCourse();
            for (Programme c : allprogramID) {

                boolean b = StudentIDComboCourseComboBox.getItems().add(c.getProgrammeId());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void RegisterComfirmOnAction(ActionEvent event) {
        try {
            String regiId = txtId.getText();
            String studentId = StudentIDComboBox.getValue();
            String courseId = StudentIDComboCourseComboBox.getValue();
            String studentFName = studentName.getText();
            String courseFullName = courseName.getText();
            String courseDuration = CourseDuration.getText();
            double payment = Double.parseDouble(Paymenttxt.getText());
            double totalFee = Double.parseDouble(fee.getText());
            double dueAmount = totalFee - payment; //due Amount

            Amountduetxt.setText(String.valueOf(dueAmount));

            LocalDate date = datecombo.getValue();

            // Load  Student and Course entities from the database
            Student student = registrationBO.serachbyIDS(studentId);
            Programme course = registrationBO.serachbyCIDs(courseId);

            if (student == null) {
                new Alert(Alert.AlertType.ERROR, "Student not found!").show();
                return;
            }
            if (course == null) {
                new Alert(Alert.AlertType.ERROR, "Course not found!").show();
                return;
            }


            Registration registration = new Registration(
                    regiId, date, payment, dueAmount, studentFName, courseFullName, courseDuration, student, course
            );

            //save
            boolean isSaved = registrationBO.saveRegistration(registration);

            if (!isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Registration saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save registration.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearOnActionRegistaion(ActionEvent event) {

    }

    @FXML
    void comboCourseList(ActionEvent event) {
        String cid = StudentIDComboCourseComboBox.getValue();
        try{
            Programme course = registrationBO.serachbyCIDs(cid);
            courseName.setText(course.getProgrammeName());
            fee.setText(String.valueOf(course.getFee()));
            CourseDuration.setText(course.getDuration());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comboStudetList(ActionEvent event) {
        String sid = StudentIDComboBox.getValue();
        try{
            Student student = registrationBO.serachbyIDS(sid);
            studentName.setText(student.getName());
            System.out.printf(student.getName());
            studentMobile.setText(student.getContact());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
