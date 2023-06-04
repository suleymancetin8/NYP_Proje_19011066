import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Child {
    private String name;
    private int score;

    public Child(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
}

class Parent {
    private String username;
    private String password;
    private List<Child> children;
    private int questionCount;
    private int maxOperandValueA;
    private int maxOperandValueB;

    public Parent(String username, String password) {
        this.username = username;
        this.password = password;
        this.children = new ArrayList<>();
        this.questionCount = 10;
        this.maxOperandValueA = 10;
        this.maxOperandValueB = 10;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addChild(Child child) {
        children.add(child);
    }

    public List<Child> getChildren() {
        return children;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getMaxOperandValueA() {
        return maxOperandValueA;
    }

    public void setMaxOperandValueA(int maxOperandValueA) {
        this.maxOperandValueA = maxOperandValueA;
    }

    public int getMaxOperandValueB() {
        return maxOperandValueB;
    }

    public void setMaxOperandValueB(int maxOperandValueB) {
        this.maxOperandValueB = maxOperandValueB;
    }
}

class ChildDashboard extends JFrame {
    private Child child;
    private JLabel questionLabel;
    private JTextField answerField;
    private JButton submitButton;
    private JLabel timerLabel;
    private Timer timer;
    private int remainingQuestions;
    private int maxOperandValueA;
    private int maxOperandValueB;
    private int score;

    public ChildDashboard(Child child, int questionCount, int maxOperandValueA, int maxOperandValueB) {
        super("Child Dashboard");

        this.child = child;
        this.remainingQuestions = questionCount;
        this.maxOperandValueA = maxOperandValueA;
        this.maxOperandValueB = maxOperandValueB;
        this.score = 0;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        questionLabel = new JLabel();
        answerField = new JTextField(10);
        submitButton = new JButton("Submit");
        timerLabel = new JLabel("Timer: 0");

        add(questionLabel);
        add(answerField);
        add(submitButton);
        add(timerLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });

        generateQuestion();

        timer = new Timer(1000, new ActionListener() {
            int time = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                timerLabel.setText("Timer: " + time);
            }
        });

        timer.start();

        setVisible(true);
    }

    private void generateQuestion() {
        if (remainingQuestions > 0) {
            Random rand = new Random();
            int operandA = rand.nextInt(maxOperandValueA) + 1;
            int operandB = rand.nextInt(maxOperandValueB) + 1;
            int answer = operandA * operandB;

            questionLabel.setText(operandA + " x " + operandB + " = ?");
            answerField.setText("");
            answerField.requestFocus();

            remainingQuestions--;
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Quiz completed!\nScore: " + score);
        }
    }

    private void submitAnswer() {
        String answerStr = answerField.getText();
        if (!answerStr.isEmpty()) {
            int answer = Integer.parseInt(answerStr);

            if (answer == getCorrectAnswer()) {
                score++;
                child.incrementScore();
            }

            generateQuestion();
        }
    }

    private int getCorrectAnswer() {
        String question = questionLabel.getText();
        String[] parts = question.split("x");
        int operandA = Integer.parseInt(parts[0].trim());
        int operandB = Integer.parseInt(parts[1].trim());
        return operandA * operandB;
    }
}

class ParentDashboard extends JFrame {
    private Parent parent;

    public ParentDashboard(Parent parent) {
        super("Parent Dashboard");

        this.parent = parent;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        JButton customizeButton = new JButton("Customize");
        JButton viewScoresButton = new JButton("View Scores");

        customizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomizeDialog();
            }
        });

        viewScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openScoresDialog();
            }
        });

        add(customizeButton);
        add(viewScoresButton);

        setVisible(true);
    }

    private void openCustomizeDialog() {
        JDialog customizeDialog = new JDialog(this, "Customize Settings", true);
        customizeDialog.setSize(300, 200);
        customizeDialog.setLayout(new FlowLayout());

        JLabel questionCountLabel = new JLabel("Question Count:");
        JTextField questionCountField = new JTextField(String.valueOf(parent.getQuestionCount()), 10);
        JLabel maxOperandValueALabel = new JLabel("Max Operand Value A:");
        JTextField maxOperandValueAField = new JTextField(String.valueOf(parent.getMaxOperandValueA()), 10);
        JLabel maxOperandValueBLabel = new JLabel("Max Operand Value B:");
        JTextField maxOperandValueBField = new JTextField(String.valueOf(parent.getMaxOperandValueB()), 10);
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setQuestionCount(Integer.parseInt(questionCountField.getText()));
                parent.setMaxOperandValueA(Integer.parseInt(maxOperandValueAField.getText()));
                parent.setMaxOperandValueB(Integer.parseInt(maxOperandValueBField.getText()));
                customizeDialog.dispose();
            }
        });

        customizeDialog.add(questionCountLabel);
        customizeDialog.add(questionCountField);
        customizeDialog.add(maxOperandValueALabel);
        customizeDialog.add(maxOperandValueAField);
        customizeDialog.add(maxOperandValueBLabel);
        customizeDialog.add(maxOperandValueBField);
        customizeDialog.add(saveButton);

        customizeDialog.setVisible(true);
    }

    private void openScoresDialog() {
        JDialog scoresDialog = new JDialog(this, "Scores", true);
        scoresDialog.setSize(300, 200);
        scoresDialog.setLayout(new FlowLayout());

        JTextArea scoresArea = new JTextArea(10, 20);
        scoresArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(scoresArea);
        JButton closeButton = new JButton("Close");

        StringBuilder scoresBuilder = new StringBuilder();
        List<Child> children = parent.getChildren();
        for (Child child : children) {
            scoresBuilder.append(child.getName()).append(": ").append(child.getScore()).append("\n");
        }
        scoresArea.setText(scoresBuilder.toString());

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoresDialog.dispose();
            }
        });

        scoresDialog.add(scrollPane);
        scoresDialog.add(closeButton);

        scoresDialog.setVisible(true);
    }
}

class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Parent parent;

    public LoginScreen() {
        super("Login");

        parent = new Parent("admin", "password"); // Default parent credentials

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals(parent.getUsername()) && password.equals(parent.getPassword())) {
                    openParentDashboard();
                } else {
                    Child child = findChild(username);
                    if (child != null) {
                        openChildDashboard(child);
                    } else {
                        JOptionPane.showMessageDialog(LoginScreen.this, "Invalid username or password");
                    }
                }
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        setVisible(true);
    }

    private Child findChild(String username) {
        List<Child> children = parent.getChildren();
        for (Child child : children) {
            if (child.getName().equals(username)) {
                return child;
            }
        }
        return null;
    }

    private void openParentDashboard() {
        new ParentDashboard(parent);
        dispose();
    }

    private void openChildDashboard(Child child) {
        new ChildDashboard(child, parent.getQuestionCount(), parent.getMaxOperandValueA(), parent.getMaxOperandValueB());
        dispose();
    }
}

public class MultiplicationTableApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen();
            }
        });
    }
}
