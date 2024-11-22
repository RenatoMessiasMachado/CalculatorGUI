import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField display;
    private JPanel buttonPanel;
    private String operator;
    private double num1, num2, result;

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(100, 100, 100));
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(150, 150, 150));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 100, 100));
            }
        });
        return button;
    }

    public CalculatorGUI() {
        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(50, 50, 50));


        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(50, 50, 50));
        display.setForeground(Color.WHITE);
        add(display, BorderLayout.NORTH);


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));
        buttonPanel.setBackground(new Color(30, 30, 30));


        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+",
                "√", "^", "CE", "DEL"
        };

        for (String text : buttons) {
            buttonPanel.add(createButton(text));
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command)) {
            display.setText(display.getText() + command);
        } else if ("+-*/^".contains(command)) {
            operator = command;
            num1 = Double.parseDouble(display.getText());
            display.setText("");
        } else if ("=".equals(command)) {
            try {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case "+" -> result = num1 + num2;
                    case "-" -> result = num1 - num2;
                    case "*" -> result = num1 * num2;
                    case "/" -> result = divide(num1, num2);
                    case "^" -> result = Math.pow(num1, num2);
                }
                display.setText(String.valueOf(result));
            } catch (ArithmeticException ex) {
                display.setText("Error: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                display.setText("Error");
            }
        } else if ("√".equals(command)) {
            try {
                num1 = Double.parseDouble(display.getText());
                if (num1 < 0) {
                    display.setText("Error: √<0");
                    return;
                }
                result = Math.sqrt(num1);
                display.setText(String.valueOf(result));
            } catch (NumberFormatException ex) {
                display.setText("Error");
            }
        } else if ("C".equals(command)) {
            display.setText("");
            num1 = num2 = result = 0;
            operator = "";
        } else if ("CE".equals(command)) {
            display.setText("");
        } else if ("DEL".equals(command)) {
            String currentText = display.getText();
            if (!currentText.isEmpty()) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    private double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Zero division");
        }
        return a / b;
    }

    public static void main(String[] args) {
        new CalculatorGUI();
    }
}
