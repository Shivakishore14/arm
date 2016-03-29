package com.test.root.arm;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by root on 10/3/16.
 */
public class Workspace1 extends Workspace {
    ListView lvstud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workspace1);
        lvstud=(ListView)findViewById(R.id.lvstud);
        ArrayAdapter<String> studadapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,R.id.tvstud,stud);
        lvstud.setAdapter(studadapter);
    }
}
