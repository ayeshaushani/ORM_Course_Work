package lk.ijse.DTO.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RegistrationTM {

    private String regiId;
    private String sid;
    private String studentName;
   // private String courseid;
    private String courseName;
    private LocalDate date;
    private String duration;
    private double payment;
    private double dueAmount;
    private JFXButton delete;

    public String getRegisterId() {
        return regiId;
    }

}
