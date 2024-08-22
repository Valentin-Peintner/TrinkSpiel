/*
* @Dependencies
* */
package playerScorePackage;

import Player.PlayerScore;
import GameUtils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class game {

    /**
    * var usable in whole scope
    */
    static List<PlayerScore> playersScore = new ArrayList<>();
    static int currentRound = 0;
    static int maxRounds = 10;
    static int currentPlayerIndex = 0;
    static int totalPlayers;
    static Integer myNumber;
    static Integer count = 0;
    static JLabel text = new JLabel();
    static JLabel tries = new JLabel("Deine Versuche: " + count, SwingConstants.CENTER);
    static JLabel currentPlayerLabel = new JLabel("Aktueller Spieler: ", SwingConstants.CENTER);

    /**
    * returns StartPage of the Game
    */
    public static void main(String[] args) {
        openStartPage();
    }

    /**
     *  Logik for GamePage
     *  creates Frame and Layouts
     *  has functional button and Labels
     */
    public static void openStartPage() {
        JFrame frame = new JFrame("Wer falsch liegt, trinkt!");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Willkommen beim Zahlenraten – Wer falsch liegt, trinkt!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(welcomeLabel, gbc);

        JLabel playerCountLabel = new JLabel("Anzahl der Spieler:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(playerCountLabel, gbc);

        JSpinner playerSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        playerSpinner.setPreferredSize(new Dimension(100, 30)); // Größe des JSpinners anpassen
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(playerSpinner, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // Layout: 1 Reihe, 2 Spalten, 10px Abstand

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonPanel.add(startButton);

        JButton scoreButton = new JButton("Score-Liste");
        scoreButton.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonPanel.add(scoreButton);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(buttonPanel, gbc);

        // @var startButton by actioning to GamePage
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfPlayers = (Integer) playerSpinner.getValue();
                frame.dispose();
                openGamePage(numberOfPlayers);
            }
        });

        // @var ScoreButton by actioning to Scorelist
        scoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameUtils.openScorePage(frame, playersScore);
            }
        });

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     *  Logik for GamePage
     *  creates Frame and Layouts
     *  has functional button and Labels
     */
    public static void openGamePage(int numberOfPlayers) {
        JFrame frame = new JFrame("Rate die Zahl richtig oder trink einen Schluck!");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));

        JButton [] buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            final int buttonNumber = i + 1;
            buttons[i] = new JButton(String.valueOf(buttonNumber));
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 36));
            buttons[i].setBackground(Color.white);
            buttons[i].setOpaque(true);
            buttons[i].setBorder(BorderFactory.createEmptyBorder());
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guess(buttonNumber, buttons, text, tries, frame);
                }
            });

            buttonPanel.add(buttons[i]);
        }

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 24));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameUtils.resetGame(text, tries);
                frame.dispose();
                openStartPage();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(resetButton, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(currentPlayerLabel, BorderLayout.CENTER);

        frame.add(text, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(tries, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.PAGE_END);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initializePlayers(numberOfPlayers);
        updateCurrentPlayerLabel();
    }

    /**
     * Initialisze Playes to the Game
     *
     */
    public static void initializePlayers(int numberOfPlayers) {
        playersScore.clear();
        totalPlayers = numberOfPlayers;

        for (int i = 1; i <= numberOfPlayers; i++) {
            String playerName = JOptionPane.showInputDialog("Gib den Namen für Spieler " + i + " ein:");
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Spieler " + i;
            }
            playersScore.add(new PlayerScore(playerName));
        }

        currentPlayerIndex = 0;
        myNumber = ThreadLocalRandom.current().nextInt(1, 10);
    }

    /**
     * Logic off guessing number by checking if button is clicked
     */
    public static void guess(int number, JButton[] buttons, JLabel text, JLabel tries, JFrame frame) {
        if (buttons == null) {
            System.err.println("Buttons array is null.");
            return;
        }

        PlayerScore currentPlayer = playersScore.get(currentPlayerIndex);

        for (JButton button : buttons) {
            button.setBackground(Color.WHITE);
        }

        JButton clickedButton = null;
        for (JButton button : buttons) {
            if (Integer.parseInt(button.getText()) == number) {
                clickedButton = button;
                break;
            }
        }

        if (clickedButton != null) {
            if (number == myNumber) {
                text.setText("Richtig geraten von " + currentPlayer.getName() + "! Mit " + count + " Versuchen!");
                clickedButton.setBackground(Color.GREEN); // Button grün
                handleCorrectGuess(frame);
            } else {
                count++;
                tries.setText("Deine Versuche: " + count);
                if (Math.abs(number - myNumber) <= 2) {
                    clickedButton.setBackground(Color.YELLOW); // Button gelb
                } else {
                    clickedButton.setBackground(Color.RED); // Button rot
                }
                switchPlayer();
            }
        }
    }

    /**
     * When Player guessed correct, 1 Point gets added
     */
    private static void handleCorrectGuess(JFrame frame) {
        PlayerScore currentPlayer = playersScore.get(currentPlayerIndex);
        currentPlayer.addPoints(1);
        GameUtils.openScorePage(frame, playersScore);
        resetRound();
    }

    /**
     * reset game after finishing all rounds
     * announceWinner function gets called
     */
    private static void resetRound() {
        currentRound++;
        if (currentRound >= maxRounds) {
            announceWinner();
        } else {
            currentPlayerIndex = 0;
            count = 0;
            tries.setText("Deine Versuche: " + count);
            myNumber = ThreadLocalRandom.current().nextInt(1, 9 + 1);
            updateCurrentPlayerLabel();
        }
    }

    /**
     * First checks if player exists
     * If Winner is not NULL shows Winner of the Game
     */
    private static void announceWinner() {
        if (playersScore.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Keine Spieler vorhanden. Es kann kein Gewinner ermittelt werden.");
            return;
        }

        PlayerScore winner = playersScore.stream()
                .max((p1, p2) -> Integer.compare(p1.getPoints(), p2.getPoints()))
                .orElse(null);

        if (winner != null) {
            JOptionPane.showMessageDialog(null, "Herzlichen Glückwunsch " + winner.getName() + ", du hast gewonnen!");

            StringBuilder scoreList = new StringBuilder("Endstand:\n");
            for (PlayerScore player : playersScore) {
                scoreList.append(player.getName()).append(": ").append(player.getPoints()).append(" Punkte\n");
            }
            JOptionPane.showMessageDialog(null, scoreList.toString(), "Score-Liste", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Es gab einen Fehler beim Ermitteln des Gewinners.");
        }
    }

    /**
     * updates Player Name Label for each try
     */
    private static void updateCurrentPlayerLabel() {
        PlayerScore currentPlayer = playersScore.get(currentPlayerIndex);
        currentPlayerLabel.setText("Aktueller Spieler: " + currentPlayer.getName());
    }

    /**
     * switch Player after each try
     * calls updateCurrentPlayerLabel to set te correct Player name
     */
    private static void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;
        updateCurrentPlayerLabel();
    }
}
