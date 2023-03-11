package com.example.Project.Service;

import com.example.Project.model.User;

import javax.transaction.InvalidTransactionException;

public interface UserService  {

    public User userRegistration(User u);
    public  boolean userAuthentication(String emailID,String password);


   public User userProfileManagement(User u, Integer userIdr) throws InvalidTransactionException, ItemNotFoundException;
   public void resetPassword(Integer userId,String password) throws ItemNotFoundException;


public void  deactivateorBan(Integer userID,String password) throws ItemNotFoundException;


    void deactivateorBan(Integer userId, boolean status) throws ItemNotFoundException;
}
