package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView pageForUser() {
        ModelAndView mav = new ModelAndView("/user/page");
        List<User> userList = userService.getAllUsers();
        mav.addObject("userList", userList);
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView getUserById(@RequestParam(name = "id") long id) {
        ModelAndView mav = new ModelAndView("/user/userById");
        User userById = userService.getUserById(id);
        mav.addObject("userById", userById);
        return mav;
    }
}