package ru.job4j.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.service.TaskService;

import javax.servlet.http.HttpSession;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/allTask")
    private String allTask(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", taskService.findAll());
        return "allTask";
    }

    @GetMapping("/newTask")
    private String newTask(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", taskService.findAll(false));
        return "newTask";
    }

    @GetMapping("/doneTask")
    private String doneTask(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", taskService.findAll(true));
        return "doneTask";
    }

    @GetMapping("/formTask/{taskId}")
    private String formTask(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "formTask";
    }

    @PostMapping("/updateTask")
    private String updateTask(@ModelAttribute Task task) {
        System.out.println(task + " --------------------------");
        taskService.update(task);
        return "formTask";
    }

    @PostMapping("/deleteTask")
    private String deleteTask(@ModelAttribute Task task) {
        taskService.delete(task);
        return "redirect:/index";
    }

    @PostMapping("/doneTask")
    private String doneTask(@ModelAttribute Task task) {
        taskService.done(task);
        return "formTask";
    }
}
