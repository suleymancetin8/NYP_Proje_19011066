import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class MultiplicationTableLearningApp {

    private static final int MIN_TABLE_VALUE = 1;
    private static final int MAX_TABLE_VALUE = 10;
    private static final int MIN_QUESTION_NUMBER = 1;
    private static final int MAX_QUESTION_NUMBER = 10;

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel tableLabel;
    private JLabel questionNumberLabel;
    private JTextField tableTextField;
    private JTextField questionNumberTextField;
    private JButton startButton;
    private JButton exitButton;
    private JTextArea outputTextArea;
    private JLabel counterLabel;

    private int tableValue;
    private int questionNumber;
    private int counter;

    public MultiplicationTableLearningApp() {
        frame = new JFrame("Multiplication Table Learning App");
        mainPanel = new JPanel(new GridLayout(0, 2));
        titleLabel = new JLabel("Multiplication Table Learning App");
        tableLabel = new JLabel("Table Value:");
        tableTextField = new JTextField(10);
        questionNumberLabel = new JLabel("Question Number:");
        questionNumberTextField = new JTextField(10);
        startButton = new JButton("Start");
        exitButton = new JButton("Exit");
        outputTextArea = new JTextArea(10, 30);
        counterLabel = new JLabel("Questions Answered: 0");

        mainPanel.add(titleLabel);
        mainPanel.add(tableLabel);
        mainPanel.add(tableTextField);
        mainPanel.add(questionNumberLabel);
        mainPanel.add(questionNumberTextField);
        mainPanel.add(startButton);
        mainPanel.add(exitButton);
        mainPanel.add(outputTextArea);
        mainPanel.add(counterLabel);

        frame.add(mainPanel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableValue = Integer.parseInt(tableTextField.getText());
                questionNumber = Integer.parseInt(questionNumberTextField.getText());

                if (tableValue < MIN_TABLE_VALUE || tableValue > MAX_TABLE_VALUE) {
                    outputTextArea.setText("Please enter a table value between 1 and 10.");
                } else if (questionNumber < MIN_QUESTION_NUMBER || questionNumber > MAX_QUESTION_NUMBER) {
                    outputTextArea.setText("Please enter a question number between 1 and 10.");
                } else {
                    outputTextArea.setText("");
                    counter = 0;
                    for (int i = 1; i <= questionNumber; i++) {
                        int answer = tableValue * i;
                        outputTextArea.append(tableValue + " x " + i + " = " + answer + "\n");
                        counter++;
                        counterLabel.setText("Questions Answered: " + counter);
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MultiplicationTableLearningApp();
    }
}
