package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Category;
import ru.job4j.model.Task;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@ThreadSafe
@Repository
public class TaskStore {

    private final SessionFactory sessionFactory;

    public TaskStore(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Collection<Task> findAll() {
        return this.tx(
                session -> session.createQuery("select distinct t from Task t join fetch t.categories")
                        .list()
        );
    }

    public Collection<Task> findAll(boolean done) {
        return this.tx(
                session -> {
                    Query query = session.createQuery(
                            "select distinct t from Task t join fetch t.categories where t.done = :done");
                    query.setParameter("done", done);
                    return query.list();
                }
        );
    }

    public void add(Task task) {
        this.tx(
                session -> session.save(task)
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

    public Task findById(int id) {
        return this.tx(
                session -> session.get(Task.class, id)
        );
    }

    public void update(Task task) {
        this.tx(
                session -> {
                    Task task1 = findById(task.getId());
                    task.setCategories(task1.getCategories());
                    session.update(task);
                    return null;
                }
        );
    }

    public void delete(Task task) {
        this.tx(
                session -> {
                    session.delete(task);
                    return null;
                }
        );
    }

    public void done(Task task) {
        task.setDone(true);
        this.tx(
                session -> {
                    Task task1 = findById(task.getId());
                    task.setCategories(task1.getCategories());
                    session.update(task);
                    return null;
                }
        );
    }

    public List<Category> getAllCategories() {
        return this.tx(
                session -> session.createQuery("from Category").list()
        );
    }
}
