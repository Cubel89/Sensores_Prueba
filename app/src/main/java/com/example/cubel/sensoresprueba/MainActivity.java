package com.example.cubel.sensoresprueba;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView lista_sensores, title, sensor_txt;
    Double limite_lum = 70.0;
    //Preferencias
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Boolean thema_claro;
    Activity act;
    private SensorManager mSensorManager;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        act = (Activity) this;
        setContentView(R.layout.activity_main);
        //Cargamos las preferencias
        prefs = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE); //Para poder leer
        editor = prefs.edit(); //Para poder guardar
        //Cargamos el contenido de las preferencias
        this.thema_claro = prefs.getBoolean("thema_claro", true);

        title = (TextView) findViewById(R.id.title);
        lista_sensores = (TextView) findViewById(R.id.lista_sensores);
        lista_sensores.setText("");
        lista_sensores.setVisibility(View.GONE);
        sensor_txt = (TextView) findViewById(R.id.sensor_txt);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> mList= mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 1; i < mList.size(); i++) {
            lista_sensores.setVisibility(View.VISIBLE);
            lista_sensores.append("\n" + mList.get(i).getName() + "\n" + mList.get(i).getVendor() + "\n" + mList.get(i).getVersion());
        }


        /*
        Sensor LightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(LightSensor != null){
            //sensor_txt.setText("Sensor.TYPE_LIGHT Available");
            mSensorManager.registerListener(
                    LightSensorListener,
                    LightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }else{
            //sensor_txt.setText("Sensor.TYPE_LIGHT NOT Available");
        }
        */


    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(LightSensorListener);

    }


    private final SensorEventListener LightSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                if (limite_lum > event.values[0]){
                    cambiar_tema(false);
                } else {
                    cambiar_tema(true);
                }
                sensor_txt.setText("LIGHT: " + event.values[0]);
                Log.d("Luz", String.valueOf(event.values[0]));
            }
        }

    };


    public void cambiar_tema(Boolean nuevo_tema){
        if (nuevo_tema != thema_claro){
            editor.putBoolean("thema_claro", nuevo_tema);
            editor.commit();
            thema_claro = nuevo_tema;
            if (thema_claro){
                //Utils.changeToTheme(act, Utils.THEME_CLARO);
                //getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                colores(true);
            } else {
                colores(false);
                //Utils.changeToTheme(act, Utils.THEME_OSCURO);
                //getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                //getWindow().getDecorView().setBackgroundColor(Color.BLACK);
            }
        }
    }


    public void colores(Boolean tema_claro){
        if (tema_claro){
            getWindow().getDecorView().setBackgroundColor(Color.WHITE); //Cambiamos el fondo
            //Textos
            lista_sensores.setTextColor(Color.BLACK);
            sensor_txt.setTextColor(Color.BLACK);
            title.setTextColor(Color.BLACK);
        } else {
            getWindow().getDecorView().setBackgroundColor(Color.BLACK); //Cambiamos el fondo
            //Textos
            lista_sensores.setTextColor(Color.WHITE);
            sensor_txt.setTextColor(Color.WHITE);
            title.setTextColor(Color.WHITE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Sensor LightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(LightSensor != null){
            //sensor_txt.setText("Sensor.TYPE_LIGHT Available");
            mSensorManager.registerListener(
                    LightSensorListener,
                    LightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }else{
            //sensor_txt.setText("Sensor.TYPE_LIGHT NOT Available");
        }
    }


}