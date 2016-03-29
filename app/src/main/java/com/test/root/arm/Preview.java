package com.test.root.arm;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 19/3/16.
 */
public class Preview extends Selection {

        ListView listabsent,listod,listpresent,listlate;
        CustomAdapter adapterabsent,adapterod,adapterlate,adapterpresent;
        public  Preview preview = null;
        public ArrayList<ListModel> Arrayabsent = new ArrayList<ListModel>(),Arrayod = new ArrayList<ListModel>(),Arraylate = new ArrayList<ListModel>(),Arraypresent = new ArrayList<ListModel>();
       int[] clri = new int[stud.length] , clr = {0xFF0DFF00,0xFFFF3C00,0xFFFF9500,0xFF00A6FF};
       // String[] pa=new String[stud.length] , pai = {"Present", "Absent","Late","OnDuty"};



        @Override
        protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.preview);
            preview = this;
            clri = getIntent().getIntArrayExtra("clri");
            setListData();

            Resources res =getResources();
            listabsent= ( ListView )findViewById( R.id.lvabsent );  // List defined in XML ( See Below )
            listlate=(ListView)findViewById(R.id.lvlate);
            listod=(ListView)findViewById(R.id.lvOD);
            listpresent=(ListView)findViewById(R.id.lvpresent);

            adapterabsent = new CustomAdapter( preview, Arrayabsent ,res );
            adapterlate = new CustomAdapter( preview, Arraylate ,res );
            adapterod = new CustomAdapter( preview, Arrayod ,res );
            adapterpresent = new CustomAdapter( preview, Arraypresent ,res );

           listabsent.setAdapter( adapterabsent );
            listpresent.setAdapter(adapterpresent);
            listod.setAdapter(adapterod);
            listlate.setAdapter(adapterlate);


        }



    public void setListData() {

        for (int i = 0; i < stud.length; i++) {

            final ListModel s = new ListModel();

            s.setName(stud[i]);
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


    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        ListModel tempValues = ( ListModel ) Arraypresent.get(mPosition);
        tempValues.setName("sk");

       // Toast.makeText(CustomListView, "" + tempValues.getName() + " Image:" + tempValues.getImage() + " Url:" + tempValues.getPra(), Toast.LENGTH_LONG).show();
    }


}