package lu.syn2cat.hackerspace.events;

import org.json.JSONException;
import org.json.JSONObject;


public class Event {
/*        <swivt:Subject rdf:about="&wiki;Chaos_Communication_Camp_2011">
                <rdfs:label>Chaos Communication Camp 2011</rdfs:label>
                <swivt:page rdf:resource="&wikiurl;Chaos_Communication_Camp_2011"/>
                <rdfs:isDefinedBy rdf:resource="&wikiurl;Special:ExportRDF/Chaos_Communication_Camp_2011"/>
                <swivt:wikiNamespace rdf:datatype="http://www.w3.org/2001/XMLSchema#integer">0</swivt:wikiNamespace>
                <property:StartDate rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime">2011-08-10T00:00:00</property:StartDate>
                <property:EndDate rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime">2011-08-14T00:00:00</property:EndDate>
        </swivt:Subject>
 */
	
	String label = "";
	String url = "";
	String location = "";
	String from = "";
	String to = "";
	String organizer = "";
    
    
    public Event(JSONObject post)
    {
		try {
			label = post.getString("label");
		} catch (JSONException e) {}
    	
		try {
			url = post.getString("url");
		} catch (JSONException e) {}
        
		try {
			location = post.getJSONArray("has_location").getString(0);
		} catch (JSONException e) {}
		
		try {
			organizer = post.getJSONArray("has_organizer").getString(0).replaceAll("Organisation:", "");
		} catch (JSONException e) {}
		
		try {
			from = post.getJSONArray("startdate").getString(0);
		} catch (JSONException e) {}
		
		try {
			to = post.getJSONArray("enddate").getString(0);
		} catch (JSONException e) {}
    }

    public String getLabel()
    {
    	return this.label;
    }
    
    public String getUrl()
    {
    	return this.url;
    }
    
    public String getLocation()
    {
    	return this.location;
    }
    
    public String getFrom()
    {
    	return this.from;
    }
    
    public String getTo()
    {
    	return this.to;
    }
    
    public String getOrganizer()
    {
    	return this.organizer;
    }
}
