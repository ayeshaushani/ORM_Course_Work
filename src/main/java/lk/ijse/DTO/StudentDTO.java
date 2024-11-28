package lk.ijse.DTO;

import lk.ijse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {
    private String student_id;
    private String name;
    private String address;
    private String contact;
    private User userId;
    private LocalDate date;



    public StudentDTO(String studentId, String name, String address, String contact, LocalDate date, User user) {
        this.student_id = studentId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.date = date;
        this.userId = user;
    }
}
