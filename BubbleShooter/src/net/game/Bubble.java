package net.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Bubble {
	 private Bitmap bitmap;	// the actual bitmap
	 int x;			// the X coordinate
	 int y;			// the Y coordinat
	 int colorIndex;
	boolean destroy ;
//	Speed speed;

	public Bubble(Bitmap bitmap, int x, int y) {
		super();
		
		this.bitmap = bitmap;
//		GameScreen.width = bitmap.getWidth();
//		GameScreen.height=bitmap.getHeight();
		this.x = x;
		this.y = y;
		destroy = false;
//		this.speed = new Speed();
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
}
