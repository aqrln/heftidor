package ninja.aqrln.editor.ui.frames;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.FlowLayout;

/**
 * @author Alexey Orlenko
 */
public class AboutDialog extends JDialog {
    private JLabel text;

    public AboutDialog() {
        super((JDialog)null, "About Editor");

        setSize(300, 200);
        setResizable(false);

        setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 35));
        setContentPane(pane);

        pane.setBackground(UIManager.getColor("window"));

        text = new JLabel("<html><div style=\"text-align: center;\">" +
                "<h1>Editor</h1><p>&copy; 2016 Alexey Orlenko</p>" +
                "<p>Coursework for the OOP course at KPI</p></div></html>");
        getContentPane().add(text);
    }
}
