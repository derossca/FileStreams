import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame implements ActionListener {
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private JButton addButton;
    private RandomAccessFile randomAccessFile;
    private int recordCount = 0;
    private final int RECORD_SIZE = 125; // Adjust this according to the size of your record

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Cost:"));
        costField = new JTextField();
        panel.add(costField);

        panel.add(new JLabel("Record Count:"));
        recordCountField = new JTextField();
        recordCountField.setEditable(false);
        panel.add(recordCountField);

        addButton = new JButton("Add");
        addButton.addActionListener(this);
        panel.add(addButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);

        try {
            randomAccessFile = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addRecord();
        }
    }

    private void addRecord() {
        try {
            String name = padString(nameField.getText(), 35);
            String description = padString(descriptionField.getText(), 75);
            String id = padString(idField.getText(), 6);
            double cost = Double.parseDouble(costField.getText());

            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeUTF(name);
            randomAccessFile.writeUTF(description);
            randomAccessFile.writeUTF(id);
            randomAccessFile.writeDouble(cost);

            recordCount++;
            recordCountField.setText(String.valueOf(recordCount));

            clearFields();
        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");
        costField.setText("");
    }

    private String padString(String str, int length) {
        if (str.length() > length) {
            return str.substring(0, length);
        } else if (str.length() < length) {
            StringBuilder sb = new StringBuilder(str);
            while (sb.length() < length) {
                sb.append(" ");
            }
            return sb.toString();
        }
        return str;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductMaker::new);
    }
}