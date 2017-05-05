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
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

import pk.house.pkhouse.adapter.PagerAdapter;

public class EditProperty extends AppCompatActivity {


    SubmitSecondPage second;
    SubmitFirstPage f;
    SubmitThirldPage thirld;

    private ProgressBar bar;

    Button buttonSubmit;
    ViewPager viewPager;
    String title = null,property_for = null,property_type = null,price = null,area = null, ed_area = null ,availability = null,rooms = null,
            bathrooms = null,floors = null,description = null,property_structure = null,floor_structure = null,walls_structure = null,
            doors_structure = null,windows_structure = null,electrical_structure = null,
            location = null,country = null, city = null,
            name = null,email= null,phone_number = null;

    ImageView  iv_right_arrow, iv_left_arrow;


    ImageView iv_first, iv_second, iv_thirld, iv_fourth, iv_fith, iv_sixth;
    Button bt_slectIVFirst, bt_slectIVSecond, bt_slectIVThirld, bt_slectIVForth, bt_slectIVFith, bt_slectIVSixth;

    File imageFileGalary;
    File imageFileFromCamera;
    Uri filePath1 = null;
    Uri filePath2 = null;
    Uri filePath3 = null;
    Uri filePath4 = null;
    Uri filePath5 = null;
    Uri filePath6 = null;

    String timestamp1 , timestamp2 , timestamp3, timestamp4, timestamp5, timestamp6;
    Calendar myCalendar = Calendar.getInstance();

    String encodedImage1, encodedImage2, encodedImage3, encodedImage4, encodedImage5, encodedImage6;

    int imageSelecter = 0;

    public static int CAMERA_CODE = 1;
    public static int GALLERY_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 000000;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 000001;

    String normalOrFranchiser = "";


