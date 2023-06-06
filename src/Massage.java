import java.util.Random;

class Massage {
    private final String massageText;
    private final String encodedText;

    public Massage(String massageText) {
        this.massageText = massageText;
        Random rand = new Random();
        if (VRD.vrdList.isEmpty()) {
            System.out.println("There are no VRD to send massage");
            encodedText = MassageEncoder.encodeMessage(massageText, 0);
        } else {
            encodedText = MassageEncoder.encodeMessage(massageText, rand.nextInt(RightPanel.vrdCounter));
        }
    }

    public String getMassageText() {
        return massageText;
    }

    public String getEncodedText() {
        return encodedText;
    }
}
