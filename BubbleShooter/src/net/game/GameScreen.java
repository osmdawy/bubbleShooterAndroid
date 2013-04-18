package net.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Delayed;

import net.game.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = GameScreen.class.getSimpleName();
	static Point originalPoint;
	private GameThread thread;
	private Bubble[][] bubbles;
	private Bitmap[] bubbles_normal = new Bitmap[8];
	Point[] positions;
	static int width;
	static int height;
	static int bubbleHight;
	static int bubblewidth;
	static int bubbleSize;
	static int numOfBubble = 0;
	Queue<Bubble> waitingBubbles = new LinkedList<Bubble>();
	int numOfWaitingBubbles = 4;
	Bubble movingBubble;
	float slope;
	int tryX = 5;
	float deltaX = 0;
	boolean moving = false;
	final int noOfSteps = 50;

	private void initialize() {
		// num of bubbles should be initialized according to level no
		numOfBubble = 8;

		originalPoint = new Point(width, height / 2);
		// generating pics of bubbles
		bubbles = new Bubble[10][8];
		bubbleHight = bubbles.length;
		bubblewidth = bubbles[0].length;
		// movingBubble = new Bubble();
		positions = new Point[100];
		BitmapFactory.Options options = new BitmapFactory.Options();
		BitmapFactory.decodeResource(getResources(), R.drawable.bubble_1,
				options);
		bubbles_normal[0] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_2, options);
		bubbles_normal[1] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_3, options);
		bubbles_normal[2] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_4, options);
		bubbles_normal[3] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_5, options);
		bubbles_normal[4] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_6, options);
		bubbles_normal[5] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_6, options);
		bubbles_normal[6] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_7, options);
		bubbles_normal[7] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bubble_8, options);
		bubbleSize = bubbles_normal[0].getWidth();

	}

	public GameScreen(Context context) {

		super(context);
		initialize();
		width = 300;
		height = 500;

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		Random random = new Random();
		// create droid and load bitmap
		// boolean odd = false;
		// float x = 1;
		// int y = 1;
		// for (int i = 0; i < bubbles.length; i++) {
		// int index = random.nextInt(8);
		// if (i % 8 == 0) {
		// x = 0;
		// y++;
		// odd = !odd;
		//
		// }
		// if (!odd) {
		// bubbles[i] = new Bubble(BitmapFactory.decodeResource(
		// getResources(), bubbles_normal[index]),
		// 30 + (int) x * 30, y * 30);
		// positions[i] = new Point(30 + (int) (x * 30), y * 30);
		//
		// } else {
		// bubbles[i] = new Bubble(BitmapFactory.decodeResource(
		// getResources(), bubbles_normal[index]),
		// 45 + (int) x * 30, y * 30);
		// positions[i] = new Point(45 + (int) (x * 30), y * 30);
		// }
		// x++;
		// }
		int numOfBubblesGen = 0;

		int index = 0;
		for (int i = 0; i < bubbleHight; i++) {

			for (int j = 0; j < bubblewidth; j++) {
				index = random.nextInt(8);
				if (numOfBubblesGen < numOfBubble) {
					if (i % 2 == 0) {

						bubbles[i][j] = new Bubble(bubbles_normal[index],
								30 + (int) j * 30, 30 + i * 30);

						// positions[i] = new Point(30 + (int) j * 30, i* 30);

					} else {
						bubbles[i][j] = new Bubble(bubbles_normal[index],
								45 + (int) j * 30, 30 + i * 30);
						// positions[i] = new Point(45 + (int) j * 30, i * 30);
					}
				} else {

					bubbles[i][j] = null;

				}
				numOfBubblesGen++;
			}

		}
		index = random.nextInt(8);
		Bubble First = new Bubble(bubbles_normal[index], width / 2, height);
		First.colorIndex = index;
		waitingBubbles.add(First);

		for (int i = 1; i < numOfWaitingBubbles; i++) {
			index = random.nextInt(8);
			Bubble curr = new Bubble(bubbles_normal[index], 30 + i * 30, height);
			curr.colorIndex = index;
			waitingBubbles.add(curr);
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
		if (!moving) {
			Bubble removed = waitingBubbles.poll();
			// changing bubble position

			// int tempY = removed.y;
			Random random = new Random();
			int index = random.nextInt(8);
			Bubble curr = new Bubble(bubbles_normal[index], 30, height);
			curr.colorIndex = index;
			waitingBubbles.add(curr);
			Iterator<Bubble> it = waitingBubbles.iterator();

			while (it.hasNext()) {
				it.next().x += 30;
			}
			// add new Bubble

			movingBubble = new Bubble(bubbles_normal[removed.colorIndex],
					width / 2, height);
			movingBubble.colorIndex = removed.colorIndex;
			float desiredX = event.getX();
			deltaX = ((desiredX - movingBubble.x) / noOfSteps);
			// if (desiredX > width / 2) {
			// deltaX = 1;
			// } else {
			// deltaX = -1;
			// }
			float desiredY = event.getY();
			slope = (desiredY - movingBubble.y) / (desiredX - movingBubble.x);
			moving = true;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(Color.BLACK);

		// fills the canvas with black
		canvas.drawColor(Color.WHITE);

		drawBubbles(canvas);
		if (movingBubble != null)
			movingBubble.draw(canvas);

	}

	public void update() {
		if (movingBubble != null) {
			if (moving) {
				if (movingBubble.x >= width) {
					deltaX = -deltaX;
				}
				if (movingBubble.x <= 0) {
					deltaX = -deltaX;
				}

				movingBubble.x = (int) (movingBubble.x + deltaX);
				int deltaY = Math.abs((int) (slope * deltaX));

				movingBubble.y = movingBubble.y - deltaY;

				checkCollision();
			}
		}
	}

	private void drawBubbles(Canvas canvas) {

		for (int i = 0; i < bubbleHight; i++) {
			for (int j = 0; j < bubblewidth; j++) {

				if (bubbles[i][j] != null && !bubbles[i][j].destroy) {

					bubbles[i][j].draw(canvas);
				}
			}
		}

		Iterator<Bubble> it = waitingBubbles.iterator();
		while (it.hasNext()) {
			Bubble curr = it.next();
			curr.draw(canvas);
		}

	}

	private ArrayList<Bubble> getBubblesFrame() {
		ArrayList<Bubble> myBubbles = new ArrayList<Bubble>();
		// for (int i = 0; i < bubbleHight; i++) {
		// for (int j = 0; j < bubbleHight; j++) {
		// Bubble curr = bubbles[i][j];
		// if(curr==null){
		// //check Right
		// if(j<bubbleHight-1){
		// Bubble bubbleFrame = bubbles[i][j+1];
		// if(bubbleFrame!=null){
		//
		// }
		// }
		//
		// //check left
		//
		//
		// //check up
		// }
		// }
		// }
		return myBubbles;
	}

	private void checkCollision() {
		if (moving) {

			for (int i = bubbleHight - 1; i >= 0 && moving; i--) {
				for (int j = bubblewidth - 1; j >= 0 && moving; j--) {
					Bubble curr = bubbles[i][j];
					if (curr != null) {
						int diffY = Math.abs(movingBubble.y - curr.y);
						int diffX = Math.abs(movingBubble.x - curr.x);
						// check if hit from right
						if ((curr.x + 30 - movingBubble.x)<5&&(curr.x + 30 - movingBubble.x)>-5&& (diffY <5)) {
							if (j < bubblewidth - 1) {
								bubbles[i][j + 1] = new

								Bubble(

								bubbles_normal[movingBubble.colorIndex],

								curr.x + 30, curr.y);
								moving = false;
								Log.d("Hi", "rightttt  " );
							}
							// check if hit from left
						} else if (curr.x == movingBubble.x + 30 &&

						(diffY < 5)) {
							if (j > 0) {
								bubbles[i][j - 1] = new

								Bubble(

								bubbles_normal[movingBubble.colorIndex],

								curr.x - 30, curr.y);
								Log.d("Hi", "lefttt  " );
								moving = false;
							}
							// check if hit from down
						} else if ((curr.y + 30) >= movingBubble.y && diffX	< 5) {
							if (i < bubbleHight - 1) {
								if ((movingBubble.x -

								curr.x) > -15 &&

								(movingBubble.x - curr.x) < 15) {// put

									// in

									// the

									// left

									// bottom
									Log.d("Hi", "bottom left  " );
									if (i % 2 == 0)

									{// for even rows
										if (j

										> 0) {

											bubbles[i + 1][j - 1] = new Bubble(

													bubbles_normal[movingBubble.colorIndex],

													curr.x - 15, curr.y + 30);

											moving = false;
										}
									} else {// for odd rows

										bubbles[i + 1][j] = new Bubble(

												bubbles_normal[movingBubble.colorIndex],

												curr.x - 15, curr.y + 30);

										moving = false;
									}

								} else if (movingBubble.x -

								curr.x > 15 &&

								movingBubble.x - curr.x < 30) {// put

									// in

									// the

									// right

									// bottom
									Log.d("Hi", "bottom right  " );
									if (i % 2 == 0)

									{

										bubbles[i + 1][j] = new Bubble(

												bubbles_normal[movingBubble.colorIndex],

												curr.x + 15, curr.y + 30);
										
										moving = false;
									} else {

										if (j

										< bubblewidth - 1) {

											bubbles[i + 1][j + 1] = new Bubble(

													bubbles_normal[movingBubble.colorIndex],

													curr.x + 15, curr.y + 30);

											moving = false;
										}
									}
								}
							}
						}
						// if (curr.x + 30 == movingBubble.x &&
						// (movingBubble.y-curr.y)<10) {
						// if (j < bubblewidth - 1) {
						// bubbles[i][j + 1] = new Bubble(
						// bubbles_normal[movingBubble.colorIndex],
						// curr.x +30 , curr.y);
						// moving = false;
						// Log.d("Hi", "henaaaa   "+movingBubble.colorIndex);
						// }
						// } else if (curr.x - 30 ==
						// movingBubble.x&&((movingBubble.y-curr.y)<10)) {
						// if (j > 0) {
						// bubbles[i][j - 1] = new Bubble(
						// bubbles_normal[movingBubble.colorIndex],
						// curr.x - 30, curr.y);
						// Log.d("Hi", "henaaaa   "+movingBubble.colorIndex);
						// moving = false;
						// }
						// }else if(curr.y+30 == movingBubble.y &&
						// (movingBubble.x-curr.x )<5 && (movingBubble.x-curr.x
						// )>-5){
						// if(i<bubbleHight){
						// bubbles[i+1][j]= new Bubble(
						// bubbles_normal[movingBubble.colorIndex],
						// curr.x+15 , curr.y+30);
						// Log.d("Hi", "henaaaa   "+movingBubble.colorIndex);
						// moving = false;
						// }
						// }else if((movingBubble.y-(curr.y+30))<5 &&
						// (curr.x-movingBubble.x )<5 &&
						// (curr.x-movingBubble.x)>-5){
						// if(i<bubbleHight && j>0){
						// bubbles[i+1][j-1]= new Bubble(
						// bubbles_normal[movingBubble.colorIndex],
						// curr.x-15 , curr.y+30);
						// Log.d("Hi", "henaaaa   "+movingBubble.colorIndex);
						// moving = false;
						// }
						// }

					}
				}
			}
		}
	}

}
