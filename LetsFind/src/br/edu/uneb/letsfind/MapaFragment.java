package br.edu.uneb.letsfind;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.edu.uneb.letsfind.db.Pergunta;
import br.edu.uneb.letsfind.db.PerguntaDataSource;
import br.edu.uneb.letsfind.db.PontoTuristico;
import br.edu.uneb.letsfind.db.PontoTuristicoDataSource;
import br.edu.uneb.letsfind.db.Tema;
import br.edu.uneb.letsfind.db.TemaDataSource;
import br.edu.uneb.letsfind.db.Usuario;
import br.edu.uneb.letsfind.db.UsuarioDataSource;

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
import android.media.MediaPlayer;
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
	
	MediaPlayer mp = null;
	
	private long temaId;
	private long perguntaId;
	private Pergunta pergunta;
	private List<PontoTuristico> pontos;
	private PontoTuristicoDataSource pontoDS;
	private PerguntaDataSource pergDS;
	private UsuarioDataSource usuarioDS;
	
	
	TextView textPergunta;
	TextView textPontos;
	TextView textMoedas;
	Button buttonDicas;
	private Usuario usuario;

	
    public MapaFragment() {
    	perguntaId = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);
                
        return rootView;
        
    }
    
    private void fillUsuario0(){
    	if(usuario == null){
    		usuarioDS = new UsuarioDataSource(getActivity());
			List<Usuario> usuarios = usuarioDS.getAllUsuarios();
			if(usuarios.size() > 0){
				usuario = usuarios.get(0);
			}
			else{
				usuario = usuarioDS.createUsuario("Anonymous", 0, 0, 0);
			}
		}
    }
    
    private void proximaPergunta1(){
    	pergunta = pergDS.getPerguntaAfterId(temaId, perguntaId);
        perguntaId = pergunta.getId();
        Log.wtf("PerguntaId", String.valueOf(perguntaId));
    	if(perguntaId == pergDS.getCount()){
    		perguntaId = 0;
    	}

    	if(textPergunta != null)
    	textPergunta.setText(pergunta.getTexto());
    	textPontos.setText(String.valueOf(usuario.getAcertos()));
    	textMoedas.setText(String.valueOf(usuario.getMoedas()));
    }
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		
		 map = ((SupportMapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
	     
	        if(map != null){
	        		
	        	textPergunta = (TextView) view.findViewById(R.id.textPergunta);
	        	textPontos = (TextView) view.findViewById(R.id.textPontos);
	        	textMoedas = (TextView) view.findViewById(R.id.textMoedas);
	        	buttonDicas = (Button) view.findViewById(R.id.buttonDicas);
	    
	        	//O usuario é usado para atualizar os pontos
	        	fillUsuario0();
	    		
	        	ContainsTema ctema = (ContainsTema) getActivity();
	        	temaId = ctema.getTemaId();
	        	Log.wtf("TemaID", String.valueOf(temaId));
	        	
	        	pergDS = new PerguntaDataSource(getActivity());
	        	
	        	proximaPergunta1();

	        
	        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(BRASIL, 4);
			map.animateCamera(update);
	        
	        }
	        
	        // TODO: saber se a pessoa clicou no ponto desejado
	        if(map != null)
	    		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

	    	        @Override
	    	        public void onMapClick(LatLng point) {
	    	        	
	    	        	pontoDS = new PontoTuristicoDataSource(getActivity());
	    	        	
	    	        	MarkerOptions mo = new MarkerOptions();
	    	        	mo.position(point);
	    	        	//mo.visible(false);
	    	            map.addMarker(mo);
	    	            
	    	            //LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
	    	            
	    	            //bounds.contains(BRASIL);
	    	            
	    	            /*
	    	            Projection proj = map.getProjection();
						Point xy = proj.toScreenLocation(point);
						*/

	    	            Log.d("MapaFragment", "LatLng("+point.latitude+","+ point.longitude+")");
	    	            
	    	            
	    	            
	    	            if(zoom >= 18){
	    	            	
	    	            	pontos = pontoDS.getPontoTuristicoByPergunta(pergunta);
	    	            	
	    	            	Iterator<PontoTuristico> it2 = pontos.iterator();
	    		        	
	    		        	while(it2.hasNext()){
	    		        		PontoTuristico ponto = it2.next();
	    		        		
	    		        		if(Geometry.isPointInCircle(
	    		        				ponto.getLatitude(),
	    		        				ponto.getLongitude(),
	    	            				raio,
	    	            				point.latitude,
	    	            				point.longitude)){
	    	            			
					    	        //o usuario pode clicar no mapa a partir do zoom 18
	    	            			showFoundDialog(getActivity());
	    	            			
	    	            			mp = MediaPlayer.create(getActivity(), R.raw.win);
	    	            			mp.start();

	    	            			//atualiza a pontuação
	    	            			usuario.setAcertos(usuario.getAcertos() + 1);
	    	            			usuario.setUltimaTentativa(new Date());
	    	            			
	    	            			
	    	            		}
	    	            		else{
	    	            			//atualiza erros
	    	            			usuario.setErros(usuario.getErros() + 1);
	    	            			usuario.setUltimaTentativa(new Date());
	    	            			
	    	            			showNotFoundDialog(getActivity());
	    	            			mp = MediaPlayer.create(getActivity(), R.raw.loose);
	    	            			mp.start();
	    	            		} //if
	    		        		
	    		        		
	    		        	}// while
	    	            	
	    	            		
	    	            } // if zoom
	    	            else{
	    	            	showClickDeniedDialog(getActivity());
	    	            	
	    	            	mp = MediaPlayer.create(getActivity(), R.raw.ding);
	    	        		mp.start();
	    	            	
	    	            	}
	    	            
	    	            
            			//salva no banco
            			usuarioDS.updateUsuario(usuario);
            			
            			proximaPergunta1();
            			
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
	
	
	public void showFoundDialog(Context context){
		
		if(context == null){
			Log.wtf("showFoundDialog", "context is null");
			return;
		}
		
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_found);
		dialog.setTitle(R.string.Success);
 
			// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(R.string.Congratulations);
		
		ImageView image = (ImageView) dialog.findViewById(R.id.foundImage);
		image.setImageResource(R.drawable.ic_launcher);
 
		Button closeButton = (Button) dialog.findViewById(R.id.closeButton);
			// if button is clicked, close the custom dialog
		
		closeButton.setText(R.string.Close);
			
		closeButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		Button detailsButton = (Button) dialog.findViewById(R.id.detailsButton);
		// if button is clicked, close the custom dialog
		
		detailsButton.setText(R.string.ShowDetails);
		
		detailsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
 
		dialog.show();
		
	}
	
	
	public void showNotFoundDialog(Context context){
		
		if(context == null){
			Log.wtf("showFoundDialog", "context is null");
			return;
		}
		
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_not_found);
		dialog.setTitle(R.string.Error);
 
			// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(R.string.YouFailed);
		
		ImageView image = (ImageView) dialog.findViewById(R.id.notFoundImage);
		image.setImageResource(R.drawable.ic_launcher);
 
		Button dialogButton = (Button) dialog.findViewById(R.id.closeButton);
			// if button is clicked, close the custom dialog
			
		dialogButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
 
		dialog.show();
		
	}
	
	
	public void showClickDeniedDialog(Context context){
		
		if(context == null){
			Log.wtf("showFoundDialog", "context is null");
			return;
		}
		
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_click_denied);
		dialog.setTitle(R.string.GameRules);
 
			// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(R.string.DeniedZoomLevel);
		
		ImageView image = (ImageView) dialog.findViewById(R.id.deniedImage);
		image.setImageResource(R.drawable.ic_launcher);
 
		Button closeButton = (Button) dialog.findViewById(R.id.closeButton);
			// if button is clicked, close the custom dialog
			
		closeButton.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
 
		dialog.show();
		
	}
	
    
}