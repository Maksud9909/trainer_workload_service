package uz.ccrew.trainerworkloadservice.service;

import uz.ccrew.trainerworkloadservice.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerWorkloadDTO;

import java.util.List;

public interface TrainerWorkloadService {
    void processTraining(TrainerWorkloadDTO dto);

    List<TrainerMonthlySummaryDTO> getMonthlyWorkload(String username);
}
