/*
 *  This file is part of Sensorium.
 *
 *   Sensorium is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Sensorium is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with Sensorium. If not, see
 *   <http://www.gnu.org/licenses/>.
 * 
 * 
 */

package com.death.sensors;

import java.util.List;

public class SensorValue {

	private Object value;
	private UNIT unit;
	private TYPE type;

	public static enum UNIT {
		DEGREE("°"), MILLISECONDS("ms"), METER("m"), HASH(" "), STRING(" "), MEM("MB"),
		OTHER(" "), NUMBER(" "), RELATIVE("%"), VOLTAGE("V"), TEMPERATURE("°C"),METERSPERSECOND("m/s"),
		STATE("state"), NAME("name"), LIST(""), DBM("dBm"), MBPS("Mbps"), ASU("asu"), PRESSURE("mbar");

		private String name;

		private UNIT(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static enum TYPE {
		LATITUDE("latitude"), LONGITUDE("longitude"), TIMESTAMP("timestamp"), ADDRESS("address"),
		ORIENTATION("orientation"), ALTITUDE("altitude"), RELATIVE_DISTANCE("distance"), ACCURACY("accuracy"),
		CHARGE("charged"), OTHER(""), BATTERY_TECHNOLOGY("battery type"), PLUGGED("power source"),
		MCCMNC("country code + network code"), LAC("location area code"),
		MCC("mobile country code"), MNC("mobile network code"),TEMPERATURE("temperature"),
		CID("cell id"), SIGNALSTRENGTH("received signal strength"),SATELLITES("satellites"),
		NETWORKTYPE("network type"),TAC("TAC"),MODEL_NAME("model"),VENDOR_NAME("vendor"),
		TOTAL_MEM("total memory"), AVAL_MEM("available memory"), THD_MEM("low memory threshold"), CPU("CPU usage"),
		DEVICE_NAME("device name"), MAC_ADDRESS("MAC address"),BEARING("bearing"),VELOCITY("speed"),
		WIFI_CONNECTION("Wifi connection"), SSID("SSID"), SSID_HIDDEN("SSID hidden"),
		BSSID("BSSID"), DEVICE_IP("device IP"), STATE("Supplicant State"), SPEED("link speed"),
        WIFI_NETWORK("Wifi networks"),FREQEUENCY("frequency"),RSSI("RSSI"),WIFI_CAPABILITIES("WiFi capabilities"),
		ROAMING("roaming"),SERVICESTATE("radio state"),OPERATOR("operator"),VOLTAGE("voltage"),ID("ID"),
		BONDED_DEV("bonded device(s)"), SCANNED_DEV("scanned device(s)"), SUBSCRIBER_ID("subscriber id"), ANDROID_VERSION("android version"),
		ATMOSPHERIC_PRESSURE("atmospheric pressure"), SENSORIUM_VERSION("Sensorium version"), SIM_SERIAL("SIM serial"), SIM_STATE("SIM state"), SIM_COUNTRY("country code"), NETWORK_PREFERENCE("preferred network");

		private String name;

		private TYPE(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public SensorValue(Object value, UNIT unit, TYPE type) {
		if (value == null)
			value = "n/a";
		this.value = value;
		this.unit = unit;
	}

	public SensorValue(SensorValue copy) {
		this.value = copy.getValue();
		this.unit = copy.getUnit();
		this.type = copy.getType();
	}

	public SensorValue(UNIT unit, TYPE type) {
		this.value = "n/a";
		this.unit = unit;
		this.type = type;
	}

	public Object getValue() {
		return value;
	}
	
	public String getValueRepresentation(){
		if(value instanceof List){
			StringBuilder sb = new StringBuilder();
			for (Object o: (List) value){
				sb.append(o.toString());
				sb.append("\n");
			}
			if (sb.length() > 0)
				sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		return value.toString();
	}

	public void setValue(Object value) {
		if (value == null)
			value = "n/a";
		this.value = value;
	}

	public UNIT getUnit() {
		return unit;
	}

	public void setUnit(UNIT unit) {
		this.unit = unit;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
	
	public void unsetValue(){
		this.value = "n/a";
	}

    public boolean isNested(){
        if (this.type == TYPE.WIFI_NETWORK)
            return true;
        return false;
    }
}
