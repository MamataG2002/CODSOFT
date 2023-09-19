import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class App{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Word Count");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.getContentPane().setBackground(new Color(200, 220, 240));

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 1));
            panel.setBackground(new Color(200, 220, 240));

            JLabel inputLabel = new JLabel("Enter text or choose a file:");
            inputLabel.setBackground(new Color(200, 220, 240));
            JTextField inputField = new JTextField();
            JButton countButton = new JButton("Count Words");

            JTextArea resultArea = new JTextArea();
            resultArea.setEditable(false);
            resultArea.setBackground(new Color(255, 255, 240));

            panel.add(inputLabel);
            panel.add(inputField);
            panel.add(countButton);

            frame.add(panel, BorderLayout.NORTH);
            frame.add(resultArea, BorderLayout.CENTER);

            countButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = inputField.getText();
                    int wordCount = 0;

                    if (input.toLowerCase().endsWith(".txt")) {
                        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
                            String line;
                            Set<String> uniqueWords = new HashSet<>();
                            while ((line = br.readLine()) != null) {
                                String[] words = line.split("\\s+");
                                uniqueWords.addAll(Arrays.asList(words));
                            }
                            wordCount = countNonCommonWords(uniqueWords);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        String[] words = input.split("\\s+");
                        wordCount = countNonCommonWords(new HashSet<>(Arrays.asList(words)));
                    }

                    resultArea.setText("Total unique words (excluding common words): " + wordCount);
                }
            });

            frame.setVisible(true);
        });
    }

    private static int countNonCommonWords(Set<String> words) {
        Set<String> commonWords = new HashSet<>(Arrays.asList(
                "the", "and", "is", "in", "to", "it", "of", "as", "with", "for")); // Add your common words here

        int wordCount = 0;

        for (String word : words) {
            if (!commonWords.contains(word.toLowerCase())) {
                wordCount++;
            }
        }

        return wordCount;
    }
}
