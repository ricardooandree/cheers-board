package com.cheersboard.backend.service;

import com.cheersboard.backend.dto.like.CreateLikeRequest;
import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.exception.DuplicateResourceException;
import com.cheersboard.backend.exception.ResourceNotFoundException;
import com.cheersboard.backend.model.Like;
import com.cheersboard.backend.model.Pin;
import com.cheersboard.backend.model.User;
import com.cheersboard.backend.repository.LikeRepository;
import com.cheersboard.backend.repository.PinRepository;
import com.cheersboard.backend.repository.UserRepository;
import com.cheersboard.backend.util.mapper.LikeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final LikeMapper likeMapper;

    public LikeService(LikeRepository likeRepository,
                       UserRepository userRepository,
                       PinRepository pinRepository,
                       LikeMapper likeMapper) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.pinRepository = pinRepository;
        this.likeMapper = likeMapper;
    }

    /**
     * CREATE Methods
     */
    public LikeResponse createLike(CreateLikeRequest createLikeRequest){
        if (likeRepository.existsByUserIdAndPinId(createLikeRequest.getUserId(), createLikeRequest.getPinId())){
            throw new DuplicateResourceException("User already liked this Pin");
        }

        User user = userRepository.findById(createLikeRequest.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Pin pin = pinRepository.findById(createLikeRequest.getPinId())
                .orElseThrow(() -> new ResourceNotFoundException("Pin not found"));

        Like newLike = likeMapper.toEntity(user, pin);

        likeRepository.save(newLike);
        return likeMapper.toResponse(newLike);
    }


    /**
     * GET Methods
     */
    public LikeResponse getLikeById(Long id){
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        return likeMapper.toResponse(like);
    }

    public List<LikeResponse> getAllLikes(){
        List<Like> likes = likeRepository.findAll();

        return likeMapper.toResponseList(likes);
    }

    /**
     * UPDATE Methods
     */


    /**
     * DELETE Methods
     */
    public void deleteLikeById(Long id){
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        likeRepository.delete(like);
    }
}
