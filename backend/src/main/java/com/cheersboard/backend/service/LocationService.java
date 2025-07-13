package com.cheersboard.backend.service;

import com.cheersboard.backend.dto.location.CreateLocationRequest;
import com.cheersboard.backend.dto.location.LocationResponse;
import com.cheersboard.backend.exception.DuplicateResourceException;
import com.cheersboard.backend.exception.ResourceNotFoundException;
import com.cheersboard.backend.model.Location;
import com.cheersboard.backend.repository.LocationRepository;
import com.cheersboard.backend.util.mapper.LocationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository,
                           LocationMapper locationMapper){
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    /**
     * CREATE Methods
     */
    public LocationResponse createLocation(CreateLocationRequest createLocationRequest){
        if (locationRepository.existsByLatitudeAndLongitude(createLocationRequest.getLatitude(), createLocationRequest.getLongitude())){
            throw new DuplicateResourceException("Location already exists");
        }

        Location newLocation = locationMapper.toEntity(createLocationRequest);
        locationRepository.save(newLocation);

        return locationMapper.toResponse(newLocation);
    }

    /**
     * GET Methods
     */
    public LocationResponse getLocationById(Long id){
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        return locationMapper.toResponse(location);
    }

    public List<LocationResponse> getAllLocations(){
        List<Location> locations = locationRepository.findAll();

        return locationMapper.toResponseList(locations);
    }

    /**
     * UPDATE Methods
     */


    /**
     * DELETE Methods
     */
    public void deleteLocationById(Long id){
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        if (!location.getPins().isEmpty()){
            throw new IllegalStateException("Location has associated pins");
        }

        locationRepository.delete(location);
    }


    /**
     * UTIL Methods
     */
    public Location resolveOrCreate(Double latitude, Double longitude){
        return locationRepository.findByLatitudeAndLongitude(latitude, longitude)
                .orElseGet(() -> {
                    CreateLocationRequest dto = new CreateLocationRequest();
                    dto.setLatitude(latitude);
                    dto.setLongitude(longitude);

                    Location newLocation = locationMapper.toEntity(dto);
                    return locationRepository.save(newLocation);
                });
    }

    // Reverse Geo-Coding API - To get City, Country, Region data from latitude/longitude
}
