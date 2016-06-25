package com.test.root.arm;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 10/3/16.
 */
public class Selection extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView list;
    CustomAdapter adapter;
    Button  bclass, bhour;
    //String[] stud = Workspace.stud;
    public Selection CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>(),noValueListView = new ArrayList<ListModel>();
    public int[] clri = new int[50], clr ={R.drawable.present, R.drawable.absent, R.drawable.late, R.drawable.od}; //{0xFF0DFF00, 0xFFFF3C00, 0xFFFF9500, 0xFF00A6FF};
    String[] pai = {"present", "absent", "late", "onDuty"} ;
    String s = "",key = "",value,cHour="hour",cClass="class";
    int btn ;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    float lastX = (float) 0.0;
    TextView importLast;
    String jsonStr , cookie ;
    static ArrayList<String> fclass = new ArrayList<String>(),stud  = new ArrayList<String>(),pa  = new ArrayList<String>();
    SharedPreferences pref ;
    java.net.CookieManager msCookieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);
        BackTask btask= new BackTask();
        btask.execute("");
        CustomListView = this;
        pref = getSharedPreferences("session", MODE_PRIVATE);
        cookie = pref.getString("cookie", null);

        msCookieManager = new java.net.CookieManager();
        List<String> cookiesHeader = Arrays.asList(cookie.split("::"));
        if(cookiesHeader != null)
        {
            for (String cookie1 : cookiesHeader)
            {
                msCookieManager.getCookieStore().add(null,HttpCookie.parse(cookie1).get(0));
            }
        }

        list = (ListView) findViewById(R.id.list);  // List defined in XML ( See Below )
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(this);



        Intent i = this.getIntent();
        Bundle b = i.getExtras();
        jsonStr =(String) b.get("json");
        jsonStr = jsonStr.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf("}") + 1);


        GetDetails g = new GetDetails();
        g.execute("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00796B"));
        }


        bclass = (Button) findViewById(R.id.btnclass);
        bhour = (Button) findViewById(R.id.btnhour);
        importLast = (TextView) findViewById(R.id.tvimport);

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
                Upload up = new Upload();
                up.execute();
                Intent i = new Intent(Selection.this, Tabpreview.class);
                i.putExtra("clri", clri);
                String[] studarray = new String[stud.size()];
                studarray = stud.toArray(studarray);
                i.putExtra("stud",studarray);
                startActivity(i);
            }
        });

        importLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackTask btask= new BackTask();
                btask.execute("cse","5");
            }
        });

    }

    private void setListData() {

        CustomListViewValuesArr.clear();
        for (int i = 0; i < stud.size(); i++) {

            final ListModel sched = new ListModel();
            Log.e("value : ",stud.get(i));
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

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        // Simulate a long running activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //updateCountries();
            }
        }, 5000);
    }

    private class BackTask extends AsyncTask<String, String, String> {

        private String result = null;

        @Override
        protected String doInBackground(String... params) {
            try {

                key = params[0];
                String data ;
                if (params.length > 1) {
                    String key1 = String.valueOf(Integer.parseInt(params[1]) - 1);
                    data = URLEncoder.encode("class", "UTF-8") + "=" +
                            URLEncoder.encode(key, "UTF-8") + "&" +
                            URLEncoder.encode("hour", "UTF-8") + "=" +
                            URLEncoder.encode(key1, "UTF-8");
                            Log.i("working","if case working");
                }else{
                    Log.i("oooops","else case working");
                    data = URLEncoder.encode("class", "UTF-8") + "=" +
                            URLEncoder.encode(key, "UTF-8");
                }
                BufferedReader reader = null;
                try {
                    URL url = new URL(util.ip+"Armweb/workspace");
                    URLConnection con = url.openConnection();
                    con.setDoOutput(true);
                    //////

                  //  msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    if(msCookieManager.getCookieStore().getCookies().size() > 0)
                    {
                        Log.e("eafdasfadsf","working");
                        //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                        con.setRequestProperty("Cookie", TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
                    }

                    ///////
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
                    stud.clear();
                    pa.clear();

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

            CustomListViewValuesArr.clear();
            setListData();
            Resources res1 = getResources();
            adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res1){
                @Override
                public View getView(int index, View convertView,ViewGroup parent) {
                    View v =super.getView(index, convertView, parent);
                    TextView tvpa = (TextView) v.findViewById(R.id.tvpa);
                    tvpa.setBackgroundResource(clr[clri[index]]);//);
                    Log.e("info",pa.get(index));
                    pa.set(index, pai[clri[index]]);
                    tvpa.setText(pa.get(index));
                    return v;
                }
            };
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
            //afterimport();
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
    private class Upload extends AsyncTask<String, String, String> {
        String data,serverResult;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... arg){
            try {
                JSONArray ja = new JSONArray(), ja1 = new JSONArray();
                JSONObject mainObj = new JSONObject();
                ja = getJsonArrayFromStringArray(stud, pa);
                mainObj.put("students", ja);
                String result = mainObj.toString();
                if(result.length()!=0){
                    data  = URLEncoder.encode("students", "UTF-8") + "=" +
                            URLEncoder.encode(result, "UTF-8") + "&" +
                            URLEncoder.encode("class", "UTF-8") + "=" +
                            URLEncoder.encode(cClass, "UTF-8")+"&"+
                            URLEncoder.encode("hour", "UTF-8") + "=" +
                            URLEncoder.encode(cHour, "UTF-8");
                    URL url = new URL(util.ip+"/Armweb/StudentsUpdate");
                    URLConnection con = url.openConnection();
                    con.setDoOutput(true);
                    ///
                    if(msCookieManager.getCookieStore().getCookies().size() > 0)
                    {
                        Log.e("eafdasfadsf","working");
                        con.setRequestProperty("Cookie", TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
                    }

                    ///
                    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                    writer.write(data);
                    writer.flush();
                    //getting response back
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder s = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        s.append(line + "\n");
                    }
                    serverResult = s.toString();
                }
                return serverResult;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(),result,Toast.LENGTH_SHORT).show();
            }

    }
    private JSONArray getJsonArrayFromStringArray(ArrayList<String> a,ArrayList<String> b)throws Exception{
        JSONObject[]obj = new JSONObject[a.size()];
        JSONArray ja = new JSONArray();
        for(int i = 0;i< a.size();i++){
            obj[i] = new JSONObject();
            obj[i].put("pa",b.get(i));
            obj[i].put("stud",a.get(i));
            ja.put(obj[i]);
        }
        return ja;
    }
}