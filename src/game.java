import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class game {

    static Integer myNumber= ThreadLocalRandom.current().nextInt(0, 50 + 1);
    static Integer count = 0;
    static JLabel text = new JLabel("Gebe eine Zahl zwischen 0 und 50 ein!");
    static JLabel textTwo = new JLabel("Bei jedem falschen Versuch musst du 1x trinken!");
    static JLabel tries = new JLabel("Deine Versuche: " + count);
    static JTextField textField = new JTextField();

    public static void main(String[] args) {
        openUI();
    }

    public static void openUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Rate die Zahl! Oder trink einen!");
        frame.setSize(400, 300);
        frame.setLocation(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        text.setBounds(80, 60, 400, 30);
        textTwo.setBounds(50, 80, 400, 30);
        textField.setBounds(100, 120, 200, 30);
        tries.setBounds(270, 10, 200,30);

        JButton button = new JButton("Raten!");
        button.setBounds(100, 150, 100, 30);
        frame.getRootPane().setDefaultButton(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textFromTextField = textField.getText();
                    Integer number = Integer.parseInt(textFromTextField);
                    guess(number);
                } catch (Exception error) {
                    text.setText("Bitte gebe eine Zahl ein!");
                    textTwo.setText("");
                }
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(200, 150, 100, 30);

        // Reset Game by Button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count = 0;

                myNumber = ThreadLocalRandom.current().nextInt(0, 50 + 1);

                text.setText("Gebe eine Zahl zwischen 0 und 100 ein!");
                textTwo.setText("Bei jedem falschen Versuch musst du 1x saufen!");
                tries.setText("Deine Versuche: " + count);
                textField.setText("");

                textField.requestFocus();
            }
        });

        frame.add(button);
        frame.add(resetButton);
        frame.add(tries);
        frame.add(text);
        frame.add(textTwo);
        frame.add(textField);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void guess(Integer number) {

        if(number.equals(myNumber)) {
            text.setText("Richtig geraten! mit " + count + " Versuchen!");
        } else {
            count++;
            tries.setText("Deine Versuche: " + count);
            if(number < myNumber) {
                text.setText("Falsch geraten! eine Zahl ist zu klein!");
            } else {
                text.setText("Falsch geraten! eine Zahl ist zu groß!");
            }
            textField.setText("");
            textTwo.setText("");
        }

    }
}