package app.entities;

import app.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parcels")
public class Parcel
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parcel_id", nullable = false)
    private Integer id;

    @Column(name = "tracking_number", nullable = false)
    private String trackingNumber;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "update")
    private LocalDateTime timeStamp;

    // bi-directional relationship with shipment
    @OneToMany(mappedBy = "pcl", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Shipment> shipments = new HashSet<>();


    public Parcel(String trackingNumber, String senderName, String receiverName, DeliveryStatus deliveryStatus)
    {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.deliveryStatus = deliveryStatus;
        this.timeStamp = LocalDateTime.now();

    }

    @PrePersist
    @PreUpdate
    public void updateTimeStamp()
    {
      LocalDateTime update = LocalDateTime.now();
    }
}
