package org.jaspr.hr.demo;

import java.util.*;

public interface IResultsDAO {

     void addQuestionResult(Student student, Quiz quiz, Question question,  int correct);

     List<Map<String, Integer>> getResultsByQuestion(int questionId, int correct);

     List<Map<String, Integer>> getResultsByQuiz(int quizId);
     void addQuizResult(int studentID, int quizID, int grade);




}
