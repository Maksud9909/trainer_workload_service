package uz.ccrew.trainer_workload_service.service;

import uz.ccrew.trainer_workload_service.dto.trainer_workload.TrainerWorkloadDTO;

public interface TrainerWorkloadService {
    void processTraining(TrainerWorkloadDTO dto);
}
