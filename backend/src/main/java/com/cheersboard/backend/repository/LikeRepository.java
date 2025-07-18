package com.cheersboard.backend.repository;

import com.cheersboard.backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByUserId(Long userId);
    List<Like> findAllByPinId(Long pinId);
    Optional<Like> findByUserIdAndPinId(Long userId, Long pinId);
    boolean existsByUserIdAndPinId(Long userId, Long pinId);  // Prevents duplicate post likes
    long countByUserId(Long userId);  // Gets the total number of likes a user did
    long countByPinId(Long pinId);    // Gets the total number of likes a pin has

    // TODO: In the future I'll probably need paginated and not paginated likes based on User/Pins amounts with @Query or @NativeQuery
    @Query(value = """
        SELECT u.id, COUNT(l.id) AS user_likes_count
        FROM "like" l
        LEFT JOIN "user" u ON l.user_id = u.id
        GROUP BY u.id
        ORDER BY user_likes_count DESC
    """, nativeQuery = true)
    List<Object[]> getUserLikesCount();

    @Query(value = """
        SELECT p.id, COUNT(l.id) AS post_likes_count
        FROM "like" l
        LEFT JOIN pin p ON l.pin_id = p.id
        GROUP BY p.id
        ORDER BY post_likes_count DESC
    """, nativeQuery = true)
    List<Object[]> getPostLikesCount();
}
