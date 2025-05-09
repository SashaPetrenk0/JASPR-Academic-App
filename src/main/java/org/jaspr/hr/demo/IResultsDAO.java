package org.jaspr.hr.demo;

public interface IResultsDAO {

     void addResult(int studentID, int id, int questionId, boolean correct);

     void getResultsByQuestion(int questionId, boolean correct);

     void getResultsByQuiz(int quizId);




}
