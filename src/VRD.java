import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

class VRD extends JPanel {
    private final JButton terminateButton;
    private final JLabel receivedMessagesLabel;

    public int numberReceivedMassages;

    private final ArrayList<Massage> receivedMassages;
    public static ArrayList<Massage> massagesWithoutReceiver = new ArrayList<>();
    private final JCheckBox autoClearCheckBox;
    public static ArrayList<VRD> vrdList = new ArrayList<>();
    private boolean isAutoClearRunning = false;
    private int number;
    Thread clearThread;

    public VRD() {
        setLayout(new GridLayout(3, 1));

        terminateButton = new JButton("Terminate");
        receivedMessagesLabel = new JLabel("Received Messages: " + numberReceivedMassages);
        autoClearCheckBox = new JCheckBox("Auto Clear");
        number = RightPanel.vrdCounter++;


        add(terminateButton);
        add(receivedMessagesLabel);
        add(autoClearCheckBox);

        receivedMassages = new ArrayList<>();

        vrdList.add(this);

        setMaximumSize(new Dimension(400, 200));

        // ActionListener for the terminate button
        terminateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(VRD.this);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });

        // ActionListener for the auto clear checkbox
        autoClearCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (autoClearCheckBox.isSelected()) {
                    startAutoClear();
                } else {
                    stopAutoClear();
                }
            }
        });
    }

    private void startAutoClear() {
        if (!isAutoClearRunning) {
            isAutoClearRunning = true;
            clearThread = new Thread(() -> {
                while (isAutoClearRunning) {
                    try {
                        Thread.sleep(10000);
                        // Code to update UI (assuming receivedMessagesLabel is accessible)
                        SwingUtilities.invokeLater(() -> numberReceivedMassages = 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            clearThread.start();
        }
    }

    private void stopAutoClear() {
        if (isAutoClearRunning) {
            isAutoClearRunning = false;
            try {
                clearThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearThread = null;
        }
    }

    public static void chooseVRD(Massage massage) {
        int encodedAddress = Integer.parseInt(massage.getEncodedText().substring(8, 10));
        if (encodedAddress == 0) {
            massagesWithoutReceiver.add(massage);
        } else {
            Optional<VRD> vrd =
                    vrdList.stream()
                            .filter(element -> element.number == encodedAddress)
                            .findFirst();
            vrd.get().addRecievedMassage(massage);
        }
    }

    private void addRecievedMassage(Massage massage) {
        receivedMassages.add(massage);
        numberReceivedMassages++;
        receivedMessagesLabel.setText("Received Messages: " + numberReceivedMassages);
        revalidate();
        repaint();
    }

    public int getNumber() {
        return number;
    }

    public static void createBinaryFile(String fileName) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName))) {

            outputStream.writeInt(vrdList.size());

            for (VRD vrd : vrdList) {
                outputStream.writeInt(vrd.getNumber());

                outputStream.writeInt(vrd.receivedMassages.size());

                for (Massage massage : vrd.receivedMassages) {
                    outputStream.writeChars(massage.getEncodedText().substring(0, 2));
                    outputStream.writeBytes(massage.getEncodedText().substring(2, 4));
                    outputStream.writeChars(massage.getEncodedText().substring(4, 6));
                    outputStream.writeBytes(massage.getEncodedText().substring(6, 8));
                    outputStream.writeBytes(massage.getEncodedText().substring(8, 10));
                    outputStream.writeBytes(massage.getEncodedText().substring(10, 12));

                    String decoded = massage.getEncodedText().substring(12);
                    MassageEncoder.decodeMessage(decoded);
                    outputStream.writeUTF(decoded);
                }
                for (Massage massage : VRD.massagesWithoutReceiver) {
                    outputStream.writeChars(massage.getEncodedText().substring(0, 2));
                    outputStream.writeBytes(massage.getEncodedText().substring(2, 4));
                    outputStream.writeChars(massage.getEncodedText().substring(4, 6));
                    outputStream.writeBytes(massage.getEncodedText().substring(6, 8));
                    outputStream.writeBytes(massage.getEncodedText().substring(8, 10));
                    outputStream.writeBytes(massage.getEncodedText().substring(10, 12));

                    String decoded = massage.getEncodedText().substring(12);
                    MassageEncoder.decodeMessage(decoded);
                    outputStream.writeUTF(decoded);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
