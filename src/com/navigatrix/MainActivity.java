package com.navigatrix;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends Activity implements SensorEventListener {

	private ImageView compassImage;
	private float currentPosition = 0f;
	private SensorManager mSensorManager;
	TextView textView1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        compassImage = (ImageView) findViewById(R.id.imageView1);
        textView1 = (TextView) findViewById(R.id.textView1);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.animation);
        textView1.startAnimation(hyperspaceJumpAnimation);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	
    	mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
    	
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    	float inclination = Math.round(event.values[0]);
    	textView1.setText("I am at " + Float.toString(inclination) + " degrees North!!");
    	RotateAnimation rotate = new RotateAnimation(currentPosition, -inclination,
    			Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	
    	rotate.setDuration(200);
    	rotate.setFillAfter(true);
    	    	
    	compassImage.startAnimation(rotate);
    	currentPosition = -inclination;
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
