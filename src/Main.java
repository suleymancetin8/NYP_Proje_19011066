import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Child {
    private String username;
    private String password;
    private int score;
    private int bestScore;

    public Child(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.bestScore = 0;
    }

    // Getters and setters

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

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
}

class Parent {
    private String username;
    private String password;
    private ArrayList<Child> childrenList;
    private int maxOperandA;
    private int maxOperandB;
    private int questionCount;

    public Parent(String username, String password) {
        this.username = username;
        this.password = password;
        this.childrenList = new ArrayList<>();
        this.maxOperandA = 5;
        this.maxOperandB = 8;
        this.questionCount = 10;
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Child> getChildrenList() {
        return childrenList;
    }

    public int getMaxOperandA() {
        return maxOperandA;
    }

    public void setMaxOperandA(int maxOperandA) {
        this.maxOperandA = maxOperandA;
    }

    public int getMaxOperandB() {
        return maxOperandB;
    }

    public void setMaxOperandB(int maxOperandB) {
        this.maxOperandB = maxOperandB;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public void addChild(Child child) {
        childrenList.add(child);
    }

    public void removeChild(Child child) {
        childrenList.remove(child);
    }

    public Child getChildByUsername(String username) {
        for (Child child : childrenList) {
            if (child.getUsername().equals(username)) {
                return child;
            }
        }
        return null;
    }
}

class LoginScreen extends JFrame {
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals("parent") && password.equals("admin")) {
                    openParentScreen();
                } else {
                    openChildScreen(username);
                }
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        pack();
        setVisible(true);
    }

    private void openParentScreen() {
        ParentScreen parentScreen = new ParentScreen();
        dispose();
    }

    private void openChildScreen(String username) {
        ChildScreen childScreen = new ChildScreen(username);
        dispose();
    }
}

class ParentScreen extends JFrame {
    private JList<Child> childList;
    private DefaultListModel<Child> childListModel;
    private JScrollPane childListScrollPane;
    private JLabel operandALabel;
    private JTextField operandAField;
    private JLabel operandBLabel;
    private JTextField operandBField;
    private JLabel questionCountLabel;
    private JTextField questionCountField;
    private JButton saveButton;
    private JButton addChildButton;
    private JButton removeChildButton;
    private JButton logoutButton;

    public ParentScreen() {
        setTitle("Parent Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        operandALabel = new JLabel("Max Operand A:");
        operandAField = new JTextField(10);
        operandBLabel = new JLabel("Max Operand B:");
        operandBField = new JTextField(10);
        questionCountLabel = new JLabel("Question Count:");
        questionCountField = new JTextField(10);
        saveButton = new JButton("Save");
        addChildButton = new JButton("Add Child");
        removeChildButton = new JButton("Remove Child");
        logoutButton = new JButton("Logout");

        operandAField.setText(String.valueOf(Main.parent.getMaxOperandA()));
        operandBField.setText(String.valueOf(Main.parent.getMaxOperandB()));
        questionCountField.setText(String.valueOf(Main.parent.getQuestionCount()));

        operandALabel.setHorizontalAlignment(SwingConstants.CENTER);
        operandBLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int maxOperandA = Integer.parseInt(operandAField.getText());
                int maxOperandB = Integer.parseInt(operandBField.getText());
                int questionCount = Integer.parseInt(questionCountField.getText());

                Main.parent.setMaxOperandA(maxOperandA);
                Main.parent.setMaxOperandB(maxOperandB);
                Main.parent.setQuestionCount(questionCount);

                JOptionPane.showMessageDialog(ParentScreen.this, "Settings saved successfully!");
            }
        });

        addChildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(ParentScreen.this, "Enter child username:");
                String password = JOptionPane.showInputDialog(ParentScreen.this, "Enter child password:");
                if (username != null && password != null) {
                    Child child = new Child(username, password);
                    Main.parent.addChild(child);
                    childListModel.addElement(child);
                }
            }
        });

        removeChildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Child selectedChild = childList.getSelectedValue();
                if (selectedChild != null) {
                    Main.parent.removeChild(selectedChild);
                    childListModel.removeElement(selectedChild);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginScreen();
            }
        });

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 2, 10, 10));
        settingsPanel.add(operandALabel);
        settingsPanel.add(operandAField);
        settingsPanel.add(operandBLabel);
        settingsPanel.add(operandBField);
        settingsPanel.add(questionCountLabel);
        settingsPanel.add(questionCountField);

        JPanel childPanel = new JPanel();
        childPanel.setLayout(new BorderLayout());

        childListModel = new DefaultListModel<>();
        for (Child child : Main.parent.getChildrenList()) {
            childListModel.addElement(child);
        }

        childList = new JList<>(childListModel);
        childListScrollPane = new JScrollPane(childList);

        childPanel.add(childListScrollPane, BorderLayout.CENTER);

        JPanel childButtonPanel = new JPanel();
        childButtonPanel.setLayout(new FlowLayout());
        childButtonPanel.add(addChildButton);
        childButtonPanel.add(removeChildButton);

        childPanel.add(childButtonPanel, BorderLayout.SOUTH);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout());
        logoutPanel.add(logoutButton);

        add(settingsPanel, BorderLayout.NORTH);
        add(childPanel, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void openLoginScreen() {
        LoginScreen loginScreen = new LoginScreen();
        dispose();
    }
}

