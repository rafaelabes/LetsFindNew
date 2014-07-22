package br.edu.uneb.letsfind;

import java.util.List;

import br.edu.uneb.letsfind.db.GameDbHelper;
import br.edu.uneb.letsfind.db.Tema;
import br.edu.uneb.letsfind.db.TemaDataSource;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class TemasFragment extends Fragment {
	
	List<Tema> temas = null;
	
    public TemasFragment() {
    	//TODO: asdf
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_temas, container, false);
        
        return rootView;
        
    }
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		GridView gvTemas = (GridView) getView().findViewById(R.id.gridView1);
		
		TemaDataSource temaDS = new TemaDataSource(getActivity());
		
		temas = temaDS.getAllTemas2();
		
		TemasArrayAdapter temasAdaper = new TemasArrayAdapter(getActivity(), temas);
		gvTemas.setAdapter(temasAdaper);
		
		gvTemas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Tema tema = temas.get(position);
				Intent intent = new Intent(getActivity(), MapaActivity.class);
				intent.putExtra(GameDbHelper.TABLE_TEMA, tema.getId());
				startActivity(intent);
			}
		});
        
	}
	
}