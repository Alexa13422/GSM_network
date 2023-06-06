import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

class BSC extends JPanel implements TransmittingMassageInterface {

    private BSC previousBSC;
    private BSC nextBSC;
    private int massageCounter = 0;
    private final List<Thread> threads;

    private ArrayList<Massage> massages;
    private int sleepTime;

    public BSC() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        threads = new CopyOnWriteArrayList<>();
        massages = new ArrayList<>();

        JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Random random = new Random();

        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        sleepTime = random.nextInt(15000 - 5000 + 1) + 5000;

        upPanel.setBackground(new Color(red, green, blue));
        JLabel label = new JLabel("BSC " + (BSC_Panel.bscCount++));
        JButton button = new JButton("Terminate");
        button.addActionListener(e -> {
            if (BSC_Panel.bscCount - 1 != 1) {
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                if (getNextBSC() != null) {
                    getNextBSC().setPreviousBSC(previousBSC);
                }
                if (getPreviousBSC() != null) {
                    getPreviousBSC().setNextBSC(nextBSC);
                }
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(BSC.this);
                    parent.revalidate();
                    parent.repaint();
                }
                BSC_Panel.bscCount--;
                for (Massage massage : massages) {
                    VRD.chooseVRD(massage);
                }
            }
        });
        upPanel.add(label);
        upPanel.add(button);

        add(upPanel);
    }

    public void transmitMassage(Massage massage) {
        if (massageCounter > 5) {
            if (nextBSC == null) {
                CentrallPanel.toRightBTS(massage);
            } else {
                nextBSC.transmitMassage(massage);
            }
        } else {
            massages.add(massage);
            JLabel label = new JLabel(massage.getMassageText());
            add(label);
            massageCounter++;
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(sleepTime);
                    SwingUtilities.invokeLater(() -> removeLabel(label));
                    massages.remove(massage);
                    massageCounter--;
                    if (nextBSC != null) {
                        nextBSC.transmitMassage(massage);
                    } else {
                        CentrallPanel.toRightBTS(massage);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            });
            thread.start();
            threads.add(thread);
            revalidate();
            repaint();
        }
    }

    public BSC getBSCWithLeastMessages() {
        if (nextBSC != null) {
            BSC bscWithLeastMessages = nextBSC.getBSCWithLeastMessages();

            if (this.massageCounter <= bscWithLeastMessages.getMassageCounter()) {
                return this;
            } else {
                return bscWithLeastMessages;
            }
        } else {
            return this;
        }
    }


    public void removeLabel(JLabel massage) {
        remove(massage);

        revalidate();
        repaint();
    }

    public BSC getPreviousBSC() {
        return previousBSC;
    }

    public void setPreviousBSC(BSC previousBSC) {
        this.previousBSC = previousBSC;
    }

    public BSC getNextBSC() {
        return nextBSC;
    }

    public void setNextBSC(BSC nextBSC) {
        this.nextBSC = nextBSC;
    }

    public int getMassageCounter() {
        return massageCounter;
    }
}
