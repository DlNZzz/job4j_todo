package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Item;
import ru.job4j.persistence.ItemStore;
import ru.job4j.persistence.UserStore;

import java.util.ArrayList;
import java.util.Collection;

@ThreadSafe
@Service
public class UserService {

    private final UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    public void create(Item item) {
        userStore.add(item);
    }

    public Collection<Item> findAll() {
        return new ArrayList<>(userStore.findAll());
    }
}
