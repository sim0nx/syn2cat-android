package lu.syn2cat.hackerspace.events;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class FeedParser {
	
    public List<Event> parse(String feed) {
        List<Event> events = new ArrayList<Event>();
        
        try
        {
            JSONObject json = new JSONObject(feed);
            JSONArray items = json.getJSONArray("items");

            for (int i=0;i<items.length();++i)
            {
                JSONObject post = items.getJSONObject(i);
                
                events.add(new Event(post.getString("label"),
                		post.getString("uri"),
                		post.getString("has_location"),
                		post.getString("startdate"),
                		post.getString("enddate")));
            }

        }
        catch (Exception e)
        {
        }

        return events;
    }

}
