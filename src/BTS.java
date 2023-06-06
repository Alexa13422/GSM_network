import javax.swing.*;
import java.awt.*;

class BTS extends JPanel implements TransmittingMassageInterface {
    private final JPanel textPanel;
    private final BTS_purpose purpose;

    public BTS(BTS_purpose purpose) {
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.CYAN);
        this.purpose = purpose;
        setLayout(new BorderLayout());

        this.add(textPanel, BorderLayout.CENTER);

    }
    @Override
    public void transmitMassage(Massage massage) {
        JLabel label = new JLabel(massage.getMassageText());
        textPanel.add(label);
        textPanel.revalidate();
        textPanel.repaint();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                textPanel.remove(label);
                textPanel.revalidate();
                textPanel.repaint();
                if (purpose.equals(BTS_purpose.start)){
                    BSC_Panel.startPrinting(massage);
                } else {
                    VRD.chooseVRD(massage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
