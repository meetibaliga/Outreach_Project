package com.example.omar.outreach.Managers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Calendar;

public class MovementDetector implements SensorEventListener {

    // Create
    private Context context;
    private SensorManager sensorManager;

    //state controls
    private boolean moving = false; // default is not moving
    int currentStoppingPoints = 0; // default is 0
    long stoppedAt = Calendar.getInstance().getTimeInMillis();

    // Constants
    double MOVEMENT_THRESHOLD = 0.5 ;
    double STOPPING_TIMES_THRESHOLD = 100;

    //public consts
    public static long TEN_MINS = 1000 * 60 * 10;

    //instance
    public static MovementDetector instance;
    public static MovementDetector getInstance(Context context) {
        if(instance == null){
            instance = new MovementDetector(context);
        }
        return instance;
    }

    private MovementDetector(Context context){
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
    }

    public void startDetecting(){

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (sensor== null){ return;}
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void stopDetecting(){
        sensorManager.unregisterListener(this);
    }

    public boolean isMoving(){
        return moving;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        double x = event.values[0];
        double y = event.values[1];
        double z = event.values[2];

        checkMovement(x,y,z);


    }

    private void checkMovement(double x, double y, double z){

        if(( x > MOVEMENT_THRESHOLD|| x < -MOVEMENT_THRESHOLD) && (y > MOVEMENT_THRESHOLD || y < -MOVEMENT_THRESHOLD) && (z > MOVEMENT_THRESHOLD || z < -MOVEMENT_THRESHOLD )){

            // is moving

            toggleMoving(true);
            currentStoppingPoints = 0;

        }else{

            currentStoppingPoints++;

            if (currentStoppingPoints >= STOPPING_TIMES_THRESHOLD){

                // is not moving
                toggleMoving(false);
                currentStoppingPoints = 0;

            }
        }

    }

    void toggleMoving(boolean newState){

        if(newState != moving){

            moving = newState;

            if(isMoving()){

                // from stopping to moving
                stoppedAt = -1;


            }else{

                // from moving to stopping
                stoppedAt = Calendar.getInstance().getTimeInMillis();

            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public long getStoppingTime(){

        if(stoppedAt == -1){
            return 0;
        }

        return Calendar.getInstance().getTimeInMillis() - stoppedAt;
    }
}
