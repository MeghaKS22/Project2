package com.example.Project.Controller;

import com.example.Project.Service.ItemNotFoundException;
import com.example.Project.Service.UserImpl;
import com.example.Project.Service.UserService;
import com.example.Project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.transaction.InvalidTransactionException;

@RestController
@RequestMapping("api/v2")
public class UserController {
    @Autowired
    UserImpl us;

    @PostMapping("/users/add")

    public ResponseEntity<Object> userRegistration(@RequestBody User u) {
        try {
            User resp = us.userRegistration(u);
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{email}/{password}")
    public ResponseEntity<Object> userAuthentication(@PathVariable("email") String email, @PathVariable("password") String password) {
        boolean res = us.userAuthentication(email, password);
        if (res) {
            return new ResponseEntity<>("user valid", HttpStatus.ACCEPTED);


        } else {
            return new ResponseEntity<>("Invalid User", HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> userProfileManagement(@RequestBody User u, @PathVariable(name = "id") Integer userId) {
        try {
            User resp = us.userProfileManagement(u, userId);
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        } catch (EntityExistsException | InvalidTransactionException | ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/resetPassword/{id}")
    public ResponseEntity<Object> resetPassword(@PathVariable(name = "id") Integer userId, @RequestBody String password) {

        try {
            us.resetPassword(userId, password);
            return new ResponseEntity<>(userId, HttpStatus.OK);

        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
@PutMapping("/status/{id}")
        public ResponseEntity<Object> deactivateorBan(@PathVariable(name = "id") Integer userId, @RequestBody String status) {

            try {
                us.deactivateorBan(userId, status);
                return new ResponseEntity<>(userId, HttpStatus.OK);
            } catch (ItemNotFoundException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }


    }
}
