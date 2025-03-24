package uz.ccrew.trainerworkloadservice.dto.trainer_workload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainerMonthlySummaryProjection {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean isActive;
    private int year;
    private int month;
    private double totalDuration;
}
