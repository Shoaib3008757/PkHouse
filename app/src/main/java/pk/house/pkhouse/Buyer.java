package pk.house.pkhouse;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Buyer extends AppCompatActivity {

    Spinner spPropertyFor, spPropertyType, spLandArea, spCountry, spCity;
    EditText etPrice, etLandArea, etDescription, etLocation, etName, etEmail, etPhone;
    Button submitButton;
    ProgressBar bar;

    SharedPreferences sharedPreferences;

    String propertyFor, propertyType, landArea, country, city, price,  description, location, name, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        init();
        spinnerValuesOnSelection();
        submitButtonHandler();

    }//end of onCreate

    public void init(){


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(Buyer.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle(R.string.tv_pk_estate);

        spPropertyFor = (Spinner) findViewById(R.id.sp_property_for);
        spPropertyType = (Spinner) findViewById(R.id.sp_property_type);
        spLandArea = (Spinner) findViewById(R.id.sp_land_area);
        spCountry = (Spinner) findViewById(R.id.sp_chose_country);
        spCity = (Spinner) findViewById(R.id.sp_chose_city);

        etPrice = (EditText) findViewById(R.id.ed_price);
        etLandArea = (EditText) findViewById(R.id.ed_land_area);
        etDescription = (EditText) findViewById(R.id.et_description);
        etLocation = (EditText) findViewById(R.id.et_location);
        etName = (EditText) findViewById(R.id.m_ed_name);
        etEmail = (EditText) findViewById(R.id.m_ed_email);
        etPhone = (EditText) findViewById(R.id.m_ed_phone_number);

        submitButton = (Button) findViewById(R.id.bt_submit_property);

        bar = (ProgressBar) findViewById(R.id.progressBar);


        sharedPreferences = getSharedPreferences("user", 0);
        if (sharedPreferences!=null) {



            String mName = sharedPreferences.getString("name", null);
            String mEmail = sharedPreferences.getString("email", null);
            String mPphone = sharedPreferences.getString("phone", null);

            if (mName!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                etName.setText(mName);
                etEmail.setText(mEmail);
                etPhone.setText(mPphone);


            }
        }


        }//end of init

    public void spinnerValuesOnSelection(){
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            Buyer.this, R.array.chose_a_city, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCity.setAdapter(adapter);


                }

                if (position==2){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            Buyer.this, R.array.chose_a_city_uae, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCity.setAdapter(adapter);


                }

                if (position==3){


                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            Buyer.this, R.array.chose_a_city_oman, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCity.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end of spinner values on Selection


    public void submitButtonHandler(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mPropertyType = spPropertyType.getSelectedItem().toString();
                String mPropertyFor = spPropertyFor.getSelectedItem().toString();
                String mPrice = etPrice.getText().toString();
                String metArea = etLandArea.getText().toString();
                String mspArea = spLandArea.getSelectedItem().toString();
                String mDescription = etDescription.getText().toString();
                String mCountry = spCountry.getSelectedItem().toString();
                String mCity = spCity.getSelectedItem().toString();
                String mLocation = etLocation.getText().toString();
                String mName = etName.getText().toString();
                String mEmail = etEmail.getText().toString();
                String mPhone = etPhone.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                int mPropertyTypePosition = spPropertyType.getSelectedItemPosition();
                int mPropertyForPosition = spPropertyFor.getSelectedItemPosition();
                int mPropertyAreaPosition = spLandArea.getSelectedItemPosition();


                if (etPrice.length()==0){
                    Toast.makeText(Buyer.this, "Price should not empty", Toast.LENGTH_SHORT).show();
                }else if (spCountry.getSelectedItemId()==0){
                    Toast.makeText(Buyer.this, "Please chose a country", Toast.LENGTH_SHORT).show();
                }else if (spCity.getSelectedItemId()==0){
                    Toast.makeText(Buyer.this, "Please chose a city", Toast.LENGTH_SHORT).show();
                }else if (etName.length()==0){
                    Toast.makeText(Buyer.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                }else if (etEmail.length()==0){
                    Toast.makeText(Buyer.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                }else if (!mEmail.matches(emailPattern)){
                    Toast.makeText(Buyer.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else if (etPhone.length()==0){
                    Toast.makeText(Buyer.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                }else{

                    if (mPropertyTypePosition==0){
                        mPropertyType = "";
                    }
                    if (mPropertyForPosition==0){

                        mPropertyFor = "";
                    }
                    if (mPropertyAreaPosition==0 || metArea.length()==0){

                        metArea = "";
                        mspArea = "";
                    }

                    propertyFor = mPropertyFor;
                    propertyType = mPropertyType;
                    landArea = metArea + " " + mspArea;
                    country = mCountry;
                    city = mCity;
                    price = mPrice;
                    description = mDescription;
                    location = mLocation;
                    name = mName;
                    email = mEmail;
                    phone = mPhone;

                    Log.e("TAG", "Values Property For " + propertyFor);
                    Log.e("TAG", "Values Property Type " + propertyType);
                    Log.e("TAG", "Values Land Area " + landArea);
                    Log.e("TAG", "Values Country " + country);
                    Log.e("TAG", "Values City " + city);
                    Log.e("TAG", "Values Price " + price);
                    Log.e("TAG", "Values Description " + description);
                    Log.e("TAG", "Values  Location " + location);
                    Log.e("TAG", "Values Name " + name);
                    Log.e("TAG", "Values Email " + email);
                    Log.e("TAG", "Values Password " + phone);


                    new LoadingToServer().execute();

                }




            }
        });

    }//end of submit button



    public class LoadingToServer extends AsyncTask<String , Void ,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                URL url = new URL("http://www.pk.estate/app_webservices/add_buyer.php");


                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("propertyfor", propertyFor)
                        .appendQueryParameter("propertytype", propertyType)
                        .appendQueryParameter("landarea", landArea)
                        .appendQueryParameter("country", country)
                        .appendQueryParameter("city", city)
                        .appendQueryParameter("price", price)
                        .appendQueryParameter("description", description)
                        .appendQueryParameter("location", location)
                        .appendQueryParameter("name", name)
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("phone", phone);



                String query = builder.build().getEncodedQuery().toString();

                // Open connection for sending data
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                connection.connect();

                int response_code = connection.getResponseCode();


                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {


                    // Read data sent from server
                    InputStream input = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    Log.e("TAG", "RESULT 123 33: " + result);
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String responce =  returnParsedJsonObject(result);
            Log.e("TAG", "RESULT 123 " + result);
            Log.e("TAG", "RESULT 1233 " + responce);
            if (responce == null)
            {
                bar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Server not Responding...", Toast.LENGTH_SHORT).show();
            }
            else if(responce.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                bar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Data Sent Successfully...",Toast.LENGTH_SHORT).show();
                finish();


            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message

                bar.setVisibility(View.GONE);

                Toast.makeText(Buyer.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                bar.setVisibility(View.GONE);
                Toast.makeText(Buyer.this, " Connection Problem... ", Toast.LENGTH_LONG).show();

            }
        }

    }


    private String returnParsedJsonObject(String result){

        JSONObject resultObject = null;

        String returnedResult = null;

        try {

            if (result!=null) {
                resultObject = new JSONObject(result);

                returnedResult = resultObject.getString("response");
            }
        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }


}
