package com.cheersboard.backend.controller;

import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.dto.pin.CreatePinRequest;
import com.cheersboard.backend.dto.pin.PinResponse;
import com.cheersboard.backend.dto.pin.UpdateDescriptionRequest;
import com.cheersboard.backend.service.PinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pins")
public class PinController {
    private final PinService pinService;

    public PinController(PinService pinService){
        this.pinService = pinService;
    }

    /**
     * CREATE Routes
     */
    @PostMapping
    public ResponseEntity<PinResponse> createPin(@Valid @RequestBody CreatePinRequest createPinRequest){
        PinResponse createdPin = pinService.createPin(createPinRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPin);
    }

    /**
     * GET Routes
     */
    @GetMapping("/{id}")
    public ResponseEntity<PinResponse> getPin(@PathVariable("id") Long id){
        PinResponse pin = pinService.getPinById(id);

        return ResponseEntity.status(HttpStatus.OK).body(pin);
    }

    @GetMapping
    public ResponseEntity<List<PinResponse>> getPins(){
        List<PinResponse> pins = pinService.getAllPins();

        return ResponseEntity.status(HttpStatus.OK).body(pins);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<LikeResponse>> getPinLikes(@PathVariable("id") Long id){
        List<LikeResponse> likes = pinService.getPinLikes(id);

        return ResponseEntity.status(HttpStatus.OK).body(likes);
    }

    /**
     * UPDATE Routes
     */
    @PutMapping("/{id}")
    public ResponseEntity<PinResponse> updatePin(@PathVariable("id") Long id,
                                                 @Valid @RequestBody UpdateDescriptionRequest updateDescriptionRequest){
        PinResponse updatedPin = pinService.updatePinDescription(id, updateDescriptionRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updatedPin);
    }

    /**
     * DELETE Routes
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePin(@PathVariable("id") Long id){
        pinService.deletePinById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
