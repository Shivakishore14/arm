package com.test.root.arm;

import android.app.Activity;
import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = (EditText)findViewById(R.id.etuname);
        pass = (EditText)findViewById(R.id.etpass);
        login = (Button)findViewById(R.id.btlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Selection.class);
                startActivity(i);
            }
        });

    }
}
