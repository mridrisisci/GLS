package app.persistence;

import app.config.HibernateConfig;
import app.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class LocationDAO implements GenericDAO<Location>
{

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static LocationDAO instance;


    private LocationDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public Location findById(Location location)
    {
        Location actualLocation;
        Integer locationId = location.getId();
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            String jpql = "SELECT l FROM Location  l WHERE l.id = :locationId";
            Query query = em.createQuery(jpql);
            query.setParameter("locationId", locationId);
            actualLocation = (Location) query.getSingleResult();
            em.getTransaction().commit();
        }
        return actualLocation;
    }

    @Override
    public Location create(Location location)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        }
        return location;
    }

    @Override
    public Location read(Location location)
    {
        return null;
    }

    @Override
    public Location update(Location location)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(location);
            em.getTransaction().commit();
        }
        return location;
    }

    @Override
    public Location delete(Location location)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(location);
            em.getTransaction().commit();
        }
        return location;
    }

    @Override
    public List<Location> findAll()
    {
        List<Location> locations;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            String jpql = "SELECT l FROM Location l";
            Query query = em.createQuery(jpql);
            locations = query.getResultList();
            locations.stream().forEach(System.out::println);
            em.getTransaction().commit();
        }
        return locations;
    }



    public static LocationDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new LocationDAO(emf);
        }
        return instance;
    }


}
