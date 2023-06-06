import javax.swing.*;

class CreateMassageFrame extends JFrame {
    public CreateMassageFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setName("Add massage");

        JPanel addPanel = new JPanel();
        JTextField textField = new JTextField(20);
        JButton addButton = new JButton("Add massage");

        addButton.addActionListener(e -> {
            if (!textField.getText().isEmpty()) {
                LeftPanel.addComponentsToPane(LeftPanel.getScrollPanePanel(), textField.getText());
                CreateMassageFrame.this.dispose();
            }
        });

        addPanel.add(textField);
        addPanel.add(addButton);

        add(addPanel);
        setVisible(true);
    }

}
