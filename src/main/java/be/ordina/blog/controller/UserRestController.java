package be.ordina.blog.controller;

import be.ordina.blog.model.User;
import be.ordina.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = GET)
    public List<User> getAllUsers() {
        logger.info("Request to get all users");
        return userService.getAllUsers();
    }

    @RequestMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        logger.info("Request to get user {}", id);
        return userService.getUserById(id);
    }
}
