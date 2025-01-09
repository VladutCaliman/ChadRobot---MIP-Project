package Utility;

import Models.Robots.BuffRobot;
import Models.Robots.NormRobot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Menu {
    private JFrame frame;

    private GameManager manager = new GameManager();

    private ImageHash imageHash = new ImageHash();

    private MenuPanel menuPanel = new MenuPanel("menu");
    private SelectPanel selectPanel = new SelectPanel("menu");
    private GamePanel gamePanel = new GamePanel("menu");

    private static final int unlockScore = 500;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Menu().createAndShowGUI();
        });
    }

    public void createAndShowGUI(){
        frame = new JFrame("ChadRobot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 550);
        frame.setResizable(false);

        frame.add(menuPanel);

        frame.setVisible(true);
    }

    class MenuPanel extends JPanel {
        private Image backgroundImage;

        public MenuPanel(String imageName) {
            this.backgroundImage = imageHash.getImage(imageName);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            addComponents();
        }

        private void addComponents() {
            JLabel titleLabel = new JLabel("ChadRobot");
            titleLabel.setFont(new Font("Verdana", Font.BOLD, 50));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel subtitleLabel = new JLabel("Real Name T.B.A.");

            subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            subtitleLabel.setForeground(Color.WHITE);
            subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            GlossyButton startButton = new GlossyButton("Start");
            styleButton(startButton);
            startButton.addActionListener(e -> {switchToPanel(selectPanel);});

            GlossyButton quitButton = new GlossyButton("Quit");
            styleButton(quitButton);
            quitButton.addActionListener(e -> System.exit(0));

            add(Box.createVerticalGlue());
            add(titleLabel);
            add(subtitleLabel);
            add(Box.createVerticalStrut(50));
            add(startButton);
            add(Box.createVerticalStrut(20));
            add(quitButton);
            add(Box.createVerticalGlue());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    class SelectPanel extends JPanel {
        private Image backgroundImage;

        public SelectPanel(String imageName) {
            this.backgroundImage = imageHash.getImage(imageName);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            addComponents();
        }

        private void addComponents() {
            JLabel scoreLabel = new JLabel("Your Score: " + manager.getScore());
            add(scoreLabel);
            scoreLabel.setFont(new Font("Verdana", Font.BOLD, 23));
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            GlossyButton character1Button = new GlossyButton("Character 1");
            styleButton(character1Button);
            character1Button.addActionListener(e -> {
                manager.setRobot(1);
                switchToPanel(gamePanel);
                gamePanel.requestFocusInWindow();
            });

            GlossyButton character2Button = new GlossyButton("Character 2");
            styleButton(character2Button);
            if(manager.getScore() < unlockScore){
                character2Button.setEnabled(false);
                character2Button.setText(unlockScore + " coins needed to unlock!");
            }
            character2Button.addActionListener(e -> {
                manager.setRobot(2);
                switchToPanel(gamePanel);
                gamePanel.requestFocusInWindow();
            });

            GlossyButton goBackButton = new GlossyButton("Go back");
            styleButton(goBackButton);


            add(Box.createVerticalGlue());
            add(character1Button);
            add(Box.createVerticalStrut(20));
            add(character2Button);
            add(Box.createVerticalStrut(20));
            add(goBackButton);
            add(Box.createVerticalGlue());

            goBackButton.addActionListener(e -> switchToPanel(menuPanel));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    class GamePanel extends JPanel implements KeyListener {
        private Image backgroundImage;
        private JLabel fuelLabel;
        private JLabel coinsLabel;

        private GlossyButton resetButton;

        public GamePanel(String imageName) {
            this.backgroundImage = imageHash.getImage(imageName);

            setLayout(new BorderLayout());
            requestFocusInWindow();
            setFocusable(true);
            addKeyListener(this);

            JPanel keyPanel = new JPanel();
            keyPanel.setLayout(new GridBagLayout());
            keyPanel.setOpaque(false);

            JPanel paddedPanel = new JPanel(new BorderLayout());
            paddedPanel.setOpaque(false);
            paddedPanel.add(keyPanel, BorderLayout.CENTER);
            paddedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 25, 10));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.gridx = 1;
            gbc.gridy = 0;

            resetButton = new GlossyButton("Reset");
            styleButton(resetButton);
            resetButton.addActionListener(e -> {
                manager.resetBoard();
                manager.resetRobot();
                repaint();
                requestFocusInWindow();
            });
            keyPanel.add(resetButton, gbc);

            gbc.insets = new Insets(15, 1, 1, 1);
            gbc.gridx = 0;
            gbc.gridy = 1;
            fuelLabel = new JLabel("<html>Fuel: " + manager.getRobot().getFuel() + "</html>", SwingConstants.CENTER);
            setFont(keyPanel, gbc, fuelLabel);


            gbc.insets = new Insets(5, 1, 1, 1);
            gbc.gridx = 1;
            gbc.gridy = 1;
            GlossyButton buttonW = new GlossyButton("W");
            styleButton(buttonW);
            buttonW.addActionListener(e -> {
                keyPressed(new KeyEvent(buttonW, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W'));
                requestFocusInWindow();
            });
            keyPanel.add(buttonW, gbc);

            gbc.gridx = 2;
            gbc.gridy = 1;
            coinsLabel = new JLabel("<html>Coins: " + manager.getRobot().getCurrentCoins() + "</html>", SwingConstants.CENTER);
            setFont(keyPanel, gbc, coinsLabel);

            gbc.gridx = 0;
            gbc.gridy = 2;
            GlossyButton buttonA = new GlossyButton("A");
            styleButton(buttonA);
            buttonA.addActionListener(e -> {
                keyPressed(new KeyEvent(buttonA, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A'));
                requestFocusInWindow();
            });
            keyPanel.add(buttonA, gbc);

            gbc.insets = new Insets(5, 1, 1, 1);
            gbc.gridx = 1;
            gbc.gridy = 2;
            GlossyButton buttonS = new GlossyButton("S");
            styleButton(buttonS);
            buttonS.addActionListener(e -> {
                keyPressed(new KeyEvent(buttonS, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S'));
                requestFocusInWindow();
            });
            keyPanel.add(buttonS, gbc);

            gbc.gridx = 2;
            gbc.gridy = 2;
            GlossyButton buttonD = new GlossyButton("D");
            styleButton(buttonD);
            buttonD.addActionListener(e -> {
                keyPressed(new KeyEvent(buttonD, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D'));
                requestFocusInWindow();
            });
            keyPanel.add(buttonD, gbc);

            add(paddedPanel, BorderLayout.SOUTH);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            resetButton.setVisible(false);
            if(manager.getRobot().didWin() || manager.getRobot().didDie()){
                if(manager.getRobot() instanceof NormRobot || (manager.getRobot() instanceof BuffRobot && ((BuffRobot) manager.getRobot()).hasUsedSecondChance())){
                    resetButton.setVisible(true);
                }
            }


            List<List<Integer>> map = manager.getBoard().getMap();
            if (map != null && !map.isEmpty()) {
                int rows = map.size();
                int cols = map.get(0).size();

                int horizontalPadding = 50;
                int topPadding = 15;

                int availableWidth = getWidth() - 2 * horizontalPadding;
                int cellWidth = availableWidth / cols;
                int cellHeight = (getHeight() - topPadding) / rows;
                int cellSize = Math.min(cellWidth, cellHeight);

                int totalMatrixWidth = cols * cellSize;
                int startX = (getWidth() - totalMatrixWidth) / 2;
                int startY = topPadding;

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int x = startX + j * cellSize;
                        int y = startY + i * cellSize;

                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, cellSize, cellSize);

                        int value = map.get(i).get(j);
                        Image cellImage = imageHash.getImage(value + "");

                        if (cellImage != null) {
                            g.drawImage(cellImage, x, y, cellSize, cellSize, this);
                        } else {
                            g.setColor(Color.ORANGE);
                            g.fillRect(x, y, cellSize, cellSize);
                        }

                        if (i == manager.getRobot().getX() && j == manager.getRobot().getY()) {
                            if (manager.getRobot() instanceof NormRobot) {
                                cellImage = imageHash.getImage("norm_robot");
                            } else {
                                cellImage = imageHash.getImage("buff_robot");
                            }

                            if (cellImage != null) {
                                g.drawImage(cellImage, x, y, cellSize, cellSize, null);
                            } else {
                                g.setColor(Color.RED);
                                g.fillOval(x + cellSize / 4, y + cellSize / 4, cellSize / 2, cellSize / 2); // Draw robot as a circle
                            }

                        }
                    }
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_W) {
                manager.processInput('w');
            } else if (keyCode == KeyEvent.VK_S) {
                manager.processInput('s');
            } else if (keyCode == KeyEvent.VK_A) {
                manager.processInput('a');
            } else if (keyCode == KeyEvent.VK_D) {
                manager.processInput('d');
            }
            coinsLabel.setText("Coins: "+ manager.getRobot().getCurrentCoins());

            fuelLabel.setText("Fuel: "+ manager.getRobot().getFuel());
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        private void setFont(JPanel keyPanel, GridBagConstraints gbc, JLabel label) {
            label.setFont(new Font("Verdana", Font.BOLD, 15));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.YELLOW);
            label.setPreferredSize(new Dimension(50, 30));
            label.setMaximumSize(new Dimension(50, 30));
            keyPanel.add(label, gbc);
        }
    }



    void switchToPanel(JPanel newPanel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(newPanel);
        frame.revalidate();
        frame.repaint();
    }
    class GlossyButton extends JButton {
        public GlossyButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(getBackground().darker());
            } else {
                g.setColor(getBackground());
            }
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getBackground().darker());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint paint = new GradientPaint(0, 0, getBackground().brighter(), 0, getHeight() / 2, getBackground());
            g2.setPaint(paint);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            super.paint(g2);
            g2.dispose();
        }
    }
    private void styleButton(JButton button) {
        button.setFont(new Font("Verdana", Font.PLAIN, 14));
        button.setBackground(new Color(255, 253, 208));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setMaximumSize(new Dimension(300, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
    }
}
