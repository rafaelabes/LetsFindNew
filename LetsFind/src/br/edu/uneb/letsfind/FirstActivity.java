package br.edu.uneb.letsfind;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController.MediaPlayerControl;
//import android.os.Build;

public class FirstActivity extends ActionBarActivity implements IHello {

	String hello = "";
	private GoogleMap map = null;
	
	private final LatLng FAROL_DA_BARRA = new LatLng(-13.010315034340541,-38.53296708315611);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MercadoFragment()).commit();
		}
		
		try{
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			CircleOptions opt = new CircleOptions();
			opt.center(FAROL_DA_BARRA);
			opt.radius(100);
			map.addCircle(opt);
			
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
			
			
			
			/*
			map.addMarker(new MarkerOptions().position(FAROL_DA_BARRA).title("Farol da Barra"));
			CameraPosition cam = map.getCameraPosition();
			float z = cam.zoom;
			*/
			
			
			
		} catch(Exception ex){
			System.err.println(ex);
		}
		
		/*
		this.hello = "hello WTF";
		cli = new ClientExec(this);
		cli.start();
		*/
		
		
		//executes asynchronously
		
		
		
	}
	
	
	public void sayHello(){
		//if(map != null)	map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		Log.wtf("hello", this.hello);
		
		MediaPlayer player = new MediaPlayer();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onStop(){
		super.onStop();
		//cli.interrupt();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_first, container,
					false);
			return rootView;
		}
	}

}
