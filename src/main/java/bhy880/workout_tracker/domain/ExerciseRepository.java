package bhy880.workout_tracker.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ExerciseRepository extends CrudRepository<Exercise,Long>{

    Optional<Exercise> findByIdAndWorkoutOwnerUsername(Long id, String username);
}