    public static final String SERVERURL = "http://www.pk.estate/app_webservices/add_property.php";



    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_property);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(EditProperty.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle("PK.HOUSE");

        getSupportActionBar().setDisplayOptions(getSupportActionBar().getDisplayOptions() | getSupportActionBar().DISPLAY_SHOW_CUSTOM);



        getSupportActionBar().setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_action, null);



        Intent intent = getIntent();
        Log.e("TAG", "TES TEST " + intent);
        if (intent.getExtras()== null){

        }else {
            final String userAs = intent.getExtras().getString("from", null);
            if (userAs!=null) {
                normalOrFranchiser = userAs;
            }
        }



        iv_right_arrow = (ImageView)v.findViewById(R.id.iv_right_arrow);
        iv_left_arrow = (ImageView)v.findViewById(R.id.iv_left_arrow);
        iv_left_arrow.setVisibility(View.GONE);
        getSupportActionBar().setCustomView(v);


        f = new SubmitFirstPage();
        second = new SubmitSecondPage();
        thirld = new SubmitThirldPage();

        bar = (ProgressBar) this.findViewById(R.id.progressBar);



        iv_first = (ImageView) findViewById(R.id.image_view1);
        iv_second = (ImageView) findViewById(R.id.image_view2);
        iv_thirld = (ImageView) findViewById(R.id.image_view3);
        iv_fourth = (ImageView) findViewById(R.id.image_view4);
        iv_fith = (ImageView) findViewById(R.id.image_view5);
        iv_sixth = (ImageView) findViewById(R.id.image_view6);

        bt_slectIVFirst = (Button) findViewById(R.id.bt_select_image1);
        bt_slectIVSecond = (Button) findViewById(R.id.bt_select_image2);
        bt_slectIVThirld = (Button) findViewById(R.id.bt_select_image3);
        bt_slectIVForth = (Button) findViewById(R.id.bt_select_image4);
        bt_slectIVFith = (Button) findViewById(R.id.bt_select_image5);
        bt_slectIVSixth = (Button) findViewById(R.id.bt_select_image6);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        buttonSubmit = (Button) findViewById(R.id.bt_submit_property);

        tabLayout.addTab(tabLayout.newTab().setText("Page 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Page 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Page 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setVisibility(View.GONE);

        buttonSubmit.setVisibility(View.GONE);


        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                String tabString = tab.getText().toString();
                int tabPos = tab.getPosition();
                Log.e("TAG", "TAG VALUE " + tabString);
                Log.e("TAG", "TAG VALUE " + tabPos);

                if (tabPos == 2) {

                    buttonSubmit.setVisibility(View.VISIBLE);
                    iv_left_arrow.setVisibility(View.VISIBLE);
                    iv_right_arrow.setVisibility(View.GONE);


                    sharedPreferences = getSharedPreferences("user", 0);
                    sharedPreferences1 = getSharedPreferences("franchiser", 0);



                    if (normalOrFranchiser.equals("normal")) {

                        sharedPreferences = getSharedPreferences("user", 0);
                    }
                    if (normalOrFranchiser.equals("franchiser")){
                        sharedPreferences =  getSharedPreferences("franchiser", 0);
                    }
                    Log.e("TAG", "SharePreference " + sharedPreferences);
                    if (sharedPreferences!=null) {



                        String mName = sharedPreferences.getString("name", null);
                        String mEmail = sharedPreferences.getString("email", null);
                        String mPphone = sharedPreferences.getString("phone", null);

                        if (mName!=null) {
                            Log.e("TAG", "SharePreference 11 " + name);

                            // set new title to the MenuItem
                            thirld.m_ed_Name.setText(mName);
                            thirld.m_ed_email.setText(mEmail);
                            thirld.m_ed_phoneNumber.setText(mPphone);



                        }

                    }
                    if (sharedPreferences1!=null) {



                        String mName = sharedPreferences1.getString("name", null);
                        String mEmail = sharedPreferences1.getString("email", null);
                        String mPphone = sharedPreferences1.getString("phone", null);

                        if (mName!=null) {
                            Log.e("TAG", "SharePreference 11 " + name);

                            // set new title to the MenuItem
                            thirld.m_ed_Name.setText(mName);
                            thirld.m_ed_email.setText(mEmail);
                            thirld.m_ed_phoneNumber.setText(mPphone);



                        }

                    }



                } else if (tabPos == 0) {
                    buttonSubmit.setVisibility(View.GONE);

                    iv_left_arrow.setVisibility(View.GONE);
                    iv_right_arrow.setVisibility(View.VISIBLE);


                } else if (tabPos == 1) {
                    buttonSubmit.setVisibility(View.GONE);


                    iv_left_arrow.setVisibility(View.VISIBLE);
                    iv_right_arrow.setVisibility(View.VISIBLE);

                    galaryImageSelectionHandler();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        nextButton();
        prevButton();
        submitButtonHandler();

    }//end of on create



    @Override
    protected void onStart() {
        super.onStart();
        checkCameraPermission();
        checkWriteExternalPermission();
    }


    public void checkCameraPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(EditProperty.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(EditProperty.this,
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
        if (ContextCompat.checkSelfPermission(EditProperty.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(EditProperty.this,
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
        Bitmap bmToUpload=null;
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bmToUpload = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                // filePath = data.getData();



                bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);

                int height =  bitmap.getHeight();
                int weidth = bitmap.getWidth();
                Log.e("TAG", "IMAGE HEIGHT " + height);
                Log.e("TAG", "IMAGE WEIDTH " + weidth);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (imageSelecter==1) {
            second.image1.setImageBitmap(bitmap);
            //iv_first.setImageBitmap(bitmap);
            filePath1 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp1 = tsLong.toString();

            encodedImage1 = getStringImage(bmToUpload);

            Log.e("TAG", "TTTTT " + encodedImage1);
            Log.e("TAG", "TTTTT SSSS " + timestamp1);

        }
        if (imageSelecter==2) {
            second.image2.setImageBitmap(bitmap);
            filePath2 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp2 = tsLong.toString();

            encodedImage2 = getStringImage(bmToUpload);


        }
        if (imageSelecter==3) {
            second.image3.setImageBitmap(bitmap);
            filePath3 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp3 = tsLong.toString();

            encodedImage3 = getStringImage(bmToUpload);

        }
        if (imageSelecter==4) {
            second.image4.setImageBitmap(bitmap);
            filePath4 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp4 = tsLong.toString();

            encodedImage4 = getStringImage(bmToUpload);
        }
        if (imageSelecter==5) {
            second.image5.setImageBitmap(bitmap);
            filePath5 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp5 = tsLong.toString();

            encodedImage5 = getStringImage(bmToUpload);
        }
        if (imageSelecter==6) {
            second.image6.setImageBitmap(bitmap);
            filePath6 = data.getData();
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp6 = tsLong.toString();


            encodedImage6 = getStringImage(bmToUpload);
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
                second.image1.setImageBitmap(photo);
                //iv_first.setImageBitmap(photo);

                filePath1 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp1 = tsLong.toString();
                encodedImage1 = getStringImage(photoToUpload);



            }
            if (imageSelecter==2) {
                second.image2.setImageBitmap(photo);

                filePath2 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp2 = tsLong.toString();
                encodedImage2 = getStringImage(photoToUpload);


            }
            if (imageSelecter==3) {
                second.image3.setImageBitmap(photo);

                filePath3 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp3 = tsLong.toString();
                encodedImage3 = getStringImage(photoToUpload);
            }
            if (imageSelecter==4) {
                second.image4.setImageBitmap(photo);

                filePath4 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp4 = tsLong.toString();
                encodedImage4 = getStringImage(photoToUpload);
            }
            if (imageSelecter==5) {
                second.image5.setImageBitmap(photo);

                filePath5 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp5 = tsLong.toString();
                encodedImage5 = getStringImage(photoToUpload);
            }
            if (imageSelecter==6) {
                second.image6.setImageBitmap(photo);

                filePath6 = getImageUri(getApplicationContext(), photoToUpload);
                Long tsLong = System.currentTimeMillis() / 1000;
                timestamp6 = tsLong.toString();
                encodedImage6 = getStringImage(photoToUpload);
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

        AlertDialog.Builder dialog = new AlertDialog.Builder(EditProperty.this);

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

    public void submitButtonHandler(){
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                title = f.ed_protpertyTitle.getText().toString();
                property_for = f.sp_propertyFor.getSelectedItem().toString();
                if(f.sp_propertyFor.getSelectedItemPosition()==0){
                    property_for="";
                }
                property_type = f.sp_propertyType.getSelectedItem().toString();
                if(f.sp_propertyType.getSelectedItemPosition()==0){
                    property_type="";
                }
                price = f.ed_propertyPrice.getText().toString();
                area = f.sp_landArea.getSelectedItem().toString();
                if(f.sp_landArea.getSelectedItemPosition()==0){
                    area="";
                }
                availability = f.sp_propertyAvail.getSelectedItem().toString();
                if(f.sp_propertyAvail.getSelectedItemPosition()==0){
                    availability="";
                }

                ed_area = f.ed_landAread.getText().toString();
                rooms = f.ed_noOfRooms.getText().toString();
                bathrooms = f.ed_ofBathrooms.getText().toString();
                floors = f.ed_floors.getText().toString();
                description = f.ed_description.getText().toString();

                Log.e("TAG", "TEST TEST " + timestamp1);


                location = thirld.m_ed_location.getText().toString();

                country = thirld.sp_country.getSelectedItem().toString();
                city = thirld.sp_city.getSelectedItem().toString();

                if (country.equals("Chose a Country")){
                    country = "";
                }
                if (city.equals("Chose a City")){
                    city = "";
                }
                country = thirld.sp_country.getSelectedItem().toString();
                city = thirld.sp_city.getSelectedItem().toString();

                name = thirld.m_ed_Name.getText().toString();
                email = thirld.m_ed_email.getText().toString();
                phone_number = thirld.m_ed_phoneNumber.getText().toString();





                if (title.isEmpty() || price.isEmpty()){
                    Toast.makeText(EditProperty.this, "Fields with a red star should not be empty.", Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 2, true);

                }

                else if(name.isEmpty() || email.isEmpty() || phone_number.isEmpty()){

                    Toast.makeText(EditProperty.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                }


                else {


                    area =  ed_area + " " + area;

                    Log.e("TAG", "Values " + title);
                    Log.e("TAG", "Values " + property_for);
                    Log.e("TAG", "Values " + property_type);
                    Log.e("TAG", "Values " + price);
                    Log.e("TAG", "Values " + area);
                    Log.e("TAG", "Values " + availability);
                    Log.e("TAG", "Values " + bathrooms);
                    Log.e("TAG", "Values " + floors);
                    Log.e("TAG", "Values " + description);
                    Log.e("TAG", "Values " + property_structure);
                    Log.e("TAG", "Values " + floor_structure);
                    Log.e("TAG", "Values " + walls_structure);
                    Log.e("TAG", "Values " + doors_structure);
                    Log.e("TAG", "Values " + windows_structure);
                    Log.e("TAG", "Values " + electrical_structure);
                    Log.e("TAG", "Values " + location);
                    Log.e("TAG", "Values " + country);
                    Log.e("TAG", "Values " + city);
                    Log.e("TAG", "Values " + name);
                    Log.e("TAG", "Values " + email);
                    Log.e("TAG", "Values " + phone_number);





                    new LoadingToServer().execute();

                    Log.e("TAG", "Image 1 " + encodedImage1);
                    Log.e("TAG", "TimeStamp 1 " + encodedImage1);


                }
            }
        });
    }//end of submit button



    public void galaryImageSelectionHandler(){

        Button btSelectImage1 = second.btImage1;
        btSelectImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 1;

                showImageChoseDialog();
                Toast.makeText(EditProperty.this, "Button 1 Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        Button btSelectImage2 = second.btImage2;
        btSelectImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 2;

                showImageChoseDialog();
                Toast.makeText(EditProperty.this, "Button 2 Clicked ", Toast.LENGTH_SHORT).show();
            }
        });

        Button btSelectImage3 = second.btImage3;
        btSelectImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 3;

                showImageChoseDialog();

            }
        });

        Button btSelectImage4 = second.btImage4;
        btSelectImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 4;

                showImageChoseDialog();

            }
        });

        Button btSelectImage5 = second.btImage5;
        btSelectImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 5;

                showImageChoseDialog();

            }
        });

        Button btSelectImage6 = second.btImage6;
        btSelectImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelecter = 6;

                showImageChoseDialog();

            }
        });

    }




    public void nextButton(){
        iv_right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitFirstPage f = new SubmitFirstPage();

                title = f.ed_protpertyTitle.getText().toString();
                price = f.ed_propertyPrice.getText().toString();
                description = f.ed_description.getText().toString();
                if (title.isEmpty() || price.isEmpty()){
                    Toast.makeText(EditProperty.this, "Fields with a red star should not be empty.", Toast.LENGTH_SHORT).show();
                }else {
                    jumpToNext(v);
                }
            }
        });
    }


    public void prevButton(){
        iv_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToPreviouce(v);
            }
        });
    }

    public void jumpToNext(View view) {

        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }


    public void jumpToPreviouce(View view) {

        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }



    //async class

    public class LoadingToServer extends  AsyncTask<String , Void ,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            try {

                URL url = new URL("http://www.pk.estate/app_webservices/add_property.php");


                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("name", name)
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("phone", phone_number)
                        .appendQueryParameter("propertytitle", title)
                        .appendQueryParameter("propertyfor", property_for)
                        .appendQueryParameter("propertytype", property_type)
                        .appendQueryParameter("propertyprice", price)
                        .appendQueryParameter("propertyavail", availability)
                        .appendQueryParameter("landarea", area)
                        .appendQueryParameter("noofrooms", rooms)
                        .appendQueryParameter("noofbathrooms", bathrooms)
                        .appendQueryParameter("noofloors", floors)
                        .appendQueryParameter("description", description)
                        .appendQueryParameter("structureofproperty", property_structure)
                        .appendQueryParameter("structureofflooring", floor_structure)
                        .appendQueryParameter("wallsstructure", walls_structure)
                        .appendQueryParameter("doorsstructure", doors_structure)
                        .appendQueryParameter("windowsstructure", windows_structure)
                        .appendQueryParameter("electricalstructure", electrical_structure)
                        .appendQueryParameter("imageurl1", encodedImage1)
                        .appendQueryParameter("timestamp1", timestamp1)
                        .appendQueryParameter("imageurl2", encodedImage2)
                        .appendQueryParameter("timestamp2", timestamp2)
                        .appendQueryParameter("imageurl3", encodedImage3)
                        .appendQueryParameter("timestamp3", timestamp3)
                        .appendQueryParameter("imageurl4", encodedImage4)
                        .appendQueryParameter("timestamp4", timestamp4)
                        .appendQueryParameter("imageurl5", encodedImage5)
                        .appendQueryParameter("timestamp5", timestamp5)
                        .appendQueryParameter("imageurl6", encodedImage6)
                        .appendQueryParameter("timestamp6", timestamp6)
                        .appendQueryParameter("location", location)
                        .appendQueryParameter("country", country)
                        .appendQueryParameter("city", city);


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




                //adding data into sharePreferences
                sharedPreferences = getSharedPreferences("userinfo", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("email", email);
                editor.putString("phone", phone_number);
                editor.commit();

                finish();



            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message

                bar.setVisibility(View.GONE);

                Toast.makeText(EditProperty.this, "Data not sent!", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                bar.setVisibility(View.GONE);
                Toast.makeText(EditProperty.this, " Connection Problem... ", Toast.LENGTH_LONG).show();

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