package ru.job4j.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.service.TaskService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class IndexControl {

    private final TaskService taskService;

    public IndexControl(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    private String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @PostMapping("/index")
    private String receiveValues(Model model) {
        return "redirect:/index";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        if (user == null) {
            user = new User();
            user.setName("Гость");
            user.setId(1);
            user.setPassword(",");
        }
        task.setUser_id(user);
        taskService.create(task);
        return "redirect:/index";
    }

    @GetMapping("/formLogin")
    public String login(Model model) {
        return "formLogin";
    }

    @GetMapping("/formRegistration")
    public String registration(Model model) {
        return "formRegistration";
    }
}