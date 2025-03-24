package uz.ccrew.trainerworkloadservice.dto.trainer_workload;

import lombok.*;
import uz.ccrew.trainerworkloadservice.enums.ActionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime trainingDate;
    private Double trainingDuration;
    private ActionType actionType;
}
