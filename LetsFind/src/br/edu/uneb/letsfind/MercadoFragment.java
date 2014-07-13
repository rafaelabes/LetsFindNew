package br.edu.uneb.letsfind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    
}