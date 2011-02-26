package lu.syn2cat.hackerspace;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lu.syn2cat.hackerspace.events.Event;
import lu.syn2cat.hackerspace.events.FeedParser;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EventsActivity extends Activity {
	TableLayout tblEvents = null;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        
        tblEvents = (TableLayout) findViewById(R.id.tblEvents);

        
        try {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DATE, -14);
            
            String mwLimiter = now.get(Calendar.YEAR) + "-2D" + (now.get(Calendar.MONTH) + 1) + "-2D" + now.get(Calendar.DATE);
        	
			String strEvents = HttpHelper.getInstance().downloadText("http://www.hackerspace.lu/wiki/Special:Ask/-5B-5BCategory:Event-7C-7CMeeting-7C-7CWorkshop-5D-5D-20-5B-5BStartDate::-3E" + mwLimiter + "-5D-5D-0A-5B-5BIs-20External::no-5D-5D/-3FStartDate/-3FEndDate/-3FHas-20location/order%3DDESC/sort%3DStartDate/limit%3D25/format%3Djson");
			FeedParser feedParser = new FeedParser();
			
			List<Event> events = feedParser.parse(strEvents);
			
			Iterator<Event> it = events.iterator();
			while (it.hasNext())
			{
				Event event = it.next();
				
	        	TableRow tblRow = new TableRow(getApplicationContext());
	        	TextView text = new TextView(getApplicationContext());
	        	text.setText(event.getLabel() + "\n" + event.getLocation() + "\n" + event.getFrom() + " - " + event.getTo() + "\n");
	        	tblRow.addView(text);

	        	if (isEventToday(event.getFrom()))
	        	{
	        		text.setTextColor(Color.BLACK);
	        		tblRow.setBackgroundColor(Color.GREEN);
	        	}
	        	
	        	tblEvents.addView(tblRow);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean isEventToday(String date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
			Date da1 = df.parse(date);
			
			Calendar now = Calendar.getInstance();
			Date da2 = now.getTime();
			
	        if (da1.getDate() == da2.getDate() &&
	        		da1.getMonth() == da2.getMonth() &&
	        		da1.getYear() == da2.getYear())
	        	return true;
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}

        return false;
      }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent=new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
            finish();
        }
        
        return super.onKeyDown(keyCode, event);
    }

}
