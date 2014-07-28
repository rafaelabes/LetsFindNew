package br.edu.uneb.letsfind;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SplashFragment extends Fragment {
	
    HideBars hidebars;
    
    public SplashFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        
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

    
}