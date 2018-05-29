package com.pluralsight.respository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.pluralsight.ApplicationConfiguration;
import com.pluralsight.TestDataSourceConfiguration;
import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;
import java.util.List;
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
  public void getRides() {
    final int ALL_RIDES = 4;
    List<Ride> rides = rideRepository.getRides();

    assertThat("Selecting all Ride(s) from the database returns the correct amount",
        rides.size(), is(ALL_RIDES));
  }

  @Test
  public void createRide() {
    final int NEW_ID = 5;
    Ride newRide = Ride.builder().withName("New Ride").withDuration(24).build();
    Ride savedRide = rideRepository.createRide(newRide);

    assertThat(savedRide.getId(), is(NEW_ID));
  }
}
