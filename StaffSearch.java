import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class StaffSearch {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Staff Search");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 450);
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

            JLabel label = new JLabel("Enter Staff ID: ");
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
            detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2), "Staff Details", 0, 0, new Font("Arial", Font.BOLD, 14), new Color(50, 50, 150)));

            JLabel nameLabel = new JLabel("Name: ");
            JLabel nameValue = new JLabel();
            JLabel salaryLabel = new JLabel("Salary: ");
            JLabel salaryValue = new JLabel();
            JLabel genderLabel = new JLabel("Gender: ");
            JLabel genderValue = new JLabel();
            JLabel phoneLabel = new JLabel("Phone: ");
            JLabel phoneValue = new JLabel();
            JLabel hotelLabel = new JLabel("Hotel Name: ");
            JLabel hotelValue = new JLabel();

            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameValue.setFont(new Font("Arial", Font.PLAIN, 14));
            salaryLabel.setFont(new Font("Arial", Font.BOLD, 14));
            salaryValue.setFont(new Font("Arial", Font.PLAIN, 14));
            genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
            genderValue.setFont(new Font("Arial", Font.PLAIN, 14));
            phoneLabel.setFont(new Font("Arial", Font.BOLD, 14));
            phoneValue.setFont(new Font("Arial", Font.PLAIN, 14));
            hotelLabel.setFont(new Font("Arial", Font.BOLD, 14));
            hotelValue.setFont(new Font("Arial", Font.PLAIN, 14));

            nameLabel.setForeground(new Color(50, 50, 150));
            salaryLabel.setForeground(new Color(50, 50, 150));
            genderLabel.setForeground(new Color(50, 50, 150));
            phoneLabel.setForeground(new Color(50, 50, 150));
            hotelLabel.setForeground(new Color(50, 50, 150));

            nameLabel.setBounds(50, 30, 100, 25);
            nameValue.setBounds(150, 30, 300, 25);
            salaryLabel.setBounds(50, 70, 100, 25);
            salaryValue.setBounds(150, 70, 300, 25);
            genderLabel.setBounds(50, 110, 100, 25);
            genderValue.setBounds(150, 110, 300, 25);
            phoneLabel.setBounds(50, 150, 100, 25);
            phoneValue.setBounds(150, 150, 300, 25);
            hotelLabel.setBounds(50, 190, 100, 25);
            hotelValue.setBounds(150, 190, 300, 25);

            detailsPanel.add(nameLabel);
            detailsPanel.add(nameValue);
            detailsPanel.add(salaryLabel);
            detailsPanel.add(salaryValue);
            detailsPanel.add(genderLabel);
            detailsPanel.add(genderValue);
            detailsPanel.add(phoneLabel);
            detailsPanel.add(phoneValue);
            detailsPanel.add(hotelLabel);
            detailsPanel.add(hotelValue);

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
                String staffId = textField.getText().trim();

                if (staffId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a Staff ID", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "dev", "dev");
                     PreparedStatement statement = connection.prepareStatement(
                         "SELECT s.S_NAME, s.SALARY, s.GENDER, s.PHONE, h.H_NAME AS HOTEL_NAME " +
                         "FROM staff s " +
                         "JOIN hotels h ON s.hotel_id = h.hotel_id " +
                         "WHERE s.S_ID = ?")) {

                    statement.setString(1, staffId);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        nameValue.setText(resultSet.getString("S_NAME"));
                        salaryValue.setText(String.valueOf(resultSet.getDouble("SALARY")));
                        genderValue.setText(resultSet.getString("GENDER"));
                        phoneValue.setText(resultSet.getString("PHONE"));
                        hotelValue.setText(resultSet.getString("HOTEL_NAME"));
                    } else {
                        JOptionPane.showMessageDialog(frame, "No staff found with ID: " + staffId, "Info", JOptionPane.INFORMATION_MESSAGE);
                        nameValue.setText("");
                        salaryValue.setText("");
                        genderValue.setText("");
                        phoneValue.setText("");
                        hotelValue.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.setVisible(true);
        });
    }
}
