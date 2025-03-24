package uz.ccrew.trainerworkloadservice.dto.summary;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerMonthlySummaryDTO {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean isActive;
    List<YearsDTO> years;
}
