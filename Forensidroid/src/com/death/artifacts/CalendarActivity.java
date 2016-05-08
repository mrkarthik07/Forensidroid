package com.death.artifacts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Attendees;
import android.provider.CalendarContract.Calendars;
import android.util.Log;
import android.widget.TextView;

public class CalendarActivity extends Activity {
	String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	//File file = null;
	public static final String FILE_DIRECTORY = "Calendar";
	private static final String TAG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView view = new TextView(this);
		
		File folder = new File(filepath+File.separator+FILE_DIRECTORY);
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				Log.d(TAG, "Calendar Folder not created :"+e);
			}
		}
		File calendar = new File(folder+File.separator+"Calendar.txt");
		if (!calendar.exists()) {
			try {
				calendar.createNewFile();
				FileOutputStream  writer = new FileOutputStream (calendar, true);
				writer.write("ID, NAME, ALLOWED_REMINDERS, CALENDAR_ACCESS_LEVEL, CALENDAR_COLOR, CALENDAR_COLOR, CALENDAR_DISPLAY_NAME, CALENDAR_TIME_ZONE, CAN_MODIFY_TIME_ZONE, CAN_ORGANIZER_RESPOND, MAX_REMINDERS, OWNER_ACCOUNT, SYNC_EVENTS, VISIBLE, ACCOUNT_NAME, ACCOUNT_TYPE, CAN_PARTIALLY_UPDATE, DELETED, DIRTY, SYNC_ID, CALENDAR_LOCATION\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "Calendar File not created :"+e);
			}
		}
		File event = new File(folder+File.separator+"Events.txt");
		if (!event.exists()) {
			try {
				event.createNewFile();
				FileOutputStream  writer = new FileOutputStream (event, true);
				writer.write("ID, ALLOWED_REMINDERS, CALENDAR_ACCESS_LEVEL, CALENDAR_COLOR, CALENDAR_DISPLAY_NAME, CALENDAR_TIME_ZONE, CAN_MODIFY_TIME_ZONE, CAN_ORGANIZER_RESPOND, MAX_REMINDERS, OWNER_ACCOUNT, VISIBLE, ACCOUNT_NAME, ACCOUNT_TYPE, DELETED, SYNC_ID, ACCESS_LEVEL, ALL_DAY, AVAILABILITY, CALENDAR_ID, DESCRIPTION, DTEND, DTSTART, DURATION, EVENT_COLOR, EVENT_END_TIMEZONE, EVENT_LOCATION, EVENT_TIMEZONE, EXDATE, EXRULE, GUESTS_CAN_INVITE_OTHERS, GUESTS_CAN_MODIFY, GUESTS_CAN_SEE_GUESTS, HAS_ALARM, HAS_ATTENDEE_DATA, HAS_EXTENDED_PROPERTIES, LAST_DATE, ORGANIZER, ORIGINAL_ALL_DAY, ORIGINAL_ID, ORIGINAL_INSTANCE_TIME, ORIGINAL_SYNC_ID, RDATE, RRULE, SELF_ATTENDEE_STATUS, STATUS, TITLE\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "Events File not created :"+e);
			}
		}
		File attendee = new File(folder+File.separator+"Attendee.txt");
		if (!attendee.exists()) {
			try {
				attendee.createNewFile();
				FileOutputStream  writer = new FileOutputStream (attendee, true);
				writer.write("ID, EVENT_ID, ATTENDEE_NAME, ATTENDEE_EMAIL, ATTENDEE_RELATIONSHIP, ATTENDEE_TYPE, ATTENDEE_STATUS\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "Attendee File not created :"+e);
			}
		}
//		File instances = new File(folder+File.separator+"Instances.txt");
//		if (!instances.exists()) {
//			try {
//				instances.createNewFile();
//				FileOutputStream  writer = new FileOutputStream (instances, true);
//				writer.write("Stand, Phone Number, Msg, Time\n".getBytes());
//			} catch (IOException e) {
//				Log.d(TAG, "File not created :"+e);
//			}
//		}
		File reminders = new File(folder+File.separator+"Reminders.txt");
		if (!reminders.exists()) {
			try {
				reminders.createNewFile();
				FileOutputStream  writer = new FileOutputStream (reminders, true);
				writer.write("ID, EVENT_ID, MINUTES, METHOD\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "File not created :"+e);
			}
		}
		/*
		 * Events holds its carriage here
		 */
		Cursor cur = getContentResolver().query(android.provider.CalendarContract.Events.CONTENT_URI, null, null, null,null);
		
		String eventsid = CalendarContract.Events._ID;
		String eventsallowedReminders = CalendarContract.Events.ALLOWED_REMINDERS;
		String eventscalendarAccessLevel = CalendarContract.Events.CALENDAR_ACCESS_LEVEL;
		String eventscalendarColor = CalendarContract.Events.CALENDAR_COLOR;
		String eventsdisplayName = CalendarContract.Events.CALENDAR_DISPLAY_NAME;
		String eventscalendarTimeZone = CalendarContract.Events.CALENDAR_TIME_ZONE;
		String eventscanModifyTimeZone = CalendarContract.Events.CAN_MODIFY_TIME_ZONE;
		String eventscanOrginizerRespond = CalendarContract.Events.CAN_ORGANIZER_RESPOND;
		String eventsmaxReminders = CalendarContract.Events.MAX_REMINDERS; 
		String eventsownerAccount = CalendarContract.Events.OWNER_ACCOUNT;
		String eventsvisible = CalendarContract.Events.VISIBLE;
		String eventsaccountName = CalendarContract.Events.ACCOUNT_NAME;
		String eventsaccountType = CalendarContract.Events.ACCOUNT_TYPE;
		String eventsdeleted = CalendarContract.Events.DELETED;
		String eventssyncId = CalendarContract.Events._SYNC_ID;
		
		String eventsaccessLevel = CalendarContract.Events.ACCESS_LEVEL;
		String eventsallDay = CalendarContract.Events.ALL_DAY;
		String eventsavailability = CalendarContract.Events.AVAILABILITY;
		String eventscalendarId = CalendarContract.Events.CALENDAR_ID;
		String eventsdescription = CalendarContract.Events.DESCRIPTION;
		String eventsdTend = CalendarContract.Events.DTEND; 
		String eventsdTStart = CalendarContract.Events.DTSTART;
		String eventsduration = CalendarContract.Events.DURATION;
		String eventColor = CalendarContract.Events.EVENT_COLOR;
		String eventEndTimeZone = CalendarContract.Events.EVENT_END_TIMEZONE;
		String eventLocation = CalendarContract.Events.EVENT_LOCATION;
		String eventTimeZone = CalendarContract.Events.EVENT_TIMEZONE;
		String eventExDate = CalendarContract.Events.EXDATE;
		String eventExRule = CalendarContract.Events.EXRULE;
		String eventsguestCanInviteOthers = CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS;
		String eventsguestCanModify = CalendarContract.Events.GUESTS_CAN_MODIFY;
		String eventsguestCanSeeQuests = CalendarContract.Events.GUESTS_CAN_SEE_GUESTS;
		String eventshasAlarm = CalendarContract.Events.HAS_ALARM;
		String eventshasAttendeeData = CalendarContract.Events.HAS_ATTENDEE_DATA;
		String eventshasExtendedProperties = CalendarContract.Events.HAS_EXTENDED_PROPERTIES;
		String eventslastDate = CalendarContract.Events.LAST_DATE;
		String eventsorganizer = CalendarContract.Events.ORGANIZER;
		String eventsoriginalAllDay = CalendarContract.Events.ORIGINAL_ALL_DAY;
		String eventsoriginalId = CalendarContract.Events.ORIGINAL_ID;
		String eventsoriginalInstanceTime = CalendarContract.Events.ORIGINAL_INSTANCE_TIME;
		String eventsoriginalSyncId = CalendarContract.Events.ORIGINAL_SYNC_ID;
		String eventsrDate = CalendarContract.Events.RDATE;
		String eventsrRule = CalendarContract.Events.RRULE;
		String eventsselfAttendeeStatus = CalendarContract.Events.SELF_ATTENDEE_STATUS;
		String eventsstatus = CalendarContract.Events.STATUS;
		String eventstitle = CalendarContract.Events.TITLE;
			
		String worde = "";
		String worda = "";
		String wordc = "";
		String wordr = "";
	    while (cur.moveToNext()) {
	    	try{
	    		worde+=(cur.getString(cur.getColumnIndex(eventsid)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsallowedReminders)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventscalendarAccessLevel)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventscalendarColor)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsdisplayName)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventscalendarTimeZone)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventscanModifyTimeZone)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventscanOrginizerRespond)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsmaxReminders)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsownerAccount)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsvisible)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsaccountName)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsaccountType)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsdeleted)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventssyncId)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsaccessLevel)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsallDay)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsavailability)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventscalendarId)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsdescription)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsdTend)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsdTStart)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsduration)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventColor)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventEndTimeZone)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventLocation)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventTimeZone)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventExDate)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventExRule)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsguestCanInviteOthers)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsguestCanModify)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsguestCanSeeQuests)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventshasAlarm)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventshasAttendeeData)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventshasExtendedProperties)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventslastDate)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsorganizer)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsoriginalAllDay)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsoriginalId)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsoriginalInstanceTime)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsoriginalSyncId)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsrDate)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsrRule)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsselfAttendeeStatus)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventsstatus)))+", "+
	    		(cur.getString(cur.getColumnIndex(eventstitle)))+"\n";
	    		
	    		
				BufferedWriter buf = new BufferedWriter(new FileWriter(event, true)); 
	    		buf.append(cur.getString(cur.getColumnIndex(eventsid)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsallowedReminders)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventscalendarAccessLevel)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventscalendarColor)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsdisplayName)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventscalendarTimeZone)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventscanModifyTimeZone)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventscanOrginizerRespond)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsmaxReminders)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsownerAccount)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsvisible)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsaccountName)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsaccountType)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsdeleted)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventssyncId)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsaccessLevel)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsallDay)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsavailability)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventscalendarId)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsdescription)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsdTend)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsdTStart)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsduration)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventColor)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventEndTimeZone)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventLocation)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventTimeZone)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventExDate)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventExRule)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsguestCanInviteOthers)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsguestCanModify)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsguestCanSeeQuests)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventshasAlarm)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventshasAttendeeData)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventshasExtendedProperties)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventslastDate)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsorganizer)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsoriginalAllDay)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsoriginalId)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsoriginalInstanceTime)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsoriginalSyncId)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsrDate)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsrRule)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsselfAttendeeStatus)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventsstatus)));buf.append(",");
	    		buf.append(cur.getString(cur.getColumnIndex(eventstitle)));
	    		buf.newLine();
	    		buf.close();
	    	}catch (IOException e){
	    		Log.d(TAG, "Dictionary file not created :"+e);
	    	}
	    }

	    /* 
	     * This belongs to Attendee
	     */
	    Cursor curattendee = getContentResolver().query(Attendees.CONTENT_URI, null, null, null,null);
		
		String attendeeid = CalendarContract.Attendees._ID;
		String attendeeeventid = CalendarContract.Attendees.EVENT_ID;
		String attendeename = CalendarContract.Attendees.ATTENDEE_NAME;
		String attendeeemail = CalendarContract.Attendees.ATTENDEE_EMAIL;
		String attendeerelationship = CalendarContract.Attendees.ATTENDEE_RELATIONSHIP;
		String attendeetype = CalendarContract.Attendees.ATTENDEE_TYPE;
		String attendeestatus = CalendarContract.Attendees.ATTENDEE_STATUS;
		
	    while (curattendee.moveToNext()) {
	    	try{
				worda+=(curattendee.getString(curattendee.getColumnIndex(attendeeid)))+", "+
	    		(curattendee.getString(curattendee.getColumnIndex(attendeeeventid)))+", "+
	    		(curattendee.getString(curattendee.getColumnIndex(attendeename)))+", "+
	    		(curattendee.getString(curattendee.getColumnIndex(attendeeemail)))+", "+
	    		(curattendee.getString(curattendee.getColumnIndex(attendeerelationship)))+", "+
	    		(curattendee.getString(curattendee.getColumnIndex(attendeetype)))+", "+
	    		(curattendee.getString(curattendee.getColumnIndex(attendeestatus))) +"\n"; 
	    		//BufferedWriter for performance, true to set append to file flag
	    		BufferedWriter buf = new BufferedWriter(new FileWriter(attendee, true)); 
	    		buf.append(curattendee.getString(curattendee.getColumnIndex(attendeeid)));buf.append(",");
	    		buf.append(curattendee.getString(curattendee.getColumnIndex(attendeeeventid)));buf.append(",");
	    		buf.append(curattendee.getString(curattendee.getColumnIndex(attendeename)));buf.append(",");
	    		buf.append(curattendee.getString(curattendee.getColumnIndex(attendeeemail)));buf.append(",");
	    		buf.append(curattendee.getString(curattendee.getColumnIndex(attendeerelationship)));buf.append(",");
	    		buf.append(curattendee.getString(curattendee.getColumnIndex(attendeetype)));buf.append(",");
	    		buf.append(curattendee.getString(curattendee.getColumnIndex(attendeestatus)));buf.append(",");
	    		buf.newLine();
	    		buf.close();
	    	}catch (IOException e){
	    		Log.d(TAG, "Events file not created :"+e);
	    	}
	    }
	    /*
	     * this is calendar
	     */

	    Cursor curcalendar = getContentResolver().query(Calendars.CONTENT_URI, null, null, null,null);
			
	    String calendarid = CalendarContract.Calendars._ID;
	    String calendarname = CalendarContract.Calendars.NAME;
	    String calendarallowedReminders = CalendarContract.Calendars.ALLOWED_REMINDERS;
	    String calendarAccessLevel = CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL;
	    String calendarColor = CalendarContract.Calendars.CALENDAR_COLOR;
	    String calendardisplayName = CalendarContract.Calendars.CALENDAR_DISPLAY_NAME;
	    String calendarTimeZone = CalendarContract.Calendars.CALENDAR_TIME_ZONE;
	    String calendarcanModifyTimeZone = CalendarContract.Calendars.CAN_MODIFY_TIME_ZONE;
	    String calendarcanOrginizerRespond = CalendarContract.Calendars.CAN_ORGANIZER_RESPOND;
	    String calendarmaxReminders = CalendarContract.Calendars.MAX_REMINDERS;
	    String calendarownerAccount = CalendarContract.Calendars.OWNER_ACCOUNT;
	    String calendarsyncEvents = CalendarContract.Calendars.SYNC_EVENTS;
	    String calendarvisible = CalendarContract.Calendars.VISIBLE;
	    String calendaraccountName = CalendarContract.Calendars.ACCOUNT_NAME;
	    String calendaraccountType = CalendarContract.Calendars.ACCOUNT_TYPE;
	    String calendarcanPartiallyUpdate = CalendarContract.Calendars.CAN_PARTIALLY_UPDATE;
	    String calendardeleted = CalendarContract.Calendars.DELETED;
	    String calendardirty = CalendarContract.Calendars.DIRTY;
	    String calendarsyncId = CalendarContract.Calendars._SYNC_ID;
	    String calendarlocation = CalendarContract.Calendars.CALENDAR_LOCATION;
			
	    while (curcalendar.moveToNext()) {
	    	try{
				worda+=(curcalendar.getString(curcalendar.getColumnIndex(calendarid)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarname)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarallowedReminders)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarAccessLevel)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarColor)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendardisplayName)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarTimeZone)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarcanModifyTimeZone)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarcanOrginizerRespond)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarmaxReminders)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarownerAccount)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarsyncEvents)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarvisible)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendaraccountName)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendaraccountType)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarcanPartiallyUpdate)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendardeleted)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendardirty)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarsyncId)))+", "+
				(curcalendar.getString(curcalendar.getColumnIndex(calendarlocation)))+"\n";
	    		
				//BufferedWriter for performance, true to set append to file flag
	    		BufferedWriter buf = new BufferedWriter(new FileWriter(calendar, true)); 
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarid)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarname)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarallowedReminders)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarAccessLevel)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarColor)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendardisplayName)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarTimeZone)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarcanModifyTimeZone)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarcanOrginizerRespond)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarmaxReminders)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarownerAccount)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarsyncEvents)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarvisible)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendaraccountName)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendaraccountType)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarcanPartiallyUpdate)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendardeleted)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendardirty)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarsyncId)));buf.append(",");
				buf.append(curcalendar.getString(curcalendar.getColumnIndex(calendarlocation)));
				buf.newLine();
				buf.close();
			}catch (IOException e){
				Log.d(TAG, "Calendar file not created :"+e);
			}
	    }   
	    /*
	     * This belongs to Instances
	     */
