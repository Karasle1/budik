
import com.jogamp.opengl.*;

import com.jogamp.opengl.glu.GLU;



import java.awt.event.*;

import java.time.LocalDateTime;
import java.util.ArrayList;



import transforms.Camera;
import transforms.Vec3D;




public class start2 implements GLEventListener, MouseListener,
        MouseMotionListener, KeyListener {
    private GLU glu = new GLU();
    Camera cam = new Camera().withPosition(new Vec3D(0, 0, -1.5))
            .withAzimuth(Math.PI * 1.25)
            .withZenith(Math.PI * -0.125);
    int  ox, oy, delta;
    int width = 100;


    ArrayList cifernik = new ArrayList<Vec3D>();
    ArrayList zada = new ArrayList<Vec3D>();


    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT );
        gl.glEnable(GL2GL3.GL_DEPTH_TEST);

        int hour = LocalDateTime.now().getHour();
        float deltaH = -(hour * 30);
        int minute = LocalDateTime.now().getMinute();
        float deltaM = -(minute * 6);
        int sec = LocalDateTime.now().getSecond();
        float deltaS = -(sec * 6);

        double xc = cam.getPosition().getX();
        double yc = cam.getPosition().getY();
        double zc = cam.getPosition().getZ();

        gl.glLoadIdentity();

        gl.glRotated(-cam.getAzimuth(), 0.0f, 1.0f, 0.0f);
        gl.glRotated(cam.getZenith()*10, 1.0f, 0.0f, 0.0f);
        gl.glRotated(delta, 0.0f, 1.0f, 0.0f);
        gl.glTranslated(xc,yc,zc);



        gl.glBegin(GL2.GL_TRIANGLE_FAN);

        gl.glColor3f(0.173f, 0.173f, 0f);
        gl.glVertex3d(0.00f,0.00f,-4.0f);
       for(int i =0; i <= 360; i++) {
           double angle = 2 * Math.PI * i / 360;
           double x = Math.cos(angle);
           double y = Math.sin(angle);
           Vec3D vrchol = new Vec3D(x,y,-4f);
           cifernik.add(vrchol);
           gl.glColor3f(0.173f, 0.173f, 0f);
           gl.glVertex3d(x, y, -4f);
           gl.glColor3f(0.173f, 0.173f, 0f);
           gl.glVertex3d(x, y, -4f);
       }

        gl.glEnd();

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glColor3f(0.09f, 0.9f, 0.09f);
        gl.glVertex3d(0.00f,0.00f,-5.0f);
           gl.glColor3f(0.9f, 0.09f, 0.09f);
           gl.glVertex3d(0.0f, 0.0f, -5f);
           for (int i = 0; i <= 360; i++) {
               double angle = 2 * Math.PI * i / 360;
               double x = Math.cos(angle);
               double y = Math.sin(angle);
               Vec3D vrchol1 = new Vec3D(x,y,-5f);
               zada.add(vrchol1);
               gl.glColor3f(0.9f, 0.09f, 0.09f);
               gl.glVertex3d(x, y, -5f);
               gl.glColor3f(0.9f, 0.09f, 0.09f);
               gl.glVertex3d(x, y, -5f);
           }
               gl.glEnd();



       gl.glBegin(GL2.GL_QUAD_STRIP);
        for(int i =0; i <= 360; i++) {

           Vec3D a = (Vec3D) cifernik.get(i);
            gl.glColor3f(0.09f, 0.09f, 0.9f);
            gl.glVertex3d(a.getX(), a.getY(), a.getZ());

            Vec3D b = (Vec3D) zada.get(i);
            gl.glColor3f(0.09f, 0.09f, 0.9f);
            gl.glVertex3d(b.getX(), b.getY(), b.getZ());
        }

        gl.glEnd();


              gl.glLoadIdentity();
        gl.glRotated(-cam.getAzimuth(), 0.0f, 1.0f, 0.0f);
        gl.glRotated(cam.getZenith()*10, 1.0f, 0.0f, 0.0f);
        gl.glRotated(delta, 0.0f, 1.0f, 0.0f);
        gl.glTranslated(xc,yc,zc);
           gl.glRotatef(deltaH, 0, 0, 1);
              gl.glBegin(GL2.GL_TRIANGLES);
           gl.glColor3f(1.0f, 0.0f, 0.0f);
           gl.glVertex3f(-0.010f, 0.00f, -3.9f);
           gl.glVertex3f(0.010f, 0.00f, -3.9f);
           gl.glVertex3f(0.00f, 0.50f, -3.9f);
        gl.glEnd();


              gl.glLoadIdentity();
        gl.glRotated(-cam.getAzimuth(), 0.0f, 1.0f, 0.0f);
        gl.glRotated(cam.getZenith()*10, 1.0f, 0.0f, 0.0f);
        gl.glRotated(delta, 0.0f, 1.0f, 0.0f);
        gl.glTranslated(xc,yc,zc);
           gl.glRotatef(deltaM, 0, 0, 1);
              gl.glBegin(GL2.GL_TRIANGLES);
           gl.glColor3f(0.0f, 1.0f, 0.0f);
           gl.glVertex3f(-0.01f, 0.0f, -3.9f);
           gl.glVertex3f(0.01f, 0.0f, -3.9f);
           gl.glVertex3f(0.0f, 0.70f, -3.9f);
        gl.glEnd();
            gl.glLoadIdentity();
        gl.glRotated(-cam.getAzimuth(), 0.0f, 1.0f, 0.0f);
        gl.glRotated(cam.getZenith()*10, 1.0f, 0.0f, 0.0f);
        gl.glRotated(delta, 0.0f, 1.0f, 0.0f);
        gl.glTranslated(xc,yc,zc);
           gl.glRotatef(deltaS, 0, 0, 1);
              gl.glBegin(GL2.GL_LINE_STRIP);
           gl.glColor3f(0.0f, 0.50f, -3.90f);
           gl.glVertex3f(0.0f, 0.0f, -3.90f);
           gl.glVertex3f(0.0f, 0.80f, -3.90f);
           gl.glEnd();
       }

    @Override
    public void dispose(GLAutoDrawable glDrawable) {

        GL2 gl = glDrawable.getGL().getGL2();

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL2GL3.GL_DEPTH_TEST);
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



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                cam = cam.forward(1);
                break;
            case KeyEvent.VK_D:
                cam = cam.right(1);
                break;
            case KeyEvent.VK_S:
                cam = cam.backward(1);
                break;
            case KeyEvent.VK_A:
                cam = cam.left(1);
                break;
            case KeyEvent.VK_CONTROL:
                cam = cam.down(1);
                break;
        /*    case KeyEvent.VK_SHIFT:
                cam = cam.up(1);
                break;*/
            case KeyEvent.VK_SPACE:
                cam = cam.withFirstPerson(!cam.getFirstPerson());
                break;
            case KeyEvent.VK_R:
                cam = cam.mulRadius(0.9f);
                break;
            case KeyEvent.VK_F:
                cam = cam.mulRadius(1.1f);
                break;
            case KeyEvent.VK_T:

                    delta ++;

                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
            ox = e.getX();
            oy = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        cam = cam.addAzimuth( Math.PI * (ox - e.getX()) / width)
                .addZenith( Math.PI * (e.getY() - oy) / width);
        ox = e.getX();
        oy = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}