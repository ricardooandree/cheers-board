package com.cheersboard.backend.repository;

import com.cheersboard.backend.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findByUserId(Long userId);  // Finds all the pins that belong to a user
    List<Pin> findByLocationId(Long locationId);

    // TODO: In the future I'll probably need paginated pins based on User/Likes/Location with @Query or @NativeQuery
}
