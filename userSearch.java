import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class userSearch {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Search");
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

            JLabel label = new JLabel("Enter User ID: ");
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
            detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2), "User Details", 0, 0, new Font("Arial", Font.BOLD, 14), new Color(50, 50, 150)));

            JLabel usernameLabel = new JLabel("Username: ");
            JLabel usernameValue = new JLabel();
            JLabel genderLabel = new JLabel("Gender: ");
            JLabel genderValue = new JLabel();
            JLabel emailLabel = new JLabel("Email: ");
            JLabel emailValue = new JLabel();
            JLabel ageLabel = new JLabel("Age: ");
            JLabel ageValue = new JLabel();
            JLabel countryLabel = new JLabel("Country: ");
            JLabel countryValue = new JLabel();
            JLabel cityLabel = new JLabel("City: ");
            JLabel cityValue = new JLabel();
            JLabel streetLabel = new JLabel("Street: ");
            JLabel streetValue = new JLabel();

            usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            usernameValue.setFont(new Font("Arial", Font.PLAIN, 14));
            genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
            genderValue.setFont(new Font("Arial", Font.PLAIN, 14));
            emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
            emailValue.setFont(new Font("Arial", Font.PLAIN, 14));
            ageLabel.setFont(new Font("Arial", Font.BOLD, 14));
            ageValue.setFont(new Font("Arial", Font.PLAIN, 14));
            countryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            countryValue.setFont(new Font("Arial", Font.PLAIN, 14));
            cityLabel.setFont(new Font("Arial", Font.BOLD, 14));
            cityValue.setFont(new Font("Arial", Font.PLAIN, 14));
            streetLabel.setFont(new Font("Arial", Font.BOLD, 14));
            streetValue.setFont(new Font("Arial", Font.PLAIN, 14));

            usernameLabel.setForeground(new Color(50, 50, 150));
            genderLabel.setForeground(new Color(50, 50, 150));
            emailLabel.setForeground(new Color(50, 50, 150));
            ageLabel.setForeground(new Color(50, 50, 150));
            countryLabel.setForeground(new Color(50, 50, 150));
            cityLabel.setForeground(new Color(50, 50, 150));
            streetLabel.setForeground(new Color(50, 50, 150));

            usernameLabel.setBounds(50, 30, 100, 25);
            usernameValue.setBounds(150, 30, 300, 25);
            genderLabel.setBounds(50, 70, 100, 25);
            genderValue.setBounds(150, 70, 300, 25);
            emailLabel.setBounds(50, 110, 100, 25);
            emailValue.setBounds(150, 110, 300, 25);
            ageLabel.setBounds(50, 150, 100, 25);
            ageValue.setBounds(150, 150, 300, 25);
            countryLabel.setBounds(50, 190, 100, 25);
            countryValue.setBounds(150, 190, 300, 25);
            cityLabel.setBounds(50, 230, 100, 25);
            cityValue.setBounds(150, 230, 300, 25);
            streetLabel.setBounds(50, 270, 100, 25);
            streetValue.setBounds(150, 270, 300, 25);

            detailsPanel.add(usernameLabel);
            detailsPanel.add(usernameValue);
            detailsPanel.add(genderLabel);
            detailsPanel.add(genderValue);
            detailsPanel.add(emailLabel);
            detailsPanel.add(emailValue);
            detailsPanel.add(ageLabel);
            detailsPanel.add(ageValue);
            detailsPanel.add(countryLabel);
            detailsPanel.add(countryValue);
            detailsPanel.add(cityLabel);
            detailsPanel.add(cityValue);
            detailsPanel.add(streetLabel);
            detailsPanel.add(streetValue);

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
                String userId = textField.getText().trim();

                if (userId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a User ID", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "your_username", "your_password");
                     PreparedStatement statement = connection.prepareStatement(
                         "SELECT username, gender, email, age, country, city, street FROM users WHERE userid = ?")) {

                    statement.setString(1, userId);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        usernameValue.setText(resultSet.getString("username"));
                        genderValue.setText(resultSet.getString("gender"));
                        emailValue.setText(resultSet.getString("email"));
                        ageValue.setText(String.valueOf(resultSet.getInt("age")));
                        countryValue.setText(resultSet.getString("country"));
                        cityValue.setText(resultSet.getString("city"));
                        streetValue.setText(resultSet.getString("street"));
                    } else {
                        JOptionPane.showMessageDialog(frame, "No user found with ID: " + userId, "Info", JOptionPane.INFORMATION_MESSAGE);
                        usernameValue.setText("");
                        genderValue.setText("");
                        emailValue.setText("");
                        ageValue.setText("");
                        countryValue.setText("");
                        cityValue.setText("");
                        streetValue.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.setVisible(true);
        });
    }
}
