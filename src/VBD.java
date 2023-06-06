import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VBD extends JPanel {
    private static int counter;
    private JLabel frequencyLabel;
    private JSlider frequencySlider;
    private JButton terminateButton;
    private JLabel deviceNumberLabel;

    private String text;
    private JTextField deviceTextField;
    private JLabel stateLabel;
    private JComboBox<String> stateComboBox;
    private Thread labelThread;
    private boolean isRunning;
    private int lastSliderValue;

    public VBD(String text) {
        setLayout(new GridLayout(4, 2));

        frequencyLabel = new JLabel("Frequency:");
        frequencySlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        terminateButton = new JButton("Terminate");
        deviceNumberLabel = new JLabel("Device Number: " + ++counter);
        this.text = text;
        deviceTextField = new JTextField(text);
        deviceTextField.setEditable(false);
        stateLabel = new JLabel("State:");
        stateComboBox = new JComboBox<>(new String[]{"WAITING", "ACTIVE"});

        add(frequencyLabel);
        add(frequencySlider);
        add(terminateButton);
        add(deviceNumberLabel);
        add(deviceTextField);
        add(stateLabel);
        add(stateComboBox);

        setVisible(true);
        setMaximumSize(new Dimension(400, 200));

        // ActionListener for the terminate button
        terminateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = getParent();
                if (parent != null) {
                    stopLabelThread();
                    parent.remove(VBD.this);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });

        frequencySlider.addChangeListener(e -> {
            int currentValue = frequencySlider.getValue();
            if (isRunning && currentValue != lastSliderValue) {
                stopLabelThread();
                startLabelThread();
            }
        });

        stateComboBox.addActionListener(e -> {
            String selectedState = (String) stateComboBox.getSelectedItem();
            if (selectedState.equals("WAITING")) {
                stopLabelThread();
            } else if (selectedState.equals("ACTIVE")) {
                startLabelThread();
            }
        });
    }

    private void startLabelThread() {
        isRunning = true;
        labelThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    Massage massage = new Massage(deviceTextField.getText());
                    JLabel label = new JLabel(massage.getMassageText());
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            CentrallPanel.toLeftBTS(massage);
                        }
                    });

                    int sleepTime = 40000 / frequencySlider.getValue();
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
        labelThread.start();
    }

    private void stopLabelThread() {
        isRunning = false;
        if (labelThread != null) {
            labelThread.interrupt();
            try {
                labelThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            labelThread = null;
        }
    }
}
