import javax.swing.*;

class BSC_Panel extends JPanel {

    private static BSC firstBSC;

    public static int bscCount = 1;

    BSC_Panel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    public static void startPrinting(Massage massage) {
        firstBSC.getBSCWithLeastMessages().transmitMassage(massage);
    }

    public void addBSC(BSC bsc) {
        if (firstBSC == null) firstBSC = bsc;
        else {
            BSC lastBSC = firstBSC;
            while (lastBSC.getNextBSC() != null) {
                lastBSC = lastBSC.getNextBSC();
            }

            lastBSC.setNextBSC(bsc);
            bsc.setPreviousBSC(lastBSC);
        }
    }
}
