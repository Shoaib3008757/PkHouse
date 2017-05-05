package pk.house.pkhouse;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;

public class FranchiserRegistration extends AppCompatActivity {


    EditText etName, etCompanyName, etEmail, etPhone, etCNIC, etPassword, etPasswordAgain;
    ImageView imageView1, imageView2;
    Button btSelectImage1, btSelectImage2, btCreateAccount;


    File imageFileFromCamera;
    Uri filePath1 = null;
    Uri filePath2 = null;


    String timestamp1 , timestamp2;
    Calendar myCalendar = Calendar.getInstance();

    String encodedImage1, encodedImage2;
    String name, companyname, email,  phone_number, cnic, password;

    int imageSelecter = 0;

    public static int CAMERA_CODE = 1;
    public static int GALLERY_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 000000;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 000001;

    public static final String SERVERURL = "http://www.pk.estate/app_webservices/add_franchise_registration.php";

    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchiser_registration);

        init();
        galaryImageSelectionHandler();
        registerButtonHandler();
    }


    public void init(){


        etName = (EditText) findViewById(R.id.register_name);
        etCompanyName = (EditText) findViewById(R.id.register_company_name);
        etEmail = (EditText) findViewById(R.id.register_email);
        etPhone = (EditText) findViewById(R.id.register_phone);
        etCNIC = (EditText) findViewById(R.id.register_cnic);
        etPassword = (EditText) findViewById(R.id.register_pass);
        etPasswordAgain = (EditText) findViewById(R.id.register_pass_again);

        imageView1 = (ImageView) findViewById(R.id.first_image);
        imageView2 = (ImageView) findViewById(R.id.second_image);

        btSelectImage1 = (Button) findViewById(R.id.bt_select_image1);
        btSelectImage2 = (Button) findViewById(R.id.bt_select_image2);
        btCreateAccount = (Button) findViewById(R.id.bt_create_account);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(FranchiserRegistration.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle(R.string.tv_pk_estate);

    }


    @Override
    protected void onStart() {
        super.onStart();
        checkCameraPermission();
        checkWriteExternalPermission();
    }


    public void checkCameraPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(FranchiserRegistration.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(FranchiserRegistration.this,
                    new String[]{android.Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }

    }

    public void checkWriteExternalPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(FranchiserRegistration.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(FranchiserRegistration.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                Log.d("tag", "Permission ");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Log.d("tag", "Permission Granted Write External ");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("tag", "Permission Not Granted Write External ");
                    checkWriteExternalPermission();
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_CAMERA: {
                Log.d("tag", "Permission ");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                    Log.d("tag", "Permission Granted Camera ");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("tag", "Permission Not Granted");
                    checkCameraPermission();
                }
                return;
            }




            // other 'case' lines to check for other
            // permissions this app might request
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_CODE) {
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == CAMERA_CODE) {
                onSelectFromCameraResult(data);

            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bmToUpload = null;
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bmToUpload = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                // filePath = data.getData();


                bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);

                int height = bitmap.getHeight();
                int weidth = bitmap.getWidth();
                Log.e("TAG", "IMAGE HEIGHT " + height);
                Log.e("TAG", "IMAGE WEIDTH " + weidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (imageSelecter == 1) {
            imageView1.setImageBitmap(bitmap);
            //iv_first.setImageBitmap(bitmap);
            filePath1 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();

            encodedImage1 = getStringImage(bmToUpload);

            Log.e("TAG", "TTTTT " + encodedImage1);
            Log.e("TAG", "TTTTT SSSS " + timestamp1);

        }
        if (imageSelecter == 2) {
            imageView2.setImageBitmap(bitmap);
            filePath2 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();

            encodedImage2 = getStringImage(bmToUpload);


        }

    }

    //camera image
    private void onSelectFromCameraResult(Intent data) {
        if (data != null) {




            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap photoToUpload = (Bitmap) data.getExtras().get("data");

            int height = photo.getHeight();
            int weidth = photo.getWidth();

            Log.e("TAG", "IMAGE HEIGHT Old " + height);
            Log.e("TAG", "IMAGE Weidht Old " + weidth);

            photo = Bitmap.createScaledBitmap(photo, 512, 512, true);

            int heightnew = photo.getHeight();
            int weidthnew = photo.getWidth();

            Log.e("TAG", "IMAGE HEIGHT New " + heightnew);
            Log.e("TAG", "IMAGE Weidht New " + heightnew);




            Uri tempUri = getImageUri(getApplicationContext(), photo);
            Log.e("TAG", "IMAGE URI " + tempUri);
            imageFileFromCamera = new File(getRealPathFromURI(tempUri));

            Log.e("TAG", "IMAGE URI 1 " + imageFileFromCamera);



            if (imageSelecter==1) {
                imageView1.setImageBitmap(photo);
                //iv_first.setImageBitmap(photo);

                filePath1 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp1 = tsLong.toString();
                encodedImage1 = getStringImage(photoToUpload);



            }
            if (imageSelecter==2) {
                imageView2.setImageBitmap(photo);

                filePath2 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp2 = tsLong.toString();
                encodedImage2 = getStringImage(photoToUpload);


            }


        }
    }


    public void selectImageFromGalaryIntent(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_CODE);
    }

    public void selectImageFromCameraIntent(){

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_CODE);
    }


    public void showImageChoseDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(FranchiserRegistration.this);

        dialog.setTitle("Please Choose Image...");
        dialog.setPositiveButton("From Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectImageFromGalaryIntent();
            }
        });

        dialog.setNegativeButton("Take Photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //select image from camera intent
                selectImageFromCameraIntent();
            }
        });

        dialog.setNeutralButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //endcoding image into base64
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    public void galaryImageSelectionHandler(){

        btSelectImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 1;

                showImageChoseDialog();
                Toast.makeText(FranchiserRegistration.this, "Button 1 Clicked ", Toast.LENGTH_SHORT).show();
            }
        });


        btSelectImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 2;

                showImageChoseDialog();
                Toast.makeText(FranchiserRegistration.this, "Button 2 Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //async class

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

                URL url = new URL("http://www.pk.estate/app_webservices/add_franchise_registration.php");


                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("name", name)
                        .appendQueryParameter("companyname", companyname)
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("phone", phone_number)
                        .appendQueryParameter("cnic", cnic)
                        .appendQueryParameter("password", password)
                        .appendQueryParameter("imageurl1", encodedImage1)
                        .appendQueryParameter("timestamp1", timestamp1)
                        .appendQueryParameter("imageurl2", encodedImage2)
                        .appendQueryParameter("timestamp2", timestamp2);


                Log.e("TAG", "Image 1111 " + encodedImage1);
                Log.e("TAG", "TimeStamp 1111 " + encodedImage1);


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

                Toast.makeText(FranchiserRegistration.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                bar.setVisibility(View.GONE);
                Toast.makeText(FranchiserRegistration.this, " Connection Problem... ", Toast.LENGTH_LONG).show();

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

    public void registerButtonHandler(){

        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String mName = etName.getText().toString();
                String mCompanyName = etCompanyName.getText().toString();
                String mEmail = etEmail.getText().toString();
                String mPbone = etPhone.getText().toString();
                String mCNIC = etCNIC.getText().toString();
                String mPassword = etPassword.getText().toString();
                String mPasswordAgain = etPasswordAgain.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



                if (mName.length()==0){
                    Toast.makeText(FranchiserRegistration.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
               else if (mCompanyName.length()==0){
                    Toast.makeText(FranchiserRegistration.this, "Please Enter Company Name", Toast.LENGTH_SHORT).show();
                }
               else if (mEmail.length()==0){
                    Toast.makeText(FranchiserRegistration.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
               else if (mPbone.length()==0){
                    Toast.makeText(FranchiserRegistration.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                }
               else if (mCNIC.length()==0){
                    Toast.makeText(FranchiserRegistration.this, "Please You CNIC Number", Toast.LENGTH_SHORT).show();
                }
               else if (mPassword.length()==0){
                    Toast.makeText(FranchiserRegistration.this, "Please Password", Toast.LENGTH_SHORT).show();
                }
               else if (mPasswordAgain.length()==0){
                    Toast.makeText(FranchiserRegistration.this, "Please Enter Password Again", Toast.LENGTH_SHORT).show();
                }
               else if (encodedImage1==null){
                    Toast.makeText(FranchiserRegistration.this, "Please Put Your CNIC Frant Image", Toast.LENGTH_SHORT).show();
                }
               else if (encodedImage2==null){
                    Toast.makeText(FranchiserRegistration.this, "Please PUT CNIC Back Image", Toast.LENGTH_SHORT).show();
                }
               else if (!mEmail.matches(emailPattern)){
                    Toast.makeText(FranchiserRegistration.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                }

               else if (!mPassword.equals(mPasswordAgain)){
                    Toast.makeText(FranchiserRegistration.this, "Password Should be Same", Toast.LENGTH_SHORT).show();
                }
                else {


                    name = mName;
                    companyname = mCompanyName;
                    email = mEmail;
                    phone_number = mPbone;
                    password = mPassword;
                    cnic = mCNIC;


                    //calling server to submit data
                   new LoadingToServer().execute();



                }

            }
        });
    }


}
