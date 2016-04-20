package com.test.root.arm;

import android.app.Activity;
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

import java.sql.ResultSet;

/**
 * Created by root on 7/3/16.
 */
public class Login extends Activity{
    Button login;
    EditText uname, pass;
    TextView notification;
    SharedPreferences sp ;
    String dbname,dbpass,dbip;
    Boolean a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = (EditText)findViewById(R.id.etuname);
        pass = (EditText)findViewById(R.id.etpass);
        login = (Button)findViewById(R.id.btlogin);
        notification = (TextView) findViewById(R.id.tvLoginNotification);
        sp  = getApplicationContext().getSharedPreferences("dbinfo", Context.MODE_PRIVATE);
        dbname = sp.getString("dbuname", null);
        dbpass = sp.getString("dbpass",null);
        dbip = sp.getString("dbip",null);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00796B"));
        }

        if(dbname == null || dbpass == null || dbip == null){
            Intent i = new Intent(Login.this,Settings.class);
            startActivity(i);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uname.getText().toString();
                String password = pass.getText().toString();
                if (username.length() != 0) {
                    notification.setText("Logging in as :" + username);
                    BackTask st = new BackTask();
                    st.execute("select username from login where username like '" + username + "' and password like '" + password + "'");
                } else {
                    notification.setText("Enter valid username");
                }
            }
        });

    }
    private class BackTask extends AsyncTask<String, String, String> {

        private String result;

        @Override
        protected String doInBackground(String... params) {
            //start
            util obj = new util("192.168.1.10:3306","test","test");
            try {
                ResultSet a = util.getResult("armtest",params[0]);
                if (a.next()){
                    return a.getString("username");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String name = uname.getText().toString();
            if(result == null || name == null){
                //Toast.makeText(getBaseContext(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                notification.setText("Invalid Username or Password");
            }else if (result.equals(name)){
                Intent i = new Intent(Login.this,Selection.class);
                startActivity(i);
            }else {
                notification.setText("Please try again");
            }
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {
            // progress. For example updating ProgessDialog
        }
    }
}
