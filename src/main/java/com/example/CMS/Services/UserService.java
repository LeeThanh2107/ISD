package com.example.CMS.Services;

import com.example.CMS.Model.User;
import com.example.CMS.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> index(){
        return userRepository.findAll();
    }

    public User detail(Long id){
        return userRepository.getReferenceById(id);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void store(User user){
        userRepository.save(user);
    }
    public void resetPassword(User user){
        userRepository.save(user);
    }
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
