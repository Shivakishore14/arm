package com.test.root.arm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by root on 7/3/16.
 */
public class Workspace extends Activity {
    String s[]={"sken","ksihore","breakfree","closed","matt"},s1[]={"sken1","ksihore1","breakfree1","closed1","matt1"};
    public String stud[]={"sken","ksihore","breakfree","stud2","stud3","kasda","asdasd","stud22","stud4"};
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workspace);
        lv=(ListView)findViewById(R.id.lvstud);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, s);
        ArrayAdapter<String> adptr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,s);
        lv.setAdapter(adptr);


    }
}
