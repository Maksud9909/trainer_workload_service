package uz.ccrew.trainerworkloadservice.service.impl;

import uz.ccrew.trainerworkloadservice.dto.summary.YearsDTO;
import uz.ccrew.trainerworkloadservice.dto.summary.MonthsDTO;
import uz.ccrew.trainerworkloadservice.entity.TrainerWorkload;
import uz.ccrew.trainerworkloadservice.exp.ObjectNotProvidedException;
import uz.ccrew.trainerworkloadservice.service.TrainerWorkloadService;
import uz.ccrew.trainerworkloadservice.repository.TrainerWorkLoadRepository;
import uz.ccrew.trainerworkloadservice.dto.summary.TrainerMonthlySummaryDTO;
import uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerWorkloadDTO;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

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

    @Override
    public List<TrainerMonthlySummaryDTO> getMonthlyWorkload(String username) {
        List<Object[]> rawData = trainerWorkLoadRepository.getMonthlySummary(username);
        if (rawData.isEmpty()) {
            return List.of();
        }

        Map<Integer, List<MonthsDTO>> yearToMonthsMap = new LinkedHashMap<>();
        String trainerUsername = null;
        String trainerFirstName = null;
        String trainerLastName = null;
        boolean isActive = false;

        for (Object[] row : rawData) {
            trainerUsername = (String) row[0];
            trainerFirstName = (String) row[1];
            trainerLastName = (String) row[2];
            isActive = (boolean) row[3];

            int year = ((Number) row[4]).intValue();
            int month = ((Number) row[5]).intValue();
            String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            double totalDuration = ((Number) row[6]).doubleValue();

            MonthsDTO monthsDTO = MonthsDTO.builder()
                    .month(monthName)
                    .totalDuration(totalDuration)
                    .build();

            yearToMonthsMap
                    .computeIfAbsent(year, y -> new ArrayList<>())
                    .add(monthsDTO);
        }

        List<YearsDTO> yearsList = (List<YearsDTO>) yearToMonthsMap.entrySet().stream()
                .map(entry -> YearsDTO.builder()
                        .year(entry.getKey())
                        .months(entry.getValue())
                        .build())
                .toList();

        TrainerMonthlySummaryDTO summary = TrainerMonthlySummaryDTO.builder()
                .trainerUsername(trainerUsername)
                .trainerFirstName(trainerFirstName)
                .trainerLastName(trainerLastName)
                .isActive(isActive)
                .years(yearsList)
                .build();
        return List.of(summary);
    }
}
