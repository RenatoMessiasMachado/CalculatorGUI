import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField display;
    private JPanel buttonPanel;
    private JPanel scientificButtonPanel;
    private String operator;
    private double num1, num2, result;
    private boolean isScientificMode = false;
    private Dimension defaultSize;


    public CalculatorGUI() {
        setTitle("Calculator");
        setSize(300, 400);
        defaultSize = new Dimension(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("Switch");
        JMenuItem toggleScientificMode = new JMenuItem("Toggle Scientific Mode");
        toggleScientificMode.addActionListener(e -> toggleMode());
        viewMenu.add(toggleScientificMode);
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);


        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
        addStandardButtons();


        scientificButtonPanel = new JPanel();
        scientificButtonPanel.setLayout(new GridLayout(2, 4));
        addScientificButtons();
        scientificButtonPanel.setVisible(false);


        add(buttonPanel, BorderLayout.CENTER);
        add(scientificButtonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addStandardButtons() {
        String[] buttons = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};
        for (String text : buttons) {
            JButton button = createButton(text);
            buttonPanel.add(button);
        }
    }

    private void addScientificButtons() {
        String[] sciButtons = {"sin", "cos", "tan", "√", "log", "ln", "π", "^"};
        for (String text : sciButtons) {
            JButton button = createButton(text);
            scientificButtonPanel.add(button);
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(100, 100, 100));
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        return button;
    }

    private void toggleMode() {
        isScientificMode = !isScientificMode;
        scientificButtonPanel.setVisible(isScientificMode);

        if (!isScientificMode) {
            setSize(defaultSize);
        } else {
            pack();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if ("0123456789".contains(command)) {
                display.setText(display.getText() + command);
            } else if ("+-*/^".contains(command)) {
                operator = command;
                num1 = Double.parseDouble(display.getText());
                display.setText("");
            } else if ("=".equals(command)) {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case "+" -> result = num1 + num2;
                    case "-" -> result = num1 - num2;
                    case "*" -> result = num1 * num2;
                    case "/" -> result = divide(num1, num2);
                    case "^" -> result = Math.pow(num1, num2);
                }
                display.setText(String.valueOf(result));
            } else if ("√".equals(command)) {
                num1 = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.sqrt(num1)));
            } else if ("sin".equals(command)) {
                num1 = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.sin(Math.toRadians(num1))));
            } else if ("cos".equals(command)) {
                num1 = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.cos(Math.toRadians(num1))));
            } else if ("tan".equals(command)) {
                num1 = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.tan(Math.toRadians(num1))));
            } else if ("log".equals(command)) {
                num1 = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.log10(num1)));
            } else if ("ln".equals(command)) {
                num1 = Double.parseDouble(display.getText());
                display.setText(String.valueOf(Math.log(num1)));
            } else if ("π".equals(command)) {
                display.setText(String.valueOf(Math.PI));
            } else if ("C".equals(command)) {
                display.setText("");
            }
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    private double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return a / b;
    }}