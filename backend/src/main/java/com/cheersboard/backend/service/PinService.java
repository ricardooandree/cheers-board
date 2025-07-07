package com.cheersboard.backend.service;

import com.cheersboard.backend.exception.ResourceNotFoundException;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import com.cheersboard.backend.repository.PinRepository;
import com.cheersboard.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PinService {
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    public PinService(PinRepository pinRepository, UserRepository userRepository) {
        this.pinRepository = pinRepository;
        this.userRepository = userRepository;
    }

    // CRUD Service Methods
    public Pin createPin(Pin pin, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.addPin(pin);
        return pinRepository.save(pin);
    }

    public Pin getPinById(Long id){
        return pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));
    }

    public List<Pin> getAllPins(){
        return pinRepository.findAll();
    }

    public Pin updatePinDescription(Long id, String newDescription){
        Pin pin = pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        pin.setDescription(newDescription);
        return pinRepository.save(pin);
    }

    public void deletePinById(Long id){
        Pin pin = pinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        pin.getUser().removePin(pin);
        pinRepository.delete(pin);
    }
}
