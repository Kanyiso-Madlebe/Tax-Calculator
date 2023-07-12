
package com.mycompany.tax;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Tax extends JFrame {
    private JTextField incomeField;
    private JTextArea resultTextArea;
    private List<String> taxHistory;

    public Tax() {
        // Set up the frame
        setTitle("Easy Tax Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Create the content pane with a background image
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = new ImageIcon("C:/Users/user/Pictures/Screenshots/sars.png").getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Create the components
        JLabel headingLabel = new JLabel("Easy Tax Calculator");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        headingLabel.setForeground(Color.WHITE);

        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        inputPanel.setOpaque(false);

        JLabel incomeLabel = new JLabel("Enter annual income:");
        incomeLabel.setForeground(Color.WHITE);

        incomeField = new JTextField(10);
        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");
        JButton historyButton = new JButton("Show History");

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setOpaque(false);

        resultTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setPreferredSize(new Dimension(100, 200));

        // Add the components to the input panel
        inputPanel.add(incomeLabel);
        inputPanel.add(incomeField);
        inputPanel.add(calculateButton);
        inputPanel.add(clearButton);
        inputPanel.add(exitButton);
        inputPanel.add(historyButton);

        // Add the components to the result panel
        resultPanel.add(scrollPane);

        // Set the bounds of the components
        headingLabel.setBounds(10, 10, 400, 40);
        inputPanel.setBounds(10, 60, 400, 200);
        resultPanel.setBounds(10, 270, 400, 220);

        // Add the components to the frame
        contentPane.add(headingLabel);
        contentPane.add(inputPanel);
        contentPane.add(resultPanel);

        // Initialize tax history
        taxHistory = new ArrayList<>();

        // Attach event listener to the calculate button
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTax();
            }
        });

        // Attach event listener to the clear button
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Attach event listener to the exit button
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Attach event listener to the history button
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTaxHistory();
            }
        });

        // Set the size and make the frame visible
        setSize(430, 530);
        setResizable(true);
        setVisible(true);
    }

    private void calculateTax() {
        String incomeText = incomeField.getText();
        if (!incomeText.isEmpty()) {
            try {
                double income = Double.parseDouble(incomeText);
                double tax = calculateTaxObligation(income);
                String result = "Tax Obligation: R" + tax;
                resultTextArea.setText(result);
                addToTaxHistory(incomeText, result);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid income amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the income amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double calculateTaxObligation(double income) {
        // Perform the tax calculation logic here
        // This is just a placeholder implementation
        double tax;
        if (income <= 0) {
            tax = 0;
        } else if (income <= 237100) {
            tax = income * 0.18;
        } else if (income <= 370500) {
            tax = 42678 + (income - 237100) * 0.26;
        } else if (income <= 512800) {
            tax = 77362 + (income - 370500) * 0.31;
        } else if (income <= 673000) {
            tax = 121475 + (income - 512800) * 0.36;
        } else if (income <= 857900) {
            tax = 179147 + (income - 673000) * 0.39;
        } else if (income <= 1817000) {
            tax = 251258 + (income - 857900) * 0.41;
        } else {
            tax = 644489 + (income - 1817000) * 0.45;
        }
        return tax;
    }

    private void clearFields() {
        incomeField.setText("");
        resultTextArea.setText("");
    }

    private void addToTaxHistory(String income, String result) {
        String entry = "Income: " + income + ", " + result;
        taxHistory.add(entry);
    }

    private void showTaxHistory() {
        StringBuilder history = new StringBuilder();
        for (String entry : taxHistory) {
            history.append(entry).append("\n");
        }
        if (history.length() == 0) {
            JOptionPane.showMessageDialog(this, "Tax history is empty.", "Tax History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, history.toString(), "Tax History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Tax();
            }
        });
    }
}
