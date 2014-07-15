package br.edu.uneb.letsfind;

import java.util.ArrayList;
import java.util.List;

import br.edu.uneb.letsfind.db.Pergunta;
import br.edu.uneb.letsfind.db.PerguntaDataSource;
import br.edu.uneb.letsfind.db.PontoTuristicoDataSource;
import br.edu.uneb.letsfind.db.Tema;
import br.edu.uneb.letsfind.db.TemaDataSource;
import br.edu.uneb.webclient.ResponseHandler;
import br.edu.uneb.webclient.WebClient;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;









import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
        	
        	getSupportFragmentManager().beginTransaction()
            .add(R.id.container, new MenuFragment())
            .commit();
            
        }
        
        WebClient web = new WebClient(this, "naiara.tk", "/", "sample.json",
        new ResponseHandler() {
			
			@Override
			public void execute(Context context, String content) {
				try {

					JSONObject jsonObject = new JSONObject(content);

					Log.v("WebClient","executando a segunda thread");

					JSONArray values = (JSONArray) jsonObject.get("values");

					for (int i = 0; i < values.length(); i++) {
						JSONObject innerObj = (JSONObject) values.get(i);

						String iorder = (String) innerObj.get("order");
						String iname = (String) innerObj.get("name");
						String iponts = (String) innerObj.get("ponts");

						Log.v("app",iorder + " / " + iname + " / " + iponts);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        List<NameValuePair> parametros = new ArrayList<NameValuePair>(2);
        parametros.add(new BasicNameValuePair("id", "12345"));
        parametros.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
        web.setParametros(parametros);
        
        //web.setMethod(WebClient.POST);
        web.start();
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

}