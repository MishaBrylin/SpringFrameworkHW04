package com.example.SpringFrameworkHW04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {


    private final DataProcessingService dataProcessingService;

    private static ArrayList<User> users = new ArrayList<>();



    @Autowired
    public UserController(DataProcessingService helloService) {

        this.dataProcessingService = helloService;
        this.users=users;
    }

    @GetMapping
    public String listUsers(Model model){
        model.addAttribute("users", this.users);
        return "users";

    }

    @RequestMapping(value = "/add",
            method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestParam String name, @RequestParam int age, @RequestParam String email) {
        this.users.add(new User(name, age, email));
        return new ResponseEntity<>(new User(name, age, email), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/sortAllUser", method = RequestMethod.GET)
    public ResponseEntity<List<User>> sortUsers() {
        return new ResponseEntity<>(dataProcessingService.sortUsersByAge(this.users), HttpStatus.OK);
    }

    @RequestMapping(value = "/filterAllUser/{age}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> filterUsersByAge(@PathVariable("age") Integer age) {
        return new ResponseEntity<>(dataProcessingService.filterUsersByAge(this.users, age), HttpStatus.OK);
    }

    @RequestMapping(value = "/averageAllUser", method = RequestMethod.GET)
    public ResponseEntity<Double> average() {
        return new ResponseEntity<>(dataProcessingService.calculateAverageAge(this.users), HttpStatus.OK);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        String responce = this.dataProcessingService.getGreeting();

        return new ResponseEntity<>(responce, HttpStatus.OK);
    }

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public ResponseEntity<List<User>> sortUsers(@RequestBody List<User> users) {
        return new ResponseEntity<>(dataProcessingService.sortUsersByAge(users), HttpStatus.OK);
    }

    @RequestMapping(value = "/filter/{age}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> filterUsersByAge(@PathVariable("age") Integer age, @RequestBody List<User> users) {
        return new ResponseEntity<>(dataProcessingService.filterUsersByAge(users, age), HttpStatus.OK);
    }

    @RequestMapping(value = "/average", method = RequestMethod.POST)
    public ResponseEntity<Double> average(@RequestBody List<User> users) {
        return new ResponseEntity<>(dataProcessingService.calculateAverageAge(users), HttpStatus.OK);
    }

}
