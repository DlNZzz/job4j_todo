package ru.job4j.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import ru.job4j.service.UserService;

@ThreadSafe
@Controller
public class RegistrationControl {

    private final UserService userService;

    public RegistrationControl(UserService userService) {
        this.userService = userService;
    }
}
