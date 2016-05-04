package com.test.root.arm;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 10/3/16.
 */
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {


    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ListModel tempValues = null;
    int i = 0;


    public CustomAdapter(Activity a, ArrayList d, Resources resLocal) {

        activity = a;
        data = d;
        res = resLocal;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public int getCount() {

        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {

        public TextView text , pa;
        public Button btn;
        public TextView textWide;
        public ImageView image;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            vi = inflater.inflate(R.layout.customitem, null);


            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.tvci);
            holder.pa = (TextView) vi.findViewById(R.id.tvpa);
            holder.image = (ImageView) vi.findViewById(R.id.ivci);

            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.text.setText("No Data");

        } else {
            tempValues = null;
            tempValues = (ListModel) data.get(position);

            holder.text.setText(tempValues.getName());
            holder.pa.setText(tempValues.getPra());
            holder.image.setImageResource(res.getIdentifier("com.androidexample.customlistview:drawable/" + tempValues.getImage(), null, null));

            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /*********
     * Called when Item click in ListView
     ************/
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            Selection sct = (Selection) activity;

            /****  Call  onItemClick Method inside Selection Class ( See Below )****/

            sct.onItemClick(mPosition);
        }
    }
    public void clearData() {
        // clear the data
        data.clear();
    }
}
