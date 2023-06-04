import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MultiplicationTableApp {
    private static final int DEFAULT_QUESTION_COUNT = 10;
    private static final int DEFAULT_MAX_OPERAND = 8;
    private static final int DEFAULT_TIME_LIMIT = 60;
    private static final int MAX_SCORE = 60;

    private JFrame frame;
    private JPanel loginPanel;
    private JPanel parentPanel;
    private JPanel childPanel;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton startButton;
    private JTextField parentUsernameField;
    private JPasswordField parentPasswordField;
    private Map<String, String> children;
    private String loggedInUser;
    private int questionCount;
    private int maxOperand;
    private int timeLimit;
    private int bestScore;

    public MultiplicationTableApp() {
        frame = new JFrame("Multiplication Table App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        children = new HashMap<>();

        createLoginPanel();
        createParentPanel();
        createChildPanel();

        showLoginPanel();

        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        parentUsernameField = new JTextField();
        parentPasswordField = new JPasswordField();
        formPanel.add(usernameLabel);
        formPanel.add(parentUsernameField);
        formPanel.add(passwordLabel);
        formPanel.add(parentPasswordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = parentUsernameField.getText();
                String password = new String(parentPasswordField.getPassword());
                if (username.equals("parent") && password.equals("admin")) {
                    loggedInUser = username;
                    showParentPanel();
                } else if (children.containsKey(username) && children.get(username).equals(password)) {
                    loggedInUser = username;
                    showChildPanel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(formPanel, BorderLayout.CENTER);
        loginPanel.add(loginButton, BorderLayout.SOUTH);
    }

    private void createParentPanel() {
        parentPanel = new JPanel(new BorderLayout());

        JPanel settingsPanel = new JPanel(new GridLayout(4, 2));
        JLabel questionCountLabel = new JLabel("Question Count:");
        JLabel maxOperandLabel = new JLabel("Max Operand:");
        JLabel timeLimitLabel = new JLabel("Time Limit (seconds):");
        JTextField questionCountField = new JTextField(Integer.toString(DEFAULT_QUESTION_COUNT));
        JTextField maxOperandField = new JTextField(Integer.toString(DEFAULT_MAX_OPERAND));
        JTextField timeLimitField = new JTextField(Integer.toString(DEFAULT_TIME_LIMIT));
        settingsPanel.add(questionCountLabel);
        settingsPanel.add(questionCountField);
        settingsPanel.add(maxOperandLabel);
        settingsPanel.add(maxOperandField);
        settingsPanel.add(timeLimitLabel);
        settingsPanel.add(timeLimitField);

        JPanel childrenPanel = new JPanel(new GridLayout(0, 2));
        JLabel childUsernameLabel = new JLabel("Child Username:");
        JLabel childPasswordLabel = new JLabel("Child Password:");
        JTextField childUsernameField = new JTextField();
        JPasswordField childPasswordField = new JPasswordField();
        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String childUsername = childUsernameField.getText();
                String childPassword = new String(childPasswordField.getPassword());

                if (childUsername.isEmpty() || childPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both username and password for the child", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    children.put(childUsername, childPassword);
                    JOptionPane.showMessageDialog(frame, "Child added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    childUsernameField.setText("");
                    childPasswordField.setText("");
                }
            }
        });
        childrenPanel.add(childUsernameLabel);
        childrenPanel.add(childUsernameField);
        childrenPanel.add(childPasswordLabel);
        childrenPanel.add(childPasswordField);
        childrenPanel.add(addChildButton);

        JButton saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionCount = Integer.parseInt(questionCountField.getText());
                maxOperand = Integer.parseInt(maxOperandField.getText());
                timeLimit = Integer.parseInt(timeLimitField.getText());
                JOptionPane.showMessageDialog(frame, "Settings saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        parentPanel.add(settingsPanel, BorderLayout.CENTER);
        parentPanel.add(childrenPanel, BorderLayout.NORTH);
        parentPanel.add(saveSettingsButton, BorderLayout.SOUTH);
    }

    private void createChildPanel() {
        childPanel = new JPanel(new BorderLayout());

        JLabel childInfoLabel = new JLabel();
        JLabel scoreLabel = new JLabel();

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startQuiz(childInfoLabel, scoreLabel);
            }
        });

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggedInUser = null;
                showLoginPanel();
            }
        });

        childPanel.add(childInfoLabel, BorderLayout.NORTH);
        childPanel.add(startButton, BorderLayout.CENTER);
        childPanel.add(scoreLabel, BorderLayout.SOUTH);
    }

    private void showLoginPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(loginPanel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void showParentPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(parentPanel);
        frame.getContentPane().add(logoutButton, BorderLayout.SOUTH);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void showChildPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(childPanel);
        frame.getContentPane().add(logoutButton, BorderLayout.SOUTH);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void startQuiz(JLabel childInfoLabel, JLabel scoreLabel) {
        int currentScore = 0;
        int questionNumber = 1;
        long startTime = System.currentTimeMillis();

        while (questionNumber <= questionCount) {
            int operand1 = (int) (Math.random() * maxOperand) + 1;
            int operand2 = (int) (Math.random() * maxOperand) + 1;
            int correctAnswer = operand1 * operand2;

            String userAnswer = JOptionPane.showInputDialog(
                    frame,
                    "Question " + questionNumber + ":\n" + operand1 + " x " + operand2 + " = ?",
                    "Question",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (userAnswer != null) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                int timeScore = calculateTimeScore(elapsedTime);

                if (userAnswer.equals(Integer.toString(correctAnswer))) {
                    currentScore += timeScore;
                }

                questionNumber++;
            } else {
                break; // User canceled the quiz
            }
        }

        int totalScore = calculateTotalScore(currentScore, questionCount);

        if (totalScore > bestScore) {
            bestScore = totalScore;
        }

        childInfoLabel.setText("Child: " + loggedInUser);
        scoreLabel.setText("Score: " + totalScore + " (Best: " + bestScore + ")");
    }

    private int calculateTimeScore(long elapsedTime) {
        if (elapsedTime <= 1000) {
            return MAX_SCORE;
        } else if (elapsedTime >= timeLimit * 1000) {
            return 0;
        } else {
            double percentage = (double) (timeLimit * 1000 - elapsedTime) / (timeLimit * 1000);
            return (int) (percentage * MAX_SCORE);
        }
    }

    private int calculateTotalScore(int currentScore, int questionCount) {
        return (int) ((double) currentScore / (MAX_SCORE * questionCount) * MAX_SCORE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MultiplicationTableApp();
            }
        });
    }
}
