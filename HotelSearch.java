import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class HotelSearch {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hotel Search");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), new Color(0, 0, 139));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            panel.setLayout(new BorderLayout());
            frame.setContentPane(panel);

            JLabel label = new JLabel("Enter Hotel ID: ");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(new Color(255, 255, 255));

            JTextField textField = new JTextField(20);
            textField.setFont(new Font("Arial", Font.PLAIN, 14));
            textField.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2));

            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Arial", Font.BOLD, 14));
            searchButton.setBackground(new Color(50, 150, 255));
            searchButton.setForeground(Color.WHITE);
            searchButton.setFocusPainted(false);

            JPanel inputPanel = new JPanel();
            inputPanel.setBackground(new Color(200, 220, 255));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            inputPanel.add(label);
            inputPanel.add(textField);
            inputPanel.add(searchButton);

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(null);
            detailsPanel.setBackground(new Color(230, 240, 255));
            detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2), "Hotel Details", 0, 0, new Font("Arial", Font.BOLD, 14), new Color(50, 50, 150)));

            JLabel nameLabel = new JLabel("Name: ");
            JLabel nameValue = new JLabel();
            JLabel cityLabel = new JLabel("City: ");
            JLabel cityValue = new JLabel();
            JLabel countryLabel = new JLabel("Country: ");
            JLabel countryValue = new JLabel();
            JLabel ratingLabel = new JLabel("Rating: ");
            JLabel ratingValue = new JLabel();

            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameValue.setFont(new Font("Arial", Font.PLAIN, 14));
            cityLabel.setFont(new Font("Arial", Font.BOLD, 14));
            cityValue.setFont(new Font("Arial", Font.PLAIN, 14));
            countryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            countryValue.setFont(new Font("Arial", Font.PLAIN, 14));
            ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
            ratingValue.setFont(new Font("Arial", Font.PLAIN, 14));

            nameLabel.setForeground(new Color(50, 50, 150));
            cityLabel.setForeground(new Color(50, 50, 150));
            countryLabel.setForeground(new Color(50, 50, 150));
            ratingLabel.setForeground(new Color(50, 50, 150));

            nameLabel.setBounds(50, 30, 100, 25);
            nameValue.setBounds(150, 30, 300, 25);
            cityLabel.setBounds(50, 70, 100, 25);
            cityValue.setBounds(150, 70, 300, 25);
            countryLabel.setBounds(50, 110, 100, 25);
            countryValue.setBounds(150, 110, 300, 25);
            ratingLabel.setBounds(50, 150, 100, 25);
            ratingValue.setBounds(150, 150, 300, 25);

            detailsPanel.add(nameLabel);
            detailsPanel.add(nameValue);
            detailsPanel.add(cityLabel);
            detailsPanel.add(cityValue);
            detailsPanel.add(countryLabel);
            detailsPanel.add(countryValue);
            detailsPanel.add(ratingLabel);
            detailsPanel.add(ratingValue);

            panel.add(inputPanel, BorderLayout.NORTH);
            panel.add(detailsPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(200, 220, 255));

            JButton backButton = new JButton("Back");

            backButton.setFont(new Font("Arial", Font.BOLD, 14));
            backButton.setBackground(new Color(50, 150, 255));
            backButton.setForeground(Color.WHITE);

            backButton.addActionListener(e -> frame.dispose());

            buttonPanel.add(backButton);

            panel.add(buttonPanel, BorderLayout.SOUTH);

            searchButton.addActionListener(e -> {
                String hotelId = textField.getText().trim();

                if (hotelId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a Hotel ID", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "dev", "dev");
                     PreparedStatement statement = connection.prepareStatement(
                         "SELECT h.h_name, h.ratings, l.city, l.country " +
                         "FROM hotels h " +
                         "JOIN location l ON h.ccid = l.ccid " +
                         "WHERE h.hotel_id = ?")) {

                    statement.setString(1, hotelId);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        nameValue.setText(resultSet.getString("h_name"));
                        ratingValue.setText(String.valueOf(resultSet.getFloat("ratings")));
                        cityValue.setText(resultSet.getString("city"));
                        countryValue.setText(resultSet.getString("country"));
                    } else {
                        JOptionPane.showMessageDialog(frame, "No hotel found with ID: " + hotelId, "Info", JOptionPane.INFORMATION_MESSAGE);
                        nameValue.setText("");
                        ratingValue.setText("");
                        cityValue.setText("");
                        countryValue.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.setVisible(true);
        });
    }
}
