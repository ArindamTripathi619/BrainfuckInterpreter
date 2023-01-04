 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class GifOptionPane extends Interpret {
    static void waitAnimation(int delaySec){
        String gifPath = "C:\\Users\\Arindam Tripathi\\Desktop\\BrainFuck Compiler 2\\Settings.gif";
        JLabel label = new JLabel(new ImageIcon(gifPath));
        JOptionPane pane = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = pane.createDialog(null, "Loading");
        dialog.setModal(false);

        Timer timer = new Timer(delaySec*1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();

        // Show the dialog until the timer runs out
        while (timer.isRunning()) {
            dialog.setVisible(true);
        }
    }
}
