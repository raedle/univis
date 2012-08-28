package unikn.dbis.univis.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * ZoomFrame.java
 *
 * Created on 14. April 2004, 18:43
 */

/**
 *
 * @author  Thomas Darimont
 */
public class ZoomFrame extends JFrame {

	//Unser "In-Memory Screen Buffer"
	private BufferedImage screen;
	//Der Ball ...
	private BufferedImage shape;
	//aktuelle X-Positionen der Maus im Panel ...
	private int xPos;
	private RenderingHints renderingHints;

	private JPanel gfxPanel;

	private final int MAX_IMAGE_SIZE_Y = 500;
	private final int MIN_IMAGE_SIZE_Y = 100;

	private final int MAX_IMAGE_SIZE_X = 500;
	private final int MIN_IMAGE_SIZE_X = 100;

	private final double SCALE_UP = 1.1d;
	private final double SCALE_DOWN = 0.9d;

	private int width = 100;
	private int height = 100;

	/** Creates new form ZoomFrame */
	public ZoomFrame() {
		initComponents();
		screen = new BufferedImage(512, 384, BufferedImage.TYPE_INT_RGB);
		loadImage();

		//Hier kann man mit den Verschiedenen RenderingOptionen der Klasser
		//RenderingHints herumspielen ...
		renderingHints =
			new RenderingHints(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

	}

	/**
	 * Hier wird das Bild von der Festpallte geladen.
	 * Durch den MediaTracker stellen wir sicher, dass das Bild
	 * nach verlassen dieser Methode vollständig geladen ist.
	 *
	 */
	private void loadImage() {

		MediaTracker mt = new MediaTracker(this);
		try {
			shape = ImageIO.read(new File("c:/ball.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mt.addImage(shape, 0);

		try {
			mt.waitForAll();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mt = null;

	}

	/**
	 * Ich überschriebe hier die paintComponent Methode von JPanel um das
	 * Bild anzeigen zu lassen ...
	 */
	private void initComponents() {
		setSize(512, 384);
		setResizable(false);
		gfxPanel = new javax.swing.JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (screen != null)
					g.drawImage(screen, 0, 0, this);
			}
		};

		getContentPane().setLayout(new FlowLayout());

		setTitle("ZoomFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gfxPanel.setBackground(new Color(102, 153, 255));
		gfxPanel.setMinimumSize(new Dimension(512, 384));
		gfxPanel.setPreferredSize(new Dimension(512, 384));
		gfxPanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent evt) {
				jPanel1MouseMoved(evt);
			}
		});

		getContentPane().add(gfxPanel);

		pack();
	}

	private void jPanel1MouseMoved(MouseEvent evt) {

		//Hat sich die Maus nach links oder rechts bewegt?
		int dx = xPos - evt.getX();

		//Nur wenn die Maus über 30 Pixel gewandert ist soll neu gezeichnet werden ...
		if (Math.abs(dx) > 30) {
			//System.out.println(width + " : " + dx);
			xPos = evt.getX();
			AffineTransform transform = null;

			//Bilden des Transform Objektes für das Skalieren ...

			if (dx > 0 && (width * SCALE_UP <= 500)) {
				transform =
					AffineTransform.getScaleInstance(SCALE_UP, SCALE_UP);
				width *= SCALE_UP;
			} else if (width * SCALE_DOWN >= 100) {
				transform =
					AffineTransform.getScaleInstance(SCALE_DOWN, SCALE_DOWN);
				width *= SCALE_DOWN;
			} else {
				return;
			}

			//Anwenden des Transform Objektes ....
			AffineTransformOp transformOp =
				new AffineTransformOp(transform, renderingHints);
			shape = transformOp.filter(shape, null);
			draw();

			//Um den Garbage Collector etwas zu unterstützen ;-)
			transform = null;
			transformOp = null;
		}
	}

	/**
	 * Zeichnet das Bild in den In-Memory-Buffer und lasst diesen durch den Aufruf
	 * von updateUI() am JPanel durch dessen überschriebene paintComponentMethode
	 * aufs Panel malen.
	 */
	private void draw() {

		if (screen != null) {
			Graphics2D g2d = (Graphics2D) screen.getGraphics();
			g2d.fillRect(0, 0, 512, 384);
			if (shape != null) {
				g2d.drawImage(shape, 50, 50, gfxPanel);
				gfxPanel.updateUI();
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		new ZoomFrame().setVisible(true);
	}
}