package com.cheersboard.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Setter
    @Column(nullable = false)
    private Double latitude;

    @NotNull
    @Setter
    @Column(nullable = false)
    private Double longitude;

    // TODO: Once the reverse geo api is integrated add @NotBlank and nullable = false
    @Setter
    @Column(updatable = false)
    private String address;

    @Setter
    @Column(updatable = false)
    private String city;

    @Setter
    @Column(updatable = false)
    private String country;

    @Setter
    @Column(updatable = false)
    private String continent;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Pin> pins = new ArrayList<>();

    public Location(Double latitude, Double longitude, String address, String city, String country, String continent) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.city = city;
        this.country = country;
        this.continent = continent;
    }

    public void addPin(Pin pin) {
        pins.add(pin);
        pin.setLocation(this);
    }

    public void removePin(Pin pin) {
        pins.remove(pin);
        pin.setLocation(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id != null && Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", continent='" + continent + '\'' +
                '}';
    }
}
