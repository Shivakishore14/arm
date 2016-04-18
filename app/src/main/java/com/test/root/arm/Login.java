package com.test.root.arm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by root on 7/3/16.
 */
public class Login extends Activity{
    Button login;
    EditText uname, pass;
    SharedPreferences sp ;
    String dbname,dbpass,dbip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = (EditText)findViewById(R.id.etuname);
        pass = (EditText)findViewById(R.id.etpass);
        login = (Button)findViewById(R.id.btlogin);
        sp  = getApplicationContext(). getSharedPreferences("dbinfo", Context.MODE_PRIVATE);
        dbname = sp.getString("dbuname",null);
        dbpass = sp.getString("dbpass",null);
        dbip = sp.getString("dbip",null);
        if(dbname == null || dbpass == null || dbip == null){
            Intent i = new Intent(Login.this,Settings.class);
            startActivity(i);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Selection.class);
                startActivity(i);
            }
        });

    }
}
