import org.jaspr.hr.demo.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizTest {
    private static final int ID = 1;
    private static final int ID_TWO = 2;
    private static final String QUIZ_TITLE = "Chemistry";
    private static final String QUIZ_TITLE_TWO = "Physics";
    private static final String DESC = "Multiple choice quiz about chem";
    private static final String DESC_TWO = "Multiple choice quiz about physics";
    private static final String TOPIC = "Chemical reactions";
    private static final String TOPIC_TWO = "Forces";
    private static final int NUMOFQUESTIONS = 5;
    private static final int NUMOFQUESTIONSTWO = 10;
    private static final int AUTHOR = 1;
    private static final int AUTHOR_TWO = 2;


    private Quiz quiz;
    private Quiz quizTwo;

    @BeforeEach
    public void setUp() {
        quiz = new Quiz(1,QUIZ_TITLE,DESC,TOPIC,NUMOFQUESTIONS,AUTHOR);

        // quizTwo = new Quiz(QUIZ_TITLE_TWO,DESC_TWO,TOPIC_TWO,NUMOFQUESTIONSTWO,AUTHOR_TWO);
    }



    @Test
    public void testGetTitle() {
        assertEquals(QUIZ_TITLE, quiz.getTitle());
    }
    @Test
    public void testSetTitle() {
            quiz.setTitle(QUIZ_TITLE_TWO);
        assertEquals(QUIZ_TITLE_TWO, quiz.getTitle());
    }
    @Test
    public void testGetDescription() {
        assertEquals(DESC, quiz.getDescription());
    }
    @Test
    public void testSetDescription() {
        quiz.setDescription(DESC_TWO);
        assertEquals(DESC_TWO, quiz.getDescription());
    }
    @Test
    public void testGetTopic() {
        assertEquals(TOPIC, quiz.getTopic());
    }
    @Test
    public void testSetTopic() {
        quiz.setTopic(TOPIC_TWO);
        assertEquals(TOPIC_TWO, quiz.getTopic());
    }
    @Test
    public void testGetNumOfQuestions() {
        assertEquals(NUMOFQUESTIONS, quiz.getNumOfQuestions());
    }
    @Test
    public void testSetNumOfQuestions() {
        quiz.setNumOfQuestions(NUMOFQUESTIONSTWO);
        assertEquals( NUMOFQUESTIONSTWO, quiz.getNumOfQuestions());
    }

    @Test
    public void testGetAuthor() {
        assertEquals(AUTHOR, quiz.getAuthor());
    }
    @Test
    public void testSetAuthor() {
        quiz.setAuthor(AUTHOR_TWO);
        assertEquals( AUTHOR_TWO, quiz.getAuthor());
    }



}