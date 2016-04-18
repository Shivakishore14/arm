package com.test.root.arm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by root on 19/4/16.
 */
public class Settings extends Activity{
    EditText uname,pass,ip;
    Button save;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        uname = (EditText)findViewById(R.id.etdbuname);
        pass = (EditText) findViewById(R.id.etdbpass);
        ip = (EditText) findViewById(R.id.etdbip);
        save = (Button) findViewById(R.id.btnsave);
        sp = getSharedPreferences("dbinfo", Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00796B"));
        }

        final SharedPreferences.Editor editor = sp.edit();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("dbuname",uname.getText().toString());
                editor.putString("dbpass",pass.getText().toString());
                editor.putString("dbip",ip.getText().toString());
                editor.commit();

            }
        });

    }
}
