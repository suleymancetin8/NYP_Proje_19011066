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

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 105, 180),  // Custom pink color
                        getWidth(), getHeight(), new Color(0, 0, 255)); // Custom blue color
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
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

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 105, 180),  // Custom pink color
                        getWidth(), getHeight(), new Color(0, 0, 255)); // Custom blue color
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
            try {
                maxA = Integer.parseInt(maxAField.getText());
                maxB = Integer.parseInt(maxBField.getText());
                numQuestions = Integer.parseInt(numQuestionsField.getText());
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(frame, "Please enter valid integers in all fields");
            }
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

    private void showAddChildScreen() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 105, 180),  // Custom pink color
                        getWidth(), getHeight(), new Color(0, 0, 255)); // Custom blue color
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };        panel.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a username and password");
                return;
            }

            users.add(new User(username, password, false));
            showParentScreen();
        });

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(saveButton);

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showEditChildScreen() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 105, 180),  // Custom pink color
                        getWidth(), getHeight(), new Color(0, 0, 255)); // Custom blue color
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (User user : users) {
            if (!user.isAdmin()) {
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new GridLayout(1, 4));

                JLabel usernameLabel = new JLabel(user.getUsername());
                JButton changePasswordButton = new JButton("Change Password");
                JButton removeButton = new JButton("Remove");
                JButton viewScoreButton = new JButton("View Score");

                changePasswordButton.addActionListener(e -> {
                    String newPassword = JOptionPane.showInputDialog(frame, "Enter a new password:");
                    user.setPassword(newPassword);
                });

                removeButton.addActionListener(e -> {
                    users.remove(user);
                    showEditChildScreen();
                });

                viewScoreButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(frame, "User's score: " + user.getBestScore());
                });

                userPanel.add(usernameLabel);
                userPanel.add(changePasswordButton);
                userPanel.add(removeButton);
                userPanel.add(viewScoreButton);

                panel.add(userPanel);
            }
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showParentScreen());
        panel.add(backButton);

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showChildScreen() {
        frame.getContentPane().removeAll();

        if (questionsAnswered == numQuestions) {
            if (totalScore > currentUser.getBestScore()) {
                currentUser.setBestScore(totalScore);
            }
            questionsAnswered = 0;
            totalScore = 0;
            JOptionPane.showMessageDialog(frame, "You finished all the questions! Your total score is: " + currentUser.getBestScore());
            showLoginScreen();
            return;
        }

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(255, 105, 180),  // Custom pink color
                        getWidth(), getHeight(), new Color(0, 0, 255)); // Custom blue color
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridLayout(3, 2));

        Random random = new Random();
        int a = random.nextInt(maxA + 1);
        int b = random.nextInt(maxB + 1);

        JLabel questionLabel = new JLabel(a + " x " + b + " = ?");
        JTextField answerField = new JTextField();
        JLabel timerLabel = new JLabel();
        JLabel scoreLabel = new JLabel("Current score: " + totalScore + ", Best score: " + currentUser.getBestScore());
        JLabel questionCounterLabel = new JLabel("Question: " + (questionsAnswered + 1) + " / " + numQuestions);

        if (questionTimer != null && questionTimer.isRunning()) {
            questionTimer.stop();
        }

        currentQuestionTime = 60;
        timerLabel.setText(currentQuestionTime + "s");
        questionTimer = new Timer(1000, e -> {
            currentQuestionTime--;
            timerLabel.setText(currentQuestionTime + "s");

            if (currentQuestionTime == 0) {
                JOptionPane.showMessageDialog(frame, "Time's up!");
                questionsAnswered++;
                if (questionsAnswered == numQuestions) {
                    if (totalScore > currentUser.getBestScore()) {
                        currentUser.setBestScore(totalScore);
                    }
                    questionsAnswered = 0;
                    totalScore = 0;
                    JOptionPane.showMessageDialog(frame, "You finished all the questions! Your total score is: " + currentUser.getBestScore());
                    showLoginScreen();
                    return;
                }
                showChildScreen();
            }
        });
        questionTimer.start();

        JButton answerButton = new JButton("Answer");
        answerButton.addActionListener(e -> {
            questionTimer.stop();
            int answer = Integer.parseInt(answerField.getText());

            if (answer == a * b) {
                totalScore += currentQuestionTime;
            }

            questionsAnswered++;
            if (questionsAnswered == numQuestions) {
                if (totalScore > currentUser.getBestScore()) {
                    currentUser.setBestScore(totalScore);
                }
                questionsAnswered = 0;
                totalScore = 0;
                JOptionPane.showMessageDialog(frame, "You finished all the questions! Your total score is: " + currentUser.getBestScore());
                showLoginScreen();
                return;
            }
            showChildScreen();
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            questionTimer.stop();
            questionsAnswered = 0;
            totalScore = 0;
            showLoginScreen();
        });

        panel.add(questionLabel);
        panel.add(answerField);
        panel.add(timerLabel);
        panel.add(scoreLabel);
        panel.add(questionCounterLabel);
        panel.add(answerButton);
        panel.add(logoutButton);

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }
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
