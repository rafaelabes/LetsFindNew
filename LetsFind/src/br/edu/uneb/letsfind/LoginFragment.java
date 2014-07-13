package br.edu.uneb.letsfind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {
		
    public LoginFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        
        return rootView;
    }
    
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		/*
		Button buttonJogar = (Button)getView().findViewById(R.id.buttonJogar);
		Button buttonSobre = (Button)getView().findViewById(R.id.buttonSobre);
		Button buttonRanking = (Button)getView().findViewById(R.id.buttonRanking);
		Button buttonMercado = (Button)getView().findViewById(R.id.buttonMercado);
		*/
		
		
	}


    
}