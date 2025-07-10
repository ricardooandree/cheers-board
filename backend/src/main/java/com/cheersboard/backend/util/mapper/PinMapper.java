package com.cheersboard.backend.util.mapper;

import com.cheersboard.backend.dto.pin.CreatePinRequest;
import com.cheersboard.backend.dto.pin.PinResponse;
import com.cheersboard.backend.model.Location;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PinMapper {
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;

    public PinMapper(UserMapper userMapper, LocationMapper locationMapper){
        this.userMapper = userMapper;
        this.locationMapper = locationMapper;
    }

    public Pin toEntity(CreatePinRequest requestDto, User user, Location location){
        if (requestDto == null || user == null || location == null) return null;

        return new Pin(
                requestDto.getDescription(),
                user,
                location
        );
    }

    public PinResponse toResponse(Pin pin){
        if (pin == null) return null;

        return new PinResponse(
                pin.getId(),
                pin.getDescription(),
                pin.getCreatedAt(),
                userMapper.toResponse(pin.getUser()),
                locationMapper.toResponse(pin.getLocation()),
                pin.getLikes().size()
        );
    }

    public List<PinResponse> toResponseList(List<Pin> pins){
        if (pins == null) return null;

        return pins.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
