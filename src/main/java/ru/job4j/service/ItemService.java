package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Item;
import ru.job4j.persistence.ItemStore;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class ItemService {

    private final ItemStore itemStore;

    public ItemService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public void create(Item item) {
        itemStore.add(item);
    }

    public List<Item> findAll(boolean done) {
        if (done) {
            System.out.println("all");
            return new ArrayList<>(itemStore.findAll());
        } else {
            System.out.println("AllFalse");
            return new ArrayList<>(itemStore.findAllFalse());
        }
    }
}
