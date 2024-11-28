package lk.ijse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgrammeDTO {

    private String programmeId;
    private String programmeName;
    private String duration;
    private double fee;
}
