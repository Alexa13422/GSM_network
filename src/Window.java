import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class Window extends JFrame {
    public Window() {
        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VRD.createBinaryFile("Olenka");
            }
        };
        addWindowListener(windowListener);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 800);

        LeftPanel leftPanel = new LeftPanel();
        CentrallPanel centrallPanel = new CentrallPanel();
        RightPanel rightPanel = new RightPanel();

        add(leftPanel, BorderLayout.WEST);
        add(centrallPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setVisible(true);
    }
}
