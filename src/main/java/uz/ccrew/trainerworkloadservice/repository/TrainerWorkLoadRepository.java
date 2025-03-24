package uz.ccrew.trainerworkloadservice.repository;

import uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerMonthlySummaryProjection;
import uz.ccrew.trainerworkloadservice.entity.TrainerWorkload;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface TrainerWorkLoadRepository extends JpaRepository<TrainerWorkload, String> {

    @Query("""
       SELECT new uz.ccrew.trainerworkloadservice.dto.trainer_workload.TrainerMonthlySummaryProjection(
              t.trainerUsername,
              t.trainerFirstName,
              t.trainerLastName,
              t.isActive,
              YEAR(t.trainingDate),
              MONTH(t.trainingDate),
              SUM(t.trainingDuration))
       FROM TrainerWorkload t
       WHERE t.trainerUsername = :username
       GROUP BY t.trainerUsername, t.trainerFirstName, t.trainerLastName, t.isActive, YEAR(t.trainingDate), MONTH(t.trainingDate)
       ORDER BY YEAR(t.trainingDate), MONTH(t.trainingDate)
""")
    List<TrainerMonthlySummaryProjection> getMonthlySummary(@Param("username") String username);


    void deleteByTrainerUsernameAndTrainingDate(String trainerUsername, LocalDate trainingDate);
}