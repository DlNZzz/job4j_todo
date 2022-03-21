package ru.job4j.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Item;
import ru.job4j.service.ItemService;

@ThreadSafe
@Controller
public class IndexControl {

    private final ItemService itemService;

    public IndexControl(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "index";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        itemService.create(item);
        return "redirect:/index";
    }
}