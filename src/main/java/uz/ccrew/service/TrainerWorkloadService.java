package uz.ccrew.service;

import uz.ccrew.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.dto.training.TrainerWorkloadDTO;

import java.util.List;

public interface TrainerWorkloadService {
    void processTraining(TrainerWorkloadDTO dto);

    List<TrainerMonthlySummaryDTO> getMonthlyWorkload(String username);
}
