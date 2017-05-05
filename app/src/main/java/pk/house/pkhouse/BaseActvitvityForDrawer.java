package pk.house.pkhouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class  BaseActvitvityForDrawer extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    MenuItem navLoginRegister;
    MenuItem navUsername;
    MenuItem navChosePlane;
    MenuItem navFranchiser;
    MenuItem navContactUs;
    MenuItem navViewYourProperties;
    MenuItem navBuyerActvity;


    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_actvity_for_drawer);
        view = new View(this);




        /**
         *Setup the DrawerLayout and NavigationView
         */




        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        // get menu from navigationView
        Menu menu = mNavigationView.getMenu();

        // find MenuItem you want to change
        navUsername = menu.findItem(R.id.nav_item_name);
        navLoginRegister = menu.findItem(R.id.nav_item_login_registe);
        navChosePlane = menu.findItem(R.id.nav_item_chose_plain);
        navFranchiser = menu.findItem(R.id.nav_item_franchise);
        navContactUs = menu.findItem(R.id.nav_item_contact_us);
        navViewYourProperties = menu.findItem(R.id.nav_item_view_your_properties);
        navBuyerActvity = menu.findItem(R.id.nav_item_buyer);







        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);
        Log.e("TAG", "SharePreference " + sharedPreferences);

        if (sharedPreferences!=null){

            String name = sharedPreferences.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");

                navChosePlane.setTitle("Chose Plane");
            }

        }
        if (sharedPreferences1!=null){
            String name = sharedPreferences1.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");

                navChosePlane.setTitle("Chose Plane");
            }

        }




        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                if (menuItem.getItemId() == R.id.nav_item_home) {
                    //home activity
                    Toast.makeText(getApplicationContext(), "Home Activity", Toast.LENGTH_SHORT).show();
                    Intent mainActvity = new Intent(BaseActvitvityForDrawer.this, Home.class);
                    startActivity(mainActvity);
                    finish();
                }

                if (menuItem.getItemId() == R.id.nav_item_about) {
                    //map activity
                    Intent aboutUs = new Intent(BaseActvitvityForDrawer.this, AboutUs.class);
                    startActivity(aboutUs);
                }

                if (menuItem.getItemId() == R.id.nav_item_contact_us){
                    //starting contact us actvity
                    Intent aboutUs = new Intent(BaseActvitvityForDrawer.this, ContactUs.class);
                    startActivity(aboutUs);
                }

                if (menuItem.getItemId() == R.id.nav_item_buyer){
                    //starting buyer actvity
                    Intent buyerActivity = new Intent(BaseActvitvityForDrawer.this, Buyer.class);
                    startActivity(buyerActivity);
                }



                if (menuItem.getItemId() == R.id.nav_item_franchise){
                    if (navFranchiser.getTitle().toString().equals("Franchise")) {

                        if (navLoginRegister.getTitle().equals("Log Out")){

                        }else {

                            Intent mapActivity = new Intent(BaseActvitvityForDrawer.this, LoginAsFranchiser.class);
                            startActivity(mapActivity);
                        }
                    }
                }



                if (menuItem.getItemId() == R.id.nav_item_view_your_properties){

                    if (navViewYourProperties.getTitle().toString().equals("View Your Properties")){

                        Intent viewSubmitedProperties = new Intent(BaseActvitvityForDrawer.this, ViewYourSubmitedProperties.class);
                        startActivity(viewSubmitedProperties);


                    }
                }

                if (menuItem.getItemId()==R.id.nav_item_login_registe){


                    String title =  menuItem.getTitle().toString();
                    Log.e("TAG", "Menu TAG " + title);


                    if (title.equals("Login/Register")) {

                        Intent mapActivity = new Intent(BaseActvitvityForDrawer.this, LoginOrRegister.class);
                        startActivity(mapActivity);
                        //finish();
                    }


                    if (title.equals("Log Out")){

                        //do logout Function here
                        final SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
                        final SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor.clear();
                        editor.commit();
                        editor1.clear();
                        editor1.commit();
                        finish();
                        Intent mapActivity = new Intent(BaseActvitvityForDrawer.this, Home.class);
                        startActivity(mapActivity);

                    }

                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();



    }//end on Create

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        SharedPreferences sharedPreferences1 = getSharedPreferences("franchiser", 0);
        Log.e("TAG", "SharePreference " + sharedPreferences);

        if (sharedPreferences!=null){

            String name = sharedPreferences.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");
                navChosePlane.setIcon(R.drawable.choose_plan);
                navChosePlane.setTitle("Chose Plane");
                navViewYourProperties.setTitle("View Your Properties");
                navViewYourProperties.setIcon(R.drawable.home_image_view);
            }

        }
        if (sharedPreferences1!=null){
            String name = sharedPreferences1.getString("name", null);

            if (name!=null) {
                Log.e("TAG", "SharePreference 11 " + name);

                // set new title to the MenuItem
                navUsername.setTitle(name);
                navUsername.setIcon(R.drawable.person_image);
                navLoginRegister.setTitle("Log Out");
                navChosePlane.setIcon(R.drawable.choose_plan);
                navChosePlane.setTitle("Chose Plane");
                navViewYourProperties.setTitle("View Your Properties");
                navViewYourProperties.setIcon(R.drawable.home_image_view);

            }

        }
    }

}
