package app.populators;

import app.entities.Parcel;
import app.enums.DeliveryStatus;
import app.persistence.ParcelDAO;

import java.time.LocalDateTime;

public class ParcelPopulator
{
    public static Parcel[] populate(ParcelDAO parcelDAO)
    {
        Parcel parcel1 = Parcel.builder()
            .senderName("Tony Stark")
            .receiverName("Iron Man")
            .deliveryStatus(DeliveryStatus.SENT)
            .trackingNumber("91111111")
            .build();

        parcelDAO.create(parcel1);

        Parcel parcel2 = Parcel.builder()
            .senderName("Spiderman")
            .receiverName("Wonderwoman")
            .deliveryStatus(DeliveryStatus.IN_TRANSIT)
            .trackingNumber("81111111")
            .build();

        parcelDAO.create(parcel2);

        return new Parcel[] {parcel1,parcel2};

    }
}
