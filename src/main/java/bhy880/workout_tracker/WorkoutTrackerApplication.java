package bhy880.workout_tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import bhy880.workout_tracker.domain.AppUser;
import bhy880.workout_tracker.domain.AppUserRepository;
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
		ExerciseRepository eRepository,
		AppUserRepository appUserRepository,
		BCryptPasswordEncoder encoder) {

		return args -> {

			// Create each test user only if it doesn't exist yet
			if (appUserRepository.findByUsername("user").isEmpty()) {
			    appUserRepository.save(new AppUser("user", encoder.encode("user"), "USER"));
			}
			if (appUserRepository.findByUsername("admin").isEmpty()) {
			    appUserRepository.save(new AppUser("admin", encoder.encode("admin"), "ADMIN"));
			}
			if (appUserRepository.findByUsername("user2").isEmpty()) {
			    appUserRepository.save(new AppUser("user2", encoder.encode("user2"), "USER"));
			}


			if (wRepository.count()== 0){

				AppUser user = appUserRepository.findByUsername("user").orElseThrow();
				Workout legDay = new Workout();
				legDay.setWorkoutName("Leg day");
				legDay.setWorkoutDate(LocalDate.of(2026, 10, 26));
				legDay.setDurationInMinutes(60);
				legDay.setOwner(user);
				wRepository.save(legDay);

				Exercise squat = new Exercise();
				squat.setExerciseName("Squat");
				squat.setSets(4);
				squat.setRepsPerSet(8);
				squat.setWeightInKg(80.0);
				squat.setWorkout(legDay);
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
				upperBody.setOwner(user);
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
