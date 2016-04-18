package com.test.root.arm;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by root on 10/3/16.
 */
public class Selection extends AppCompatActivity{

    ListView list;
    CustomAdapter adapter;
    Button  bclass, bhour;
    String[] stud = Workspace.stud;
    public Selection CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    public int[] clri = new int[stud.length], clr ={R.drawable.present, R.drawable.absent, R.drawable.late, R.drawable.od}; //{0xFF0DFF00, 0xFFFF3C00, 0xFFFF9500, 0xFF00A6FF};
    String[] pa = new String[stud.length], pai = {"Present", "Absent", "Late", "OnDuty"} , fclass={"cse_a","cse_b","cse c","eee","IT"};
    String s = "";
    int btn ;
    float lastX = (float) 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);

        CustomListView = this;

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.list);  // List defined in XML ( See Below )

        adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);

        setpa();


        //btest = (Button) findViewById(R.id.btntest);
        bclass = (Button) findViewById(R.id.btnclass);
        bhour = (Button) findViewById(R.id.btnhour);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        registerForContextMenu(bclass);
        registerForContextMenu(bhour);
        bclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
        bhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Selection.this, Tabpreview.class);
                i.putExtra("clri", clri);
                startActivity(i);
            }
        });
//           getActionBar().setTitle("Hello world App");
       // getSupportActionBar().setTitle("Hello world App");
    }
   /* @Override
    public void setActionBar(String heading) {
        // TODO Auto-generated method stub

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.title_bar_gray)));
        actionBar.setTitle(heading);
        actionBar.show();

    }*/

    private void setListData() {

        for (int i = 0; i < stud.length; i++) {

            final ListModel sched = new ListModel();

            sched.setName(stud[i]);
            sched.setImage("image" + i);
            sched.setPra("Present");
            // CustomListViewValuesArr.
            CustomListViewValuesArr.add(sched);
        }

    }


    public void onItemClick(int mPosition) {
        ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);
        updateView(mPosition);
        tempValues.setName("sk");
        // SHOW ALERT
    }

    private void updateView(int index) {
        View v = list.getChildAt(index -
                list.getFirstVisiblePosition());
        clri[index] += 1;
        if (clri[index] > 3)
            clri[index] %= 4;
        if (v == null)
            return;

        TextView tvpa = (TextView) v.findViewById(R.id.tvpa);
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.llpa);
        int id = getResources().getIdentifier("com.test.root.arm:drawable/"+"od", null, null);
        tvpa.setBackgroundResource(clr[clri[index]]);//);
        pa[index] = pai[clri[index]];
        tvpa.setText(pa[index]);


    }

    private void setpa() {
        for (int i = 0; i < stud.length; i++) {
            pa[i] = "P";
            clri[i] = 0;
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main, menu);
        switch(v.getId()) {
            case R.id.btnhour:
                menu.setHeaderTitle("Select Hour");
                btn = 0;
                for ( int i = 1; i < 9; i++ ) {
                    menu.add(0, v.getId(), 0, String.valueOf(i));
                }
                break;
            case R.id.btnclass:
                menu.setHeaderTitle("Select The Class");
                btn = 1;
                for(int i=0;i<fclass.length;i++) {
                    menu.add(0, v.getId(), 0, fclass[i]);
                }
                break;
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(btn == 0)
            bhour.setText("Hour : "+item.getTitle());
        else
            bclass.setText("Class : "+item.getTitle());
        return true;
    }


}