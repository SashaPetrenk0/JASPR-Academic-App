import org.jaspr.hr.demo.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class QuestionTests {
    @Test
    public void testConstructor() {
        String questionText  = "What is the capital of France?";
        String[] choices = {"Berlin", "Paris", "Rome", "Madrid"};
        int correctAnswerIndex = 2;

        Question question = new Question(questionText, choices, correctAnswerIndex);

        assertEquals(questionText, question.getQuestion());
        assertArrayEquals(choices, question.getChoices());
        assertEquals(correctAnswerIndex, question.getCorrectAnswerIndex());
    }

    @Test
    public void testGetChoices() {
        String[] choices = {"Red", "Green", "Blue"};
        Question question = new Question("What colour is the sky?", choices, 2);

        assertEquals("Red", question.getChoices()[0]);
        assertEquals("Green", question.getChoices()[1]);
        assertEquals("Blue", question.getChoices()[2]);
    }
}
