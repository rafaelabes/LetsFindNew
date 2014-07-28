package br.edu.uneb.letsfind;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient;

import android.app.ProgressDialog;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CopyOfLoginFragment extends Fragment implements OnClickListener,
PlusClient.ConnectionCallbacks, PlusClient.OnConnectionFailedListener,
PlusClient.OnAccessRevokedListener {
	
	
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private ProgressDialog mConnectionProgressDialog;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	
    public CopyOfLoginFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signin, container, false);
        return rootView;
    }
    
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mConnectionProgressDialog = new ProgressDialog(getActivity());
		
		mConnectionProgressDialog.setMessage("Signing in...");
		
        mPlusClient = new PlusClient.Builder(getActivity(), this, this)
        .setActions("http://schemas.google.com/AddActivity","http://schemas.google.com/BuyActivity")
        .setScopes("PLUS_LOGIN") // Space separated list of scopes
        .build();
		
		getActivity().findViewById(R.id.sign_in_button).setOnClickListener(this);
		
		
	}

	@Override
	public void onAccessRevoked(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		mConnectionProgressDialog.dismiss();
	    Toast.makeText(getActivity(), "User is connected!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
	        if (mConnectionResult == null) {
	            mConnectionProgressDialog.show();
	        } else {
	            try {
	                mConnectionResult.startResolutionForResult(getActivity(), REQUEST_CODE_RESOLVE_ERR);
	            } catch (SendIntentException e) {
	                // Tente se conectar novamente.
	                mConnectionResult = null;
	                mPlusClient.connect();
	            }
	        }
	    }
	}


    
}