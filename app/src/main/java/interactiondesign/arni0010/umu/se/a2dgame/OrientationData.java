package interactiondesign.arni0010.umu.se.a2dgame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Creates a SensorManager and register and unregister listeners to the sensors. The sensor used
 * are the accelerometer and the magnometer to get the gyroscopic orientation of the phone.
 */
public class OrientationData implements SensorEventListener{

    private SensorManager manager;
    private Sensor accelerometer, magnometer;

    private float[] accelOutput, magOutput;

    private float[] orientation = new float[3];

    /**
     * @return the current orientation of the phone.
     */
    public float[] getOrientation(){return orientation;}

    private float[] startOrientation = null;

    /**
     * @return the set start orientation.
     */
    public float[] getStartOrientation(){return startOrientation;}

    /**
     * Resets start orientation to null.
     */
    public void newGame(){

        startOrientation = null;
    }

    /**
     * Initializes the SensorManager and the sensors, Accelerometer and Magnometer.
     */
    public OrientationData(){

        manager = (SensorManager)Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    /**
     * Registers listeners to the sensors.
     */
    public void register(){

        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Unregisters the listeners.
     */
    public void pause(){

        manager.unregisterListener(this);
    }

    /**
     * Not used. Has to be overwritten.
     * @param sensor The sensor.
     * @param i int.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Gets the value of each sensor as the phones orientation changes.
     * @param sensorEvent The SensorEvent.
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = sensorEvent.values;

        else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = sensorEvent.values;

        if(accelOutput != null && magOutput != null) {

            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput);

            if(success){

                SensorManager.getOrientation(R, orientation);

                if(startOrientation == null){
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
            }
        }
    }
}
