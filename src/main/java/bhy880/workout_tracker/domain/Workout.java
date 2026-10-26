package bhy880.workout_tracker.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;

@Entity 
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String workoutName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Excercise> excercises;

    @Transient
    public Duration getDuration() {
        
        return Duration.between(startTime, endTime);
    }

    public Workout(){

    }

    

    public Workout(Long id, String workoutName, LocalDateTime startTime, LocalDateTime endTime,
            List<Excercise> excercises) {
        this.id = id;
        this.workoutName = workoutName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.excercises = excercises;
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
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public List<Excercise> getExcercises() {
        return excercises;
    }
    public void setExcercises(List<Excercise> excercises) {
        this.excercises = excercises;
    }

    
}
