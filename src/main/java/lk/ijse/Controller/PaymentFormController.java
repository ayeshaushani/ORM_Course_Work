package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.DTO.PaymentDto;
import lk.ijse.DTO.tm.RegistrationTM;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.entity.Registration;
import lk.ijse.DTO.tm.PayementTm;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

public class PaymentFormController {
    public JFXButton btnPay;
    public Text studentid111;
    public Text studentid12;
    public Text studentid2;
    public Text studentid11;
    public Text studentid1;
    public Text studentid;
    public Text RegistationNumbertxt;
    public AnchorPane rootStudent;
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);

    @FXML
    private ComboBox<Registration> cmb_Registr_ID;

    @FXML
    private TableColumn<?, ?> col_Amount;

    @FXML
    private TableColumn<?, ?> col_FullCourse_fee;

    @FXML
    private TableColumn<Double, PayementTm> col_balance;

    @FXML
    private TableColumn<Double, PayementTm> col_paid_Amount;

    @FXML
    private TableColumn<String, PayementTm> col_payment_ID;

    @FXML
    private TableColumn<String, RegistrationTM > col_registr_ID;

    @FXML
    private AnchorPane registrPane;

    @FXML
    private TableView<PayementTm> tbl_registr;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtPaidAmount;

    @FXML
    private TextField txt_Full_Course_Fee;

    @FXML
    private TextField txt_payment_ID;

    @FXML
    private TableColumn<?, ?> col_pay;

    @Setter
    private AnchorPane centerNode;

    @FXML
    private TextField txtCash;

    @FXML
    void btn_Payment_onAction(ActionEvent event) throws IOException {
        String paymentId = txt_payment_ID.getText();
        double paidAmount = Double.parseDouble(txtPaidAmount.getText());
        double fullPayment = Double.parseDouble(txt_Full_Course_Fee.getText());
        double pay = Double.parseDouble(txtCash.getText());
        double amount = fullPayment - paidAmount;
        double balance = pay - amount;

        Registration selectedRegistr = this.cmb_Registr_ID.getSelectionModel().getSelectedItem();
        System.out.println(selectedRegistr);
        if (pay <= amount) {
            boolean isSaved = paymentBO.save(new PaymentDto(paymentId, amount, paidAmount, fullPayment, pay, balance, selectedRegistr));
            if (isSaved) {
                setCellValueFactory();
                clearValue();
                new Alert(Alert.AlertType.CONFIRMATION, "Payment successfully").show();
            } else {
                clearValue();
                new Alert(Alert.AlertType.ERROR, "Payment unsuccessfully").show();
            }
        } else {
            clearValue();
            new Alert(Alert.AlertType.ERROR, "Invalid payment, please try again").show();
        }
    }

    @FXML
    void btn_clear_onAction(ActionEvent event) {
        clearValue();
    }

    private void clearValue() {
        txtAmount.clear();
        txtPaidAmount.clear();
        txtCash.clear();
        txt_Full_Course_Fee.clear();
        txt_payment_ID.clear();
        cmb_Registr_ID.getSelectionModel().clearSelection();
    }

    public void initialize() throws IOException {
        setComboRegistr();
        try {
            setTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        generatePaymentId();
    }
   private void generatePaymentId() throws IOException {
       String paymentID = paymentBO.getCurrentId();

       if (paymentID == null) {
           txt_payment_ID.setText("P000001");
       } else {
           // Check if the studentID starts with "ST"
           if (paymentID.startsWith("P")) {
               // Extract the numeric part after "ST"
               String numericPart = paymentID.substring(2);

               try {
                   int lastDigits = Integer.parseInt(numericPart);
                   lastDigits++;
                   String newID = String.format("P%06d", lastDigits);
                   txt_payment_ID.setText(newID);
               } catch (NumberFormatException e) {
                   // Handle case if the numeric part is not a valid number
                   txt_payment_ID.setText("P000001");
               }
           } else {
               // In case the studentID does not follow the expected format
               txt_payment_ID.setText("P000001");
           }
       }
    }

  //  private String generatePaymentId() {
      /*  try {
            String currentId = paymentBO.getCurrentId();
            if (currentId != null) {
                String[] split = currentId.split("Pay00");
                int idNum = Integer.parseInt(split[1]);
                String availableId = "P00" + ++idNum;
                txt_payment_ID.setText(availableId);
                return availableId;
            } else {
                txt_payment_ID.setText("P001");
                return "P001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
  //  }

    private void setCellValueFactory() {
        col_payment_ID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        col_registr_ID.setCellValueFactory(new PropertyValueFactory<>("registration_regiId"));
        col_Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_paid_Amount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        col_FullCourse_fee.setCellValueFactory(new PropertyValueFactory<>("fullPayment"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        col_pay.setCellValueFactory(new PropertyValueFactory<>("pay"));

    }

    private void setTable() throws IOException {
        ObservableList<PayementTm> payementTms = FXCollections.observableArrayList();
        List<PaymentDto> all = paymentBO.getAll();
        for (PaymentDto paymentDto : all) {
            PayementTm payementTm = new PayementTm(paymentDto.getPaymentId(), paymentDto.getAmount(), paymentDto.getPaidAmount(), paymentDto.getFullPayment(), paymentDto.getPay(), paymentDto.getBalance(), paymentDto.getRegistration().getRegiId());
            payementTms.add(payementTm);
        }
        tbl_registr.setItems(payementTms);
    }


    private void setComboRegistr() {
        List<Registration> registrationIds = paymentBO.getRegistrationIds();
        System.out.println("Registrations: " + registrationIds); // Debugging print

        cmb_Registr_ID.getItems().addAll(registrationIds);

        cmb_Registr_ID.setOnAction(event -> {
            Registration selectedRegistrationId = cmb_Registr_ID.getValue();
            System.out.println("Selected Registration: " + selectedRegistrationId); // Debugging print

            if (selectedRegistrationId != null) {
                double paidAmount = paymentBO.getPaidAmountByRegistrationId(selectedRegistrationId);
                txtPaidAmount.setText(String.valueOf(paidAmount));

                double fullFee = paymentBO.getFullFeeRegistrationId(selectedRegistrationId);
                txt_Full_Course_Fee.setText(String.valueOf(fullFee));

                double amount = paymentBO.getAmounteRegistrationId(selectedRegistrationId);
                txtAmount.setText(String.valueOf(amount));
            }
        });
    }


}
