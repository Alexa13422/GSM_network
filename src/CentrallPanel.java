import javax.swing.*;
import java.awt.*;

class CentrallPanel extends JPanel {

    private static JPanel leftBtsPanel;

    private static JPanel rightBtsPanel;
    private static BSC_Panel bscPanel;

    public CentrallPanel() {
        setLayout(new BorderLayout());

        leftBtsPanel = new JPanel();
        leftBtsPanel.add(new BTS(BTS_purpose.start));
        rightBtsPanel = new JPanel();
        rightBtsPanel.add(new BTS(BTS_purpose.end));

        bscPanel = new BSC_Panel();
        JScrollPane scrollPane = new JScrollPane(bscPanel);
        BSC first = new BSC();
        bscPanel.add(first);
        bscPanel.addBSC(first);


        add(leftBtsPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(rightBtsPanel, BorderLayout.EAST);

        JButton addBscButton = new JButton("Add BSC Layer");
        addBscButton.addActionListener(e -> addBscLayer());

        // Add the add BSC button to the main panel
        add(addBscButton, BorderLayout.SOUTH);

    }

    public static void toLeftBTS(Massage massage) {
        Component[] components = leftBtsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof BTS) {
                ((BTS) component).transmitMassage(massage);
            }

        }
    }

    public static void toRightBTS(Massage massage) {
        Component[] components = rightBtsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof BTS) {
                ((BTS) component).transmitMassage(massage);
            }

        }
    }
    private void addBscLayer() {

        BSC bscLayer = new BSC();
        bscPanel.add(bscLayer);
        bscPanel.addBSC(bscLayer);

        revalidate();
        repaint();
    }

}
