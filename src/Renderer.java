import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import java.awt.event.*;
import java.io.*;
import java.nio.DoubleBuffer;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import transforms.Camera;
import transforms.Vec3D;

import com.jogamp.opengl.GL2GL3;

public class Renderer extends Thread implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

	private GLU glu = new GLU();
	private Camera cam = new Camera().withPosition(new Vec3D(0, 0, 0)).withAzimuth(Math.PI * 0.5)
			.withZenith(Math.PI * 0);
	private int ox, oy;
	private double ratio = 1, width, height;
	private double buzeniX, buzeniY, deltaBuzeni, buzeniPozice, deltaH;
	private Texture texture, texture0, texture1;
	private ArrayList<Vec3D> cifernik = new ArrayList<>();
	private ArrayList<Vec3D> zada = new ArrayList<>();
	private boolean mode = false;

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, ratio, 0.1, 20.0);
		gl.glMultMatrixf(cam.getViewMatrix().floatArray(), 0);
		gl.glRotated(90, 1.0f, 0.0f, 0.0f);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_NORMALIZE);
		double minute = LocalDateTime.now().getMinute(), sec = LocalDateTime.now().getSecond();
		double hour = LocalDateTime.now().getHour();
		deltaH = -(hour * 30) - (Math.abs((double) LocalDateTime.now().getMinute() / 2));
		double deltaM = -(minute * 6), deltaS = -(sec * 6);
		double xc = cam.getPosition().getX(), yc = cam.getPosition().getY(), zc = cam.getPosition().getZ();
		GLUquadric quadObj;
		quadObj = glu.gluNewQuadric();

		float[] light_position = new float[] { 1f, 2f, 1f, 0f };
		float[] ambientLight = { 1f, 2f, 1f, 0f };

		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);

		// Nozicky a drzak zvonku
		gl.glLoadIdentity();
		gl.glTranslatef(-0.5f, -0.7f, -4.5f);
		gl.glRotated(90, 0.0f, 1.0f, 0.0f);
		gl.glRotated(120, 1.0f, 0.0f, 0.0f);
		glu.gluCylinder(quadObj, 0.05f, 0.01f, 0.70f, 32, 32);

		gl.glLoadIdentity();
		gl.glTranslatef(0.5f, -0.7f, -4.5f);
		gl.glRotated(90, 0.0f, 1.0f, 0.0f);
		gl.glRotated(60, 1.0f, 0.0f, 0.0f);
		glu.gluCylinder(quadObj, 0.05f, 0.01f, 0.70f, 32, 32);

		gl.glLoadIdentity();
		gl.glTranslatef(-0.8f, 1.2f, -4.5f);
		gl.glRotated(90, 0.0f, 1.0f, 0.0f);
		gl.glRotated(60, 1.0f, 0.0f, 0.0f);
		glu.gluCylinder(quadObj, 0.01f, 0.05f, 0.70f, 32, 32);

		gl.glLoadIdentity();
		gl.glTranslatef(0.8f, 1.2f, -4.5f);
		gl.glRotated(90, 0.0f, 1.0f, 0.0f);
		gl.glRotated(120, 1.0f, 0.0f, 0.0f);
		glu.gluCylinder(quadObj, 0.01f, 0.05f, 0.70f, 32, 32);

		// zvonky
		DoubleBuffer clipEq = DoubleBuffer.allocate(4);
		clipEq.put(0, 0.0);
		clipEq.put(1, 0.5);

		gl.glLoadIdentity();
		gl.glTranslatef(0.7f, 1.1f, -4.5f);
		gl.glRotated(-30, 0.0f, 0.0f, 1.0f);
		gl.glClipPlane(GL2.GL_CLIP_PLANE0, clipEq);
		gl.glEnable(GL2.GL_CLIP_PLANE0);
		glu.gluSphere(quadObj, 0.5, 32, 32);
		gl.glDisable(GL2.GL_CLIP_PLANE0);

		gl.glLoadIdentity();
		gl.glTranslatef(-0.7f, 1.1f, -4.5f);
		gl.glRotated(30, 0.0f, 0.0f, 1.0f);
		gl.glClipPlane(GL2.GL_CLIP_PLANE1, clipEq);
		gl.glEnable(GL2.GL_CLIP_PLANE1);
		glu.gluSphere(quadObj, 0.5, 32, 32);
		gl.glDisable(GL2.GL_CLIP_PLANE1);

		// cifernik
		gl.glLoadIdentity();
		texture.bind(gl);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2d(0.49, 0.5);
		gl.glVertex3d(0.00f, 0.00f, -4.0f);
		for (int i = 0; i <= 360; i++) {
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
			double angle = 2 * Math.PI * i / 360;
			double x = Math.cos(angle);
			double y = Math.sin(angle);
			Vec3D vrchol = new Vec3D(x, y, -4f);
			cifernik.add(vrchol);
			gl.glTexCoord2d((x / 2) + 0.49, (y / 2) + 0.5);
			gl.glVertex3d(x, y, -4f);
		}
		gl.glEnd();

		// záda
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor3f(0.09f, 0.9f, 0.09f);
		gl.glVertex3d(0.00f, 0.00f, -5.0f);
		gl.glColor3f(0.9f, 0.09f, 0.09f);
		gl.glVertex3d(0.0f, 0.0f, -5f);
		for (int i = 0; i <= 360; i++) {
			double angle = 2 * Math.PI * i / 360;
			double x = Math.cos(angle);
			double y = Math.sin(angle);
			Vec3D vrchol1 = new Vec3D(x, y, -5f);
			zada.add(vrchol1);
			gl.glColor3f(0.9f, 0.09f, 0.09f);
			gl.glVertex3d(x, y, -5f);
		}
		gl.glEnd();
		// bok
		texture0.bind(gl);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2GL3.GL_REPLACE);
		gl.glBegin(GL2.GL_QUAD_STRIP);
		for (int i = 0; i <= 360; i++) {
			Vec3D a = (Vec3D) cifernik.get(i);
			gl.glTexCoord2d(a.getX(), a.getY());
			gl.glVertex3d(a.getX(), a.getY(), a.getZ());
			Vec3D b = (Vec3D) zada.get(i);
			gl.glTexCoord2d(b.getX(), b.getY());
			gl.glVertex3d(b.getX(), b.getY(), b.getZ());
		}
		gl.glEnd();

		// rucicky
		gl.glLoadIdentity();
		gl.glRotated(deltaH, 0, 0, 1);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(-0.030f, 0.00f, -3.9f);
		gl.glVertex3f(0.030f, 0.00f, -3.9f);
		gl.glVertex3f(0.00f, 0.50f, -3.9f);
		gl.glEnd();

		gl.glLoadIdentity();
		gl.glRotated(deltaM, 0, 0, 1);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(-0.03f, 0.0f, -3.9f);
		gl.glVertex3f(0.03f, 0.0f, -3.9f);
		gl.glVertex3f(0.0f, 0.70f, -3.9f);
		gl.glEnd();

		gl.glLoadIdentity();
		gl.glRotated(deltaS, 0, 0, 1);
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glVertex3f(0.0f, 0.0f, -3.90f);
		gl.glVertex3f(0.0f, 0.80f, -3.90f);
		gl.glEnd();

		gl.glLoadIdentity();
		gl.glRotated(deltaBuzeni, 0, 0, 1);
		texture1.bind(gl);
		gl.glBegin(GL2.GL_QUAD_STRIP);
		gl.glVertex3f(-0.01f, 0.0f, -3.90f);
		gl.glVertex3f(0.01f, 0.0f, -3.90f);
		gl.glVertex3f(-0.01f, 0.8f, -3.90f);
		gl.glVertex3f(0.01f, 0.8f, -3.90f);
		gl.glEnd();
		texture0.bind(gl);

		// palicka
		if ((((Math.abs(buzeniPozice)) > Math.abs(deltaH) - 0.6) && ((Math.abs(buzeniPozice)) < Math.abs(deltaH) + 0.4))
				&& ((LocalDateTime.now().getSecond() >= 0) && (LocalDateTime.now().getSecond() <= 10))) {
			long mili = System.currentTimeMillis();
			zvoneni(gl, quadObj, xc, yc, zc, mili);
			if (LocalDateTime.now().getSecond() == 0) {
				Zvuk zvuk = new Zvuk("zvonime");
				zvuk.run();
			}
		} else {
			gl.glLoadIdentity();
			gl.glTranslatef(0f, 1.5f, -4.5f);
			gl.glRotated(90, 0.0f, 1.0f, 0.0f);
			gl.glRotated(90, 1.0f, 0.0f, 0.0f);
			glu.gluCylinder(quadObj, 0.02f, 0.02f, 0.70f, 32, 32);

			gl.glLoadIdentity();
			gl.glTranslatef(0.15f, 1.5f, -4.5f);
			gl.glRotated(90, 0.0f, 1.0f, 0.0f);
			gl.glRotated(180, 1.0f, 0.0f, 0.0f);
			glu.gluCylinder(quadObj, 0.05f, 0.05f, 0.3f, 32, 32);
		}
	}

	private void zvoneni(GL2 gl, GLUquadric quadObj, double xc, double yc, double zc, long mili) {

		if ((mili) % 2 != 0) {
			gl.glLoadIdentity();
			gl.glTranslatef(0f, 0.9f, -4.5f);
			gl.glRotated(90, 0.0f, 1.0f, 0.0f);
			gl.glRotated(250, 1.0f, 0.0f, 0.0f);
			glu.gluCylinder(quadObj, 0.02f, 0.02f, 0.70f, 32, 32);
			gl.glLoadIdentity();
			gl.glTranslatef(-0.1f, 1.6f, -4.5f);
			gl.glRotated(90, 0.0f, 1.0f, 0.0f);
			gl.glRotated(160, 1.0f, 0.0f, 0.0f);
			glu.gluCylinder(quadObj, 0.05f, 0.05f, 0.3f, 32, 32);
		} else if ((mili) % 2 == 0) {
			gl.glLoadIdentity();
			gl.glTranslatef(0f, 0.9f, -4.5f);
			gl.glRotated(90, 0.0f, 1.0f, 0.0f);
			gl.glRotated(295, 1.0f, 0.0f, 0.0f);
			glu.gluCylinder(quadObj, 0.02f, 0.02f, 0.70f, 32, 32);
			gl.glLoadIdentity();
			gl.glTranslatef(0.45f, 1.5f, -4.5f);
			gl.glRotated(90, 0.0f, 1.0f, 0.0f);
			gl.glRotated(200, 1.0f, 0.0f, 0.0f);
			glu.gluCylinder(quadObj, 0.05f, 0.05f, 0.3f, 32, 32);
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glEnable(GL2GL3.GL_DEPTH_TEST);
		gl.glFrustum(-1.0, 1.0, -1.0, 1.0, 3.0, 500.0);
		try {

			texture = TextureIO.newTexture(new File("textures/ciselnik.png"), true);
			texture0 = TextureIO.newTexture(new File("textures/mosaz1.jpg"), true);
			texture1 = TextureIO.newTexture(new File("textures/textureRed.jpg"), true);
		} catch (IOException e) {
			System.out.println(e);
		}
		gl.glTexParameteri(GL2GL3.GL_TEXTURE_2D, GL2GL3.GL_TEXTURE_MAG_FILTER, GL2GL3.GL_LINEAR);
		gl.glTexParameteri(GL2GL3.GL_TEXTURE_2D, GL2GL3.GL_TEXTURE_MIN_FILTER, GL2GL3.GL_NEAREST);
		gl.glEnable(GL2GL3.GL_TEXTURE_2D);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glViewport(0, 0, width, height);
		ratio = width / (double) height;
		this.width = width;
		this.height = height;
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
		/*
		 * case KeyEvent.VK_SHIFT: cam = cam.up(1); break;
		 */
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

			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		buzeniX = e.getX();
		buzeniY = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mode = e.getButton() == MouseEvent.BUTTON1;

		ox = e.getX();
		oy = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mode = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (mode) {
			cam = cam.addAzimuth(Math.PI * (ox - e.getX()) / width).addZenith(Math.PI * (e.getY() - oy) / height);
			ox = e.getX();
			oy = e.getY();
		}

		else {
			buzeniX = e.getX();
			buzeniY = e.getY();
			buzeniX = -(buzeniX / 1000 - 0.5);
			buzeniY = -(buzeniY / 1000 - 0.5);

			if (buzeniX <= 0) {
				deltaBuzeni = Math.toDegrees(Math.atan2(buzeniX, buzeniY));
				buzeniPozice = deltaBuzeni - 360;
			} else {
				deltaBuzeni = Math.toDegrees(Math.atan2(buzeniX, buzeniY));
				buzeniPozice = (180 - deltaBuzeni) + 180;
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}