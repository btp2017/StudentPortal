package comc.android.vishal.studentportal;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class NoticeBoardActivity extends ListActivity {
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayList<String> respectiveUrls=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private CircleProgressBar progressBar;
    private ListView listView;
//    EditText et = (EditText) findViewById(R.id.testview);
    boolean doneFilling;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        doneFilling = false;
       // listItems.add("test");
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("downloads");
        progressBar = (CircleProgressBar) findViewById(R.id.progressBar2);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                new DownloadFileFromURL(listItems.get(position)).execute(respectiveUrls.get(position));
            }
        });
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        new Wait(dataSnapshot).execute();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }


    public void fillArrays(DataSnapshot snapshot){
        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            listItems.add(postSnapshot.getKey());
            respectiveUrls.add((String) postSnapshot.getValue());
        }
        doneFilling = true;
    }

    private class Wait extends AsyncTask<Void, Void, Void> {
        DataSnapshot snapshot;

        public Wait(DataSnapshot snapshot){
            this.snapshot = snapshot;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... param) {
            fillArrays(snapshot);
            while (!doneFilling);

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            updateView();
        }
    }


    public void updateView() {
      //  listView.invalidate();
        View v = (View) progressBar.getParent();
        //v.setVisibility(View.GONE);

         progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        //adapter.notifyDataSetChanged();
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
        //Log.i("HEREEEEEEEEEEEEE",Integer.toString(listItems.size()));
        //listView.setAdapter(adapter);

    }




        private ProgressDialog pDialog;
        public static final int progress_bar_type = 0;

        // File url to download




        /**
         * Showing Dialog
         * */

        @Override
        protected Dialog onCreateDialog(int id) {
            switch (id) {
                case progress_bar_type: // we set this to 0
                    pDialog = new ProgressDialog(this);
                    pDialog.setMessage("Downloading file. Please wait...");
                    pDialog.setIndeterminate(false);
                    pDialog.setMax(100);
                    pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pDialog.setCancelable(true);
                    pDialog.show();
                    return pDialog;
                default:
                    return null;
            }
        }


       private class DownloadFileFromURL extends AsyncTask<String, String, String> {
            String fileName;
            /**
             * Before starting background thread Show Progress Bar Dialog
             * */

            DownloadFileFromURL(String fileName){
                this.fileName = fileName;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(progress_bar_type);
            }

            /**
             * Downloading file in background thread
             * */
            @Override
            protected String doInBackground(String... f_url) {
                int count;
                try {
                    URL url = new URL(f_url[0]);
                    URLConnection conection = url.openConnection();
                    conection.connect();

                    // this will be useful so that you can show a tipical 0-100%
                    // progress bar
                    int lenghtOfFile = conection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream(),
                            8192);

                    // Output stream
                    OutputStream output = new FileOutputStream(Environment
                            .getExternalStorageDirectory().toString()
                            + "/" + fileName);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                return null;
            }

            /**
             * Updating progress bar
             * */
            protected void onProgressUpdate(String... progress) {
                // setting progress percentage
                pDialog.setProgress(Integer.parseInt(progress[0]));
            }

            /**
             * After completing background task Dismiss the progress dialog
             * **/
            @Override
            protected void onPostExecute(String file_url) {
                // dismiss the dialog after the file was downloaded
                dismissDialog(progress_bar_type);

            }

        }
    }






