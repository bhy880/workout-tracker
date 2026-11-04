package bhy880.workout_tracker.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import bhy880.workout_tracker.domain.AppUser;
import bhy880.workout_tracker.domain.AppUserRepository;
import bhy880.workout_tracker.domain.Workout;
import bhy880.workout_tracker.domain.WorkoutRepository;
import jakarta.validation.Valid;

@Controller
public class WorkoutController {

    private final WorkoutRepository wRepository;
    private final AppUserRepository userRepository;

    public WorkoutController(WorkoutRepository wRepository, AppUserRepository userRepository) {
        this.wRepository = wRepository;
        this.userRepository = userRepository;
    }

    // Finds a workout only if it belongs to the logged-in user, otherwise 404
    private Workout findOwnWorkout(Long id, Authentication auth) {
        return wRepository.findByIdAndOwnerUsername(id, auth.getName())
                .orElseThrow();
    }

    // Public list of everyone's workouts (read-only)
    @GetMapping("/workouts")
    public String showWorkouts(Model model) {
        model.addAttribute("workouts", wRepository.findAll());
        model.addAttribute("mine", false);
        return "workouts";
    }

    // Logged-in user's own workouts
    @GetMapping("/myworkouts")
    public String myWorkouts(Model model, Authentication auth) {
        model.addAttribute("workouts", wRepository.findByOwnerUsername(auth.getName()));
        model.addAttribute("mine", true);
        return "workouts";
    }

    @GetMapping("/addworkout")
    public String addWorkout(Model model) {
        model.addAttribute("workout", new Workout());
        return "workoutform";
    }

    @PostMapping("/addworkout")
    public String saveWorkout(@Valid Workout workout, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return "workoutform";
        }
        AppUser user = userRepository.findByUsername(auth.getName()).orElseThrow();
        workout.setOwner(user);                 // creator becomes the owner
        wRepository.save(workout);
        return "redirect:/myworkouts";
    }

    @GetMapping("/editworkout/{id}")
    public String editWorkoutForm(@PathVariable Long id, Model model, Authentication auth) {
        model.addAttribute("workout", findOwnWorkout(id, auth));
        return "workoutform";
    }

    @PostMapping("/editworkout/{id}")
    public String editWorkout(@PathVariable Long id, @Valid Workout currWorkout,
                              BindingResult result, Authentication auth) {
        Workout workout = findOwnWorkout(id, auth);
        if (result.hasErrors()) {
            return "workoutform";
        }
        workout.setWorkoutName(currWorkout.getWorkoutName());
        workout.setWorkoutDate(currWorkout.getWorkoutDate());
        workout.setDurationInMinutes(currWorkout.getDurationInMinutes());
        wRepository.save(workout);
        return "redirect:/myworkouts";
    }

    @PostMapping("/deleteworkout/{id}")
    public String deleteWorkout(@PathVariable Long id, Authentication auth) {
        wRepository.delete(findOwnWorkout(id, auth));
        return "redirect:/myworkouts";
    }

    // Detail page is public; canEdit decides if the buttons are shown
    @GetMapping("/workout/{id}")
    public String workoutDetail(@PathVariable Long id, Model model, Authentication auth) {
        Workout workout = wRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        boolean canEdit = auth != null
                && workout.getOwner() != null
                && workout.getOwner().getUsername().equals(auth.getName());
        model.addAttribute("workout", workout);
        model.addAttribute("canEdit", canEdit);
        return "workoutdetails";
    }
}