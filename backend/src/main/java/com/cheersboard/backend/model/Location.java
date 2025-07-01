package com.cheersboard.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double latitude;

    @NotNull
    @Column(nullable = false)
    private Double longitude;

    // TODO: Once the reverse geo api is integrated add @NotBlank and nullable = false
    @Column(updatable = false)
    private String address;

    @Column(updatable = false)
    private String city;

    @Column(updatable = false)
    private String country;

    @Column(updatable = false)
    private String continent;

    @OneToMany(mappedBy = "location")
    private List<Pin> pins;
}
