package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.query.internal.QueryImpl;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ThreadSafe
@Repository
public class UserStore {

    private final SessionFactory sessionFactory;

    public UserStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> add(User user) {
        this.tx(
                session -> session.save(user)
        );
        return Optional.of(new User());
    }

    public Optional<User> findUserByEmailAndPwd(String name, String password) {
        return this.tx(
                session -> {
                    Query query = session.createQuery(
                            "FROM User WHERE name = :name AND password = :password"
                    );
                    query.setParameter("name", name);
                    query.setParameter("password", password);
                    return query.uniqueResultOptional();
                }
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
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
