
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class NewSetForm {
    private static final Color BACKGROUND_COLOR = new Color(62, 62, 66);
    private static final Color PANEL_COLOR = new Color(40, 40, 40);
    private static final Color TEXT_COLOR;
    private static final Color BUTTON_COLOR;
    private static final Color BORDER_COLOR;
    private final JFrame frame = new JFrame("Enter Rectangles");
    private final JTextField numRectanglesField;
    private final JPanel inputPanel;
    private final JButton submitButton;
    private final ArrayList<Rectangle> userRectangles = new ArrayList();

    public NewSetForm() {
        this.frame.setSize(500, 500);
        this.frame.setLocationRelativeTo((Component)null);
        this.frame.setLayout(new BorderLayout());
        this.frame.getContentPane().setBackground(BACKGROUND_COLOR);
        JPanel topPanel = new JPanel();
        topPanel.setBackground(PANEL_COLOR);
        topPanel.setForeground(TEXT_COLOR);
        JLabel titleLabel = new JLabel("Number of Rectangles:");
        titleLabel.setForeground(TEXT_COLOR);
        topPanel.add(titleLabel);
        this.numRectanglesField = this.createTextField();
        topPanel.add(this.numRectanglesField);
        JButton nextButton = this.createButton("Next");
        nextButton.addActionListener((e) -> this.showRectangleInputs());
        topPanel.add(nextButton);
        this.frame.add(topPanel, "North");
        this.inputPanel = new JPanel();
        this.inputPanel.setLayout(new BoxLayout(this.inputPanel, 1));
        this.inputPanel.setBackground(BACKGROUND_COLOR);
        this.frame.add(new JScrollPane(this.inputPanel), "Center");
        this.submitButton = this.createButton("Submit");
        this.submitButton.addActionListener((e) -> this.submitRectangles());
        this.submitButton.setVisible(false);
        this.frame.add(this.submitButton, "South");
        this.frame.setDefaultCloseOperation(2);
        this.frame.setVisible(true);
    }

    private void showRectangleInputs() {
        this.inputPanel.removeAll();

        int numRectangles;
        try {
            numRectangles = Integer.parseInt(this.numRectanglesField.getText().trim());
            if (numRectangles <= 0) {
                throw new NumberFormatException("Number must be positive");
            }
        } catch (NumberFormatException var4) {
            this.showError("Please enter a valid positive integer for the number of rectangles.");
            return;
        }

        for(int i = 0; i < numRectangles; ++i) {
            JPanel panel = this.createRectangleInputPanel();
            this.inputPanel.add(panel);
        }

        this.submitButton.setVisible(true);
        this.inputPanel.revalidate();
        this.inputPanel.repaint();
    }

    private JPanel createRectangleInputPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL_COLOR);
        panel.setForeground(TEXT_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(TEXT_COLOR);
        JTextField nameField = this.createTextField();
        nameField.putClientProperty("fieldType", "name");
        panel.add(nameLabel);
        panel.add(nameField);
        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setForeground(TEXT_COLOR);
        JTextField widthField = this.createTextField();
        widthField.putClientProperty("fieldType", "width");
        panel.add(widthLabel);
        panel.add(widthField);
        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setForeground(TEXT_COLOR);
        JTextField heightField = this.createTextField();
        heightField.putClientProperty("fieldType", "height");
        panel.add(heightLabel);
        panel.add(heightField);
        return panel;
    }

    private void submitRectangles() {
        this.userRectangles.clear();

        for(Component component : this.inputPanel.getComponents()) {
            if (component instanceof JPanel panel) {
                String name = null;
                Integer width = null;
                Integer height = null;

                for(Component comp : panel.getComponents()) {
                    if (comp instanceof JTextField field) {
                        String fieldType = (String)field.getClientProperty("fieldType");
                        if ("name".equals(fieldType)) {
                            name = field.getText().trim();
                        } else if ("width".equals(fieldType)) {
                            try {
                                width = Integer.parseInt(field.getText().trim());
                            } catch (NumberFormatException var17) {
                                this.showError("Width must be a valid number.");
                                return;
                            }
                        } else if ("height".equals(fieldType)) {
                            try {
                                height = Integer.parseInt(field.getText().trim());
                            } catch (NumberFormatException var16) {
                                this.showError("Height must be a valid number.");
                                return;
                            }
                        }
                    }
                }

                if (name == null || name.isEmpty() || width == null || height == null) {
                    this.showError("All fields must be filled out correctly.");
                    return;
                }

                this.userRectangles.add(new Rectangle(name, width, height));
            }
        }

        this.frame.dispose();
        Manager manager = new Manager();
        manager.processNewSet(this.userRectangles);
        SwingUtilities.invokeLater(Home::new);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(3);
        textField.setBackground(BACKGROUND_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        return textField;
    }

    private JButton createButton(String title) {
        JButton button = new JButton(title);
        button.setFocusable(false);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        return button;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this.frame, message, "Input Error", 0);
    }

    static {
        TEXT_COLOR = Color.WHITE;
        BUTTON_COLOR = new Color(70, 74, 81);
        BORDER_COLOR = new Color(4, 98, 221);
    }
}
