package pk.house.pkhouse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import pk.house.pkhouse.adapter.LazyAdapter;
import pk.house.pkhouse.adapter.SingleImageLazyLoad;

public class SinglePropertyImage extends AppCompatActivity {


    ListView listViewShowProperties;
    private ProgressBar progressBar;

    final String TAG = "ShowLiisActvity";

    ListView list;
    LazyAdapter adapter;
    SingleImageLazyLoad singleAdapter;
    ArrayList<HashMap<String, String>> singlePropertyImageList;

    String pID;

    GridView gridView;
    TextView pTitle;
    String propertyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_property_image);


        init();
        showListofProperties();

    }//end of OnCreate


    public void init(){
        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        Intent i = getIntent();
        pID = i.getExtras().getString("ID", null);
        propertyTitle = i.getExtras().getString("TITLE", null);
        singlePropertyImageList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);
        pTitle = (TextView) findViewById(R.id.sinlge_image_dialog_title_text) ;
        pTitle.setText(propertyTitle);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(SinglePropertyImage.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle(R.string.tv_pk_estate);

        list=(ListView)findViewById(R.id.listView_show_properties);

        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
    }//endo of init


    public void showListofProperties(){

        new GetContacts().execute();

    }//end of showListOfProperties


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


                // Making a request to url and getting response
                String url = "http://www.pk.estate/app_webservices/get_properties_images.php?propertyid="+pID;
                Log.e("TAG", "URL  @@ " + url);
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        Log.e("TAG", "RESULT 1" + jsonObj);



                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("property_images");

                        Log.e("TAG", "RESULT 2" + contacts);

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String Imageurl = c.getString("name");

                            final String staticURL = "http://www.pk.estate/frontend/propertyimages/";

                            String imageURL = staticURL+Imageurl;

                            Log.e("TAG", "URL 123 " + " TEST TEST ");
                            Log.e("TAG", "URL 123 " + imageURL);
                            // tmp hash map for single contact
                            HashMap<String, String> contact = new HashMap<>();
                            contact.put("imageurl", imageURL);

                            singlePropertyImageList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               /* Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();*/

                                Toast.makeText(SinglePropertyImage.this, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            singleAdapter = new SingleImageLazyLoad(SinglePropertyImage.this, singlePropertyImageList);
            gridView.setAdapter(singleAdapter);
            progressBar.setVisibility(View.GONE);


        }
    }

}
