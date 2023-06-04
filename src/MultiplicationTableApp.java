import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MultiplicationTableApp {
    private JFrame frame;
    private JPanel loginPanel;
    private JPanel parentPanel;
    private JPanel childPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Map<String, String> parentCredentials;
    private Map<String, Child> children;

    private int operandA;
    private int operandB;
    private int questionCount;

    public MultiplicationTableApp() {
        parentCredentials = new HashMap<>();
        parentCredentials.put("parent", "admin");

        children = new HashMap<>();
        children.put("child1", new Child("child1", "pass1"));
        children.put("child2", new Child("child2", "pass2"));

        operandA = 5;
        operandB = 8;
        questionCount = 10;

        initializeGUI();
        setupLoginPanel();
        setupParentPanel();
        setupChildPanel();
    }

    private void initializeGUI() {
        frame = new JFrame("Multiplication Table App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new CardLayout());

        loginPanel = new JPanel();
        parentPanel = new JPanel();
        childPanel = new JPanel();

        frame.add(loginPanel, "login");
        frame.add(parentPanel, "parent");
        frame.add(childPanel, "child");
    }

    private void setupLoginPanel() {
        loginPanel.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        loginPanel.add(usernameLabel);

        usernameField = new JTextField(10);
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField(10);
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (parentCredentials.containsKey(username) && parentCredentials.get(username).equals(password)) {
                    usernameField.setText("");
                    passwordField.setText("");
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(parentPanel);
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();
                } else if (children.containsKey(username) && children.get(username).getPassword().equals(password)) {
                    usernameField.setText("");
                    passwordField.setText("");
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(childPanel);
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();
                    startQuiz(username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginPanel.add(loginButton);
    }

    private void setupParentPanel() {
        parentPanel.setLayout(new FlowLayout());

        JLabel aLabel = new JLabel("Operand A:");
        JTextField aField = new JTextField(String.valueOf(operandA), 10);
        parentPanel.add(aLabel);
        parentPanel.add(aField);

        JLabel bLabel = new JLabel("Operand B:");
        JTextField bField = new JTextField(String.valueOf(operandB), 10);
        parentPanel.add(bLabel);
        parentPanel.add(bField);

        JLabel countLabel = new JLabel("Question Count:");
        JTextField countField = new JTextField(String.valueOf(questionCount), 10);
        parentPanel.add(countLabel);
        parentPanel.add(countField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operandA = Integer.parseInt(aField.getText());
                operandB = Integer.parseInt(bField.getText());
                questionCount = Integer.parseInt(countField.getText());
                JOptionPane.showMessageDialog(frame, "Settings updated successfully", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        parentPanel.add(updateButton);
    }

    private void setupChildPanel() {
        childPanel.setLayout(new BorderLayout());

        JPanel questionPanel = new JPanel();
        JLabel questionLabel = new JLabel("Question:");
        JLabel questionText = new JLabel();
        JTextField answerField = new JTextField(5);
        JButton submitButton = new JButton("Submit");
        JLabel counterLabel = new JLabel("Time left: 60 seconds");
        JLabel scoreLabel = new JLabel("Score: 0");
        JLabel resultLabel = new JLabel();

        submitButton.addActionListener(new ActionListener() {
            private int questionNumber = 1;
            private int score = 0;
            private long startTime;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (questionNumber > questionCount) {
                    JOptionPane.showMessageDialog(frame, "You have answered all the questions!\nFinal score: " + score,
                            "Quiz Completed", JOptionPane.INFORMATION_MESSAGE);
                    children.get(usernameField.getText()).setScore(score);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(loginPanel);
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();
                    return;
                }

                String answer = answerField.getText();

                if (answer.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter an answer.", "Answer Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int result = operandA * operandB;
                long endTime = System.currentTimeMillis();
                long elapsedTime = (endTime - startTime) / 1000;
                int timeLeft = 60 - (int) elapsedTime;

                if (Integer.parseInt(answer) == result && timeLeft >= 0) {
                    score += 60 - timeLeft;
                    resultLabel.setText("Correct!");
                } else {
                    resultLabel.setText("Wrong!");
                }

                answerField.setText("");
                questionNumber++;
                scoreLabel.setText("Score: " + score);
                counterLabel.setText("Time left: " + timeLeft + " seconds");

                if (questionNumber > questionCount) {
                    submitButton.setText("Finish");
                }

                startTime = System.currentTimeMillis();

                // Display the next question
                int nextOperandA = (int) (Math.random() * operandA) + 1;
                int nextOperandB = (int) (Math.random() * operandB) + 1;
                questionText.setText(nextOperandA + " x " + nextOperandB);
            }
        });

        questionPanel.add(questionLabel);
        questionPanel.add(questionText);
        questionPanel.add(answerField);
        questionPanel.add(submitButton);
        questionPanel.add(counterLabel);
        questionPanel.add(scoreLabel);
        questionPanel.add(resultLabel);

        childPanel.add(questionPanel, BorderLayout.CENTER);

        // Initial question display
        int initialOperandA = (int) (Math.random() * operandA) + 1;
        int initialOperandB = (int) (Math.random() * operandB) + 1;
        questionText.setText(initialOperandA + " x " + initialOperandB);
    }

    private void startQuiz(String childUsername) {
        Child child = children.get(childUsername);
        JLabel counterLabel = (JLabel) ((JPanel) childPanel.getComponent(0)).getComponent(4);
        Thread timerThread = new Thread(new Runnable() {
            private int timeLeft = 60;

            @Override
            public void run() {
                while (timeLeft >= 0) {
                    try {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                counterLabel.setText("Time left: " + timeLeft + " seconds");
                            }
                        });
                        Thread.sleep(1000);
                        timeLeft--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(frame, "Time's up!", "Quiz Completed", JOptionPane.INFORMATION_MESSAGE);
                children.get(childUsername).setScore(0);
                frame.getContentPane().removeAll();
                frame.getContentPane().add(loginPanel);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
            }
        });
        timerThread.start();
    }

    public void showLoginScreen() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(loginPanel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    public void startApp() {
        showLoginScreen();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MultiplicationTableApp app = new MultiplicationTableApp();
        app.startApp();
    }

    private class Child {
        private String username;
        private String password;
        private int score;

        public Child(String username, String password) {
            this.username = username;
            this.password = password;
            this.score = 0;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
