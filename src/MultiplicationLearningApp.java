import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiplicationLearningApp {
    private JFrame frame;
    private User currentUser;
    private List<User> users;
    private int maxA = 5;
    private int maxB = 8;
    private int numQuestions = 3;
    private int questionsAnswered = 0;
    private int totalScore = 0;
    private Timer questionTimer;
    private int currentQuestionTime;

    public MultiplicationLearningApp() {
        users = new ArrayList<>();
        users.add(new User("parent", "admin", true));

        frame = new JFrame("Multiplication Learning App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        showLoginScreen();
        frame.setVisible(true);
    }

    private void showLoginScreen() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    currentUser = user;

                    if (user.isAdmin()) {
                        showParentScreen();
                    } else {
                        showChildScreen();
                    }

                    return;
                }
            }

            JOptionPane.showMessageDialog(frame, "Incorrect username or password");
        });

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showParentScreen() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel settingsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField maxAField = new JTextField(Integer.toString(maxA));
        JTextField maxBField = new JTextField(Integer.toString(maxB));
        JTextField numQuestionsField = new JTextField(Integer.toString(numQuestions));
        settingsPanel.add(new JLabel("Max A:"));
        settingsPanel.add(maxAField);
        settingsPanel.add(new JLabel("Max B:"));
        settingsPanel.add(maxBField);
        settingsPanel.add(new JLabel("Number of questions:"));
        settingsPanel.add(numQuestionsField);

        JButton saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.addActionListener(e -> {
            maxA = Integer.parseInt(maxAField.getText());
            maxB = Integer.parseInt(maxBField.getText());
            numQuestions = Integer.parseInt(numQuestionsField.getText());
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(e -> showAddChildScreen());
        JButton editChildButton = new JButton("Edit Child");
        editChildButton.addActionListener(e -> showEditChildScreen());
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> showLoginScreen());

        buttonPanel.add(addChildButton);
        buttonPanel.add(editChildButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        mainPanel.add(saveSettingsButton, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    // methods omitted for brevity

    // public methods for testing
    public List<User> getUsers() {
        return users;
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void setMaxA(int maxA) {
        this.maxA = maxA;
    }

    public void setMaxB(int maxB) {
        this.maxB = maxB;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public int getMaxA() {
        return maxA;
    }

    public int getMaxB() {
        return maxB;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public boolean checkChildAnswer(int a, int b, int answer) {
        return a * b == answer;
    }

    public static void main(String[] args) {
        new MultiplicationLearningApp();
    }
}
