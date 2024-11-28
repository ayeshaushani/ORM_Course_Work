package lk.ijse.DTO.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgrammeTM {

    private String programmeId;
    private String programmeName;
    private String duration;
    private double fee;
}
