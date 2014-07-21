package br.edu.uneb.letsfind;

import java.util.List;

import br.edu.uneb.letsfind.db.Tema;
import br.edu.uneb.letsfind.db.TemaDataSource;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class TemasFragment extends Fragment {
	
	
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
		temaDS.open();
		List<Tema> temas = temaDS.getAllTemas();
		temaDS.close();
		
		TemasArrayAdapter temasAdaper = new TemasArrayAdapter(getActivity(), temas);
		gvTemas.setAdapter(temasAdaper);
        
	}
	
}