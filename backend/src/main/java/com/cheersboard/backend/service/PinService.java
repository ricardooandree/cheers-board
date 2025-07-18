package com.cheersboard.backend.service;

import com.cheersboard.backend.dto.like.CreateLikeRequest;
import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.dto.pin.CreatePinRequest;
import com.cheersboard.backend.dto.pin.PinResponse;
import com.cheersboard.backend.dto.pin.UpdateDescriptionRequest;
import com.cheersboard.backend.exception.DuplicateResourceException;
import com.cheersboard.backend.exception.ResourceNotFoundException;
import com.cheersboard.backend.model.Like;
import com.cheersboard.backend.model.Location;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import com.cheersboard.backend.repository.LikeRepository;
import com.cheersboard.backend.repository.LocationRepository;
import com.cheersboard.backend.repository.PinRepository;
import com.cheersboard.backend.repository.UserRepository;
import com.cheersboard.backend.util.mapper.LikeMapper;
import com.cheersboard.backend.util.mapper.PinMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PinService {
    private final PinRepository pinRepository;
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final PinMapper pinMapper;
    private final LikeMapper likeMapper;
    private final LikeRepository likeRepository;

    public PinService(PinRepository pinRepository,
                      UserRepository userRepository,
                      LocationService locationService,
                      PinMapper pinMapper,
                      LikeMapper likeMapper, LocationRepository locationRepository, LikeRepository likeRepository) {
        this.pinRepository = pinRepository;
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.pinMapper = pinMapper;
        this.likeMapper = likeMapper;
        this.likeRepository = likeRepository;
    }

    /**
     * CREATE Methods
     */
    public PinResponse createPin(CreatePinRequest createPinRequest){
        // TODO: Remove userId from CreatePinRequest DTO after JWT implementation
        User user = userRepository.findById(createPinRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Location location = locationService.resolveOrCreate(createPinRequest.getLatitude(), createPinRequest.getLongitude());

        Pin newPin = pinMapper.toEntity(createPinRequest, user, location);

        user.addPin(newPin);
        location.addPin(newPin);

        pinRepository.save(newPin);
        return pinMapper.toResponse(newPin);
    }

    public LikeResponse likePin(Long id, CreateLikeRequest createLikeRequest){
        if (likeRepository.existsByUserIdAndPinId(createLikeRequest.getUserId(), id)) {
            throw new DuplicateResourceException("This Pin is already liked");
        }

        User user = userRepository.findById(createLikeRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Pin pin = pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        Like newLike = likeMapper.toEntity(user, pin);

        user.addLike(newLike);
        pin.addLike(newLike);

        likeRepository.save(newLike);
        return likeMapper.toResponse(newLike);
    }

    /**
     * GET Methods
     */
    public PinResponse getPinById(Long id){
        Pin pin = pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        return pinMapper.toResponse(pin);
    }

    public List<PinResponse> getAllPins(){
        List<Pin> pins = pinRepository.findAll();

        return pinMapper.toResponseList(pins);
    }

    public List<LikeResponse> getPinLikes(Long id){
        Pin pin = pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        return pin.getLikes().stream()
                .map(likeMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE Methods
     */
    public PinResponse updatePinDescription(Long id, UpdateDescriptionRequest updateDescriptionRequest){
        Pin pin = pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        pin.setDescription(updateDescriptionRequest.getDescription());
        pinRepository.save(pin);

        return pinMapper.toResponse(pin);
    }

    /**
     * DELETE Methods
     */
    public void deletePinById(Long id){
        Pin pin = pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        pin.getUser().removePin(pin);
        pin.getLocation().removePin(pin);

        pinRepository.delete(pin);
    }

    public void unlikePin(Long id, Long userId){
        Like like = likeRepository.findByUserIdAndPinId(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        like.getUser().removeLike(like);
        like.getPin().removeLike(like);

        likeRepository.delete(like);
    }
}
