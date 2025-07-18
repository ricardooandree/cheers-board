package com.cheersboard.backend.controller;

import com.cheersboard.backend.dto.like.LikeResponse;
import com.cheersboard.backend.service.LikeService;
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

}
