package pk.house.pkhouse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import pk.house.pkhouse.adapter.RecyclicAdapter;
import pk.house.pkhouse.adapter.SingleImageLazyLoad;


public class ViewYourSubmitedProperties extends AppCompatActivity {


    ListView listViewShowProperties;
    private ProgressBar progressBar;


    Dialog singleIamgeListDialog;

    final String TAG = "ShowLiisActvity";

    int imageCode = 0;
    String image_ID;
    String firstID;

    ListView list;
    LazyAdapter adapter;
    SingleImageLazyLoad singleAdapter;
    RecyclicAdapter recylerAdapter;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> singlePropertyImageList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_submited_properties);
        init();

        showListofProperties();
        listviewOnItemClickHander();
        listViewLognClickHandler();

    }

    public void init(){

        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);

        String userIdAsNormal = sharedPreferences.getString("user_id", null);

        String userIdAsFrenchiser = sharedPreferences1.getString("user_id", null);

        if (userIdAsNormal!=null){
            firstID = userIdAsNormal;
        }
        if (userIdAsFrenchiser!=null){
            firstID = userIdAsFrenchiser;
        }



        listViewShowProperties = (ListView) findViewById(R.id.listView_show_properties);
        contactList = new ArrayList<>();
        singlePropertyImageList = new ArrayList<>();

        singleIamgeListDialog = new Dialog(ViewYourSubmitedProperties.this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ViewYourSubmitedProperties.this ,R.color.colorSkyBlue)));
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

            if (imageCode==1){
                // Making a request to url and getting response
                String url = "http://www.pk.estate/app_webservices/get_properties_images.php?propertyid="+image_ID;
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

                            if (!singlePropertyImageList.isEmpty()){
                                singlePropertyImageList.clear();
                            }
                            singlePropertyImageList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();*/
                                Toast.makeText(ViewYourSubmitedProperties.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
            }else if (imageCode==2){//if image code 2 then call delete service

                // Making a request to url and getting response
                String url = "http://www.pk.estate/app_webservices/delete_property.php?property_id="+image_ID;
                // String url = "http://www.pk.estate/app_webservices/get_properties.php";
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);
                Log.e(TAG, jsonStr);

                if (jsonStr != null) {

                    finish();
//                    Toast.makeText(ViewYourSubmitedProperties.this, "Delected Successfully", Toast.LENGTH_SHORT).show();

                    String result = jsonStr.toString();


                    if (result.equals("Deleted successfully")){
                        Toast.makeText(ViewYourSubmitedProperties.this, "Delected Successfully", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "SSSSSSS " + "123");



                    }

                }
                else {
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
            }//end for delete else statment
            else { //starting else for calling all iamges actvity services




                // Making a request to url and getting response
                String url = "http://www.pk.estate/app_webservices/user_listing.php?user_id="+firstID;
               // String url = "http://www.pk.estate/app_webservices/get_properties.php";
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        Log.e("TAG", "RESULT 1" + jsonObj);

                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("property_data");

                        Log.e("TAG", "RESULT 2" + contacts);

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String property_id = c.getString("property_id");
                            String property_title = c.getString("property_title");
                            String price = c.getString("price");
                            String landArea = c.getString("land_area");
                            String city = c.getString("city");
                            String propertyLocation = c.getString("location");
                            String propertyType = c.getString("property_type");
                            String propertystatus = c.getString("status");
                            String description = c.getString("property_description");
                            String rooms = c.getString("rooms");
                            String bathrooms = c.getString("bathrooms");
                            String floors = c.getString("floors");
                            String status_property = c.getString("status_property");
                            String Imageurl = c.getString("images");
                            String dealer_email = c.getString("dealer_email");
                            String dealer_phone = c.getString("dealer_phone");

                            String phone = dealer_phone;

                            final String staticURL = "http://www.pk.estate/frontend/propertyimages/";

                            String imageURL = staticURL + Imageurl;

                            Log.e("TAG", "URL 123 " + imageURL);

                            // tmp hash map for single contact
                            HashMap<String, String> contact = new HashMap<>();

                            contact.put("property_id", property_id);
                            contact.put("imageurl", imageURL);
                            contact.put("property_title", property_title);
                            contact.put("price", price);
                            contact.put("landArea", landArea);
                            contact.put("city", city);
                            contact.put("phone", phone);
                            contact.put("property_type", propertyType);
                            contact.put("status", propertystatus);
                            contact.put("property_description", description);
                            contact.put("location", propertyLocation);
                            contact.put("rooms", rooms);
                            contact.put("bathrooms", bathrooms);
                            contact.put("floors", floors);
                            contact.put("status_property", status_property);

                            contact.put("dealer_email", dealer_email);

                            contactList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();*/

                                Toast.makeText(ViewYourSubmitedProperties.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            adapter=new LazyAdapter(ViewYourSubmitedProperties.this, contactList);
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);


        }
    }//end of getContact async class

    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        super.onDestroy();
    }


    public void listviewOnItemClickHander(){
        listViewShowProperties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                imageCode = 1;

                TextView tv_id = (TextView)view.findViewById(R.id.tv_protperty_id);
                TextView tv_price = (TextView)view.findViewById(R.id.tv_price);
                TextView tv_propertyTitle = (TextView)view.findViewById(R.id.tv_property_title);
                TextView tv_propertyPropertyLandArea = (TextView)view.findViewById(R.id.tv_land_area);
                TextView tv_propertyCity = (TextView)view.findViewById(R.id.tv_location);
                TextView tv_propertyPhone = (TextView)view.findViewById(R.id.tv_contact);
                TextView tv_protperty_type = (TextView)view.findViewById(R.id.tv_protperty_type);
                TextView tv_protperty_status = (TextView)view.findViewById(R.id.tv_protperty_status);
                TextView tv_protperty_description = (TextView)view.findViewById(R.id.tv_protperty_description);
                ImageView image=(ImageView)view.findViewById(R.id.image);

                final BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                final Bitmap imageBitmap = bitmapDrawable.getBitmap();


                final String propertyID = tv_id.getText().toString();
                final String propertyTitle = tv_propertyTitle.getText().toString();
                String propertyPrice = tv_price.getText().toString();
                String propertyLandArea = tv_propertyPropertyLandArea.getText().toString();
                String propertyCity = tv_propertyCity.getText().toString();
                String propertyContact = tv_propertyPhone.getText().toString();
                String propertyType = tv_protperty_type.getText().toString();
                String propertyStatus = tv_protperty_status.getText().toString();
                String propertyDescription = tv_protperty_description.getText().toString();



                Log.e("TAG", "Property Id: " + propertyID);
                Log.e("TAG", "Property TILE: " + propertyTitle);
                Log.e("TAG", "Property Price: " + propertyPrice);
                Log.e("TAG", "Property Area: " + propertyLandArea);
                Log.e("TAG", "Property City: " + propertyCity);
                Log.e("TAG", "Property Contact: " + propertyContact);
                Log.e("TAG", "Property Type: " + propertyType);
                Log.e("TAG", "Property Status: " + propertyStatus);
                Log.e("TAG", "Property Description: " + propertyDescription);

                image_ID = propertyID;


                final Dialog singleListViewItemDialog = new Dialog(ViewYourSubmitedProperties.this);
                singleListViewItemDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                singleListViewItemDialog.setContentView(R.layout.single_list_item_view_dialog);

                TextView dialog_tv_propertyTitle = (TextView)singleListViewItemDialog.findViewById(R.id.dialog_tv_title);
                TextView dialog_tv_price = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_price);
                TextView dialog_tv_propertyPropertyLandArea = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_area);
                TextView dialog_tv_propertyCity = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_location);
                TextView dialog_tv_propertyPhone = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_contact);
                TextView dialog_tv_protperty_type = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_type);
                TextView dialog_tv_protperty_status = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_status);
                TextView dialog_tv_protperty_description = (TextView)singleListViewItemDialog.findViewById(R.id.tv_text_description);
                ImageView dialog_image = (ImageView)singleListViewItemDialog.findViewById(R.id.dialog_property_image);

                // GridView  gridView = (GridView)singleListViewItemDialog.findViewById(R.id.gridView);

                // RecyclerView recylerView = (RecyclerView)singleListViewItemDialog.findViewById(R.id.recycler_view);



                dialog_tv_propertyTitle.setText(propertyTitle);
                dialog_tv_price.setText(propertyPrice);
                dialog_tv_propertyPropertyLandArea.setText(propertyLandArea);
                dialog_tv_propertyCity.setText(propertyCity);
                dialog_tv_propertyPhone.setText(propertyContact);
                dialog_tv_protperty_type.setText(propertyType);
                dialog_tv_protperty_status.setText(propertyStatus);
                dialog_tv_protperty_description.setText(propertyDescription);

                dialog_image.setImageBitmap(imageBitmap);





               /* singleAdapter = new SingleImageLazyLoad(showProperties.this, singlePropertyImageList);
                gridView.setAdapter(singleAdapter);*/

                // new GetContacts().execute();

                singleListViewItemDialog.show();


                dialog_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        singleListViewItemDialog.dismiss();

                        Intent i = new Intent(ViewYourSubmitedProperties.this, SinglePropertyImage.class);
                        i.putExtra("ID", propertyID);
                        i.putExtra("TITLE", propertyTitle);
                        startActivity(i);


                    }
                });//end of dialog_image click



            }

        });
    }//end of listview on itemClick



    public void listViewLognClickHandler(){

        listViewShowProperties.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ViewYourSubmitedProperties.this, "Manage Properties...", Toast.LENGTH_SHORT).show();


                TextView tv_id = (TextView)view.findViewById(R.id.tv_protperty_id);
                TextView tv_price = (TextView)view.findViewById(R.id.tv_price);
                TextView tv_propertyTitle = (TextView)view.findViewById(R.id.tv_property_title);
                TextView tv_propertyPropertyLandArea = (TextView)view.findViewById(R.id.tv_land_area);
                TextView tv_propertyCity = (TextView)view.findViewById(R.id.tv_city);
                TextView tv_propertyLocation = (TextView)view.findViewById(R.id.tv_location);
                TextView tv_propertyPhone = (TextView)view.findViewById(R.id.tv_contact);
                TextView tv_protperty_type = (TextView)view.findViewById(R.id.tv_protperty_type);
                TextView tv_protperty_status = (TextView)view.findViewById(R.id.tv_protperty_status);
                TextView tv_protperty_description = (TextView)view.findViewById(R.id.tv_protperty_description);

                TextView tv_property_rooms = (TextView)view.findViewById(R.id.tv_protperty_rooms);
                TextView tv_property_floors = (TextView)view.findViewById(R.id.tv_protperty_floors);
                TextView tv_property_bathrooms = (TextView)view.findViewById(R.id.tv_protperty_bathrooms);
                TextView tv_property_status_prpoperty = (TextView)view.findViewById(R.id.tv_protperty_status_property);

                ImageView image=(ImageView)view.findViewById(R.id.image);

                final BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                final Bitmap imageBitmap = bitmapDrawable.getBitmap();


                final String propertyID = tv_id.getText().toString();
                final String propertyTitle = tv_propertyTitle.getText().toString();
                final String propertyPrice = tv_price.getText().toString();
                final String propertyLandArea = tv_propertyPropertyLandArea.getText().toString();
                final String propertyCity = tv_propertyCity.getText().toString();
                final String propertyLocation = tv_propertyLocation.getText().toString();
                final String propertyContact = tv_propertyPhone.getText().toString();
                final String propertyType = tv_protperty_type.getText().toString();
                final String propertyStatus = tv_protperty_status.getText().toString();
                final String propertyDescription = tv_protperty_description.getText().toString();
                final String propertyRooms = tv_property_rooms.getText().toString();
                final String propertyFloors = tv_property_floors.getText().toString();
                final String propertyBathrooms = tv_property_bathrooms.getText().toString();
                final String propertyStatus_prpoperty = tv_property_status_prpoperty.getText().toString();

                image_ID = propertyID;
                Log.e("TAG", "ID ID: " + image_ID);


                AlertDialog.Builder alert = new AlertDialog.Builder(ViewYourSubmitedProperties.this);
                alert.setTitle("ALERT!");
                alert.setMessage("Edit Or Delete your property");

                alert.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        //




                        Log.e("TAG", "Property Id: " + propertyID);
                        Log.e("TAG", "Property TILE: " + propertyTitle);
                        Log.e("TAG", "Property Price: " + propertyPrice);
                        Log.e("TAG", "Property description: " + propertyDescription);
                        Log.e("TAG", "Property Area: " + propertyLandArea);
                        Log.e("TAG", "Property City: " + propertyCity);
                        Log.e("TAG", "Property Location: " + propertyLocation);
                        Log.e("TAG", "Property Contact: " + propertyContact);
                        Log.e("TAG", "Property Type: " + propertyType);
                        Log.e("TAG", "Property Status: " + propertyStatus);
                        Log.e("TAG", "Property Rooms: " + propertyRooms);
                        Log.e("TAG", "Property Floors: " + propertyFloors);
                        Log.e("TAG", "Property Bathrooms: " + propertyBathrooms);
                        Log.e("TAG", "Property status_property: " + propertyStatus_prpoperty);



                        Intent editProperties = new Intent(ViewYourSubmitedProperties.this, EditProperty.class);
                        editProperties.putExtra("propertyID", propertyID);
                        editProperties.putExtra("propertyTitle", propertyTitle);
                        editProperties.putExtra("propertyPrice", propertyPrice);
                        editProperties.putExtra("propertyDescription", propertyDescription);
                        editProperties.putExtra("propertyLandArea", propertyLandArea);
                        editProperties.putExtra("propertyCity", propertyCity);
                        editProperties.putExtra("propertyLocation", propertyLocation);
                        editProperties.putExtra("propertyContact", propertyContact);
                        editProperties.putExtra("propertyType", propertyType);
                        editProperties.putExtra("propertyStatus", propertyStatus);
                        editProperties.putExtra("propertyRooms", propertyRooms);
                        editProperties.putExtra("propertyFloors", propertyFloors);
                        editProperties.putExtra("propertyBathrooms", propertyBathrooms);
                        editProperties.putExtra("propertyStatus_prpoperty", propertyStatus_prpoperty);
                        startActivity(editProperties);




//
                    }
                });//end of edit

                alert.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        imageCode=2;

                        new GetContacts().execute();

                        //call delete functionlaity here
                        Log.e("TAG", "ID ID: " + propertyID);
                    }
                });

                alert. create();
                alert.show();

                return true;
            }
        });


    }//end of listViewLognClickHandler

    public void deletingRecord(){

    }


}//***************** Shoaib Anwar ********************




