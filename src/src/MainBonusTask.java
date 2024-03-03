import javax.swing.*;
import java.awt.*;

import static constants.Constants.*;
import static utils.Utils.*;

public class MainBonusTask {

    private static JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainBonusTask::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        final JFrame frame = new JFrame(UI_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel(new BorderLayout());
        textArea = new JTextArea(10, 40);

        final JButton selectFileButton = getSelectFileButton(frame, textArea);

        textArea.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(textArea);

        panel.add(selectFileButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
