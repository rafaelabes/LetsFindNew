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

public class MapaFragment extends Fragment {

	private GoogleMap map = null;
	
	private final LatLng FAROL_DA_BARRA = new LatLng(-13.010315034340541,-38.53296708315611);
	
	private final LatLng BRASIL = new LatLng(-13.921117774841237, -54.58670552819967);
	
    public MapaFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);
        
        return rootView;
        
    }
    
    
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		Log.e("MapaFragment","onViewCreated");
		
		 map = ((SupportMapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
	        
	        if(map != null){
	        map.addMarker(new MarkerOptions().position(BRASIL));
	        
	        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(BRASIL, 4);
			map.animateCamera(update);
	        
	        }
	        
	        // TODO: saber se a pessoa clicou no ponto desejado
	        if(map != null)
	    		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

	    	        @Override
	    	        public void onMapClick(LatLng point) {
	    	        	
	    	            map.addMarker(new MarkerOptions().position(point));

	    	            Log.d("MapaFragment", "LatLng("+point.latitude+","+ point.longitude+")");
	    	        }
	    		});
	        
	        
	        CircleOptions opt = new CircleOptions();
			opt.center(FAROL_DA_BARRA);
			opt.radius(100);
			map.addCircle(opt);
			
			//TODO: não encontrei uma função para mudar o raio de acordo com o zoom
			/*
			map.setOnCameraChangeListener(new OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition position) {
					float zoom = position.zoom;
					
					CircleOptions opt2 = new CircleOptions();
					opt2.center(FAROL_DA_BARRA);
					map.clear();
					opt2.radius(100 * zoom);
					map.addCircle(opt2);
					
				}
			});
			//*/

		
		
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