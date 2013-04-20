package net.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Bubble {
	  Bitmap bitmap;	// the actual bitmap
	 int x;			// the X coordinate
	 int y;			// the Y coordinat
	 int colorIndex;
	boolean destroy ;
	boolean markedCheck=false;
	int noOfShiftedRows=0;
	
//	Speed speed;

	public Bubble() {
		// TODO Auto-generated constructor stub
		x=0;
		y=0;
		destroy=true;
		boolean markedCheck=false;
		int noOfShiftedRows=0;
	}
	public Bubble(Bitmap bitmap, int x, int y) {
		super();
		
		this.bitmap = bitmap;
//		GameScreen.width = bitmap.getWidth();
//		GameScreen.height=bitmap.getHeight();
		this.x = x;
		this.y = y;
		colorIndex=0;
		destroy = false;
//		this.speed = new Speed();
	}
	public Bubble( int x, int y,int colInd,boolean check) {
		// TODO Auto-generated constructor stub
//		this.bitmap = bitmap;
//		GameScreen.width = bitmap.getWidth();
//		GameScreen.height=bitmap.getHeight();
		this.x = x;
		this.y = y;
		colorIndex=colInd;
		destroy = false;
		markedCheck=check;
		
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
}
