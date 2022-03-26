package ru.job4j.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Item;

import java.util.Collection;

@ThreadSafe
@Repository
public class UserStore {

    public void add(Item item) {
    }

    public Collection<Item> findAll() {
        return null;
    }
}
