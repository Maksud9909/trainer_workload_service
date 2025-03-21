package uz.ccrew.trainer_workload_service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ccrew.trainer_workload_service.entity.TrainerWorkload;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TrainerWorkLoadRepository extends JpaRepository<TrainerWorkload, String> {

    @Query("SELECT t FROM TrainerWorkload t WHERE t.trainerUsername = :username AND t.trainingDate = :date ORDER BY t.id DESC")
    Optional<TrainerWorkload> findLatestByTrainerUsernameAndTrainingDate(@Param("username") String username, @Param("date") LocalDate date);
}