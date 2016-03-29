package com.test.root.arm;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 10/3/16.
 */
public class Selection extends Workspace {

    ListView list;
    CustomAdapter adapter;
    Button btest, bclass;
    public Selection CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    public int[] clri = new int[stud.length], clr = {0xFF0DFF00, 0xFFFF3C00, 0xFFFF9500, 0xFF00A6FF};
    String[] pa = new String[stud.length], pai = {"Present", "Absent", "Late", "OnDuty"} , fclass={"cse_a","cse_b","cse c","eee","IT"};
    String s = "";

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


        btest = (Button) findViewById(R.id.btntest);
        bclass = (Button) findViewById(R.id.btnclass);
        registerForContextMenu(bclass);
        btest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Selection.this, Preview.class);
                i.putExtra("clri", clri);
                startActivity(i);
                //testfn();
            }
        });
        bclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });

    }

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

        Toast.makeText(CustomListView, "" + tempValues.getName() + " Image:" + tempValues.getImage() + " Url:" + tempValues.getPra(), Toast.LENGTH_LONG).show();
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
        ll.setBackgroundColor(clr[clri[index]]);
        pa[index] = pai[clri[index]];
        tvpa.setText(pa[index]);


    }

    private void setpa() {
        for (int i = 0; i < stud.length; i++) {
            pa[i] = "P";
            clri[i] = 0;
        }
    }

    public void testfn() {
        for (int i = 0; i < stud.length; i++)
            s += String.valueOf(clri[i]);
        btest.setText(s);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main, menu);
        menu.setHeaderTitle("Select The Class");
        for(int i=0;i<fclass.length;i++) {
            menu.add(0, v.getId(), 0, fclass[i]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        bclass.setText(item.getTitle());
        return true;
    }

}