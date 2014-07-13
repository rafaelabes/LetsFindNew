package br.edu.uneb.letsfind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
		
    public MenuFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        
        return rootView;
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
				Intent it = new Intent(getActivity(), MapaActivity.class);
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
				// TODO Auto-generated method stub
				Log.v("MenuFragment","buttonMercado clicked");
				Intent it = new Intent(getActivity(), MercadoActivity.class);
				startActivity(it);
			}
		});
			
		
	}

    
}