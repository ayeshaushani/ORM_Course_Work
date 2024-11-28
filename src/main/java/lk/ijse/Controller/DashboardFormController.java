package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.entity.UserSession;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    public JFXButton btnStudent;
    public JFXButton btnUser;
    public JFXButton btnRegister;
    public JFXButton btnPayment;
    public JFXButton btnProgramm;
    public JFXButton btnDash;
    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane mainroot;

    @FXML
    private AnchorPane root;
    String role = UserSession.getInstance().getRole();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setTime();
        checkLoggedUser();
      //  TimeDate.localDateAndTime(lblDate,lblTime);
    }
    void checkLoggedUser() {
        if (role.equals("admissions coordinator")){
            btnUser.setVisible(false);
            btnProgramm.setVisible(false);
        }
        if (role.equals("tempory_user")){
            btnProgramm.setVisible(false);
            btnDash.setVisible(false);
            btnPayment.setVisible(false);
            btnRegister.setVisible(false);
            btnStudent.setVisible(false);
        }
    }
    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)mainroot .getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"))));
        stage.setTitle("DashBoard ");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));
            loadWindow(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnProgrammeOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/Programme_form.fxml"));
            loadWindow(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/student_form.fxml"));
            loadWindow(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUserOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/user_form.fxml"));
            loadWindow(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void btnRegisterOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/Register_form.fxml"));
            loadWindow(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnDashboardOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
            loadWindow(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }
    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
    private void loadWindow(AnchorPane anchorPane) {
        root.getChildren().clear();
        root.getChildren().add(anchorPane);
    }
}