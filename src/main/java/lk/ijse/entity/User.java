package lk.ijse.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {

    @Id
    private String userId;
    private String password;
    private String userName;
    private String role;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Student> students;

    @Override
    public String toString() {
        return this.userId; //
    }

}
