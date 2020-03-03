package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A window for custom drawing.
 *
 * To use this class, create a subclass, say MyCanvasWindow, that overrides
 * methods {@link #paint(Graphics)}, {@link #handleMouseEvent(int,int,int,int)}, and {@link #handleKeyEvent(int,int,char)}, and then launch
 * it from your main method as follows:
 * 
 * <pre>
 * public static void main(String[] args) {
 *     java.awt.EventQueue.invokeLater(() -> {
 *         new MyCanvasWindow("My Canvas Window").show();
 *     });
 * }
 * </pre>
 */

abstract class RecordingItem {
	abstract void save(String path, int itemIndex, PrintWriter writer) throws IOException;
	abstract void replay(int itemIndex, CanvasWindow window);
}
class MouseEventItem extends RecordingItem {
	int id;
	int x;
	int y;
	int clickCount;
	
	MouseEventItem(int id, int x, int y, int clickCount) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.clickCount = clickCount;
	}

	@Override
	void save(String path, int itemIndex, PrintWriter writer) throws IOException {
		String id;
		switch (this.id) {
		case MouseEvent.MOUSE_CLICKED: id = "MOUSE_CLICKED"; break;
		case MouseEvent.MOUSE_PRESSED: id = "MOUSE_PRESSED"; break;
		case MouseEvent.MOUSE_RELEASED: id = "MOUSE_RELEASED"; break;
		case MouseEvent.MOUSE_DRAGGED: id = "MOUSE_DRAGGED"; break;
		default: id = "unknown"; break;
		}
		writer.println("MouseEvent " + id + " " + x + " " + y + " " + clickCount);
	}

	@Override
	void replay(int itemIndex, CanvasWindow window) {
		window.handleMouseEvent(id, x, y, clickCount);
	}
}
class KeyEventItem extends RecordingItem {
	int id;
	int keyCode;
	char keyChar;
	
	KeyEventItem(int id, int keyCode, char keyChar) {
		this.id = id;
		this.keyCode = keyCode;
		this.keyChar = keyChar;
	}

	@Override
	void save(String path, int itemIndex, PrintWriter writer) throws IOException {
		String id;
		switch (this.id) {
		case KeyEvent.KEY_PRESSED: id = "KEY_PRESSED"; break;
		case KeyEvent.KEY_TYPED: id = "KEY_TYPED"; break;
		default: id = "unknown"; break;
		}
		writer.println("KeyEvent " + id + " " + keyCode + " " + (int)keyChar);
	}

	@Override
	void replay(int itemIndex, CanvasWindow window) {
		window.handleKeyEvent(id, keyCode, keyChar);
	}
}
class PaintItem extends RecordingItem {
	BufferedImage image;
	
	PaintItem(BufferedImage image) {
		this.image = image;
	}
	
	static String imagePathOf(String basePath, int itemIndex) {
		return basePath + ".image" + itemIndex + ".png";
	}
	
	void save(String path, int itemIndex, PrintWriter writer) throws IOException {
		String imagePath = imagePathOf(path, itemIndex);
		javax.imageio.ImageIO.write(image, "PNG", new File(imagePath));
		writer.println("Paint");
	}
	
	void replay(int itemIndex, CanvasWindow window) {
		BufferedImage observedImage = window.captureImage();
		for (int y = 0; y < observedImage.getHeight(); y++) {
			for (int x = 0; x < observedImage.getWidth(); x++) {
				if (observedImage.getRGB(x, y) != image.getRGB(x, y)) {
					try {
						ImageIO.write(observedImage, "PNG", new File("observedImage"+itemIndex+".png"));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					throw new RuntimeException("Replay: Paint item " + itemIndex + " does not match at x=" + x + " and y=" + y + ".");
				}
			}
		}
	}
}

class CanvasWindowRecording {
	
	ArrayList<RecordingItem> items = new ArrayList<>();
	
	CanvasWindowRecording() {}
	
	CanvasWindowRecording(String path) throws IOException {
		load(path);
	}
	
	void save(String path) throws IOException {
		try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(path)))) {
			save(path, writer);
		}
	}
	
	void save(String basePath, PrintWriter writer) throws IOException {
		int itemIndex = 0;
		for (RecordingItem item : items)
			item.save(basePath, itemIndex++, writer);
	}
	
	void load(String path) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
			load(path, reader);
		}
	}
	
	void load(String basePath, BufferedReader reader) throws IOException {
		Component dummyComponent = new JPanel();
		for (int itemIndex = 0;; itemIndex++) {
			String line = reader.readLine();
			if (line == null) break;
			String[] words = line.split(" ");
			switch (words[0]) {
			case "MouseEvent": {
				int id;
				switch (words[1]) {
				case "MOUSE_PRESSED": id = MouseEvent.MOUSE_PRESSED; break;
				case "MOUSE_CLICKED": id = MouseEvent.MOUSE_CLICKED; break;
				case "MOUSE_RELEASED": id = MouseEvent.MOUSE_RELEASED; break;
				case "MOUSE_DRAGGED": id = MouseEvent.MOUSE_DRAGGED; break;
				default: throw new AssertionError();
				}
				int x = Integer.parseInt(words[2]);
				int y = Integer.parseInt(words[3]);
				int clickCount = Integer.parseInt(words[4]);
				items.add(new MouseEventItem(id, x, y, clickCount));
				break;
			}
			case "KeyEvent": {
				int id;
				switch (words[1]) {
				case "KEY_PRESSED": id = KeyEvent.KEY_PRESSED; break;
				case "KEY_TYPED": id = KeyEvent.KEY_TYPED; break;
				default: throw new AssertionError();
				}
				int keyCode = Integer.parseInt(words[2]);
				char keyChar = (char)Integer.parseInt(words[3]);
				items.add(new KeyEventItem(id, keyCode, keyChar));
				break;
			}
			case "Paint": {
				String imagePath = PaintItem.imagePathOf(basePath, itemIndex);
				items.add(new PaintItem(ImageIO.read(new File(imagePath))));
				break;
			}
			default: throw new AssertionError();
			}
		}
	}
	
	void replay(CanvasWindow window) {
		int itemIndex = 0;
		for (RecordingItem item : items) {
			item.replay(itemIndex++, window);
		}
	}
	
}

