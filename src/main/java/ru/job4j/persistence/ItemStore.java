package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

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
        return this.tx(
                session -> session.createQuery("from Item").list()
        );
    }

    public Collection<Item> findAllFalse() {
        return this.tx(
                session -> {
                    /*
                    String sql = "select * from items where done is true";
                    Query que = session.createSQLQuery(sql).addEntity(Item.class);
                    return que.list();
                     */
                    Criteria criteria = session.createCriteria(Item.class)
                            .add(Restrictions.eq("done", false));
                    List<Item> list = criteria.list();
                    for (Iterator<Item> it = list.iterator(); it.hasNext();) {
                        Item item = it.next();
                    }
                    return list;
                }
        );
    }

    public void add(Item item) {
        this.tx(
                session -> session.save(item)
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
