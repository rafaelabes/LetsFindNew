package br.edu.uneb.letsfind;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MercadoFragment extends Fragment {
	static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
		"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
		"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };
		
    public MercadoFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_mercado, container, false);
        
              
        return rootView;
        
    }
    
    
    
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		ListView lista = (ListView) this.getView().findViewById(R.id.listView1);
        MyArrayAdapter adapter = new MyArrayAdapter(getActivity().getApplicationContext(), FRUITS);
        lista.setAdapter(adapter);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	
	@Override
    public void onStart() {
        super.onStart();        
    }
	
	
	@Override
	public void onResume() {
		super.onResume();
	}
    
}