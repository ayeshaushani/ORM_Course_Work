package lk.ijse.DTO.tm;

import lk.ijse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentTM {
    private String student_id;
    private String name;
    private String address;
    private String contact;
    private User useId;
    private LocalDate date;


}
