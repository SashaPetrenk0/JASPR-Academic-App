package org.jaspr.hr.demo;

/**
 * Helper class for managing the quiz taking process.
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

    /**
     * Method to get the current question based on what question the user is up to from the array of questions
     * @return a question object
     */
    public Question getCurrentQuestion() {
            if (questionIndex < questions.length) {
                return questions[questionIndex];
            }
            return null;
        }

    /**
     * Adds student result to the question results table
     * @param quizId the id of the quiz that the question is from
     * @param question object that the user answered
     * @param student student object for the student who answered the question
     * @param grade integer either 1 or 0, 1 for a correctly answered question and 0 for an incorrect question
     */

        public void recordAnswerResult(int quizId, Question question, Student student, int grade){
            resultsDAO.addQuestionResult( quizId, question.getId(), student.getStudentID(), grade );
        }

    /**
     * Method to check the answer, increment number of correct answers or incorrect answers accordingly
     * @param answer string A,B,C or D
     * @return true or false according to if the answer matches the correct answer for the question
     */
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

    /**
     * Increment the index of the current question
     * @return the question new question index
     */
    public boolean nextQuestion() {
            questionIndex++;
            return questionIndex < questions.length;
        }

    /**
     * Return the number of the correct questions
     * @return the number of correct answers
     */
    public int getCorrectCount() {
            return correctCount;
        }

    /**
     * get the number of incorrect answers
     * @return the number of incorrect answers
     */
    public int getIncorrectCount() {
            return incorrectCount;
        }

    /**
     * get the length of the question array for the quiz
     * @return the length of the questions array
     */
    public int getTotalQuestions() {
            return questions.length;
        }
    }


