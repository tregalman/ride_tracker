package com.pluralsight.controller;

import com.pluralsight.model.Ride;
import com.pluralsight.service.RideService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rides")
public class RideController {

  private final RideService rideService;

  @Autowired
  public RideController(RideService rideService) {
    this.rideService = rideService;
  }

  @GetMapping
  public List<Ride> getRides() {
    return rideService.getRides();
  }
}
