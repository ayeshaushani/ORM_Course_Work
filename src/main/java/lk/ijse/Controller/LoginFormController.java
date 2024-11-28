package lk.ijse.Controller;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.DTO.LoginDTO;
import lk.ijse.DTO.UserDTO;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.LoginBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.entity.UserSession;
import lk.ijse.util.config.FactoryConfiguration;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//import static java.awt.SystemColor.window;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private RadioButton btnRadio;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;
    String temporaryUserName = "admin";
    String temporaryPassword = "admin";


    LoginBO loginBO  = (LoginBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOGIN);


    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
    private Text txtPasswordHidden;
    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
       /* Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"))));
        stage.setTitle("DashBoard ");
        stage.centerOnScreen();
        stage.show();*/
        Session session = FactoryConfiguration.getInstance().getSession();

        String userName= txtUserName.getText();
        String password = txtPassword.getText();

        try {
            if (userName.equals(temporaryUserName)  && password.equals(temporaryPassword)) {
                /*methana krala thinne wena kenekge pc ekakata me system eka dammothi mulin DB ekk nathi
                  nisa tempory login details tikak dila manual user id ekakui role ekakui dapu eka*/

                // Store userId and role in Session singleton
                UserSession.getInstance().setUser("123", "tempory_user");

                navigateToTheDashboard((Stage) root.getScene().getWindow());

            } else {
                LoginDTO loginDTO = new LoginDTO(userName, password);

                boolean loginResult = loginBO.checkCredential(loginDTO);
                if (loginResult) {
                    navigateToTheDashboard((Stage) root.getScene().getWindow());

                } else {
                    // Show alert if credentials are incorrect
                    new Alert(Alert.AlertType.ERROR, "Invalid credentials! Please try again.").show();
                }
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private void navigateToTheDashboard(Stage window) throws IOException {
        // Load the dashboard
      /*  FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Parent customerRoot = customerLoader.load();
        root.getChildren().clear();
        root.getChildren().add(customerRoot);

        stage = (Stage) root.getScene().getWindow();
        stage.setTitle("Dashboard");*/
        try {
            // Dashboard Form එක load කරන්න
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
            AnchorPane dashboardPane = loader.load();

            // Dashboard එකට නව Scene එකක් assign කරන්න
            Scene dashboardScene = new Scene(dashboardPane);

            // මූලික Stage එකේ Scene එක වෙනස් කරන්න
            window.setScene(dashboardScene);

            // Stage එක මිනුම් වෙනස් කරන්න (Full Screen එකේ නෙරඹුමට ගැළපෙන ලෙස)
            window.centerOnScreen(); // Center Screen එකේ තබන්න
            window.setResizable(true); // විශාලත්වය වෙනස් කළ හැකි ලෙස සකසන්න
            window.show(); // නව Scene එක දැක්වීමේ වැඩි විශාලත්වයකින්
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the dashboard.").show();
        }
    }



    private UserDTO validateLogin(String username, String password) throws IOException {
        List<UserDTO> allUsers = userBO.getAllusers();

        for (UserDTO user : allUsers) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user; // Return the UserDTO if credentials are valid
            }
        }
        return null; // Return null if credentials are invalid
    }



    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {

    }

    public void btnRadioOnAction(ActionEvent actionEvent) {
    }
}
