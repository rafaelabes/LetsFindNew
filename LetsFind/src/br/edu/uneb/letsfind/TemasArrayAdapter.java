package br.edu.uneb.letsfind;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.edu.uneb.letsfind.db.Imagem;
import br.edu.uneb.letsfind.db.ImagemDataSource;
import br.edu.uneb.letsfind.db.Tema;
import br.edu.uneb.webclient.ResponseHandler;
import br.edu.uneb.webclient.WebClient;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TemasArrayAdapter extends ArrayAdapter<Tema> {
	private final Context context;
	private final List<Tema> values;
	
	static byte[] photo = null;
 
	public TemasArrayAdapter (Context context, List<Tema> values) {
		super(context, R.layout.adapter_mercado, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Tema tema = values.get(position);
		
		View rowView = inflater.inflate(R.layout.adapter_temas, parent, false);
		ImageView imageView1 = (ImageView) rowView.findViewById(R.id.imageView1);
		
		//usando o id do tema como chave estrangeira para a imagem
		byte[] imagem = tema.getImagem();
		
		//Imagem imagem = null;
		
		if(imagem != null){
			ByteArrayInputStream imageStream = new ByteArrayInputStream(imagem);
			Bitmap theImage = BitmapFactory.decodeStream(imageStream);
			imageView1.setImageBitmap(theImage);
		}
		else{
			Log.wtf("Image", "A imagem é nula");
		}
		
 
		return rowView;
	}
}
	