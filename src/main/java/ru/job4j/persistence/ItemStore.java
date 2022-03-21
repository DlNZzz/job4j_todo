package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Repository
public class ItemStore {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    public Collection<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        System.out.println(item);
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }
}
