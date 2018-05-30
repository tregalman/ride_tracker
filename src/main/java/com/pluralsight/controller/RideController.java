package com.pluralsight.controller;

import com.pluralsight.model.Ride;
import com.pluralsight.service.RideService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public ResponseEntity<Ride> createRide(@RequestBody Ride ride, HttpServletRequest request) {
    Ride created = rideService.create(ride);
    HttpHeaders headers = new HttpHeaders();
    headers.toSingleValueMap();
    headers.setLocation(URI.create(request.getRequestURL().toString() + "/" + created.getId()));
    return new ResponseEntity<>(created, headers, HttpStatus.MOVED_PERMANENTLY);
  }

  @RequestMapping(value = "/{id}")
  public Ride find(@PathVariable("id") int id) {
    return rideService.getRide(id);
  }

  @GetMapping
  public List<Ride> getRides() {
    return rideService.getRides();
  }
}
