package com.example.Project.Service;
import com.example.Project.Repository.UserRepository;
import com.example.Project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.InvalidTransactionException;
import java.util.Objects;

import static java.lang.Character.toUpperCase;

@Service
public class UserImpl implements UserService {

    @Autowired
    UserRepository ur;

    @Override
    public User userRegistration(User newUser) {
        //User u has the item returned by findByEmail. If email exists dont add
        User u = ur.findByEmail(newUser.getEmail());
        if (u == null) {
            ur.save(newUser);
            return newUser;
        } else {
            throw new EntityExistsException("email Id already exists");
        }
    }

    @Override
    public boolean userAuthentication(String email, String password) {
        User u = ur.findByEmail(email);
        return u.getPassword().equals(password);
    }

    @Override
    public User userProfileManagement(User updatedUser, Integer userId) throws InvalidTransactionException, ItemNotFoundException {
        User oldUser = ur.findById(userId).orElseThrow(() -> new ItemNotFoundException("No user exists with given id"));
        oldUser.setName(Objects.isNull(updatedUser.getName()) ? oldUser.getName() : updatedUser.getName());
        //oldUser.setEmail(Objects.isNull(updatedUser.getEmail())?oldUser.getEmail():updatedUser.getEmail());
        if (!Objects.isNull(updatedUser.getEmail())) {
            User u = ur.findByEmail(updatedUser.getEmail());
            if (u == null) {
                oldUser.setEmail(updatedUser.getEmail());
                ur.save(oldUser);

            } else {
                throw new EntityExistsException("email address already used by someone else");
            }
        }
        oldUser.setAddress(Objects.isNull(updatedUser.getAddress()) ? oldUser.getAddress() : updatedUser.getAddress());
        oldUser.setPassword(Objects.isNull(updatedUser.getPassword()) ? oldUser.getPassword() : updatedUser.getPassword());
        oldUser.setPhoneNumber(Objects.isNull(updatedUser.getPhoneNumber()) ? oldUser.getPhoneNumber() : updatedUser.getPhoneNumber());
        oldUser.setDob(Objects.isNull(updatedUser.getDob()) ? oldUser.getDob() : updatedUser.getDob());
        if (!Objects.isNull(updatedUser.getGender())) {
            switch (updatedUser.getGender()) {
                case "m", "f", "o", "M", "F", "O":
                    oldUser.setGender((updatedUser.getGender()));
                default:
                    throw new InvalidTransactionException("Gender type not valid");
            }
        }oldUser.setAccountStatus(String.valueOf(Objects.isNull(updatedUser.getAccountStatus())?oldUser.getAccountStatus():updatedUser.getAccountStatus()));
        /*if (!Objects.isNull(updatedUser.getAccountStatus())) {
            switch (updatedUser.getAccountStatus()) {
                case "a", "b", "i", "A", "B", "I":
                    oldUser.setAccountStatus(updatedUser.getAccountStatus());
                    ur.save(oldUser);
                default:
                    throw new InvalidTransactionException("No such account status");
            }
        }
        oldUser.setLastLogin(Objects.isNull(updatedUser.getLastLogin()) ? oldUser.getLastLogin() : updatedUser.getLastLogin());
        /*if(oldUser.getPermissions()!=null) {
            permissionManagement(userId, oldUser.getPermissions());
        }*/
        ur.save(oldUser);
        return oldUser;
    }

    @Override
    public void resetPassword(Integer userId, String password) throws ItemNotFoundException {
        User oldUser = ur.findById(userId).orElseThrow(() -> new ItemNotFoundException("invalid"));
        oldUser.setPassword(password);
        ur.save(oldUser);

    }

    @Override
    public void deactivateorBan(Integer userID, String password) throws ItemNotFoundException {

    }

    @Override
    public void deactivateorBan(Integer userId, boolean status) throws ItemNotFoundException {
        System.out.println(status);
        User oldUser = ur.findById(userId).orElseThrow(() -> new ItemNotFoundException("invalid"));
        if(status = Boolean.parseBoolean("A")){
            oldUser.setAccountStatus(String.valueOf(status));
            ur.save(oldUser);
        }
        else{
            oldUser.setAccountStatus(null);
            ur.save(oldUser);
        }

        }
        /*switch (status) {
            case "A", "B", "I", "a", "b", "i" -> {
                oldUser.setAccountStatus(status);
                ur.save(oldUser);

            }
        }
    }
   /* @Override
        public void resetPassword(Integer userId, String password) throws ItemNotFoundException {
            User oldUser = ur.findById(userId).orElseThrow(() -> new ItemNotFoundException("No user exists with given id"));
            oldUser.setPassword(password);
            ur.save(oldUser);
        }

   }


    @Override
        public void DeactivateOrBan(Integer userId, String status) throws ItemNotFoundException {
            User oldUser = ur.findById(userId).orElseThrow(() -> new ItemNotFoundException("No user exists with given id"));
            switch (status) {
                case "a", "b", "i", "A", "B", "I" -> {
                    oldUser.setAccountStatus(status);
                    ur.save(oldUser);
                }
            }
        }*/


        //  public User permissionManagement(Integer userId, HashMap<String,Boolean> userInput) {
        //     User oldUser = ur.findById(userId).orElseThrow(() -> new ItemNotFoundException("No user exists with given id"));
        //     for (Map.Entry data : userInput.entrySet()) {
        //         //each data value i will update
        //         if(oldUser.getPermissions().containsKey(data.getKey()))
        //         {
        //             HashMap<String, Boolean> perm = oldUser.getPermissions();
        //             perm.replace((String) data.getKey(), (Boolean) data.getValue());
        //         }
        //         else {
        //             throw new ItemNotFoundException("Cannot update permission to something that doesn't exist");
        //         }
        //     }
        //     ur.save(oldUser);
        //     return oldUser;
        // }


    }

