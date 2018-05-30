package com.pluralsight.service;

import java.util.List;

import com.pluralsight.model.Ride;

public interface RideService {

  List<Ride> getRides();

  Ride create(Ride ride);

  Ride getRide(int id);
}