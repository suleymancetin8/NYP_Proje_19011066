
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MultiplicationLearningAppTest {
    private MultiplicationLearningApp app;

    @BeforeEach
    public void setup() {
        app = new MultiplicationLearningApp();
    }

    @Test
    public void testUserAuthentication() {
        User admin = app.getUsers().get(0);
        assertTrue(app.authenticateUser(admin.getUsername(), admin.getPassword()));
        assertFalse(app.authenticateUser("invalid", "credentials"));
    }

    @Test
    public void testChangeSettings() {
        User admin = app.getUsers().get(0);
        app.authenticateUser(admin.getUsername(), admin.getPassword());

        app.setMaxA(10);
        app.setMaxB(12);
        app.setNumQuestions(5);

        assertEquals(10, app.getMaxA());
        assertEquals(12, app.getMaxB());
        assertEquals(5, app.getNumQuestions());
    }

    @Test
    public void testChildCalculations() {
        User child = new User("child", "childpass", false);
        app.getUsers().add(child);
        app.authenticateUser(child.getUsername(), child.getPassword());

        int correctAnswer = 3 * 2;
        assertTrue(app.checkChildAnswer(3, 2, correctAnswer));
        assertFalse(app.checkChildAnswer(3, 2, correctAnswer + 1));
    }
}


