import org.jaspr.hr.demo.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.List;


public class QuestionTests {
    @BeforeEach
    public void setup() {
        String questionText  = "What is the capital of France?";
        String[] choices = {"Berlin", "Paris", "Rome", "Madrid"};
        int correctAnswerIndex = 2;

        Question question = new Question(questionText, choices, correctAnswerIndex);
    }

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
}
