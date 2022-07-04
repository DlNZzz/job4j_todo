package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.Category;
import ru.job4j.model.Task;
import ru.job4j.persistence.TaskStore;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class TaskService {

    private final TaskStore taskStore;

    public TaskService(TaskStore taskStore) {
        this.taskStore = taskStore;
    }

    public void create(Task task) {
        taskStore.add(task);
    }

    public List<Task> findAll() {
        return new ArrayList<>(taskStore.findAll());
    }

    public List<Task> findAll(boolean done) {
        return new ArrayList<>(taskStore.findAll(done));
    }

    public Task findById(int id) {
        return taskStore.findById(id);
    }

    public void update(Task task) {
        taskStore.update(task);
    }

    public void delete(Task task) {
        taskStore.delete(task);
    }

    public void done(Task task) {
        taskStore.done(task);
    }

    public List<Category> getAllCategories() {
        return taskStore.getAllCategories();
    }
}
