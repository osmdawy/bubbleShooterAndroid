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
	private static final String TAG = BubbleShooterActivity.class.getSimpleName();
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        Map.initMaps();
//        screen = new GameScreen(this);
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
