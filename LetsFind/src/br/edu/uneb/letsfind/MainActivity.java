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
        
        Double raio = 3.0694775721281596E-4;
        
        TemaDataSource temaDS = new TemaDataSource(this);
        temaDS.open();
        
        Tema tema = temaDS.createTema("Copa do Mundo 2014");
        
        temaDS.close();
        
        PontoTuristicoDataSource ptd = new PontoTuristicoDataSource(this);
        ptd.open();
        
        PerguntaDataSource perDS = new PerguntaDataSource(this);
        perDS.open();
        
        Pergunta pergunta = perDS.createPergunta(tema, "Arena Fonte Nova" , false);
        ptd.createPontoTuristico(pergunta, "Arena Fonte Nova", -12.97883,-38.504371, raio);
        
        pergunta = perDS.createPergunta(tema, "Arena Corinthians" , false);
        ptd.createPontoTuristico(pergunta, "Arena Corinthians", -23.545333,-46.473702, raio);
        
        pergunta = perDS.createPergunta(tema, "Mané Garrincha" , false);
        ptd.createPontoTuristico(pergunta, "Mané Garrincha", -15.783519,-47.899211, raio);
        
        pergunta = perDS.createPergunta(tema, "Mineirão Estádio" , false);
        ptd.createPontoTuristico(pergunta,"Mineirão Estádio", -19.865867,-43.971132,raio);
        
        pergunta = perDS.createPergunta(tema, "Maracanã Estádio" , false);
        ptd.createPontoTuristico(pergunta,"Maracanã Estádio", -22.9127667,-43.2300316, raio);
        
        pergunta = perDS.createPergunta(tema, "José do Rego Maciel Estádio" , false);
        ptd.createPontoTuristico(pergunta,"José do Rego Maciel Estádio", -8.026699,-34.891111, raio);
        
        pergunta = perDS.createPergunta(tema, "Villa Estádio Alvorada" , false);
        ptd.createPontoTuristico(pergunta,"Villa Estádio Alvorada", -3.08026,-60.034402, raio);
        
        pergunta = perDS.createPergunta(tema, "Arena da Amazônia" , false);
        ptd.createPontoTuristico(pergunta,"Arena da Amazônia", -3.0836637,-60.0279703, raio);
        
        pergunta = perDS.createPergunta(tema, "Arena Pantanal" , false);
        ptd.createPontoTuristico(pergunta,"Arena Pantanal", -15.604017,-56.121632, raio);
        
        pergunta = perDS.createPergunta(tema, "Estadio Beira Rio" , false);
        ptd.createPontoTuristico(pergunta,"Estadio Beira Rio", -30.0654729,-51.2358289, raio);
        
        pergunta = perDS.createPergunta(tema, "Arena da Baixada"  , false);
        ptd.createPontoTuristico(pergunta,"Arena da Baixada", -25.448212,-49.276987, raio);
        
        pergunta = perDS.createPergunta(tema,"Estadio das Dunas"  , false);
        ptd.createPontoTuristico(pergunta,"Estadio das Dunas", -5.826827,-35.21243, raio);

        pergunta = perDS.createPergunta(tema, "Estadio Castelão" , false);
        ptd.createPontoTuristico(pergunta,"Estadio Castelão", -3.807312,-38.522269, raio);
        
        pergunta = perDS.createPergunta(tema, "Arena da baixada" , false);
        ptd.createPontoTuristico(pergunta,"Arena da baixada", -25.448212,-49.276987, raio);

        perDS.close();
        ptd.close();
        		 
        
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