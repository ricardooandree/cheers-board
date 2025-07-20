package com.cheersboard.backend.config;

import com.cheersboard.backend.dto.like.CreateLikeRequest;
import com.cheersboard.backend.dto.pin.CreatePinRequest;
import com.cheersboard.backend.dto.user.CreateUserRequest;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import com.cheersboard.backend.repository.PinRepository;
import com.cheersboard.backend.repository.UserRepository;
import com.cheersboard.backend.service.PinService;
import com.cheersboard.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PinService pinService;
    private final PinRepository pinRepository;

    public DataSeeder(UserService userService,
                      UserRepository userRepository,
                      PinService pinService,
                      PinRepository pinRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.pinService = pinService;
        this.pinRepository = pinRepository;
    }

    private CreateUserRequest makeUser(String username, String password, String email){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setPassword(password);
        createUserRequest.setEmail(email);

        return createUserRequest;
    }

    private CreatePinRequest makePin(String username, String description, Double Latitude, Double Longitude){
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new RuntimeException("User not found"));

        CreatePinRequest createPinRequest = new CreatePinRequest();
        createPinRequest.setUserId(user.getId());
        createPinRequest.setDescription(description);
        createPinRequest.setLatitude(Latitude);
        createPinRequest.setLongitude(Longitude);

        return createPinRequest;
    }

    /**
     *
     * NOTE: This is not a good practice! Description is not a unique field. This is only used for seeding.
     */
    private Long getPinIdByDescription(String description){
        Pin pin = pinRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Pin not found"));

        return pin.getId();
    }

    private CreateLikeRequest makeLike(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new RuntimeException("User not found"));

        CreateLikeRequest createLikeRequest = new CreateLikeRequest();
        createLikeRequest.setUserId(user.getId());

        return createLikeRequest;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        userService.createUser(makeUser("TestUsername1", "testpassword1", "testemail_1@gmail.com"));
        userService.createUser(makeUser("TestUsername2", "testpassword2", "testemail_2@gmail.com"));
        userService.createUser(makeUser("TestUsername3", "testpassword3", "testemail_3@gmail.com"));

        pinService.createPin(makePin("TestUsername1", "Drinking a beer in Rossio Square, Lisbon", 38.7149, -9.1406));
        pinService.createPin(makePin("TestUsername1", "Having one in Times Square, NYC", 40.7580, -73.9855));
        pinService.createPin(makePin("TestUsername1", "Cheers to the sunset of Copacabana, Rio de Janeiro", -22.9711, -43.1822));
        pinService.createPin(makePin("TestUsername2", "A pint in a pub next to Trafalgar Square, London", 51.5080, -0.1281));
        pinService.createPin(makePin("TestUsername3", "Trying soju at the Shibuya Crossroad, Tokyo", 35.6595	, 139.7005));

        pinService.likePin(getPinIdByDescription("Drinking a beer in Rossio Square, Lisbon"), makeLike("TestUsername1"));
        pinService.likePin(getPinIdByDescription("Drinking a beer in Rossio Square, Lisbon"), makeLike("TestUsername2"));
        pinService.likePin(getPinIdByDescription("Drinking a beer in Rossio Square, Lisbon"), makeLike("TestUsername3"));
        pinService.likePin(getPinIdByDescription("A pint in a pub next to Trafalgar Square, London"), makeLike("TestUsername1"));
        pinService.likePin(getPinIdByDescription("Trying soju at the Shibuya Crossroad, Tokyo"), makeLike("TestUsername1"));
    }
}
