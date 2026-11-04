package bhy880.workout_tracker.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bhy880.workout_tracker.domain.Workout;
import bhy880.workout_tracker.domain.WorkoutRepository;

// REST API: returns workouts as JSON instead of HTML pages
@RestController
@RequestMapping("/api")
public class WorkoutRestController {

    private final WorkoutRepository wRepository;

    public WorkoutRestController(WorkoutRepository wRepository) {
        this.wRepository = wRepository;
    }

    // GET /api/workouts → all workouts (public)
    @GetMapping("/workouts")
    public Iterable<Workout> getAllWorkouts() {
        return wRepository.findAll();
    }

    // GET /api/workouts/5 → one workout, or 404 if it doesn't exist (public)
    @GetMapping("/workouts/{id}")
    public Workout getWorkout(@PathVariable Long id) {
        return wRepository.findById(id)
                .orElseThrow();
    }

    // GET /api/myworkouts → the logged-in user's workouts (login required)
    @GetMapping("/myworkouts")
    public List<Workout> getMyWorkouts(Authentication auth) {
        return wRepository.findByOwnerUsername(auth.getName());
    }
}