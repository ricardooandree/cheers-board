package com.cheersboard.backend.repository;

import com.cheersboard.backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);
    List<Like> findByPinId(Long pinId);
    boolean existsByUserIdAndPinId(Long userId, Long pinId);  // Prevents duplicate post likes
    long countByUserId(Long userId);  // Gets the total number of likes a user did
    long countByPinId(Long pinId);    // Gets the total number of likes a pin has

    // TODO: In the future I'll probably need paginated and not paginated likes based on User/Pins amounts with @Query or @NativeQuery
}
