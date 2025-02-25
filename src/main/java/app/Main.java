package app;

import app.config.HibernateConfig;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import app.enums.DeliveryStatus;
import app.persistence.LocationDAO;
import app.persistence.ParcelDAO;
import app.persistence.ShipmentDAO;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

public class Main
{

    // TODO: Integrationstest mangler



    public static void main(String[] args)
    {
        final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        final ParcelDAO parcelDAO = ParcelDAO.getInstance(emf);
        final ShipmentDAO shipmentDAO = ShipmentDAO.getInstance(emf);
        final LocationDAO locationDAO = LocationDAO.getInstance(emf);

        Parcel parcel = new Parcel("90909090", "Mogens", "Josefine", DeliveryStatus.DELIVERED);
        Parcel parcel2 = new Parcel("12121212", "Sarah", "Torben", DeliveryStatus.IN_TRANSIT);
        Parcel parcel3 = new Parcel("13131313", "Veronika", "Sofie", DeliveryStatus.IN_TRANSIT);
        Parcel parcel4 = new Parcel("22222222", "Lars", "Madeleine", DeliveryStatus.IN_TRANSIT);
        Parcel parcel5 = new Parcel("90909090", "Lasse", "Ulrik", DeliveryStatus.IN_TRANSIT);
        Parcel parcel6 = new Parcel("31313131", "hans", "Erik", DeliveryStatus.IN_TRANSIT);
        Parcel parcel7 = new Parcel("43434343", "Henrik", "Vesth", DeliveryStatus.IN_TRANSIT);


        //parcelDAO.create(parcel);

        /*parcelDAO.getPackageByTrackingNumber("29102933");
        */

        // updat delivery status for a parcel
        /*Parcel updateParcel = parcelDAO.getParcelByTrackingNumber("29102933"); // HANS - SARAH
        if (updateParcel != null)
        {
            updateParcel.setDeliveryStatus(DeliveryStatus.DELIVERED);
            parcelDAO.update(updateParcel);

        }*/

        //parcelDAO.update(parcel);

        //parcelDAO.getAll();

        //parcelDAO.delete(1);

        // ------------------------- PART 2 ------------------------------ //



        Location sourceLocationPcl1 = new Location();
        sourceLocationPcl1.setLatitude(77.33455);
        sourceLocationPcl1.setLongitude(165.67161);
        sourceLocationPcl1.setAddress("København, Danmark");

        Location destinationLocationPcl1 = new Location();
        destinationLocationPcl1.setLatitude(56.1629);
        destinationLocationPcl1.setLongitude(10.2039);
        destinationLocationPcl1.setAddress("Aarhus, Danmark");

        Location secondShipmentDestLocationPcl1 = new Location();
        secondShipmentDestLocationPcl1.setLatitude(-30.06653);
        secondShipmentDestLocationPcl1.setLongitude(-129.93545);
        secondShipmentDestLocationPcl1.setAddress("Undisclosed territory");

        Location secondShipmentSourceLocationPcl1 = new Location();
        secondShipmentSourceLocationPcl1.setAddress("København V");
        secondShipmentSourceLocationPcl1.setLongitude(165.67161);
        secondShipmentSourceLocationPcl1.setLatitude(77.33455);


        // create shipment objects
        Shipment shipment1 = new Shipment();
        shipment1.setSourceLocation(sourceLocationPcl1);
        shipment1.setDestinationLocation(destinationLocationPcl1);

        Shipment shipment2 = new Shipment();
        shipment2.setDestinationLocation(secondShipmentDestLocationPcl1);
        shipment2.setSourceLocation(secondShipmentSourceLocationPcl1);

        // persist coordinates (locations) to db
        locationDAO.create(sourceLocationPcl1);
        locationDAO.create(destinationLocationPcl1);
        locationDAO.create(secondShipmentDestLocationPcl1);
        locationDAO.create(secondShipmentSourceLocationPcl1);

        // persist parcels
        parcelDAO.create(parcel);
        parcelDAO.create(parcel2);
        parcelDAO.create(parcel3);
        parcelDAO.create(parcel4);
        parcelDAO.create(parcel5);
        parcelDAO.create(parcel6);
        parcelDAO.create(parcel7);

        // persist shipments
        shipmentDAO.create(shipment1);
        shipmentDAO.create(shipment2);


        // crud operations
        parcelDAO.findAll();
    }
}