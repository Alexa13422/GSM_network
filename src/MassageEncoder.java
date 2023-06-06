class MassageEncoder {
    public static String encodeMessage(String message, int destinationIndex) {
        String vrdAdress = Integer.toHexString(destinationIndex).toUpperCase();

        String encodedMessage = String.format("%02X", (message.length())).toUpperCase() + //SMS length
                "04" + //SMS-DELIVER message type
                String.format("%02X", vrdAdress.length()) + // Destination address length
                "81" + //Type-of-Address (TOA) value for address index
                String.format("%02X", destinationIndex) + //Encoded destination address
                String.format("%02X", message.length()) + //Message length
                encodeMessage(message); //Encoded message body

        return encodedMessage;
    }

    private static String encodeMessage(String message) {
        StringBuilder encodedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            encodedMessage.append(encodeCharacter(c));
        }
        return encodedMessage.toString();
    }

    public static String encodeCharacter(char c) {
        return Integer.toHexString((int) c).toUpperCase();
    }

    public static String decodeMessage(String encodedMessage) {
        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < encodedMessage.length(); i += 2) {
            String hex = encodedMessage.substring(i, i + 2);
            char decodedCharacter = decodeCharacter(hex);
            decodedMessage.append(decodedCharacter);
        }

        return decodedMessage.toString();
    }

    private static char decodeCharacter(String hex) {
        int decimalValue = Integer.parseInt(hex, 16);
        return (char) decimalValue;
    }
}
