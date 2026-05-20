import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RoomSearch {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Room Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
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
        panel.setLayout(null);
        frame.setContentPane(panel);

        JLabel titleLabel = new JLabel("Room Search", JLabel.CENTER);
        titleLabel.setBounds(50, 30, 500, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));
        panel.add(titleLabel);

        JLabel roomLabel = new JLabel("Enter Room No:");
        roomLabel.setBounds(50, 100, 150, 30);
        roomLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roomLabel.setForeground(Color.WHITE);
        panel.add(roomLabel);

        JTextField roomField = new JTextField();
        roomField.setBounds(200, 100, 200, 30);
        roomField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(roomField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(420, 100, 100, 30);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        panel.add(searchButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(50, 160, 500, 250);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panel.add(resultArea);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomNo = roomField.getText().trim();

                if (roomNo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a Room No", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe", "dev", "dev");
                     PreparedStatement statement = connection.prepareStatement(
                             "SELECT r.roomno, r.cost, c.category, c.status, h.h_name " +
                                     "FROM room_details r " +
                                     "JOIN category c ON r.csid = c.csid " +
                                     "JOIN hotels h ON r.hotel_id = h.hotel_id " +
                                     "WHERE r.roomno = ?")) {

                    statement.setString(1, roomNo);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        StringBuilder result = new StringBuilder();
                        result.append("Room No: ").append(resultSet.getString("roomno")).append("\n");
                        result.append("Cost: ").append(resultSet.getDouble("cost")).append("\n");
                        result.append("Category: ").append(resultSet.getString("category")).append("\n");
                        result.append("Status: ").append(resultSet.getString("status")).append("\n");
                        result.append("Hotel Name: ").append(resultSet.getString("h_name")).append("\n");

                        resultArea.setText(result.toString());
                    } else {
                        JOptionPane.showMessageDialog(frame, "No room found with Room No: " + roomNo, "Info", JOptionPane.INFORMATION_MESSAGE);
                        resultArea.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 430, 100, 30);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        panel.add(backButton);

        backButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}
