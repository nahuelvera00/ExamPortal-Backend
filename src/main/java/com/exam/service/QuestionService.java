package com.exam.service;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface QuestionService {

    public Question addQuestion(Question question);

    public Question updateQuestion(Question question);

    public Set<Question> getQuestions();

    public Question getQuestion(Long questionId);

    public Set<Question> getQuestionsOfQuiz(Quiz quiz);

    public void deleteQuestion(Long questionId);

    public Question get(Long questionsId);
}
