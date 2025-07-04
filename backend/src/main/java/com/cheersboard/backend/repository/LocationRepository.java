package com.cheersboard.backend.repository;

import com.cheersboard.backend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByLatitudeAndLongitude(Double latitude, Double longitude);
    List<Location> findByAddress(String address);
    List<Location> findByCity(String city);
    List<Location> findByCountry(String country);
    List<Location> findByContinent(String continent);
    List<Location> findByCityAndCountry(String city, String country);
    List<Location> findByCountryAndContinent(String country, String continent);
    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
    boolean existsByAddress(String address);
    boolean existsByCity(String city);
    boolean existsByCountry(String country);
    boolean existsByContinent(String continent);
    boolean existsByCityAndCountry(String city, String country);
    boolean existsByCountryAndContinent(String country, String continent);

    // TODO: Find a way to query by a Location radius (between?)
}
