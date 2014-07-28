package br.edu.uneb.letsfind;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

public class SplashFragment extends Fragment implements OnTouchListener {
	
    HideBars hidebars;
    
    double downXValue = 0d;
    
    public SplashFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        
        rootView.setOnTouchListener(this);
        
        hidebars = new HideBars(getActivity()).Hide();;
        
        //setDecorator();
        //hideBars();
        
        return rootView;
    }
    
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		
		Button buttonJogar = (Button)getView().findViewById(R.id.buttonJogar);
		Button buttonSobre = (Button)getView().findViewById(R.id.buttonSobre);
		Button buttonRanking = (Button)getView().findViewById(R.id.buttonRanking);
		Button buttonMercado = (Button)getView().findViewById(R.id.buttonMercado);
		
		
		buttonJogar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("MenuFragment","buttonJogar clicked");
				Intent it = new Intent(getActivity(), TemasActivity.class);
				startActivity(it);
				
			}
		});
		
		
		buttonSobre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("MenuFragment","buttonSobre clicked");
				Intent it = new Intent(getActivity(), AboutActivity.class);
				startActivity(it);
			}
		});


		buttonRanking.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("MenuFragment","buttonRanking clicked");
				Intent it = new Intent(getActivity(), LoginActivity.class);
				startActivity(it);
			}
		});


		buttonMercado.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				
				Log.v("MenuFragment","buttonMercado clicked");
				Intent it = new Intent(getActivity(), MercadoActivity.class);
				startActivity(it);
			}
		});
		
	}

	@Override
	public void onResume() {
		super.onResume();
		
		hidebars.Hide();
		
	}

	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		// Get the action that was done on this touch event
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                // store the X value when the user's finger was pressed down
                downXValue = event.getX();
                
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                // Get the X value when the user released his/her finger
                float currentX = event.getX();            

                // going backwards: pushing stuff to the right
                if (downXValue < currentX)
                {
                	
                    // Get a reference to the ViewFlipper
                     ViewFlipper vf = (ViewFlipper) getActivity().findViewById(R.id.panel);
                     // Set the animation
                      vf.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right));
                      // Flip!
                      vf.showPrevious();
                      
                      Log.wtf("wtfL","Right In");
                }

                // going forwards: pushing stuff to the left
                if (downXValue > currentX)
                {
                    // Get a reference to the ViewFlipper
                    ViewFlipper vf = (ViewFlipper) getActivity().findViewById(R.id.panel);
                     // Set the animation
                     vf.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left));
                      // Flip!
                     vf.showNext();
                     
                     
                     Log.wtf("wtfL","Left In");
                }
                break;
            }
        }

        // if you return false, these actions will not be recorded
        return true;

		
	}
    
}