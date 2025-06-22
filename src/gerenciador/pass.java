/**
 * I built this account manager because I wanted something simple that can work offline.
 * Still not what an expert would make, but still something that takes a while to learn and make it work.
 * Feel free to do whaterver you want with it. Hack, tweak, break, make it better (contact me if so, i'd like to see how to make it better).
 * 
 * How it works:
 * - The interface is built with Java Swing, so no doubt it would be kinda hard to mess with.
 * - Passwords are saved into a .txt file on your desktop that is recongnized automatically. Easy to open, no DB, no fuss.
 * - I threw in a gradient animation to make it less boring, since JPanels are ugly.
 * Have fun.
 * - Vitor Souza, 2025
 */

package gerenciador;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;
import javazoom.jl.player.Player;

public class pass extends JFrame {

    private JPanel contentPanel;
    private JLabel logoLabel;

    private Color startColor = new Color(180, 0, 0);
    private Color endColor = new Color(60, 0, 0);
    private float hue = 0.0f;

    private Thread musicThread;
    private Player player;

    public pass() {
        setTitle("Account Manager");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
        startGradientAnimation();
        playSong();
    }

    private void initComponents() {
        setContentPane(new GradientPanel());

        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(Box.createVerticalGlue());
        try {
            ImageIcon logoIcon = new ImageIcon("src/assets/logo.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaledLogo));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(logoLabel);
        } catch (Exception e) {
            System.err.println("Could not load logo: " + e.getMessage());
        }
        contentPanel.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton addButton = new JButton("Add account");
        JButton viewButton = new JButton("Saved accounts");

        styleButton(addButton);
        styleButton(viewButton);

        addButton.addActionListener(e -> {
            addAccountBlock();
        });

        viewButton.addActionListener(e -> readLog());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(10, 20, 10, 20));
        bottom.add(addButton);
        bottom.add(viewButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void addAccountBlock() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 10, 10, 10),
            new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY)
        ));

        GridBagConstraints comp = new GridBagConstraints();
        comp.insets = new Insets(6, 6, 6, 6);
        comp.fill = GridBagConstraints.HORIZONTAL;
        comp.weightx = 1;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color fontColor = Color.WHITE;

        comp.gridx = 0; comp.gridy = 0;
        card.add(createLabel("Origin:", fontColor), comp);
        comp.gridx = 1;
        JTextField originField = createTextField(fieldFont);
        card.add(originField, comp);

        comp.gridx = 0; comp.gridy = 1;
        card.add(createLabel("User:", fontColor), comp);
        comp.gridx = 1;
        JTextField userField = createTextField(fieldFont);
        card.add(userField, comp);

        comp.gridx = 0; comp.gridy = 2;
        card.add(createLabel("Email:", fontColor), comp);
        comp.gridx = 1;
        JTextField emailField = createTextField(fieldFont);
        card.add(emailField, comp);

        comp.gridx = 0; comp.gridy = 3;
        card.add(createLabel("Password:", fontColor), comp);
        comp.gridx = 1;
        JTextField passField = createTextField(fieldFont);
        card.add(passField, comp);

        comp.gridx = 1; comp.gridy = 4;
        comp.anchor = GridBagConstraints.EAST;
        JButton saveBtn = new JButton("Save account");
        styleButton(saveBtn);
        card.add(saveBtn, comp);

        saveBtn.addActionListener(e -> {
            String origin = originField.getText().trim();
            String user = userField.getText().trim();
            String email = emailField.getText().trim();
            String password = passField.getText().trim();

            if (origin.isEmpty() || user.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "fill all the fields", "warning", JOptionPane.WARNING_MESSAGE);
            } else {
                saveToLog(origin, user, email, password);
                saveBtn.setText("Salvo");
                saveBtn.setEnabled(false);
            }
        });

        contentPanel.add(card);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField field = new JTextField(25);
        field.setFont(font);
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            new EmptyBorder(6, 8, 6, 8)
        ));
        return field;
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(200, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void saveToLog(String origin, String user, String email, String password) {
        try {
            String desktop = System.getProperty("user.home") + "/Desktop";
            String logPath = Paths.get(desktop, "passlog.txt").toString();

            try (FileWriter fw = new FileWriter(logPath, true)) {
                fw.write("Origin: " + origin + "\n");
                fw.write("User: " + user + "\n");
                fw.write("Email: " + email + "\n");
                fw.write("password: " + password + "\n");
                fw.write("------------------------------\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Log error", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void readLog() {
        try {
            String desktop = System.getProperty("user.home") + "/Desktop";
            String logPath = Paths.get(desktop, "passlog.txt").toString();

            String content = new String(Files.readAllBytes(Paths.get(logPath)));
            JTextArea area = new JTextArea(content);
            area.setEditable(false);
            area.setBackground(new Color(20, 20, 20));
            area.setForeground(Color.LIGHT_GRAY);
            area.setFont(new Font("Consolas", Font.PLAIN, 12));

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(600, 400));

            JOptionPane.showMessageDialog(this, scroll, "Saved accounts", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No logs found", "warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void startGradientAnimation() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                hue += 0.01f;
                if (hue > 1) hue = 0;
                startColor = Color.getHSBColor(0, 1.0f, 0.4f + 0.2f * (float)Math.sin(hue * 2 * Math.PI));
                endColor = Color.getHSBColor(0, 1.0f, 0.2f + 0.2f * (float)Math.cos(hue * 2 * Math.PI));
                repaint();
            }
        }, 0, 30);
    }

    private void playSong() {
        musicThread = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream("src/assets/song.mp3");
                BufferedInputStream bis = new BufferedInputStream(fis);
                player = new Player(bis);
                player.play();
            } catch (Exception e) {
                System.err.println("Error while trying to play the song" + e.getMessage());
            }
        });
        musicThread.start();
    }

    private class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, startColor, width, height, endColor);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new pass().setVisible(true));
    }
}
