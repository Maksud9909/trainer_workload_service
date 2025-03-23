package uz.ccrew.trainerworkloadservice.dto.trainer_workload;

import lombok.*;
import uz.ccrew.trainerworkloadservice.enums.ActionType;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerWorkloadDTO {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean isActive;
    private LocalDate trainingDate;
    private Double trainingDuration;
    private ActionType actionType;
}