class ChildScreen extends JFrame {
    private JLabel usernameLabel;
    private JLabel scoreLabel;
    private JLabel bestScoreLabel;
    private JLabel questionNumberLabel;
    private JLabel timerLabel;
    private JTextField answerField;
    private JButton submitButton;
    private JButton logoutButton;

    private javax.swing.Timer timer;
    private int questionNumber;
    private int secondsRemaining;
    private int childScore;
    private boolean questionAnswered;

    public ChildScreen(String username) {
        setTitle("Child Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        Child child = Main.parent.getChildByUsername(username);

        usernameLabel = new JLabel("Username: " + child.getUsername());
        scoreLabel = new JLabel("Score: " + child.getScore());
        bestScoreLabel = new JLabel("Best Score: " + child.getBestScore());
        questionNumberLabel = new JLabel("Question Number: ");
        timerLabel = new JLabel("Time Remaining: ");
        answerField = new JTextField(10);
        submitButton = new JButton("Submit");
        logoutButton = new JButton("Logout");

        questionNumber = 1;
        secondsRemaining = 60;
        childScore = 0;
        questionAnswered = false;

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!questionAnswered) {
                    checkAnswer(child);
                    questionAnswered = true;
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginScreen();
            }
        });

        timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsRemaining--;
                timerLabel.setText("Time Remaining: " + secondsRemaining + " seconds");

                if (secondsRemaining <= 0) {
                    timer.stop();
                    if (!questionAnswered) {
                        checkAnswer(child);
                        questionAnswered = true;
                    }
                    if (questionNumber <= Main.parent.getQuestionCount()) {
                        displayNextQuestion(child);
                    } else {
                        finishQuiz(child);
                    }
                }
            }
        });

        displayNextQuestion(child);

        add(usernameLabel);
        add(scoreLabel);
        add(bestScoreLabel);
        add(questionNumberLabel);
        add(timerLabel);
        add(answerField);
        add(submitButton);
        add(logoutButton);

        pack();
        setVisible(true);

        timer.start();
    }

    private void displayNextQuestion(Child child) {
        questionNumberLabel.setText("Question Number: " + questionNumber);
        timerLabel.setText("Time Remaining: " + secondsRemaining + " seconds");
        answerField.setText("");
        questionAnswered = false;
        secondsRemaining = 60;
        timer.restart();
    }

    private void checkAnswer(Child child) {
        int answer = Integer.parseInt(answerField.getText());
        int operandA = getRandomNumber(Main.parent.getMaxOperandA());
        int operandB = getRandomNumber(Main.parent.getMaxOperandB());
        int correctAnswer = operandA * operandB;

        if (answer == correctAnswer) {
            int questionScore = 60 - secondsRemaining;
            child.setScore(child.getScore() + questionScore);
            child.setBestScore(Math.max(child.getBestScore(), questionScore));
            scoreLabel.setText("Score: " + child.getScore());
            bestScoreLabel.setText("Best Score: " + child.getBestScore());
            JOptionPane.showMessageDialog(ChildScreen.this, "Correct answer! You earned " + questionScore + " points.");
        } else {
            JOptionPane.showMessageDialog(ChildScreen.this, "Incorrect answer! The correct answer is " + correctAnswer);
        }

        questionNumber++;
        if (questionNumber <= Main.parent.getQuestionCount()) {
            displayNextQuestion(child);
        } else {
            finishQuiz(child);
        }
    }

    private int getRandomNumber(int max) {
        return (int) (Math.random() * max) + 1;
    }

    private void finishQuiz(Child child) {
        timer.stop();
        JOptionPane.showMessageDialog(ChildScreen.this, "Quiz finished! Your score: " + child.getScore());
    }

    private void openLoginScreen() {
        LoginScreen loginScreen = new LoginScreen();
        dispose();
    }
}

public class Main {
    public static Parent parent;

    public static void main(String[] args) {
        parent = new Parent("parent", "admin");

        LoginScreen loginScreen = new LoginScreen();
    }
}
