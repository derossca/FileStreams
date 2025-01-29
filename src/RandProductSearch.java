import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame implements ActionListener {
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    private RandomAccessFile randomAccessFile;
    private final int RECORD_SIZE = 125; // Adjust this according to the size of your record

    public RandProductSearch() {
        setTitle("Random Product Search");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(searchButton, BorderLayout.EAST);

        panel.add(searchPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(panel);
        setVisible(true);

        try {
            randomAccessFile = new RandomAccessFile("products.dat", "r");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchProducts();
        }
    }

    private void searchProducts() {
        resultArea.setText("");
        String searchTerm = searchField.getText().trim();
        try {
            randomAccessFile.seek(0);
            long fileLength = randomAccessFile.length();
            while (randomAccessFile.getFilePointer() < fileLength) {
                String name = padString(randomAccessFile.readUTF().trim(), 35);
                String description = padString(randomAccessFile.readUTF().trim(), 75);
                String id = padString(randomAccessFile.readUTF().trim(), 6);
                double cost = randomAccessFile.readDouble();
                if (name.toLowerCase().contains(searchTerm.toLowerCase())) {
                    resultArea.append("Name: " + name.trim() + "\n");
                    resultArea.append("Description: " + description.trim() + "\n");
                    resultArea.append("ID: " + id.trim() + "\n");
                    resultArea.append("Cost: $" + cost + "\n\n");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error searching products: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        SwingUtilities.invokeLater(RandProductSearch::new);
    }
}