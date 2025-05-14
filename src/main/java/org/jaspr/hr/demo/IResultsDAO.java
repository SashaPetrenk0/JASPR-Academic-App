package org.jaspr.hr.demo;

public interface IResultsDAO {

     void addQuestionResult(Student student, Quiz quiz, Question question,  int correct);

     void getResultsByQuestion(int questionId, int correct);

     void getResultsByQuiz(int quizId);
     void addQuizResult(int studentID, int quizID, int grade);




}
