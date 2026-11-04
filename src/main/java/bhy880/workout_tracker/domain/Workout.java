package bhy880.workout_tracker.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.GenerationType;

@Entity 
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Workout name required")
    @Size(max = 40, message = "Max 40 characters")
    private String workoutName;

    @NotNull(message = "Date required")
    private LocalDate workoutDate;

    @NotNull(message = "Duration required")
    @Min(value = 1)
    @Max(value = 600)
    private int durationInMinutes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workout")
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userid")
    private AppUser owner;

    public Workout(){

    }

    public Workout(Long id, String workoutName, LocalDate workoutDate, int durationInMinutes,
            List<Exercise> exercises) {
        this.id = id;
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
        this.durationInMinutes = durationInMinutes;
        this.exercises = exercises;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWorkoutName() {
        return workoutName;
    }
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
    
    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workouDate) {
        this.workoutDate = workouDate;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }  
    
}
