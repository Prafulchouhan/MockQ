package com.server.mock.Service;

import com.server.mock.model.exam.Quiz;

import java.util.List;
import java.util.Set;

public interface QuizService {

    public Quiz createQuiz(Quiz quiz);

    public Quiz updateQuiz(Quiz quiz);

    public Set<Quiz> getAllQuiz();

    public Quiz getQuizById(Long id);

    public List<Quiz> getQuizByCategoryId(Long ig);

    public void deleteQuiz(Long id);
}
