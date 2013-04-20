package net.game;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;

public class BubbleShooterActivity extends Activity {
	Button selectLevel;
	Button newGame;
	GameScreen screen;
	Button level1;
	Button level2;
	Button level3;
	
	private static final String TAG = BubbleShooterActivity.class.getSimpleName();
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        Map.initMaps();
        screen = new GameScreen(this);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
    	setContentView(R.layout.main);
    	newGame = (Button)findViewById(R.id.newGame);
    	newGame.setOnClickListener(new  View.OnClickListener() {
    		   @Override
    		   public void onClick(View v) {
    			   setContentView(screen);
    		   }
    		   });
    	selectLevel = (Button)findViewById(R.id.selectLevel);
    	selectLevel.setOnClickListener(new  View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.levelselector);
		    	level1 = (Button)findViewById(R.id.level1);
		    	level1.setOnClickListener(new  View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GameScreen.levelN=1;
						GameScreen.reInit();
						 setContentView(screen);
					}
		    		
		    	});
		    	level2 = (Button)findViewById(R.id.level2);
		    	level2.setOnClickListener(new  View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GameScreen.levelN=2;
						GameScreen.reInit();
						 setContentView(screen);
					}
		    		
		    	});
		    	level3 = (Button)findViewById(R.id.level3);
		    	level3.setOnClickListener(new  View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GameScreen.levelN=3;
						GameScreen.reInit();
						 setContentView(screen);
					}
		    		
		    	});
			}
			
    		
    	});

//        setContentView(new GameScreen(this));
        Log.d(TAG, "View added");
    }

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
	
}