//	    Uri uri = Uri.parse("content://com.android.calendar/instances");
//	    Cursor curinst = getContentResolver().query(uri, null, null, null,null);

//		String instid = CalendarContract.Instances._ID;
//		String insteventId = CalendarContract.Instances.EVENT_ID;
//		String instbegin = CalendarContract.Instances.BEGIN;
//		String instend = CalendarContract.Instances.END;
//		String inststartDay = CalendarContract.Instances.START_DAY;
//		String inststartMinute = CalendarContract.Instances.START_MINUTE;
//		String instendDay = CalendarContract.Instances.END_DAY;
//		String instendMinute = CalendarContract.Instances.END_MINUTE;
//		
//	    while (curinst.moveToNext()) {
//	    	try{
//				   //BufferedWriter for performance, true to set append to file flag
//	    		BufferedWriter buf = new BufferedWriter(new FileWriter(instances, true)); 
//	    		buf.append(curinst.getString(curinst.getColumnIndex(instid)));buf.append(",");
//	    		buf.append(curinst.getString(curinst.getColumnIndex(insteventId)));buf.append(",");
//	    		buf.append(curinst.getString(curinst.getColumnIndex(instbegin)));buf.append(",");
//	    		buf.append(curinst.getString(curinst.getColumnIndex(instend)));buf.append(",");
//	    		buf.append(curinst.getString(curinst.getColumnIndex(inststartDay)));buf.append(",");
//	    		buf.append(curinst.getString(curinst.getColumnIndex(inststartMinute)));buf.append(",");
//	    		buf.append(curinst.getString(curinst.getColumnIndex(instendDay)));buf.append(",");
//	    		buf.append(curinst.getString(curinst.getColumnIndex(curinst.getString(curinst.getColumnIndex(instendMinute)))));
//	    		buf.newLine();
//	    		buf.close();
//	    	}catch (IOException e){
//	    		Log.d(TAG, "Instances file not created :"+e);
//	    	}
//	    }
	    /*
	     * This belongs to Reminders
	     */
	    Cursor curremind = getContentResolver().query(android.provider.CalendarContract.Reminders.CONTENT_URI, null, null, null,null);

		String remindid = CalendarContract.Reminders._ID;
		String remindeventId = CalendarContract.Reminders.EVENT_ID;
		String remindminutes = CalendarContract.Reminders.MINUTES;
		String remindmethod = CalendarContract.Reminders.METHOD;
		
	    while (curremind.moveToNext()) {
	    	try{
				wordr+=(curremind.getString(curremind.getColumnIndex(remindid)))+", "+
	    		(curremind.getString(curremind.getColumnIndex(remindeventId)))+", "+
	    		(curremind.getString(curremind.getColumnIndex(remindminutes)))+", "+
	    		(curremind.getString(curremind.getColumnIndex(remindmethod)))+"\n";
	    		
	    		//BufferedWriter for performance, true to set append to file flag
	    		BufferedWriter buf = new BufferedWriter(new FileWriter(reminders, true)); 
	    		buf.append(curremind.getString(curremind.getColumnIndex(remindid)));buf.append(",");
	    		buf.append(curremind.getString(curremind.getColumnIndex(remindeventId)));buf.append(",");
	    		buf.append(curremind.getString(curremind.getColumnIndex(remindminutes)));buf.append(",");
	    		buf.append(curremind.getString(curremind.getColumnIndex(remindmethod)));
	    		buf.newLine();
	    		buf.close();
	    	}catch (IOException e){
	    		Log.d(TAG, "Reminders file not created :"+e);
	    	}
	    }
	    view.setText("Event\n"+worde+"Attendee\n"+worda+"Calendar\n"+wordc+"Reminder\n"+wordr);
	    setContentView(view);
	}
	public enum MethodType {
        DEFAULT(0),
        ALERT(1),
        EMAIL(2),
        SMS(3),
        ALARM(4);

        int val;

        MethodType(int val) {
            this.val = val;
        }

        public static MethodType fromVal(int val) {
            for (MethodType methodType : values()) {
                if (methodType.val == val) {
                    return methodType;
                }
            }
            return DEFAULT;
        }
    }
}
