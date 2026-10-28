package bhy880.workout_tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

import bhy880.workout_tracker.domain.Exercise;
import bhy880.workout_tracker.domain.ExerciseRepository;
import bhy880.workout_tracker.domain.Workout;
import bhy880.workout_tracker.domain.WorkoutRepository;

@SpringBootApplication
public class WorkoutTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkoutTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner alustusData(WorkoutRepository wRepository,
	ExerciseRepository eRepository) {

		return args -> {

			if (wRepository.count()== 0){

				Workout legDay = new Workout();
				legDay.setWorkoutName("Leg day");
				legDay.setWorkoutDate(LocalDate.of(2026, 10, 26));
				legDay.setDurationInMinutes(60);
				wRepository.save(legDay);

				Exercise squat = new Exercise();
				squat.setExerciseName("Squat");
				squat.setSets(4);
				squat.setRepsPerSet(8);
				squat.setWeightInKg(80.0);
				squat.setWorkout(legDay);      // link the exercise to its workout
				eRepository.save(squat);
				
				Exercise lunges = new Exercise();
				lunges.setExerciseName("Lunges");
				lunges.setSets(3);
            lunges.setRepsPerSet(12);
            lunges.setWeightInKg(20.0);
            lunges.setWorkout(legDay);
            eRepository.save(lunges);
			
            Workout upperBody = new Workout();
            upperBody.setWorkoutName("Upper body");
            upperBody.setWorkoutDate(LocalDate.of(2026, 10, 28));
            upperBody.setDurationInMinutes(45);
            wRepository.save(upperBody);
			
            Exercise bench = new Exercise();
            bench.setExerciseName("Bench press");
            bench.setSets(4);
            bench.setRepsPerSet(6);
            bench.setWeightInKg(60.0);
            bench.setWorkout(upperBody);
            eRepository.save(bench);
			
		}

		};


	}
}
