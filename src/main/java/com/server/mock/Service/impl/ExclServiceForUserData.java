package com.server.mock.Service.impl;

import com.server.mock.helper.QuizAttemptExclHelper;
import com.server.mock.helper.UserExclHelper;
import com.server.mock.model.User;
import com.server.mock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExclServiceForUserData {

    @Autowired
    private UserRepository userRepository;

    public ByteArrayInputStream getUserData(String searchTerm) throws IOException {
        List<User> user = userRepository.findBySearchTerm(searchTerm).get();

        ByteArrayInputStream byteArrayInputStream = UserExclHelper.dataToExcel(user);

        return byteArrayInputStream;
    }
    public ByteArrayInputStream getUserQuizAttemptData(Long userId) throws IOException {

        User user = userRepository.findById(userId).get();

        ByteArrayInputStream byteArrayInputStream = QuizAttemptExclHelper.dataToExcel(user.getQuizAttempts(),user);

        return byteArrayInputStream;
    }
}
