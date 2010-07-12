package org.trypill.simon;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
    Button btnSignin;
    String loginmessage = null;
    private SharedPreferences mPreferences;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

		((TextView) findViewById(R.id.txt_username)).setText(mPreferences
				.getString("username", ""));
		((TextView) findViewById(R.id.txt_password)).setText(mPreferences
				.getString("password", ""));

		btnSignin = (Button) findViewById(R.id.btn_sign_in);
		btnSignin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String sweepCode = doLogin();
					
                    SharedPreferences.Editor editor=mPreferences.edit();
                    editor.putString("username", ((TextView) findViewById(R.id.txt_username)).getText().toString());
                    editor.putString("password", ((TextView) findViewById(R.id.txt_password)).getText().toString());
                    editor.commit();
					
					String returnMsg = HttpHelper.getInstance().downloadText(sweepCode);
					
					System.err.println(returnMsg);
					
					Intent intent=new Intent(getApplicationContext(), Main.class);
	                startActivity(intent);
	                finish();
					
				} catch (InvalidLogin il)
				{
					displayError("Invalid Login");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
    }
	
    private void displayError(String msg)
    {
    	new AlertDialog.Builder(this)
        .setTitle("Error")
        .setMessage(msg)
        .setPositiveButton("OK", null)
        .show();
    }
	
    private String doLogin() throws UnknownHostException, IOException, InvalidLogin
    {
		URL url = new URL("https://www.hackerspace.lu/alarm.php");
		HttpURLConnection cnx = (HttpURLConnection)url.openConnection();

		try {
			cnx.setDoOutput( true );
			cnx.setRequestMethod("POST");
			
			String username = ((TextView) findViewById(R.id.txt_username)).getText().toString();
			String password = ((TextView) findViewById(R.id.txt_password)).getText().toString();
			
			OutputStreamWriter os = new OutputStreamWriter(cnx.getOutputStream());
			os.write("username=" + username + "&password=" + password);
			os.flush();
			os.close();
			

			String header = cnx.getHeaderField("location");
			
			cnx.disconnect();
			
			if (header != null)
			{				
				return header;
			}
			
			throw new InvalidLogin();
		} catch (UnknownHostException uh) {
			throw uh;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException("Error connecting");
		}
    }	
}
