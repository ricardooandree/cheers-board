package com.cheersboard.backend.controller;

import com.cheersboard.backend.dto.like.CreateLikeRequest;
import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    /**
     * CREATE Routes
     */
    @PostMapping
    public ResponseEntity<LikeResponse> createLike(@Valid @RequestBody CreateLikeRequest createLikeRequest){
        LikeResponse createdLike = likeService.createLike(createLikeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdLike);
    }


    /**
     * GET Routes
     */
    @GetMapping("/{id}")
    public ResponseEntity<LikeResponse> getLike(@PathVariable("id") Long id){
        LikeResponse like = likeService.getLikeById(id);

        return ResponseEntity.status(HttpStatus.OK).body(like);
    }

    @GetMapping()
    public ResponseEntity<List<LikeResponse>> getLikes(){
        List<LikeResponse> likes = likeService.getAllLikes();

        return ResponseEntity.status(HttpStatus.OK).body(likes);
    }

    /**
     * UPDATE Routes
     */


    /**
     * DELETE Routes
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable("id") Long id){
        likeService.deleteLikeById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
