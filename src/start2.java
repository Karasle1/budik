
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.Frame;
import javax.swing.JFrame;

import static com.jogamp.opengl.math.FloatUtil.sin;
import static com.jogamp.opengl.math.FloatUtil.cos;


public class start2 implements GLEventListener {

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glBegin(GL.GL_LINE_LOOP);
        for(int i =0; i <= 300; i++){
            double angle = 2 * Math.PI * i / 300;
            double x = Math.cos(angle);
            double y = Math.sin(angle);
            gl.glVertex2d(x,y);
        }
        gl.glEnd();
    }
    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body
    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        // method body
    }

    public static void main(String[] args) {

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        start2 s = new start2();
        glcanvas.addGLEventListener(s);
        glcanvas.setSize(400, 400);

        //creating frame
        final Frame frame = new Frame (" Budik");

        //adding canvas to frame
        frame.add(glcanvas);
        frame.setSize( 800, 600 );
        frame.setVisible(true);
    }

}