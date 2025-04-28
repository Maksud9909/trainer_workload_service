package uz.ccrew.dto.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"trainerUsername", "trainerFirstName", "trainerLastName", "isActive", "years"})
@EqualsAndHashCode
public class TrainerMonthlySummaryDTO implements Serializable {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    @JsonProperty("isActive")
    private boolean isActive;
    List<YearsDTO> years;
}
