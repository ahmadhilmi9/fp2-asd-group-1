/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026221016 - Arjuna Ahmad Dewangga Aljabbar
 * 2 - 5026221126 -  Nur Ghulam Musthafa Al Kautsar
 * 3 - 5026221147 -  Ahmad Hilmi Dwi Setiawan
 */

package tictactoe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WelcomeScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final Color COLOR_BG = new Color(20, 20, 20); // Darker background for zombie vibes

    public WelcomeScreen(JFrame frame) {
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);

        // Title Panel (Dynamic)
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(COLOR_BG);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Plant VS Zombie Tic Tac Toe");
        titleLabel.setFont(new Font("Chiller", Font.BOLD, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(200, 0, 0)); // Blood red title

        JLabel subtitleLabel = new JLabel("Choose Your Game Mode");
        subtitleLabel.setFont(new Font("Chiller", Font.BOLD, 26));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(new Color(100, 100, 100)); // Subtle gray subtitle

        titlePanel.add(Box.createVerticalGlue()); // Add vertical space (makes the title dynamic)
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10)); // Small space between title and subtitle
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalGlue()); // Add vertical space (makes the title dynamic)

        add(titlePanel, BorderLayout.CENTER); // Center section grows dynamically

        // Button Panel (Fixed)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30)); // Darker button panel background
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(0, 80)); // Fixed height for the button panel

        // Single Player Button
        JButton singlePlayerButton = createStyledButton("Single Player", new Color(0, 100, 0)); // Darker green for zombie vibe
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCharacterSelectionDialog(frame, true);
            }
        });

        // Multi Player Button
        JButton multiPlayerButton = createStyledButton("Multi Player", new Color(100, 0, 0)); // Dark red for zombie vibe
        multiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new TicTacToe(frame, Seed.CROSS, false));
                frame.revalidate();
            }
        });

        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiPlayerButton);

        add(buttonPanel, BorderLayout.SOUTH); // Pin the button panel to the bottom
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

    private void showCharacterSelectionDialog(JFrame frame, boolean isSinglePlayer) {
        JDialog dialog = new JDialog(frame, "Choose Character", true);
        dialog.getContentPane().setBackground(new Color(15, 15, 15)); // Very dark background
        dialog.setLayout(new BorderLayout(10, 10));

        JLabel chooseLabel = new JLabel("Choose Character (Zombie play first)", JLabel.CENTER);
        chooseLabel.setFont(new Font("Chiller", Font.BOLD, 26));
        chooseLabel.setForeground(new Color(200, 0, 0)); // Blood red text
        dialog.add(chooseLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(15, 15, 15)); // Match dialog background
        buttonPanel.setLayout(new FlowLayout());

        JRadioButton zombieButton = new JRadioButton("Zombie");
        zombieButton.setFont(new Font("Chiller", Font.BOLD, 26));
        zombieButton.setSelected(true);
        zombieButton.setForeground(new Color(200, 200, 200)); // Subtle gray text
        zombieButton.setBackground(new Color(15, 15, 15));

        JRadioButton plantButton = new JRadioButton("Plant");
        plantButton.setFont(new Font("Chiller", Font.BOLD, 26));
        plantButton.setForeground(new Color(200, 200, 200)); // Subtle gray text
        plantButton.setBackground(new Color(15, 15, 15));

        ButtonGroup group = new ButtonGroup();
        group.add(zombieButton);
        group.add(plantButton);

        buttonPanel.add(zombieButton);
        buttonPanel.add(plantButton);
        dialog.add(buttonPanel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Chiller", Font.PLAIN, 26));
        confirmButton.setBackground(new Color(0, 100, 0)); // Dark green button
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Seed playerSeed = zombieButton.isSelected() ? Seed.CROSS : Seed.NOUGHT;
                frame.setContentPane(new TicTacToe(frame, playerSeed, isSinglePlayer));
                frame.revalidate();
                dialog.dispose();
            }
        });
        dialog.add(confirmButton, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
