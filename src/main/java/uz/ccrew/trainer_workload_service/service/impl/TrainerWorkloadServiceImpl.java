package uz.ccrew.trainer_workload_service.service.impl;

import uz.ccrew.trainer_workload_service.entity.TrainerWorkload;
import uz.ccrew.trainer_workload_service.exp.ObjectNotProvidedException;
import uz.ccrew.trainer_workload_service.service.TrainerWorkloadService;
import uz.ccrew.trainer_workload_service.repository.TrainerWorkLoadRepository;
import uz.ccrew.trainer_workload_service.dto.trainer_workload.TrainerWorkloadDTO;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerWorkloadServiceImpl implements TrainerWorkloadService {
    private final TrainerWorkLoadRepository trainerWorkLoadRepository;

    @Override
    @Transactional
    public void processTraining(TrainerWorkloadDTO dto) {
        if (dto.getActionType() == null) {
            throw new ObjectNotProvidedException("ActionType cannot be null");
        }

        switch (dto.getActionType()) {
            case ADD -> {
                TrainerWorkload newWorkload = TrainerWorkload.builder()
                        .trainerUsername(dto.getTrainerUsername())
                        .trainerLastName(dto.getTrainerLastName())
                        .trainerFirstName(dto.getTrainerFirstName())
                        .trainingDuration(dto.getTrainingDuration())
                        .trainingDate(dto.getTrainingDate())
                        .isActive(dto.isActive())
                        .build();
                trainerWorkLoadRepository.save(newWorkload);
                log.info("Added new trainer workload for {} on {}", dto.getTrainerUsername(), dto.getTrainingDate());
            }

            case DELETE -> {
                trainerWorkLoadRepository.deleteByTrainerUsernameAndTrainingDate(dto.getTrainerUsername(), dto.getTrainingDate());
                log.info("Deleted the trainer workload for {} on {}", dto.getTrainerUsername(), dto.getTrainingDate());
            }
        }
    }
}
