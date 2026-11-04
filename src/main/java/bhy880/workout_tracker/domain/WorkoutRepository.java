package bhy880.workout_tracker.domain;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface WorkoutRepository extends CrudRepository<Workout,Long>  {
 
    List<Workout> findByOwnerUsername(String username);
    Optional<Workout> findByIdAndOwnerUsername(Long id, String username);
    

}
