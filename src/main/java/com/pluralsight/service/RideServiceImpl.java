package com.pluralsight.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;

@Service
public class RideServiceImpl implements RideService {

  @Autowired
  private RideRepository rideRepository;

  public RideServiceImpl(RideRepository rideRepository) {
    this.rideRepository = rideRepository;
  }

  @Override
  public List<Ride> getRides() {
    return rideRepository.getRides();
  }

  @Override
  public Ride create(Ride ride) {
    return rideRepository.createRide(ride);
  }

  @Override
  public Ride getRide(int id) {
    return rideRepository.getRide(id);
  }
}
