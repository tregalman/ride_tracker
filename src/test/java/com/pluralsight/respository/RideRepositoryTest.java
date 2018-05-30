package com.pluralsight.respository;

import static org.assertj.core.api.Assertions.assertThat;

import com.pluralsight.ApplicationConfiguration;
import com.pluralsight.TestDataSourceConfiguration;
import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@ContextConfiguration(classes = {TestDataSourceConfiguration.class, ApplicationConfiguration.class})
public class RideRepositoryTest {

  @ClassRule
  public static final SpringClassRule springClassRule = new SpringClassRule();
  @Rule
  public final SpringMethodRule springMethodRule = new SpringMethodRule();
  @Autowired
  private RideRepository rideRepository;

  @Test
  public void findRideByIdTest() {
    final int RIDE_ID = 1;
    final Ride ride = rideRepository.findById(RIDE_ID);

    assertThat(ride)
        .isNotNull();

    assertThat(ride.getId())
        .isEqualTo(RIDE_ID);
  }

  @Test
  public void getRides() {
    final int ALL_RIDES = 4;
    List<Ride> rides = rideRepository.findAll();

    assertThat(rides.size()).isEqualTo(ALL_RIDES);
  }

  @Test
  public void createRide() {
    final int NEW_ID = 5;
    Ride newRide = Ride.builder().withName("New Ride").withDuration(24).build();
    Ride savedRide = rideRepository.create(newRide);

    assertThat(savedRide.getId()).isEqualTo(NEW_ID);
  }

  @Test
  public void updateAllRidesTest() {
    List<Ride> rides = rideRepository.findAll()
        .stream().map(ride ->
            Ride.builder()
                .withName(ride.getName())
                .withDuration(ride.getDuration() + 10)
                .withId(ride.getId())
                .build())
        .collect(Collectors.toList());
    List<Ride> updatedRides = rideRepository.update(rides);

    assertThat(updatedRides).hasSameElementsAs(rides);
  }
}
