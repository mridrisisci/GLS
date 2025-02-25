package app.persistence;


import app.config.HibernateConfig;
import app.entities.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class ShipmentDAO implements GenericDAO<Shipment>
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static ShipmentDAO instance;


    private ShipmentDAO(EntityManagerFactory emf) { this.emf = emf; }

    @Override
    public Shipment findById(Shipment shipment)
    {
        Integer shipmentId = shipment.getId();
        Shipment shipmentResult;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            String jpql = "SELECT s FROM Shipment  s WHERE s.id = :shipmentId";
            Query query = em.createQuery(jpql);
            query.setParameter("shipmentId", shipmentId);
            shipmentResult = (Shipment) query.getSingleResult();
            em.getTransaction().commit();
        }
        return shipmentResult;
    }

    @Override
    public Shipment create(Shipment shipment)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
        }
        return shipment;
    }

@Override
public Shipment read(Shipment shipment)
{
    return null;
}

@Override
public Shipment update(Shipment shipment)
{
    try (EntityManager em = emf.createEntityManager())
    {
        em.getTransaction().begin();
        em.merge(shipment);
        em.getTransaction().commit();
    }
    return shipment;
}

@Override
public Shipment delete(Shipment shipment)
{
    try (EntityManager em = emf.createEntityManager())
    {
        em.getTransaction().begin();
        em.remove(shipment);
        em.getTransaction().commit();
    }
    return shipment;
}

@Override
public List<Shipment> findAll()
{
    List<Shipment> shipments;
    try (EntityManager em = emf.createEntityManager())
    {
        em.getTransaction().begin();
        String jpql = "SELECT c FROM Shipment c";
        Query query = em.createQuery(jpql);
        shipments = query.getResultList();
        shipments.stream().forEach(System.out::println);
        em.getTransaction().commit();
    }
    return shipments;
}

public static ShipmentDAO getInstance(EntityManagerFactory emf)
{
    if (instance == null)
    {
        instance = new ShipmentDAO(emf);
    }
    return instance;
}
}