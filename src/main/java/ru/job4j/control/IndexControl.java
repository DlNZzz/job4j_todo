package ru.job4j.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.model.DemoDto;
import ru.job4j.model.Item;
import ru.job4j.service.ItemService;

@ThreadSafe
@Controller
public class IndexControl {

    private DemoDto demoDto = new DemoDto();
    private final ItemService itemService;

    public IndexControl(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    private String index(Model model, @ModelAttribute DemoDto demoDt) {
        model.addAttribute("items", itemService.findAll(demoDto.getDone()));
        return "index";
    }

    @PostMapping("/index")
    private String receiveValues(Model model, @ModelAttribute DemoDto demoDto) {
        this.demoDto = demoDto;
        return "redirect:/index";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        itemService.create(item);
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