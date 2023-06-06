import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LeftPanel extends JPanel {
    private static JPanel scrollPanePanel;

    public LeftPanel() {
        setLayout(new BorderLayout());

        scrollPanePanel = new JPanel();
        scrollPanePanel.setBackground(Color.BLUE);


        JScrollPane scrollPane = new JScrollPane(scrollPanePanel);
        scrollPane.createHorizontalScrollBar();
        add(scrollPane, BorderLayout.CENTER);


        // Create a nested panel for the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.yellow);

        JButton addButton = new JButton();
        addButton.setText("Add");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateMassageFrame();

            }
        });

        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void addComponentsToPane(Container pane, String text) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(new VBD(text));
        scrollPanePanel.revalidate();

    }

    @Override
    public Dimension getPreferredSize() {
        Dimension parentSize = getParent().getSize();
        int panelWidth = parentSize.width / 4;
        int panelHeight = parentSize.height;

        return new Dimension(panelWidth, panelHeight);
    }

    public static JPanel getScrollPanePanel() {
        return scrollPanePanel;
    }
}
