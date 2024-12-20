/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026221016 - Arjuna Ahmad Dewangga Aljabbar
 * 2 - 5026221126 -  Nur Ghulam Musthafa Al Kautsar
 * 3 - 5026221147 -  Ahmad Hilmi Dwi Setiawan
 */

package main;

import tictactoe.SoundEffect;
import tictactoe.TicTacToe;
import connectfour.GameMain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.ImageIcon;

public class WelcomeScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final Color COLOR_BG = new Color(20, 20, 20); // Gelap untuk latar belakang

    public WelcomeScreen(JFrame frame) {
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(COLOR_BG);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS)); // Mengubah ke layout horizontal

        // Membuat ikon dan teks
        ImageIcon icon = new ImageIcon("src/zombie.gif"); // Ganti dengan path gambar Anda
        Image image = icon.getImage(); // Mengambil gambar asli
        Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Mengubah ukuran gambar ke 100x100
        icon = new ImageIcon(resizedImage); // Membuat ImageIcon baru dengan gambar yang sudah diubah ukurannya

        JLabel titleLabel = new JLabel("Choose Your Game", icon, JLabel.LEFT); // Menambahkan ikon di kiri teks
        titleLabel.setFont(new Font("Chiller", Font.BOLD, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(255, 0, 0)); // Merah darah untuk teks

        // Menambahkan label ke panel
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createHorizontalGlue());

        add(titlePanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30)); // Gelap untuk latar belakang tombol
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setPreferredSize(new Dimension(0, 120));

        // Tic Tac Toe Button
        JButton ticTacToeButton = createStyledButton("Tic Tac Toe", new Color(0, 100, 0)); // Hijau gelap zombie
        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to Tic Tac Toe
                frame.setContentPane(new tictactoe.WelcomeScreen(frame)); // Multiplayer mode
                frame.revalidate();
            }
        });

        // Connect Four Button
        JButton connectFourButton = createStyledButton("Connect Four", new Color(139, 0, 0)); // Merah darah gelap
        connectFourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to Connect Four
                frame.setContentPane(new GameMain(frame)); // Connect Four main panel
                frame.revalidate();
            }
        });

        buttonPanel.add(ticTacToeButton);
        buttonPanel.add(connectFourButton);

        add(buttonPanel, BorderLayout.SOUTH); // Fix buttons at the bottom
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Chiller", Font.BOLD, 26));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }
}
