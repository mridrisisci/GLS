package app.persistence;

import app.config.HibernateConfig;
import app.entities.Parcel;
import app.enums.DeliveryStatus;
import app.populators.ParcelPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class parcelDAOTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final ParcelDAO parcelDAO = ParcelDAO.getInstance(emf);
    private static Parcel parcel1;
    private static Parcel parcel2;


    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Parcel").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE parcels_parcel_id_seq RESTART WITH 1");
            em.getTransaction().commit();
            Parcel[] parcels = ParcelPopulator.populate(parcelDAO);
            parcel1 = parcels[0];
            parcel2 = parcels[1];
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
    *
    *
    *     @Test
    void create()
    {
        //Arrange
        GLSPackage ePackage = GLSPackage.builder()
                .trackingNumber("345")
                .senderName("Ivy")
                .receiverName("Jack")
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();
        Long count = 0L;
        //Act
        ePackage = packageDAO.create(ePackage);

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            count = em.createQuery("SELECT COUNT(*) FROM GLSPackage", Long.class).getSingleResult();
            em.getTransaction().commit();
        }

        //Assert
        assertNotNull(ePackage);
        assertEquals(5, ePackage.getId());
        assertEquals(5, count);
    }
    * */

    @Test
    void create()
    {
        Parcel parcel3 = Parcel.builder()
            .senderName("Thor")
            .receiverName("Green Lantern")
            .deliveryStatus(DeliveryStatus.DELIVERED)
            .trackingNumber("33333333")
            .build();

        Parcel p3 =  parcelDAO.create(parcel3);

        assertNotNull(p3.getId());
        List<Parcel> parcelList = parcelDAO.findAll();
        assertEquals(3, parcelList.size());
    }

    @Test
    void delete()
    {
        Parcel parcelToDelete = Parcel.builder()
            .senderName("Margot Robbie")
            .receiverName("Dicaprio")
            .trackingNumber("22222222")
            .deliveryStatus(DeliveryStatus.DELIVERED)
            .build();

        parcelDAO.create(parcelToDelete);
        //Long parcelid = parcelToDelete.getId();
        //parcelDAO.delete(parcelid);
        List<Parcel> parcelList = parcelDAO.findAll();
        assertEquals(2, parcelList.size());
        //assertThat(parcelList, hasItem(parcelToDelete));

    }


    @Test
    void getAll()
    {
        List<Parcel> list = parcelDAO.findAll();
        assertEquals(2, list.size());
    }

    @Test
    void getPackageByTrackingNumber()
    {

        Parcel parcel3 = Parcel.builder()
            .senderName("Thor")
            .receiverName("Green Lantern")
            .deliveryStatus(DeliveryStatus.DELIVERED)
            .trackingNumber("33333333")
            .build();

        Parcel p3 =  parcelDAO.create(parcel3);
        String trackingNumber = p3.getTrackingNumber();
        Parcel res = parcelDAO.getParcelByTrackingNumber(trackingNumber);
        assertEquals(trackingNumber, res.getTrackingNumber());

    }

    @Test
    void update()
    {
        Parcel parcelToUpdate = Parcel.builder()
            .id(parcel1.getId()) // T & T : 91111111 ???
            .senderName("Margot Stark") // Tony Stark
            .receiverName("Hulk")
            .deliveryStatus(DeliveryStatus.DELIVERED)
            .trackingNumber("00000000")
            .build();

        parcelDAO.update(parcelToUpdate);
        Parcel p1Update = parcelDAO.getParcelByTrackingNumber(parcelToUpdate.getTrackingNumber());
        assertThat(parcelToUpdate, samePropertyValuesAs(p1Update));

    }

    @Test
    void getInstance() { assertNotNull(emf); }

}