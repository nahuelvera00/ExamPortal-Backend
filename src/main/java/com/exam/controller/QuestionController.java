package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    //Add question
    @PostMapping("/")
    public ResponseEntity<Question> add(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    //update question
    @PutMapping("/")
    public ResponseEntity<Question> update(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    //get all questions
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {

        Quiz quiz = new Quiz();
        quiz.setQid(qid);
        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);

       return ResponseEntity.ok(questionsOfQuiz);

       // return ResponseEntity.ok(list);
    }



    //get all questions
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {

//        Quiz quiz = new Quiz();
//        quiz.setQid(qid);
//        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
//
//        return ResponseEntity.ok(questionsOfQuiz);

        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();

        List list = new ArrayList(questions);
        if(list.size() > Integer.parseInt((quiz.getNumberOfQuestions()))){
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
        }

        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    //Get single question
    @GetMapping("/{quesId}")
    public Question get(@PathVariable("quesId") Long quesId){
        return this.questionService.getQuestion(quesId);
    }

    //delete question
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId){
        this.questionService.deleteQuestion(quesId);
    }

    //Eval quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){

        double marksGot = 0;
        int correctAnswer = 0;
        int attempted = 0;

        for( Question q: questions) {

            //single questions
            Question question = this.questionService.get(q.getQuesId());
            if(question.getAnswer().trim().equals(q.getGivenAnswer())){
                //correct
                correctAnswer ++;

                double markSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
                marksGot += markSingle;


            }
            if( q.getGivenAnswer() != null){
                attempted++;
            }
        };

        Map<String, Object> map = Map.of("marksGot", marksGot,"correctAnswer", correctAnswer, "attempted", attempted);
        return ResponseEntity.ok(map);
    }

}
