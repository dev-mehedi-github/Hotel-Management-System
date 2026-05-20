import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class BillSearch {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bill Search");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBackground(new Color(230, 240, 255));

            JLabel label = new JLabel("Enter Bill ID: ");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(new Color(50, 50, 150));

            JTextField textField = new JTextField(20);
            textField.setFont(new Font("Arial", Font.PLAIN, 14));
            textField.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2));
            textField.setPreferredSize(new Dimension(200, 30));

            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Arial", Font.BOLD, 14));
            searchButton.setBackground(new Color(50, 150, 255));
            searchButton.setForeground(Color.WHITE);
            searchButton.setFocusPainted(false);
            searchButton.setPreferredSize(new Dimension(100, 30));

            JPanel inputPanel = new JPanel();
            inputPanel.setBackground(new Color(200, 220, 255));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
            inputPanel.add(label);
            inputPanel.add(textField);
            inputPanel.add(searchButton);

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(null);
            detailsPanel.setBackground(new Color(230, 240, 255));
            detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2), "Bill Details", 0, 0, new Font("Arial", Font.BOLD, 14), new Color(50, 50, 150)));

            JLabel nameLabel = new JLabel("Name: ");
            JLabel nameValue = new JLabel();
            JLabel datesLabel = new JLabel("Dates: ");
            JLabel datesValue = new JLabel();
            JLabel amountLabel = new JLabel("Amount: ");
            JLabel amountValue = new JLabel();

            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameValue.setFont(new Font("Arial", Font.PLAIN, 14));
            datesLabel.setFont(new Font("Arial", Font.BOLD, 14));
            datesValue.setFont(new Font("Arial", Font.PLAIN, 14));
            amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
            amountValue.setFont(new Font("Arial", Font.PLAIN, 14));

            nameLabel.setForeground(new Color(50, 50, 150));
            datesLabel.setForeground(new Color(50, 50, 150));
            amountLabel.setForeground(new Color(50, 50, 150));

            nameLabel.setBounds(50, 30, 100, 25);
            nameValue.setBounds(150, 30, 300, 25);
            datesLabel.setBounds(50, 70, 100, 25);
            datesValue.setBounds(150, 70, 300, 25);
            amountLabel.setBounds(50, 110, 100, 25);
            amountValue.setBounds(150, 110, 300, 25);

            detailsPanel.add(nameLabel);
            detailsPanel.add(nameValue);
            detailsPanel.add(datesLabel);
            detailsPanel.add(datesValue);
            detailsPanel.add(amountLabel);
            detailsPanel.add(amountValue);

            panel.add(inputPanel, BorderLayout.NORTH);
            panel.add(detailsPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(200, 220, 255));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Arial", Font.BOLD, 14));
            backButton.setBackground(new Color(255, 50, 50));
            backButton.setForeground(Color.WHITE);
            backButton.setPreferredSize(new Dimension(100, 30));

            backButton.addActionListener(e -> frame.dispose());

            buttonPanel.add(backButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            frame.add(panel);

            // Action for search button
            searchButton.addActionListener(e -> {
                String billId = textField.getText().trim();

                if (billId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a Bill ID", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "DEV", "dev");
                     PreparedStatement statement = connection.prepareStatement(
                             "SELECT name, amount, dates FROM BILL WHERE billid = ?")) {

                    statement.setString(1, billId);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        nameValue.setText(resultSet.getString("name"));
                        amountValue.setText(String.valueOf(resultSet.getDouble("amount")));
                        datesValue.setText(resultSet.getString("dates"));
                    } else {
                        JOptionPane.showMessageDialog(frame, "No bill found with ID: " + billId, "Info", JOptionPane.INFORMATION_MESSAGE);
                        nameValue.setText("");
                        amountValue.setText("");
                        datesValue.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Center the frame on the screen
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });
    }
}
