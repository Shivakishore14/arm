package com.test.root.arm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.ResultSet;

/**
 * Created by root on 7/3/16.
 */
public class Login extends Activity{
    Button login;
    EditText uname, pass;
    TextView notification;
    SharedPreferences sp ;
    String username, password;
    Boolean a;
    ProgressDialog progress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = (EditText)findViewById(R.id.etuname);
        pass = (EditText)findViewById(R.id.etpass);
        login = (Button)findViewById(R.id.btlogin);
        notification = (TextView) findViewById(R.id.tvLoginNotification);
        sp  = getApplicationContext().getSharedPreferences("dbinfo", Context.MODE_PRIVATE);
        progress = new ProgressDialog(this);
        progress.setMessage("Loging in...");

// To dismiss the dialog


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00796B"));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = uname.getText().toString();
                password = pass.getText().toString();
                if (username.length() != 0) {
                    try {
                        BackTask st = new BackTask();
                        st.execute();
                        notification.setText("Logging in as :" + username);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                   } else {
                    notification.setText("Enter valid username");
                }
            }
        });

    }

    private class BackTask extends AsyncTask<String, String, String> {

        private String result = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                String data = URLEncoder.encode("name", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
                BufferedReader reader = null;
                try {
                    URL url = new URL(util.ip+"z.php");
                    URLConnection con = url.openConnection();
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(data);
                    writer.flush();
                    //getting response back
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder s = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        s.append(line + "\n");
                    }
                    result = s.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            notification.setText(result);
            result = result.trim();
            if (!result.equals("NOT AUTHENTICATED")){
                Intent i = new Intent(Login.this,Selection.class);
                startActivity(i);
            }
        }

        @Override
        protected void onPreExecute() {
            progress.show();
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // progress. For example updating ProgessDialog
        }
    }
}

