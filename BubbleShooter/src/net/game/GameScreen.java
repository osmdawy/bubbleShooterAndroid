package net.game;

import java.util.Random;

import net.game.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = GameScreen.class.getSimpleName();

	private GameThread thread;
	private Bubble[] bubbles = new Bubble[30];
	private int[] bubbles_normal = new int[8];
	Point[] positions = new Point[bubbles.length];

	private void initialize() {
		bubbles_normal[0] = R.drawable.bubble_1;
		bubbles_normal[1] = R.drawable.bubble_2;
		bubbles_normal[2] = R.drawable.bubble_3;
		bubbles_normal[3] = R.drawable.bubble_4;
		bubbles_normal[4] = R.drawable.bubble_5;
		bubbles_normal[5] = R.drawable.bubble_6;
		bubbles_normal[6] = R.drawable.bubble_7;
		bubbles_normal[7] = R.drawable.bubble_8;
	}

	public GameScreen(Context context) {

		super(context);
		initialize();
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		Random random = new Random();
		// create droid and load bitmap
		boolean odd = false;
		float x = 1;
		int y = 1;
		for (int i = 0; i < bubbles.length; i++) {
			int index = random.nextInt(8);
			if (i % 8 == 0) {
				x = 0;
				y++;
				odd = !odd;

			}
			if (!odd) {
				bubbles[i] = new Bubble(BitmapFactory.decodeResource(
						getResources(), bubbles_normal[index]),
						30 + (int) x * 30, y * 30);
				positions[i] = new Point(30 + (int) (x * 30), y * 30);

			} else {
				bubbles[i] = new Bubble(BitmapFactory.decodeResource(
						getResources(), bubbles_normal[index]),
						45 + (int) x * 30, y * 30);
				positions[i] = new Point(45 + (int) (x * 30), y * 30);
			}
			x++;
		}

		// create the game loop thread
		thread = new GameThread(getHolder(), this);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		// thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// fills the canvas with black
		canvas.drawColor(Color.WHITE);
		for (int i = 0; i < bubbles.length; i++) {
			if (!bubbles[i].destroy)
				bubbles[i].x = positions[i].x;
			bubbles[i].y = positions[i].y;
			bubbles[i].draw(canvas);
		}
	}

}
