package lu.syn2cat.hackerspace;

import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class Syn2catWidgetProvider extends AppWidgetProvider {
	
	public Syn2catWidgetProvider()
	{
		initDefaultSSLVerifier();
	}
	
    private boolean isSpaceOpen() throws UnknownHostException
    {
    	String strAlarm = HttpHelper.getInstance().downloadText("http://open.hackerspace.lu");
    	
    	if (strAlarm.contains("\"open\":true"))
    		return true;
    	
    	return false;
    }
	

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.syn2catwidget);
			
			try
			{
				if (isSpaceOpen())
				{
					views.setImageViewResource(R.id.widgetSpaceStatus, R.drawable.widget_space_open);
				}else{
					views.setImageViewResource(R.id.widgetSpaceStatus, R.drawable.widget_space_closed);
				}
			} catch (UnknownHostException uhe)
			{
				
			}


			// Tell the AppWidgetManager to perform an update on the current App
			// Widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

    private void initDefaultSSLVerifier()
    {
		/** @TODO verify key */
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname,
								SSLSession session) {
							return true;
						}
					});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context
					.getSocketFactory());
		} catch (Exception e) { // should never happen
			e.printStackTrace();
		}
    }
}
