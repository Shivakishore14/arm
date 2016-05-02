package com.test.root.arm;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by root on 10/3/16.
 */
public class Selection extends AppCompatActivity{

    ListView list;
    CustomAdapter adapter;
    Button  bclass, bhour;
    //String[] stud = Workspace.stud;
    public Selection CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    public int[] clri = new int[50], clr ={R.drawable.present, R.drawable.absent, R.drawable.late, R.drawable.od}; //{0xFF0DFF00, 0xFFFF3C00, 0xFFFF9500, 0xFF00A6FF};
    String[] pai = {"present", "absent", "late", "onDuty"} ;
    String s = "",key = "",value,cHour="hour",cClass="class";
    int btn ;
    float lastX = (float) 0.0;
    String jsonStr ;
    static ArrayList<String> fclass = new ArrayList<String>(),stud  = new ArrayList<String>(),pa  = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);
        BackTask btask= new BackTask();
        btask.execute("null");
        CustomListView = this;


        list = (ListView) findViewById(R.id.list);  // List defined in XML ( See Below )



        Intent i = this.getIntent();
        Bundle b = i.getExtras();
        jsonStr =(String) b.get("json");
        jsonStr = jsonStr.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf("}") + 1);


        GetDetails g = new GetDetails();
        g.execute("null");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00796B"));
        }


        bclass = (Button) findViewById(R.id.btnclass);
        bhour = (Button) findViewById(R.id.btnhour);

        bclass.setText(cClass);
        bhour.setText(cHour);
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
    }

    private void setListData() {

        for (int i = 0; i < stud.size(); i++) {

            final ListModel sched = new ListModel();

            sched.setName(stud.get(i));
            sched.setImage("image" + i);
            pa.set(i, pai[clri[i]]);
            sched.setPra(pa.get(i));
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
        pa.set(index,pai[clri[index]]);
        tvpa.setText(pa.get(index));
    }
    public void afterimport(){
        for(int index = 0; index < stud.size(); index++) {
            View v = list.getChildAt(index -
                    list.getFirstVisiblePosition());
            TextView tvpa = (TextView) v.findViewById(R.id.tvpa);
            tvpa.setBackgroundResource(clr[clri[index]]);//);
            Log.e("info",pa.get(index));
            pa.set(index, pai[clri[index]]);
            tvpa.setText(pa.get(index));
        }
    }

    private void setpa() {
        for (int i = 0; i < stud.size(); i++) {
            //pa[i] = "P";
            clri[i] = 0;
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
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
                for(int i=0;i<fclass.size();i++) {
                    menu.add(0, v.getId(), 0, fclass.get(i));
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
    private class BackTask extends AsyncTask<String, String, String> {

        private String result = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                String data = URLEncoder.encode("class", "UTF-8") + "=" +
                        URLEncoder.encode(key, "UTF-8");
                BufferedReader reader = null;
                try {
                    URL url = new URL(util.ip+"Armweb/workspace");
                    URLConnection con = url.openConnection();
                    con.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(data);
                    writer.flush();
                    //getting response back
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder s = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        s.append(line + "\n");
                    }
                    result = s.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            Log.d("Response BackTask : ", "> " + result);

            if (result != null) {
                try {
                    JSONObject mainjsonObj = new JSONObject(result);
                    JSONArray jsonar = mainjsonObj.getJSONArray("students");

                    // looping through All Contacts
                    for (int i = 0; i < jsonar.length(); i++) {
                        JSONObject c = jsonar.getJSONObject(i);
                        stud.add(c.getString("stud"));
                        pa.add(c.getString("pa"));
                        for(int j=0; j<4 ; j++ )
                            if(pa.get(i).equals(pai[j])){
                                clri[i] = j;

                            }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("json", "not available");
            }

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            setListData();
            Resources res = getResources();
            adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
            list.setAdapter(adapter);
            afterimport();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
            // progress. For example updating ProgessDialog
        }
    }
    private class GetDetails extends AsyncTask<String, String, String> {
        String hour ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... arg) {
            // Creating service handler class instance

            // Making a request to url and getting response


            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject mainjsonObj = new JSONObject(jsonStr);
                    JSONArray jsonObj = mainjsonObj.getJSONArray("json");
                    JSONObject classesstdclass = jsonObj.getJSONObject(0);
                    JSONArray classes = classesstdclass.getJSONArray("classes");
                    JSONObject period = jsonObj.getJSONObject(1);
                    JSONObject currentclass = jsonObj.getJSONObject(2);
                    // looping through All Contacts
                   for (int i = 0; i < classes.length(); i++) {
                       JSONObject c = classes.getJSONObject(i);
                        fclass.add(c.getString("Class"));
                    }
                    cHour = period.getString("Hour");
                    cClass = currentclass.getString("current");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("json", "not available");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            bclass.setText(cClass);
            bhour.setText("Hour:"+cHour);
        }


    }

}