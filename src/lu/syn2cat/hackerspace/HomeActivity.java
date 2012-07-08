package lu.syn2cat.hackerspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HomeActivity extends Activity {
	WebView webView1 = null;
	Button btnToggleAlarm = null;
	ProgressBar pbProgressbar = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        pbProgressbar = (ProgressBar) findViewById(R.id.pbhome_progressbar);
        pbProgressbar.setVisibility(View.INVISIBLE);
        
        webView1 = (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView1.setVisibility(View.INVISIBLE);
        
        webView1.setWebChromeClient(new WebChromeClient() {
        	@Override
        	public void onProgressChanged(WebView view, int progress) {
        	  super.onProgressChanged(view, progress);
        	  pbProgressbar.setProgress(progress);
        	  
        	  if (progress == 100)
        	  {
        		  pbProgressbar.setVisibility(View.INVISIBLE);
        		  webView1.setVisibility(View.VISIBLE);
        	  }else{
    			  pbProgressbar.setVisibility(View.VISIBLE);
    			  webView1.setVisibility(View.INVISIBLE);
    		  }
        	  
        	  System.out.println(progress);
          }
        });

        final Activity activity = this;
        webView1.setWebViewClient(new WebViewClient() {
        	   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        	     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        	   }
        	 });
        
        
        webView1.loadUrl("https://status.syn2cat.lu");
        
        btnToggleAlarm = (Button) findViewById(R.id.btnHome_ToggleAlarm);
            
        btnToggleAlarm.setOnClickListener(new Button.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
			}
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
}
