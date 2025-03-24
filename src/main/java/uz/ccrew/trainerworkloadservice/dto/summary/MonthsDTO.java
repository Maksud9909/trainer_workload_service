package uz.ccrew.trainerworkloadservice.dto.summary;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Month;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthsDTO {
    private Month month;
    private double totalDuration;
}
