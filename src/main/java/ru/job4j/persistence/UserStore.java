package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Task;

import java.util.Collection;

@ThreadSafe
@Repository
public class UserStore {

    public void add(Task task) {
    }

    public Collection<Task> findAll() {
        return null;
    }
}
