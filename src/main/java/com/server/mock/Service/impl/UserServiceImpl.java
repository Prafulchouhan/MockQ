package com.server.mock.Service.impl;

import com.server.mock.dto.LoginBody;
import com.server.mock.dto.RegistrationBody;
import com.server.mock.exception.UserAlreadyExistsException;
import com.server.mock.model.User;
import com.server.mock.model.exam.QuizAttempt;
import com.server.mock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private JWTService jwtService;

    public User registerUser(RegistrationBody body) throws UserAlreadyExistsException {
        if(userRepository.findByUserNameIgnoreCase(body.getUserName()).isPresent() ||
                userRepository.findByEmailIgnoreCase(body.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        User user=new User();
        user.setUserName(body.getUserName());
        user.setFirstName(body.getFirstName());
        user.setLastName(body.getLastName());
        user.setRole("NORMAL");
        user.setEmail(body.getEmail());
        user.setPhone(body.getPhone());
        //encrypted password
        user.setPassword(encryptionService.encryptPassword(body.getPassword()));
        return userRepository.save(user);
    }

    public String logIn(LoginBody body){
        Optional<User> user=userRepository.findByUserNameIgnoreCase(body.getUserName());
        if(user.isPresent()){
            User localUser=user.get();
            if(encryptionService.verifyPassword(body.getPassword(),localUser.getPassword())){
                System.out.println("localUser = " + localUser);
                return jwtService.generateJWT(localUser);
            }
        }
        return null;
    }

    public User getName(String name){
        return userRepository.findByUserNameIgnoreCase(name).get();
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).get();
    }

    public List<QuizAttempt> findQuizAttemptsByUserId(Long id){
        return this.userRepository.findById(id).get().getQuizAttempts();
    }
}
