package br.edu.uneb.letsfind;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

public class HideBars {

	View decorView;
	Handler mHideHandler;
    Runnable mHideRunnable;
    Activity activity;
    
    //FragmentActivity
    
    public HideBars(Activity activity){
    	
    	this.activity = activity;
    	
        mHideHandler = new Handler();
        mHideRunnable = new Runnable() {
	        @Override
	        public void run() {
	        	Hide();
	        }
        };
    	
        decorView = activity.getWindow().getDecorView();
    	
        decorView.setOnSystemUiVisibilityChangeListener(
		new View.OnSystemUiVisibilityChangeListener() {
		    @Override
		    public void onSystemUiVisibilityChange(int visibility) {
		        // Note that system bars will only be "visible" if none of the
		        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
		        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
		            // TODO: The system bars are visible. Make any desired
		            // adjustments to your UI, such as showing the action bar or
		            // other navigational controls.
		        	mHideHandler.postDelayed(mHideRunnable, 2000);
		        	
		        } else {
		            // TODO: The system bars are NOT visible. Make any desired
		            // adjustments to your UI, such as hiding the action bar or
		            // other navigational controls.
		        }
		    }
		});
        
    }
    
    public HideBars Hide(){
    	
    	decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar 
                );
    
    	return this;
    }
    
    
	
}
