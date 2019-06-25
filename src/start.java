import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class start {
    private static final int FPS = 300; // animator's target frames per second

    public void start() {
        try {
            //getting the capabilities object of GL2 profile
            final GLProfile profile = GLProfile.get(GLProfile.GL2);
            GLCapabilities capabilities = new GLCapabilities(profile);

            // The canvas
            final GLCanvas glcanvas = new GLCanvas(capabilities);
            Renderer s = new Renderer();
            glcanvas.addGLEventListener(s);
            glcanvas.addMouseListener(s);
            glcanvas.addMouseMotionListener(s);
            glcanvas.addKeyListener(s);
            glcanvas.setSize(1024, 780);
            //creating frame
            final Frame frame = new Frame (" Budik");
            final JButton btHelp = new JButton("?");
            final Help help;
            help = new Help();
            JToolBar tlacitka = new JToolBar("Help");
            tlacitka.add(btHelp);
            frame.add(tlacitka, BorderLayout.NORTH);
            btHelp.setMnemonic(KeyEvent.VK_F1);
            btHelp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = new JDialog(frame);
                    JTextArea textArea = new JTextArea(help.getText());
                    textArea.setBackground(help.getBackround());
                    textArea.setEditable(false);
                    dialog.add(textArea);
                    dialog.setTitle(help.getTitle());
                    dialog.setSize(new Dimension(800, 600));
                    dialog.setLocationRelativeTo(frame);
                    dialog.setModal(true);
                    dialog.setVisible(true);
                }
            });



            frame.add(glcanvas);
            frame.setSize( 1024, 780 );
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    System.exit(0);
                }
            });

            final FPSAnimator animator = new FPSAnimator(glcanvas,30,true);
            animator.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new start().start());
    }

}
