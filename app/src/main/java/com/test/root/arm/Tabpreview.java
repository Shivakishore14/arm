package com.test.root.arm;

import android.content.res.Resources;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 15/4/16.
 */
public class Tabpreview extends  AppCompatActivity{
    TabHost tabHost;
    ListView listabsent,listod,listpresent,listlate;
    CustomAdapter adapterabsent,adapterod,adapterlate,adapterpresent;
    public  Tabpreview preview = null;
    public ArrayList<ListModel> Arrayabsent = new ArrayList<ListModel>(),Arrayod = new ArrayList<ListModel>(),Arraylate = new ArrayList<ListModel>(),Arraypresent = new ArrayList<ListModel>();
    int[] clri = new int[50] , clr = {0xFF0DFF00,0xFFFF3C00,0xFFFF9500,0xFF00A6FF};
    float x=0,y=0;
    // String[] pa=new String[stud.length] , pai = {"Present", "Absent","Late","OnDuty"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabpreview);
        preview = this;
        clri = getIntent().getIntArrayExtra("clri");
        setListData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00796B"));
        }

        listabsent = ( ListView )findViewById( R.id.lvabsent );  // List defined in XML ( See Below )
        listlate = (ListView)findViewById(R.id.lvlate);
        listod = (ListView)findViewById(R.id.lvOD);
        listpresent = (ListView)findViewById(R.id.lvpresent);
        tabHost = (TabHost)findViewById(R.id.tabHost);

        setList();

        TabHost host = (TabHost)findViewById(R.id.tabHost);

        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Absent");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Late");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Present");
        host.addTab(spec);

        //tab 4
        spec = host.newTabSpec("Tab 4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("O.D");
        host.addTab(spec);
       for (int i=0; i < 4;i++)
           ((TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title)).setTextColor(0xFFFFFFFF);


    }
    private void setList(){
        Resources res = getResources();
        if(!Arrayabsent.isEmpty()) {
            adapterabsent = new CustomAdapter(preview, Arrayabsent, res);
            listabsent.setAdapter(adapterabsent);
        }
        if(!Arraylate.isEmpty()) {
            adapterlate = new CustomAdapter(preview, Arraylate, res);
            listlate.setAdapter(adapterlate);
        }
        if(!Arrayod.isEmpty()) {
            adapterod = new CustomAdapter(preview, Arrayod, res);
            listod.setAdapter(adapterod);
        }
        if(!Arraypresent.isEmpty()) {
            adapterpresent = new CustomAdapter(preview, Arraypresent, res);
            listpresent.setAdapter(adapterpresent);
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!

    }

    public void setListData() {

        for (int i = 0; i < 50; i++) {

            final ListModel s = new ListModel();

            s.setName(Selection.stud.get(i));
            if(clri[i] == 0){
                s.setPra("Present");
                Arraypresent.add(s);
            }else if(clri[i] == 1){
                s.setPra("Absent");
                Arrayabsent.add(s);
            }else if(clri[i] == 2){
                s.setPra("Late");
                Arraylate.add(s);
            }else if(clri[i] == 3){
                s.setPra("OnDuty");
                Arrayod.add(s);
            }


            // CustomListViewValuesArr.
            /******** Take Model Object in ArrayList **********/
            /// CustomListViewValuesArr.add( sched );
        }

    }

    public void onItemClick(int mPosition) {
        ListModel tempValues = ( ListModel ) Arraypresent.get(mPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}