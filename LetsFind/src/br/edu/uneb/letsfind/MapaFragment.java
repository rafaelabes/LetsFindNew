package br.edu.uneb.letsfind;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MapaFragment extends Fragment {

	private GoogleMap map = null;
	
	private Float zoom = null;
	
	private final Double raio = 3.0694775721281596E-4;
	
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
		
		 map = ((SupportMapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
	        
	        if(map != null){
	        	
	        //map.addMarker(new MarkerOptions().position(FAROL_DA_BARRA));
	        
	        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(BRASIL, 4);
			map.animateCamera(update);
			
			
			
			
	        
	        }
	        
	        // TODO: saber se a pessoa clicou no ponto desejado
	        if(map != null)
	    		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

	    	        @Override
	    	        public void onMapClick(LatLng point) {
	    	        	
	    	        	
	    	        	MarkerOptions mo = new MarkerOptions();
	    	        	mo.position(point);
	    	        	//mo.visible(false);
	    	            map.addMarker(mo);
	    	            
	    	            //LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
	    	            
	    	            //bounds.contains(BRASIL);
	    	            
	    	            Projection proj = map.getProjection();
	    	            
	    	            @SuppressWarnings("unused")
						Point xy = proj.toScreenLocation(point);
	    	            

	    	            Log.d("MapaFragment", "LatLng("+point.latitude+","+ point.longitude+")");
	    	            
	    	            if((zoom >= 18) && (Geometry.isPointInCircle(FAROL_DA_BARRA.latitude, FAROL_DA_BARRA.longitude, raio, point.latitude, point.longitude))){
	    	            	//o usuario pode clicar no mapa a partir do zoom 18
	    	            	showFoundDialog(getActivity());
	    	            }
	    	            
	    	        }
	    	        
	    		});

			
			//TODO: não encontrei uma função para mudar o raio de acordo com o zoom
			
			map.setOnCameraChangeListener(new OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition position) {
					zoom = position.zoom;
					
					Log.v("Zoom", zoom.toString());
					
				}
			});
			

		
		
	}
	
	
	
	//public void showNotFoundDialog(Context context){}
	//public void showFoundDialog2(Context context){}
	public void showFoundDialog(Context context){
		
		if(context == null){
			Log.wtf("showFoundDialog", "context is null");
			return;
		}
		
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_found);
		dialog.setTitle("Title...");
 
			// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText("Parabens! Você acertou");
		
		ImageView image = (ImageView) dialog.findViewById(R.id.dialogFoundImage);
		image.setImageResource(R.drawable.ic_launcher);
 
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			
		dialogButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
 
		dialog.show();
		
	}
    
}