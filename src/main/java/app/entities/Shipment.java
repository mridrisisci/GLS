package app.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Shipment
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @ToString.Exclude
    private Parcel pcl;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "source_location_id")
    private Location sourceLocation;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;

    private LocalDateTime shipment;

    private LocalDateTime update;


    @PrePersist
    public void createDateTime()
    {
        shipment = LocalDateTime.now();
    }

    @PreUpdate
    public void createUpdateTime()
    {
        update = LocalDateTime.now();
    }
}