public class CanvasWindow {
	
	int width = 600;
	int height = 600;
	String title;
	Panel panel;
	private Frame frame;
	
	private String recordingPath;
	private CanvasWindowRecording recording;
	
	void updateFrameTitle() {
		frame.setTitle(recording == null ? title : title + " - Recording: " + recording.items.size() + " items recorded");
	}

        public void setTitle(String title) {
            this.title = title;
            updateFrameTitle();
        }
	
	/**
	 * Initializes a CanvasWindow object.
	 * 
	 * @param title Window title
	 */
	protected CanvasWindow(String title) {
		this.title = title;
	}
	
	public final void recordSession(String path) {
		recordingPath = path;
		recording = new CanvasWindowRecording();
	}
	
	/**
	 * Call this method if the canvas is out of date and needs to be repainted.
	 * This will cause method {@link #paint(Graphics)} to be called after the current call of method handleMouseEvent or handleKeyEvent finishes.
	 */
	protected final void repaint() {
		if (panel != null)
			panel.repaint();
	}
	
	/**
	 * Called to allow you to paint on the canvas.
	 * 
	 * You should not use the Graphics object after you return from this method.
	 * 
	 * @param g This object offers the methods that allow you to paint on the canvas.
	 */
	protected void paint(Graphics g) {
	}
	
	private void handleMouseEvent_(MouseEvent e) {
		System.out.println(e);
		if (recording != null)
			recording.items.add(new MouseEventItem(e.getID(), e.getX(), e.getY(), e.getClickCount()));
		handleMouseEvent(e.getID(), e.getX(), e.getY(), e.getClickCount());
	}
	
	/**
	 * Called when the user presses (id == MouseEvent.MOUSE_PRESSED), releases (id == MouseEvent.MOUSE_RELEASED), or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
	 * 
	 * @param e Details about the event
	 */
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
	}
	
	private void handleKeyEvent_(KeyEvent e) {
		System.out.println(e);
		if (recording != null)
			recording.items.add(new KeyEventItem(e.getID(), e.getKeyCode(), e.getKeyChar()));
		handleKeyEvent(e.getID(), e.getKeyCode(), e.getKeyChar());
	}
	
	/**
	 * Called when the user presses a key (id == KeyEvent.KEY_PRESSED) or enters a character (id == KeyEvent.KEY_TYPED).
	 * 
	 * @param e
	 */
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
	}

	BufferedImage captureImage() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics imageGraphics = image.getGraphics();
		imageGraphics.setColor(Color.WHITE);
		imageGraphics.fillRect(0, 0, width, height);
		imageGraphics.setColor(Color.BLACK);
		CanvasWindow.this.paint(imageGraphics);
		return image;
	}
	
	class Panel extends JPanel {
		
		{
			setPreferredSize(new Dimension(width, height));
			setBackground(Color.WHITE);
			setFocusable(true);
			
			addMouseListener(new MouseAdapter() {
	
				@Override
				public void mouseClicked(MouseEvent e) {
					handleMouseEvent_(e);
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					handleMouseEvent_(e);
				}
	
				@Override
				public void mouseReleased(MouseEvent e) {
					handleMouseEvent_(e);
				}
				
			});
			
			addMouseMotionListener(new MouseAdapter() {
	
				@Override
				public void mouseDragged(MouseEvent e) {
					handleMouseEvent_(e);
				}
				
			});
			
			addKeyListener(new KeyAdapter() {
	
				@Override
				public void keyTyped(KeyEvent e) {
					handleKeyEvent_(e);
				}
	
				@Override
				public void keyPressed(KeyEvent e) {
					handleKeyEvent_(e);
				}
				
			});
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			System.out.println("Painting...");
			super.paintComponent(g);
			
			if (recording != null) {
				BufferedImage image = captureImage();
				g.drawImage(image, 0, 0, null);
				recording.items.add(new PaintItem(image));
				updateFrameTitle();
			} else {
				CanvasWindow.this.paint(g);
			}
		}
		
	}

	private class Frame extends JFrame {
		Frame(String title) {
			super(title);
			
			addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosed(WindowEvent e) {
					if (recording != null)
						try {
							System.out.println(new File(".").getCanonicalPath());
							recording.save(recordingPath);
						} catch (IOException ex) {
							throw new RuntimeException(ex);
						}
					System.exit(0);
				}
				
			});
			getContentPane().add(panel);
			pack();
			setLocationRelativeTo(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
	}

	public final void show() {
		if (!EventQueue.isDispatchThread())
			throw new RuntimeException("You must call this method from the AWT dipatch thread");
		panel = new Panel();
		frame = new Frame(title);
		frame.setVisible(true);
	}
	
	public static void replayRecording(String path, CanvasWindow window) {
		try {
			new CanvasWindowRecording(path).replay(window);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
