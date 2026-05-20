import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
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

        JLabel titleLabel = new JLabel("Welcome to the Hotel Management System", JLabel.CENTER);
        titleLabel.setBounds(50, 30, 500, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 255, 255));
        frame.add(titleLabel);

        // Hotel Search Button
        JButton hotelSearchButton = new JButton("Hotel Search");
        hotelSearchButton.setBounds(200, 120, 200, 40);
        hotelSearchButton.setBackground(new Color(70, 130, 180));
        hotelSearchButton.setForeground(Color.WHITE);
        hotelSearchButton.setFont(new Font("Arial", Font.BOLD, 16));
        hotelSearchButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        hotelSearchButton.setFocusPainted(false);
        hotelSearchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hotelSearchButton.setContentAreaFilled(false);

        hotelSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelSearch.main(new String[]{});
            }
        });

        // Staff Search Button
        JButton staffSearchButton = new JButton("Staff Search");
        staffSearchButton.setBounds(200, 180, 200, 40);
        staffSearchButton.setBackground(new Color(30, 144, 255));
        staffSearchButton.setForeground(Color.WHITE);
        staffSearchButton.setFont(new Font("Arial", Font.BOLD, 16));
        staffSearchButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        staffSearchButton.setFocusPainted(false);
        staffSearchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        staffSearchButton.setContentAreaFilled(false);

        staffSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StaffSearch.main(new String[]{});
            }
        });

        // Payment Button
        JButton paymentButton = new JButton("Payment");
        paymentButton.setBounds(200, 240, 200, 40);
        paymentButton.setBackground(new Color(50, 205, 50));
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setFont(new Font("Arial", Font.BOLD, 16));
        paymentButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        paymentButton.setFocusPainted(false);
        paymentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        paymentButton.setContentAreaFilled(false);

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BillSearch.main(new String[]{});
            }
        });

        // Room Search Button
        JButton roomSearchButton = new JButton("Room Search");
        roomSearchButton.setBounds(200, 300, 200, 40);
        roomSearchButton.setBackground(new Color(255, 165, 0));
        roomSearchButton.setForeground(Color.WHITE);
        roomSearchButton.setFont(new Font("Arial", Font.BOLD, 16));
        roomSearchButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        roomSearchButton.setFocusPainted(false);
        roomSearchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        roomSearchButton.setContentAreaFilled(false);

        roomSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomSearch.main(new String[]{});
            }
        });

        // User Search Button
        JButton userSearchButton = new JButton("User Search");
        userSearchButton.setBounds(200, 360, 200, 40);
        userSearchButton.setBackground(new Color(255, 69, 0));
        userSearchButton.setForeground(Color.WHITE);
        userSearchButton.setFont(new Font("Arial", Font.BOLD, 16));
        userSearchButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        userSearchButton.setFocusPainted(false);
        userSearchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userSearchButton.setContentAreaFilled(false);

        userSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userSearch.main(new String[]{});
            }
        });

        frame.add(hotelSearchButton);
        frame.add(staffSearchButton);
        frame.add(paymentButton);
        frame.add(roomSearchButton);
        frame.add(userSearchButton);

        frame.setVisible(true);
    }
}