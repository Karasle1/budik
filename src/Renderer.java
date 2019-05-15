import com.jogamp.opengl.*;

import com.jogamp.opengl.util.texture.Texture;
import oglutils.OGLBuffers;
import oglutils.OGLTextRenderer;
import oglutils.OGLUtils;
import transforms.Mat4;
import oglutils.ShaderUtils;
import oglutils.ToFloatArray;

import java.awt.event.*;
import java.time.LocalDateTime;
import transforms.Camera;
import transforms.Vec3D;

public class Renderer implements GLEventListener, MouseListener,
        MouseMotionListener, KeyListener {
    int width, height, ox, oy;
    int shaderProgram, locMat;
    OGLBuffers buffers;
    OGLTextRenderer textRenderer;

    Texture texture;

    Camera cam = new Camera();
    Mat4 proj;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

        // check whether shaders are supported
        GL2GL3 gl = glAutoDrawable.getGL().getGL2GL3();
        OGLUtils.shaderCheck(gl);

        // get and set debug version of GL class
        gl = OGLUtils.getDebugGL(gl);
        glAutoDrawable.setGL(gl);

        OGLUtils.printOGLparameters(gl);



        cam = cam.withPosition(new Vec3D(5, 5, 2.5))
                .withAzimuth(Math.PI * 1.25)
                .withZenith(Math.PI * -0.125);

        gl.glEnable(GL2GL3.GL_DEPTH_TEST);

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

            GL2GL3 gl = glAutoDrawable.getGL().getGL2GL3();
            gl.glDeleteProgram(shaderProgram);

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
       GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2GL3.GL_DEPTH_BUFFER_BIT );
      //  gl.glUseProgram(shaderProgram);
        locMat = gl.glGetUniformLocation(0, "mat");
      gl.glUniformMatrix4fv(locMat, 1, false,
                ToFloatArray.convert(cam.getViewMatrix().mul(proj)), 0);

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex3d(0.00f,0.00f,5.0f);
        for(int i =0; i <= 360; i++){
            double angle = 2 * Math.PI * i / 360;
            double x = Math.cos(angle);
            double y = Math.sin(angle);
            gl.glColor3f(0.09f, 0.09f, 0.9f );
            gl.glVertex3d(x,y,5f);
            gl.glVertex3d(x,y,5f);
        }
        gl.glEnd();

/*
        gl.glLoadIdentity();
        gl.glRotatef(20,0,0,0);
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
        gl.glVertex3f(-0.010f,0.00f,-3.9f);
        gl.glVertex3f(0.010f,0.00f,-3.9f);
        gl.glVertex3f(0.00f,0.50f,-3.9f);
        gl.glEnd();

        int minute = LocalDateTime.now().getMinute();
        float deltaM = -(minute * 6);
        gl.glLoadIdentity();
        gl.glRotatef(deltaM,0,0,1);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3f(-0.01f,0.0f,-3.9f);
        gl.glVertex3f(0.01f,0.0f,-3.9f);
        gl.glVertex3f(0.0f,0.70f,-3.9f);
        gl.glEnd();

        int sec = LocalDateTime.now().getSecond();
        float deltaS = -(sec * 6);
        gl.glLoadIdentity();
        gl.glRotatef(deltaS,0,0,1);
        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glColor3f(0.0f,0.50f,-3.90f);
        gl.glVertex3f(0.0f,0.0f,-3.90f);
        gl.glVertex3f(0.0f,0.80f,-3.90f);
        gl.glEnd();
*/

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
