package com.server.mock.Service;


import com.server.mock.model.exam.Question;
import com.server.mock.model.exam.Quiz;

import java.util.Set;

public interface QuestionService {

    public Question createQuestion(Question question);

    public Question updateQuestion(Question question);

    public Set<Question> getAllQuestion();

    public Question getQuestionById(Long id);

    public void deleteQuestion(Long id);

    public Set<Question> findByQuid(Quiz quiz);
}
