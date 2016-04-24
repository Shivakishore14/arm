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

import java.sql.ResultSet;

/**
 * Created by root on 7/3/16.
 */
public class Login extends Activity{
    Button login;
    EditText uname, pass;
    TextView notification;
    SharedPreferences sp ;

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
                String username = uname.getText().toString();
                String password = pass.getText().toString();
                if (username.length() != 0) {
                    notification.setText("Logging in as :" + username);
                   } else {
                    notification.setText("Enter valid username");
                }
            }
        });

    }
}
