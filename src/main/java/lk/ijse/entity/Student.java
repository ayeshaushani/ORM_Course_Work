package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student {

    @Id
    private String student_id;  // Consider renaming to "studentId" for Java naming convention

    private String name;
    private String address;
    private String contact;
    private LocalDate date;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    public Student(String studentId, String name, String address, String contact, LocalDate date, User userId) {
    }
}
