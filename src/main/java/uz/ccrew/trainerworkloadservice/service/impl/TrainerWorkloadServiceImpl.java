package uz.ccrew.trainerworkloadservice.service.impl;

import uz.ccrew.trainerworkloadservice.enums.ActionType;
import uz.ccrew.trainerworkloadservice.dto.summary.YearsDTO;
import uz.ccrew.trainerworkloadservice.dto.summary.MonthsDTO;
import uz.ccrew.trainerworkloadservice.entity.TrainerWorkload;
import uz.ccrew.trainerworkloadservice.exp.ObjectNotProvidedException;
import uz.ccrew.trainerworkloadservice.service.TrainerWorkloadService;
import uz.ccrew.trainerworkloadservice.repository.TrainerWorkLoadRepository;
import uz.ccrew.trainerworkloadservice.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerWorkloadDTO;
import uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerMonthlySummaryProjection;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.Month;

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

        if (dto.getActionType() == ActionType.ADD) {
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
        } else if (dto.getActionType() == ActionType.DELETE) {
            trainerWorkLoadRepository.deleteByTrainerUsernameAndTrainingDate(dto.getTrainerUsername(), dto.getTrainingDate());
            log.info("Deleted the trainer workload for {} on {}", dto.getTrainerUsername(), dto.getTrainingDate());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerMonthlySummaryDTO> getMonthlyWorkload(String username) {
        List<TrainerMonthlySummaryProjection> workload = trainerWorkLoadRepository.getMonthlySummary(username);
        if (workload.isEmpty()) {
            return List.of();
        }
        Map<Integer, List<MonthsDTO>> yearToMonthsMap = new LinkedHashMap<>();
        TrainerMonthlySummaryProjection firstEntry = workload.getFirst();

        for (TrainerMonthlySummaryProjection projection : workload) {
            MonthsDTO monthsDTO = MonthsDTO.builder()
                    .month(Month.of(projection.getMonth()))
                    .totalDuration(projection.getTotalDuration())
                    .build();

            yearToMonthsMap
                    .computeIfAbsent(projection.getYear(), y -> new ArrayList<>())
                    .add(monthsDTO);
        }

        List<YearsDTO> yearsList = yearToMonthsMap.entrySet().stream()
                .map(entry -> YearsDTO.builder()
                        .year(entry.getKey())
                        .months(entry.getValue())
                        .build())
                .toList();

        TrainerMonthlySummaryDTO summary = TrainerMonthlySummaryDTO.builder()
                .trainerUsername(firstEntry.getTrainerUsername())
                .trainerFirstName(firstEntry.getTrainerUsername())
                .trainerLastName(firstEntry.getTrainerLastName())
                .isActive(firstEntry.isActive())
                .years(yearsList)
                .build();

        return List.of(summary);
    }
}
