import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RightPanel extends JPanel {
    private final JPanel scrollPanePanel;

    public static int vrdCounter = 1;

    public RightPanel() {
        setLayout(new BorderLayout());

        scrollPanePanel = new JPanel();
        scrollPanePanel.setBackground(Color.RED);

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
                addComponentsToPane(scrollPanePanel);
                revalidate();
            }
        });

        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        VRD vrd = new VRD();
        pane.add(vrd);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension parentSize = getParent().getSize();
        int panelWidth = parentSize.width / 4;
        int panelHeight = parentSize.height;

        return new Dimension(panelWidth, panelHeight);
    }
}
