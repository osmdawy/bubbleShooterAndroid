package net.game;
import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Bubble {
	private Bitmap bitmap;	// the actual bitmap
	 int x;			// the X coordinate
	 int y;			// the Y coordinat
	boolean destroy ;
	public Bubble(Bitmap bitmap, int x, int y) {
		super();
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		destroy = false;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
}
