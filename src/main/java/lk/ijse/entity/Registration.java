package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Registration {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String regiId;

    private LocalDate enrollmentDate;

    private Double Payment;

    private Double DueAmount;

    private String studentName;

    private String ProgramName;

    private String duration;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Programme programme;


   /* public Registration(String regiId, LocalDate enrollmentDate, Double payment, Double dueAmount, String studentName, String programName, String duration, Student student, Programme programme) {
        this.regiId = regiId;
        this.enrollmentDate = enrollmentDate;
        Payment = payment;
        DueAmount = dueAmount;
        this.studentName = studentName;
        ProgramName = programName;
        this.duration = duration;
        this.student = student;
        this.programme = programme;
    }*/

    @Override
    public String toString() {
        return this.regiId; //

    }

}
