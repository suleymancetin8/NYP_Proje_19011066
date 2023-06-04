import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
    private Timer questionTimer;
    private int currentQuestionTime;

    public MultiplicationLearningApp() {
        users = new ArrayList<>();
        users.add(new User("parent", "admin", true));

        frame = new JFrame("Multiplication Learning App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        showLoginScreen();
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

            JOptionPane.showMessageDialog(frame, "Invalid credentials");
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
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(e -> showAddChildScreen());

        JButton editChildButton = new JButton("Edit Child");
        editChildButton.addActionListener(e -> showEditChildScreen());

        panel.add(addChildButton);
        panel.add(editChildButton);

        for (User user : users) {
            if (!user.isAdmin()) {
                panel.add(new JLabel(user.getUsername() + ": " + user.getBestScore()));
            }
        }

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showAddChildScreen() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill out all fields");
                return;
            }

            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(frame, "Username already exists");
                    return;
                }
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

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JComboBox<User> userComboBox = new JComboBox<>();
        for (User user : users) {
            if (!user.isAdmin()) {
                userComboBox.addItem(user);
            }
        }

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            User user = (User) userComboBox.getSelectedItem();

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill out all fields");
                return;
            }

            for (User otherUser : users) {
                if (otherUser != user && otherUser.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(frame, "Username already exists");
                    return;
                }
            }

            user.setUsername(username);
            user.setPassword(password);

            showParentScreen();
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            User user = (User) userComboBox.getSelectedItem();

            users.remove(user);

            showParentScreen();
        });

        panel.add(new JLabel("User:"));
        panel.add(userComboBox);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(saveButton);
        panel.add(deleteButton);

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showChildScreen() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        Random random = new Random();
        int a = random.nextInt(maxA + 1);
        int b = random.nextInt(maxB + 1);

        JLabel questionLabel = new JLabel(a + " x " + b + " = ?");
        JTextField answerField = new JTextField();
        JLabel timerLabel = new JLabel();

        if (questionTimer != null && questionTimer.isRunning()) {
            questionTimer.stop();
        }

        currentQuestionTime = 0;
        questionTimer = new Timer(1000, e -> {
            currentQuestionTime++;
            timerLabel.setText(currentQuestionTime + "s");

            if (currentQuestionTime == 60) {
                JOptionPane.showMessageDialog(frame, "Time's up!");
                showChildScreen();
            }
        });
        questionTimer.start();

        JButton answerButton = new JButton("Answer");
        answerButton.addActionListener(e -> {
            int answer = Integer.parseInt(answerField.getText());

            if (answer == a * b) {
                questionTimer.stop();
                int score = 60 - currentQuestionTime;

                if (score > currentUser.getBestScore()) {
                    currentUser.setBestScore(score);
                }

                JOptionPane.showMessageDialog(frame, "Correct! Your score: " + score);
                showChildScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect, try again.");
            }
        });

        panel.add(questionLabel);
        panel.add(answerField);
        panel.add(timerLabel);
        panel.add(answerButton);

        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MultiplicationLearningApp::new);
    }
}
