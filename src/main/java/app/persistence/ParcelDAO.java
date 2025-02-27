package app.persistence;

import app.config.HibernateConfig;
import app.entities.Parcel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class ParcelDAO implements GenericDAO<Parcel>
{
    private static ParcelDAO instance;
    private EntityManagerFactory emf;

    private ParcelDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }


    @Override
    public Parcel delete(Parcel parcel)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(parcel);
            em.getTransaction().commit();
        }
        return parcel;
    }

    @Override
    public List<Parcel> findAll()
    {
        List<Parcel> parcels;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            String jpql = "SELECT p FROM Parcel p";
            Query query = em.createQuery(jpql);
            parcels = query.getResultList();
            parcels.stream().forEach(System.out::println);
            em.getTransaction().commit();
        }
        return parcels;
    }

    @Override
    public Parcel create(Parcel parcel)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(parcel);
            em.getTransaction().commit();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return parcel;
    }

    @Override
    public Parcel read(Parcel parcel)
    {
        return null;
    }


    @Override
    public Parcel update(Parcel updateParcel)
    {
        Parcel updatedParcelResult;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            updatedParcelResult = em.merge(updateParcel);
            em.getTransaction().commit();
        }
        return updatedParcelResult;

    }


    @Override
    public Parcel findById(Parcel parcel)
    {
        Integer id =  parcel.getId();
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            String jpql = "SELECT p FROM Parcel p WHERE p.id = :id";
            Query query = em.createQuery(jpql);
            query.setParameter("id", id);
            Integer n = (Integer) query.getSingleResult();
            em.getTransaction().commit();
        }
        return parcel;
    }


    public Parcel getParcelByTrackingNumber(String trackingNumber)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            String jpql = "SELECT  p FROM Parcel p WHERE p.trackingNumber = :trackingNumber";
            Query query = em.createQuery(jpql);
            query.setParameter("trackingNumber", trackingNumber);
            Parcel parcel = (Parcel) query.getSingleResult();
            System.out.println(parcel.toString());
            return parcel;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public static ParcelDAO getInstance(EntityManagerFactory emf)
    {
        if(instance == null)
        {
            instance = new ParcelDAO(emf);
        }
        return instance;
    }

}


