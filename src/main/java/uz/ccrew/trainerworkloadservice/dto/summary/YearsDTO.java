package uz.ccrew.trainerworkloadservice.dto.summary;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearsDTO {
    private int year;
    List<MonthsDTO> months;
}
