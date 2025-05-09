package org.jaspr.hr.demo;

public interface IResultsDAO {

     void addResult(int studentID, int id, int questionId, int correct);

     void getResultsByQuestion(int questionId, int correct);

     void getResultsByQuiz(int quizId);




}
