package br.edu.uneb.webclient;

import android.content.Context;
import android.view.View;

public abstract class ResponseHandler {
	public void execute(Context context, String content){}
	public void execute(Context context, byte[] content){}
}
