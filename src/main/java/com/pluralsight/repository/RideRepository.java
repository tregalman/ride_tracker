package com.pluralsight.repository;

import java.util.List;

import com.pluralsight.model.Ride;
import java.util.Set;

public interface RideRepository {

  Ride create(Ride ride);

  List<Ride> update(List<Ride> rides);

  Ride update(Ride rides);

  List<Ride> findAll();

  Ride findById(int id);

  List<Ride> findById(List<Integer> ids);
}