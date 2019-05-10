

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;

public class Sandworm extends GameFrame {

	private static final long serialVersionUID = -2450477630768116721L;

	private static int DEFAULT_FPS = 100;

	private Worm worm; // the worm

	private int score = 0;
	private Font font;
	private FontMetrics metrics;

	// used by quit 'button'
	private volatile boolean isOverQuitButton = false;
	private Rectangle quitArea;

	// used by the pause 'button'
	private volatile boolean isOverPauseButton = false;
	private Rectangle pauseArea;
	
    private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp

	private double speed;


	public Sandworm(long period) {
		super(period);
	}

	@Override
	protected void simpleInitialize() {
		// create game components
		speed = 3;
		worm = new Worm(pWidth/2, pHeight/2, 50, 10, speed);

		// set up message font
		font = new Font("SansSerif", Font.BOLD, 24);
		metrics = this.getFontMetrics(font);

		// specify screen areas for the buttons
		pauseArea = new Rectangle(pWidth - 100, pHeight - 45, 70, 15);
		quitArea = new Rectangle(pWidth - 100, pHeight - 20, 70, 15);
	}

	@Override
	protected void mouseLeftPress(int x, int y) {
		if (isOverPauseButton)
			isPaused = !isPaused; // toggle pausing
		else if (isOverQuitButton)
			running = false;
		else {
			/*if (!isPaused && !gameOver) {
				worm.setDest(x,y);
			}*/
		}
	} // end of testPress()

	@Override
	protected void mouseMiddlePress(int x, int y) {

	}

	@Override
	protected void mouseRightPress(int x, int y) {

	}

	@Override
	protected void mouseMove(int x, int y) {
		if (running) { // stops problems with a rapid move after pressing 'quit'
			isOverPauseButton = pauseArea.contains(x, y) ? true : false;
			isOverQuitButton = quitArea.contains(x, y) ? true : false;
			if (!isPaused && !gameOver) {
				worm.setDest(x,y);
			}
		}
	}


	@Override
	protected void simpleRender(Graphics gScr) {

		gScr.setColor(Color.blue);
		gScr.setFont(font);
		gScr.setColor(Color.blue);
	    gScr.setFont(font);

	    // report frame count & average FPS and UPS at top left
		gScr.drawString("Average FPS/UPS: " + df.format(averageFPS) + ", " +
	                                df.format(averageUPS), 20, 25);  // was (10,55)
		
		// report time used and boxes used at bottom left
		gScr.drawString("Time Spent: " + timeSpentInGame + " secs", 10,
				pHeight - 15);

		// draw the pause and quit 'buttons'
		drawButtons(gScr);

		gScr.setColor(Color.black);

		// draw game elements: the obstacles and the worm
		worm.draw(gScr);
	} // end of simpleRender()

	private void drawButtons(Graphics g) {
		g.setColor(Color.black);

		// draw the pause 'button'
		if (isOverPauseButton)
			g.setColor(Color.green);

		g.drawOval(pauseArea.x, pauseArea.y, pauseArea.width, pauseArea.height);
		if (isPaused)
			g.drawString("Paused", pauseArea.x, pauseArea.y + 10);
		else
			g.drawString("Pause", pauseArea.x + 5, pauseArea.y + 10);

		if (isOverPauseButton)
			g.setColor(Color.black);

		// draw the quit 'button'
		if (isOverQuitButton)
			g.setColor(Color.green);

		g.drawOval(quitArea.x, quitArea.y, quitArea.width, quitArea.height);
		g.drawString("Quit", quitArea.x + 15, quitArea.y + 10);

		if (isOverQuitButton)
			g.setColor(Color.black);
	} // drawButtons()

	@Override
	protected void gameOverMessage(Graphics g)
	// center the game-over message in the panel
	{
		String msg = "Game Over. Your Score: " + score;
		int x = (pWidth - metrics.stringWidth(msg)) / 2;
		int y = (pHeight - metrics.getHeight()) / 2;
		g.setColor(Color.red);
		g.setFont(font);
		g.drawString(msg, x, y);
	} // end of gameOverMessage()

	@Override
	protected void simpleUpdate() {
		worm.move();
	}

	public static void main(String args[]) {
		int fps = DEFAULT_FPS;
		if (args.length != 0)
			fps = Integer.parseInt(args[0]);

		long period = (long) 1000.0 / fps;
		System.out.println("fps: " + fps + "; period: " + period + " ms");
		new Sandworm(period * 1000000L); // ms --> nanosecs
	} // end of main()

} // end of Sandworm class

