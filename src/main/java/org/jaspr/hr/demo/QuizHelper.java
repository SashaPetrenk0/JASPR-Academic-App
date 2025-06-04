package org.jaspr.hr.demo;

/**
 *
 */

public class QuizHelper {
        private Question[] questions;
        private int questionIndex = 0;
        private int correctCount = 0;
        private int incorrectCount = 0;

        private final SqliteResultsDAO resultsDAO = new SqliteResultsDAO();

        public QuizHelper(Question[] questions) {
            this.questions = questions;
        }

        public Question getCurrentQuestion() {
            if (questionIndex < questions.length) {
                return questions[questionIndex];
            }
            return null;
        }
        public void recordAnswerResult(int quizId, Question question, Student student, int grade){
            resultsDAO.addQuestionResult( quizId, question.getId(), student.getStudentID(), grade );
        }

        public boolean checkAnswer(String answer) {
            Question q = getCurrentQuestion();
            boolean correct = answer.equals(q.getCorrectAnswer());
            if (correct) {
                correctCount++;


            } else {
                incorrectCount++;
            }
            return correct;
        }

        public boolean nextQuestion() {
            questionIndex++;
            return questionIndex < questions.length;
        }

        public int getCorrectCount() {
            return correctCount;
        }

        public int getIncorrectCount() {
            return incorrectCount;
        }

        public int getTotalQuestions() {
            return questions.length;
        }
    }


