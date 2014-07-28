package br.edu.uneb.letsfind;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import br.edu.uneb.webclient.ResponseHandler;
import br.edu.uneb.webclient.WebClient;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CadastroFragment extends Fragment {
	
	TextView editTextName;
	TextView editTextPassword;
	Button buttonCadastro;
	
    public CadastroFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return rootView;
    }
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		editTextName = (TextView) getActivity().findViewById(R.id.editTextName);
		editTextPassword = (TextView) getActivity().findViewById(R.id.editTextPassword);
		buttonCadastro = (Button) getActivity().findViewById(R.id.buttonCadastro);
		
		
		buttonCadastro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
		 		WebClient login = new WebClient(getActivity(), 
		 			"naiara.tk", "/", "users/add/json", 
					new ResponseHandler() {
					
					@Override
					public void execute(Context context, String content) {
						
						Log.wtf("Cadastro", content);
						
						try {
							JSONObject jsonObject = new JSONObject(content);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
						
					}
				});
				
				List<NameValuePair> parametros = new ArrayList<NameValuePair>(2);
		        parametros.add(new BasicNameValuePair("name", editTextName.getText().toString()));
		        parametros.add(new BasicNameValuePair("password", editTextPassword.getText().toString()));
		        login.setParametros(parametros);
		        login.setMethod(WebClient.GET);
		        
		        login.start();
			}
		});
		
	}

}