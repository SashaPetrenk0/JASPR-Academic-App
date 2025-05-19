package org.jaspr.hr.demo;

import java.util.*;

public interface IResultsDAO {

     void addQuestionResult( int quiz, int question, int student, int correct);

     List<Map<String, Integer>> getResultsByQuestion(int questionId, int correct);

     List<Map<String, Object>> getResultsByQuiz(int quizId);
     void addQuizResult(int studentID, int quizID, double grade);




}
