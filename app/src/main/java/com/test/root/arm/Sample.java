package com.test.root.arm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

public class Sample extends Activity {

    private static final String url = "jdbc:mysql://192.168.1.10:3306/storedb";
    private static final String user = "test";
    private static final String pass = "test";
    public TextView tv;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        testDB();
        BackTask st=new BackTask();
        st.execute("none");

    }

    public void testDB() {
        tv = (TextView)this.findViewById(R.id.tvtest);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


        }

    }
    private class BackTask extends AsyncTask<String, String, String> {

        private String result;

        @Override
        protected String doInBackground(String... params) {
            //start
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);

                result = "Database connection success\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from test");
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                    result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                    result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                }

                String sql = "insert into test values(5,'skishore','pass','kcom',22)";
                st.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
                result = e.toString();
            }

            //end
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
        tv.setText(result);
        }

        @Override
        protected void onPreExecute() {
            tv.setText("Updating");

        }

        @Override
        protected void onProgressUpdate(String... text) {
               // progress. For example updating ProgessDialog
        }
    }
}