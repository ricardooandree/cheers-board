package com.cheersboard.backend.controller;

import com.cheersboard.backend.dto.location.CreateLocationRequest;
import com.cheersboard.backend.dto.location.LocationResponse;
import com.cheersboard.backend.dto.pin.PinResponse;
import com.cheersboard.backend.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    LocationService locationService;

    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    /**
     * CREATE Routes
     */
    @PostMapping
    public ResponseEntity<LocationResponse> createPin(@Valid @RequestBody CreateLocationRequest createLocationRequest){
        LocationResponse createdLocation = locationService.createLocation(createLocationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
    }


    /**
     * GET Routes
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable("id") Long id){
        LocationResponse location = locationService.getLocationById(id);

        return ResponseEntity.status(HttpStatus.OK).body(location);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations(){
        List<LocationResponse> locations = locationService.getAllLocations();

        return ResponseEntity.status(HttpStatus.OK).body(locations);
    }

    @GetMapping("/{id}/pins")
    public ResponseEntity<List<PinResponse>> getLocationPins(@PathVariable("id") Long id){
        List<PinResponse> pins = locationService.getLocationPins(id);

        return ResponseEntity.status(HttpStatus.OK).body(pins);
    }


    /**
     * UPDATE Routes
     */


    /**
     * DELETE Routes
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<LocationResponse> deleteLocation(@PathVariable("id") Long id){
        locationService.deleteLocationById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
