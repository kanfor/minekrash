package com.minekrash.game.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.minekrash.game.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(), config);
		readFile("MAX_METROS"); //Cargamos datos guardados del juego
		saveData = new SaveData();
		saveData.start();
	}
	
	//SISTEMA DE GUARDADO DE PARTIDA//
	
	SaveData saveData;
	
	public class SaveData implements Runnable {
		
		private Thread thread;

		public void start() {
			if (thread == null) {
				thread = new Thread(this);
				thread.start();
			}
		}
		
		public void run() {
			while (true) {
				if (Main.isSaving) {
					try {
						String[] data = new String[1];
						data[0] = ""+Main.maxMetros;
						writeFile("MAX_METROS", data);
						Main.isSaving = false;
					} catch (Exception e) {
						Log.e("test", "ERROR GUARDANDO");
					}
				}
			}
		}
	}
	
	public void writeFile(String filename, String textfile[]) {
		try {
			//Guardado interno
			OutputStreamWriter fout = new OutputStreamWriter(openFileOutput(""+filename, 0));
			for (int i = 0; i< textfile.length; i++) {
				fout.write(textfile[i]+"\n");
			}
			fout.close();
			Log.e("test", "Exito guardando los datos");
			
			//Lectura de prueba	
		    BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput(""+filename)));
		    String linea;
		    do {
		        linea = fin.readLine();
		        if (linea != null) {
		        	Log.e("test", "Dato guardado: "+linea);
		        }
		     } while (linea != null);
		    fin.close();
				
		} catch (Exception e) {
			Log.e("test", "Error guardando datos");
		}
	}
	
	public void readFile(String filename) {
		try {
			BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput(""+filename)));
		    String linea;
		    int nextLinea = 0;
		    
		    do {
		        linea = fin.readLine();
		        if (linea != null) {
		        	switch (nextLinea) {
		        	case 0:
		        		System.out.println("El fichero tiene guardado: "+linea);
		        		Log.e("test", "El fichero tiene guardado: "+linea);
		        		Main.maxMetros = Integer.parseInt(linea); //Pasamos a la variable del juego el valor guardado
						break;
					}
		        	nextLinea++;
		        }
		     } while (linea != null);

		    fin.close();
		} catch (Exception e) {
			Log.e("test", "No hay datos guardados");
		}
	}
}
