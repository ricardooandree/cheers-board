package com.cheersboard.backend.service;

import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.exception.ResourceNotFoundException;
import com.cheersboard.backend.model.Like;
import com.cheersboard.backend.repository.LikeRepository;
import com.cheersboard.backend.util.mapper.LikeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    public LikeService(LikeRepository likeRepository,
                       LikeMapper likeMapper) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
    }

    /**
     * CREATE Methods
     */


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

}
