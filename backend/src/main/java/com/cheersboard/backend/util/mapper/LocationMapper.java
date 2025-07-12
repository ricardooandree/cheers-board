package com.cheersboard.backend.util.mapper;

import com.cheersboard.backend.dto.location.CreateLocationRequest;
import com.cheersboard.backend.dto.location.LocationResponse;
import com.cheersboard.backend.model.Location;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public Location toEntity(CreateLocationRequest requestDto){
        if (requestDto == null) return null;

        return new Location(
                requestDto.getLatitude(),
                requestDto.getLongitude()
        );
    }

    public LocationResponse toResponse(Location location){
        if (location == null) return null;

        return new LocationResponse(
                location.getId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getPins().size()
        );
    }

    public List<LocationResponse> toResponseList(List<Location> locations){
        if (locations == null) return null;

        return locations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
