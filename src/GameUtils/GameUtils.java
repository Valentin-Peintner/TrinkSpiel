package GameUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Player.PlayerScore;
import Player.Scorable;

public class GameUtils {
    static Integer myNumber = ThreadLocalRandom.current().nextInt(1, 9 + 1);

    public static void openScorePage(JFrame frame, List<PlayerScore> playerScore) {
        JFrame scoreFrame = new JFrame("Score-Seite");
        scoreFrame.setSize(400, 200);
        scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scoreFrame.setLayout(new BorderLayout());

        JTextArea scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setText("Endstand:\n");
        for (PlayerScore player : playerScore) {
            scoreArea.append(player.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(scoreArea);
        scoreFrame.add(scrollPane, BorderLayout.CENTER);
        scoreFrame.setLocationRelativeTo(null);
        scoreFrame.setVisible(true);

        StringBuilder scoreMessage = new StringBuilder("Punkteübersicht:\n");
        for (PlayerScore player : playerScore) {
            scoreMessage.append(player.getName()).append(": ").append(player.getPoints()).append(" Punkte\n");
        }
        JOptionPane.showMessageDialog(frame, scoreMessage.toString(), "Punkte", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void resetGame(JLabel text, JLabel tries) {
        int count = 0;
        text.setText("Rate die Zahl zwischen 1 und 9!");
        tries.setText("Deine Versuche: " + count);
        myNumber = ThreadLocalRandom.current().nextInt(1, 9 + 1);
    }
}
