package app.controller;

import app.model.Role;
import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView pageForAdmin() {
        ModelAndView mav = new ModelAndView("admin/page");
        List<User> userList = userService.getAllUsers();
        mav.addObject("userList", userList);
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView getUserById(@RequestParam(name = "id") long id) {
        ModelAndView mav = new ModelAndView("/admin/userById");
        User userById = userService.getUserById(id);
        mav.addObject("userById", userById);
        return mav;
    }

    @GetMapping("/userAdding")
    public ModelAndView userAddingForm() {
        ModelAndView mav = new ModelAndView("/admin/userAdding");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping(value = "/save")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(name = "role", required = false) String...role) {
        Set<Role> roles = userService.setRoles(role);
        user.setRolesOfUser(roles);
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/userUpdating")
    public ModelAndView userUpdatingForm(@RequestParam(name = "id") long id) {
        ModelAndView mav = new ModelAndView("/admin/userUpdating");
        mav.addObject("user", userService.getUserById(id));
        return mav;
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                          @RequestParam(name = "role", required = false) String...role) {
        Set<Role> roles = userService.setRoles(role);
        user.setRolesOfUser(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}