import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class Home {
    private static final Color BACKGROUND_COLOR = new Color(62, 62, 66);
    private static final Color PANEL_COLOR = new Color(40, 40, 40);
    private static final Color SET_PANEL_COLOR = new Color(4, 98, 221);
    private static final Color RECTANGLE_BORDER_COLOR;
    private static final Font TITLE_FONT;
    private static final Font INFO_FONT;
    private static final Font RECTANGLE_FONT;
    private JFrame homeFrame;
    private final ArrayList<ArrayList<Rectangle>> allSets = Manager.getAllSets();
    private final ArrayList<TopLeft> dimensions = Manager.getDimensions();
    private JPanel infoPanel;
    private JLabel validSetsLabel;

    public Home() {
        if (this.allSets.isEmpty()) {
            JOptionPane.showMessageDialog((Component)null, "No valid rectangle sets found!");
        }

        this.setupUI();
    }

    private void setupUI() {
        this.homeFrame = this.createFrame("Rectangles Packing Project", 1400, 750);
        JPanel mainPanel = new JPanel((LayoutManager)null);
        mainPanel.setBackground(BACKGROUND_COLOR);
        this.homeFrame.add(mainPanel);
        JPanel leftPanel = this.createPanel(0, 0, 200, 750, PANEL_COLOR);
        leftPanel.setLayout((LayoutManager)null);
        JButton newSetButton = this.createButton("New Set", 25, 20, 150, 50);
        newSetButton.addActionListener((e) -> {
            this.homeFrame.dispose();
            new NewSetForm();
        });
        leftPanel.add(newSetButton);
        JLabel infoTitle = new JLabel("Rectangles Info");
        infoTitle.setBounds(25, 80, 150, 30);
        infoTitle.setForeground(Color.WHITE);
        infoTitle.setFont(TITLE_FONT);
        infoTitle.setHorizontalAlignment(0);
        leftPanel.add(infoTitle);
        this.infoPanel = this.createPanel(10, 120, 180, 500, PANEL_COLOR);
        this.infoPanel.setLayout(new BoxLayout(this.infoPanel, 1));
        leftPanel.add(this.infoPanel);
        this.validSetsLabel = new JLabel("Valid Sets Found: " + this.allSets.size());
        this.validSetsLabel.setBounds(25, 630, 150, 30);
        this.validSetsLabel.setForeground(Color.WHITE);
        this.validSetsLabel.setFont(INFO_FONT);
        this.validSetsLabel.setHorizontalAlignment(0);
        leftPanel.add(this.validSetsLabel);
        mainPanel.add(leftPanel);
        JScrollPane scrollPane = this.createScrollablePanel(mainPanel);
        this.displayRectangles(scrollPane);
        this.homeFrame.setDefaultCloseOperation(3);
        this.homeFrame.setVisible(true);
    }

    private void displayRectangles(JScrollPane scrollPane) {
        JPanel rightPanel = (JPanel)scrollPane.getViewport().getView();
        rightPanel.removeAll();
        int verticalOffset = 10;
        new ArrayList();

        for(int i = 0; i < this.allSets.size(); ++i) {
            ArrayList<Rectangle> rectangles = (ArrayList)this.allSets.get(i);
            TopLeft dimension = (TopLeft)this.dimensions.get(i);
            JPanel panel = this.createPanel(10, verticalOffset, dimension.getX() * 50, dimension.getY() * 50, SET_PANEL_COLOR);
            this.addRectangleLabels(rectangles, panel);
            rightPanel.add(panel);
            if (i == this.allSets.size() - 1) {
                ;
            }

            verticalOffset += dimension.getY() * 50 + 25;
        }

        if (!this.allSets.isEmpty()) {
            JPanel reversedPanel = this.createReversedPanel((ArrayList)this.allSets.get(this.allSets.size() - 1), (TopLeft)this.dimensions.get(this.dimensions.size() - 1), verticalOffset);
            rightPanel.add(reversedPanel);
            verticalOffset += ((TopLeft)this.dimensions.get(this.dimensions.size() - 1)).getX() * 50 + 25;
        }

        this.updateRectangleInfo();
        this.updateValidSetsCount();
        this.updatePanelSize(rightPanel, verticalOffset);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private JPanel createReversedPanel(ArrayList<Rectangle> lastSet, TopLeft lastDimension, int verticalOffset) {
        JPanel reversedPanel = this.createPanel(10, verticalOffset, lastDimension.getY() * 50, lastDimension.getX() * 50, SET_PANEL_COLOR);

        for(Rectangle rectangle : lastSet) {
            JLabel label = new JLabel(rectangle.getName());
            label.setBounds(rectangle.getTopLeft().getY() * 50, rectangle.getTopLeft().getX() * 50, rectangle.getHeight() * 50, rectangle.getWidth() * 50);
            label.setForeground(Color.WHITE);
            label.setFont(RECTANGLE_FONT);
            label.setHorizontalAlignment(0);
            label.setBorder(new LineBorder(RECTANGLE_BORDER_COLOR));
            reversedPanel.add(label);
        }

        return reversedPanel;
    }

    private void updateRectangleInfo() {
        this.infoPanel.removeAll();

        for(Rectangle rectangle : Manager.getEnteredRectangles()) {
            String var10002 = rectangle.getName();
            JLabel label = new JLabel(var10002 + " ( " + rectangle.getWidth() + " , " + rectangle.getHeight() + " )");
            label.setForeground(Color.WHITE);
            label.setFont(INFO_FONT);
            this.infoPanel.add(label);
        }

        this.infoPanel.revalidate();
        this.infoPanel.repaint();
    }

    private void updateValidSetsCount() {
        this.validSetsLabel.setText("Valid Sets Found: " + this.allSets.size());
    }

    private JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo((Component)null);
        frame.setExtendedState(6);
        return frame;
    }

    private JPanel createPanel(int x, int y, int width, int height, Color color) {
        JPanel panel = new JPanel((LayoutManager)null);
        panel.setBounds(x, y, width, height);
        panel.setBackground(color);
        return panel;
    }

    private JScrollPane createScrollablePanel(JPanel mainPanel) {
        JPanel rightPanel = new JPanel((LayoutManager)null);
        rightPanel.setBackground(BACKGROUND_COLOR);
        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.setBounds(200, 0, 1160, 750);
        mainPanel.add(scrollPane);
        return scrollPane;
    }

    private void addRectangleLabels(ArrayList<Rectangle> rectangles, JPanel panel) {
        for(Rectangle rectangle : rectangles) {
            JLabel label = new JLabel(rectangle.getName());
            label.setBounds(rectangle.getTopLeft().getX() * 50, rectangle.getTopLeft().getY() * 50, rectangle.getWidth() * 50, rectangle.getHeight() * 50);
            label.setForeground(Color.WHITE);
            label.setFont(RECTANGLE_FONT);
            label.setHorizontalAlignment(0);
            label.setBorder(new LineBorder(RECTANGLE_BORDER_COLOR));
            panel.add(label);
        }

    }

    private JButton createButton(String title, int x, int y, int width, int height) {
        JButton button = new JButton(title);
        button.setBounds(x, y, width, height);
        button.setFocusable(false);
        button.setForeground(SET_PANEL_COLOR);
        return button;
    }

    private void updatePanelSize(JPanel rightPanel, int height) {
        rightPanel.setPreferredSize(new Dimension(1200, height + 100));
        SwingUtilities.invokeLater(() -> {
            rightPanel.revalidate();
            rightPanel.repaint();
        });
    }

    static {
        RECTANGLE_BORDER_COLOR = Color.WHITE;
        TITLE_FONT = new Font("Arial", 1, 16);
        INFO_FONT = new Font("Arial", 1, 14);
        RECTANGLE_FONT = new Font("Verdana", 2, 16);
    }
}
