
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;


import java.awt.*;
import java.time.LocalDateTime;



public class start2 implements GLEventListener {
    private GLU glu = new GLU();

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT );


        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex3d(0.00f,0.00f,5.0f);
       for(int i =0; i <= 360; i++){
            double angle = 2 * Math.PI * i / 360;
            double x = Math.cos(angle);
            double y = Math.sin(angle);
            gl.glColor3f(0.09f, 0.09f, 0.09f );
            gl.glVertex3d(x,y,5.0f);
            gl.glVertex3d(x,y,5.0f);
        }
       gl.glEnd();

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex3d(0.0f,0.0f,-5f);
        for(int i =0; i <= 360; i++){
            double angle = 2 * Math.PI * i / 360;
            double x = Math.cos(angle);
            double y = Math.sin(angle);
            gl.glColor3f(0.9f, 0.09f, 0.09f );
            gl.glVertex3d(x,y,-5f);
            gl.glVertex3d(x,y,-5f);

        }
        gl.glEnd();


        int hour = LocalDateTime.now().getHour();
        float deltaH = -(hour * 30);
        gl.glLoadIdentity();
        gl.glRotatef(deltaH,0,0,1);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(1.0f, 0.0f, 0.0f );
        gl.glVertex3f(-0.010f,0.00f,-5.0f);
        gl.glVertex3f(0.010f,0.00f,-5.0f);
        gl.glVertex3f(0.00f,0.50f,-5.0f);
        gl.glEnd();

        int minute = LocalDateTime.now().getMinute();
        float deltaM = -(minute * 6);
        gl.glLoadIdentity();
        gl.glRotatef(deltaM,0,0,1);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3f(-0.01f,0.0f,-5.0f);
        gl.glVertex3f(0.01f,0.0f,-5.0f);
        gl.glVertex3f(0.0f,0.70f,-5.0f);
        gl.glEnd();

        int sec = LocalDateTime.now().getSecond();
        float deltaS = -(sec * 6);
        gl.glLoadIdentity();
        gl.glRotatef(deltaS,0,0,1);
        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glColor3f(0.0f,0.50f,-5.50f);
        gl.glVertex3f(0.0f,0.0f,-5.0f);
        gl.glVertex3f(0.0f,0.80f,-5.0f);
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
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        if( height <= 0 )
            height = 1;

        final float h = ( float ) width / ( float ) height;
        gl.glViewport( 0, 0, width, height );
        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        glu.gluPerspective( 45.0f, h, 1.0, 20.0 );
        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();
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
        frame.setSize( 1024, 780 );
        frame.setVisible(true);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 1,true);
        animator.start();
    }

}