import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.Random;

// Main application class to launch the Swing UI
public class RandomBarsApp {
    public static void main(String[] args) {
        // Ensures GUI updates are executed in the Event Dispatch Thread
        SwingUtilities.invokeLater(RandomBarsFrame::new);
    }
}

// JFrame subclass for the main application window
class RandomBarsFrame extends JFrame {
    private RandomBarsPanel panel; // Panel where bars are drawn

    // Constructor initializes the window
    public RandomBarsFrame() {
        setTitle("Random Bar Chart"); // Set window title
        setSize(500, 500); // Set window dimensions
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close program on exit
        setLayout(new BorderLayout()); // Use BorderLayout to arrange components

        panel = new RandomBarsPanel(); // Create drawing panel
        JButton redrawButton = new JButton("Redraw"); // Button to trigger redraw

        // Add event listener to redraw bars on button click
        redrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint(); // Calls paintComponent() to redraw
            }
        });

        add(panel, BorderLayout.CENTER); // Add panel to center of window
        add(redrawButton, BorderLayout.SOUTH); // Add button to bottom

        setVisible(true); // Make window visible
    }
}

// JPanel subclass responsible for drawing the grid and random bars
class RandomBarsPanel extends JPanel {
    private static final int GRID_SIZE = 10; // Defines a 10x10 grid
    private Random rand = new Random(); // Random number generator

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure proper rendering
        Graphics2D g2 = (Graphics2D) g; // Enable advanced graphics rendering
        g2.setStroke(new BasicStroke(10f)); // Set line thickness to 10f

        // Get panel dimensions
        int width = getWidth();
        int height = getHeight();
        int cellWidth = width / GRID_SIZE; // Width of each grid cell
        int cellHeight = height / GRID_SIZE; // Height of each grid cell

        // Draw 10x10 Grid
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= GRID_SIZE; i++) {
            g2.drawLine(i * cellWidth, 0, i * cellWidth, height); // Vertical grid lines
            g2.drawLine(0, i * cellHeight, width, i * cellHeight); // Horizontal grid lines
        }

        // Draw Random Bars using Line2D.Double
        for (int i = 0; i < GRID_SIZE; i++) {
            // Generate random color for each bar
            g2.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));

            int x = i * cellWidth + cellWidth / 2; // Center bar in grid column
            int barHeight = rand.nextInt(height / 2) + height / 10; // Random bar height

            // Draw two parallel lines to form a "bar" with 10px width
            g2.draw(new Line2D.Double(x , height, x , height - barHeight));
            g2.draw(new Line2D.Double(x , height, x , height - barHeight));
        }
    }
}