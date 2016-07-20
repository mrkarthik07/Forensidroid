package com.death.sensors;

import android.content.Context;

public class SensorRegistry {
	private static SensorRegistry instance = null;
	
	public static SensorRegistry getInstance() {
		if (instance == null) {
			instance = new SensorRegistry();
		}
		return instance;
	}
	
	protected Context getContext() {
		return SensorRegistry.getInstance().getContext();
	}
}


