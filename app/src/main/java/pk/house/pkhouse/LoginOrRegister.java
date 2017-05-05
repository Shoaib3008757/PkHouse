package pk.house.pkhouse;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoginOrRegister extends AppCompatActivity {


    EditText etLoginEmail, etLoginPassword;
    EditText etRegisterName, etRegisterEmail, etRegisterPass, etRegisterPhone;

    LinearLayout ll_login_email, ll_login_pass, ll_dont_have_an_account;
    RelativeLayout rl_login_title_text;
    TextView tv_dont_have_accont;
    Button bt_login_now;

    LinearLayout ll_register_email, ll_register_name, ll_register_pass, ll_register_phone, ll_already_have_an_account;
    RelativeLayout rl_register_title_text;
    TextView tv_already_have_account, tv_forget_password;
    Button bt_create_account;

    private final String serverUrlLogin = "http://www.pk.estate/app_webservices/login.php";
    private final String serverUrlRegistration = "http://www.pk.estate/app_webservices/registration.php";

    private final String serverUrlForgotPassword = "http://www.pk.estate/app_webservices/forgot.php";
    private final String asNormalUser = "normal";

    int loginRegister = 0;


    String name = null;

    SharedPreferences sharedPreferences;
    private ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        init();
        visible_loginView();
        visibleRegisterViews();
        btLoginHandler();
        btCreateAccountHandler();
        forgoetPasswordHandler();
    }

    public void init(){

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(LoginOrRegister.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle(R.string.tv_pk_estate);


        etLoginEmail = (EditText) findViewById(R.id.login_email);
        etLoginPassword = (EditText) findViewById(R.id.login_password);

        etRegisterName = (EditText) findViewById(R.id.register_name);
        etRegisterEmail = (EditText) findViewById(R.id.register_email);
        etRegisterPass = (EditText) findViewById(R.id.register_pass);
        etRegisterPhone = (EditText) findViewById(R.id.register_phone);


        //registing views for login
        ll_login_email = (LinearLayout) findViewById(R.id.ll_login_email);
        ll_login_pass = (LinearLayout) findViewById(R.id.ll_login_password);
        ll_dont_have_an_account = (LinearLayout) findViewById(R.id.ll_tv_dont_have_account);
        rl_login_title_text = (RelativeLayout) findViewById(R.id.rl_login_title_text);
        tv_dont_have_accont = (TextView) findViewById(R.id.tv_dont_have_account);
        tv_forget_password = (TextView) findViewById(R.id.tv_forgot_password);
        bt_login_now = (Button) findViewById(R.id.bt_login_now);

        //registring views for registeration
        ll_register_name = (LinearLayout) findViewById(R.id.ll_register_name);
        ll_register_email = (LinearLayout) findViewById(R.id.ll_register_email);
        ll_register_pass = (LinearLayout) findViewById(R.id.ll_regiter_pass);
        ll_register_phone = (LinearLayout) findViewById(R.id.ll_regiter_phone);
        ll_already_have_an_account = (LinearLayout) findViewById(R.id.ll_tv_already_have_account);
        rl_register_title_text = (RelativeLayout) findViewById(R.id.rl_register_title_text);
        tv_already_have_account = (TextView) findViewById(R.id.tv_already_have_account);
        bt_create_account = (Button) findViewById(R.id.bt_create_account);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);


    }


    public void visible_loginView(){
        tv_already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ll_register_name.setVisibility(View.GONE);
                ll_register_email.setVisibility(View.GONE);
                ll_register_pass.setVisibility(View.GONE);
                ll_register_phone.setVisibility(View.GONE);
                ll_already_have_an_account.setVisibility(View.GONE);
                rl_register_title_text.setVisibility(View.GONE);
                tv_already_have_account.setVisibility(View.GONE);
                bt_create_account.setVisibility(View.GONE);

                ll_login_email.setVisibility(View.VISIBLE);
                ll_login_pass.setVisibility(View.VISIBLE);
                ll_dont_have_an_account.setVisibility(View.VISIBLE);
                rl_login_title_text.setVisibility(View.VISIBLE);
                tv_dont_have_accont.setVisibility(View.VISIBLE);
                bt_login_now.setVisibility(View.VISIBLE);




            }
        });
    }//end of viewing loginViews

    public void visibleRegisterViews(){

        tv_dont_have_accont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_register_name.setVisibility(View.VISIBLE);
                ll_register_email.setVisibility(View.VISIBLE);
                ll_register_pass.setVisibility(View.VISIBLE);
                ll_register_phone.setVisibility(View.VISIBLE);
                ll_already_have_an_account.setVisibility(View.VISIBLE);
                rl_register_title_text.setVisibility(View.VISIBLE);
                tv_already_have_account.setVisibility(View.VISIBLE);
                bt_create_account.setVisibility(View.VISIBLE);

                ll_login_email.setVisibility(View.GONE);
                ll_login_pass.setVisibility(View.GONE);
                ll_dont_have_an_account.setVisibility(View.GONE);
                rl_login_title_text.setVisibility(View.GONE);
                tv_dont_have_accont.setVisibility(View.GONE);
                bt_login_now.setVisibility(View.GONE);


            }
        });


    }


    public void btLoginHandler(){

        bt_login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int login_mail = etLoginEmail.getText().length();
                int login_pass = etLoginPassword.getText().length();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String loginEmail = etLoginEmail.getText().toString();
                if (login_mail==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(!loginEmail.matches(emailPattern)){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }
                else if (login_pass==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter pass", Toast.LENGTH_SHORT).show();
                }
                else if (login_pass<=4){
                    Toast.makeText(LoginOrRegister.this, "Password should be atleast 5 charecters long", Toast.LENGTH_SHORT).show();
                }else {
                    String lEmail = etLoginEmail.getText().toString();
                    String lPass  = etLoginPassword.getText().toString();
                    Toast.makeText(LoginOrRegister.this, "Loging...", Toast.LENGTH_SHORT).show();


                    loginRegister = 1;

                    AsyncDataClass asyncRequestObject = new AsyncDataClass();

                    asyncRequestObject.execute(serverUrlLogin, lEmail, lPass, asNormalUser);
                }

            }
        });
    }//end of login button handling

    public void btCreateAccountHandler(){

        bt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String MobilePattern = "[0-9]{10}";
                String register_email = etRegisterEmail.getText().toString();
                String register_phone = etRegisterPhone.getText().toString();

                int registerName = etRegisterName.getText().length();
                int registerEmail = etRegisterEmail.getText().length();
                int registerPass = etRegisterPass.getText().length();
                int registerPhone = etRegisterPhone.getText().length();

                if (registerName==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                else if (registerEmail==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Emial", Toast.LENGTH_SHORT).show();
                }

                else if (!register_email.matches(emailPattern)){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                }
                else if (registerPhone==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
                }

                else if (isValidPhoneNumber(register_phone)){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();

                }
                else if (registerPass==0){
                    Toast.makeText(LoginOrRegister.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (registerPass<=4){
                    Toast.makeText(LoginOrRegister.this, "Password should be atleast 5 charecters long", Toast.LENGTH_SHORT).show();
                }
                else {

                    String rName = etRegisterName.getText().toString();
                    String rEmail = etRegisterEmail.getText().toString();
                    String rPhone = etRegisterPhone.getText().toString();
                    String rPass = etRegisterPass.getText().toString();

                    Toast.makeText(LoginOrRegister.this, "Registring", Toast.LENGTH_SHORT).show();


                    loginRegister = 2;

                    AsyncDataClass asyncRequestObject = new AsyncDataClass();

                    asyncRequestObject.execute(serverUrlRegistration, rName, rEmail, rPhone, rPass);
                }


            }
        });

    }//end of createAccount button handling

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }



    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override

        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 8000);

            HttpConnectionParams.setSoTimeout(httpParameters, 8000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);

            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";

            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);


                //for login user
                if (loginRegister==1) {

                    nameValuePairs.add(new BasicNameValuePair("email", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("user_type", params[3]));

                }
                //for registring user
                if (loginRegister==2){

                    nameValuePairs.add(new BasicNameValuePair("name", params[1]));

                    nameValuePairs.add(new BasicNameValuePair("email", params[2]));

                    nameValuePairs.add(new BasicNameValuePair("phone", params[3]));

                    nameValuePairs.add(new BasicNameValuePair("password", params[4]));
                }

                //for forget password
                if (loginRegister==3){

                    nameValuePairs.add(new BasicNameValuePair("email", params[1]));
                }

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

                Log.e("TAG", "Resulted Returned Json object " + jsonResult.toString());

            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return jsonResult;

        }

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

            bar.setVisibility(View.VISIBLE);


        }

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);




            Log.e("TAG", "Resulted Value: " + result);

            if(result.equals("") || result == null){

                Toast.makeText(LoginOrRegister.this, "Server connection failed", Toast.LENGTH_LONG).show();

                bar.setVisibility(View.GONE);



                return;

            }

            String jsonResult = returnParsedJsonObject(result);

            Log.e("TAG", "RESULT 123" + result);
            Log.e("TAG", "RESULT 123" + jsonResult);


            if (loginRegister==1){

                if (result!=null){



                    if (name==null){
                        Toast.makeText(LoginOrRegister.this, "Invalid Password or Email", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        tv_forget_password.setVisibility(View.VISIBLE);
                    }
                    else {

                        Toast.makeText(LoginOrRegister.this, "Login Successfully ", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                        Intent submitProperty = new Intent(LoginOrRegister.this, SubmitProperty.class);
                        submitProperty.putExtra("from", "normal");
                        startActivity(submitProperty);
                        finish();

                    }
                }
            }




            if (loginRegister==2){

                if (result!=null) {

                    if (jsonResult.equals("already registered")) {

                        Toast.makeText(LoginOrRegister.this, "User Already Registered", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                        hindingViewOfRegistration();

                        return;

                    }

                    if (jsonResult.equals("registered successfully")) {

                        Toast.makeText(LoginOrRegister.this, "Register Successfully", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", etRegisterName.getText().toString());
                        editor.putString("email", etRegisterName.getText().toString());
                        editor.putString("phone", etRegisterPhone.getText().toString());
                        editor.clear();
                        editor.commit();

                        Intent submitProperty = new Intent(LoginOrRegister.this, SubmitProperty.class);
                        submitProperty.putExtra("from", "normal");
                        startActivity(submitProperty);
                        finish();




                    }

                }

            }


            if (loginRegister==3){


                if (result!=null) {

                    if (jsonResult.equals("true")) {

                        Toast.makeText(LoginOrRegister.this, "Please Check You Email For Password", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                        tv_forget_password.setVisibility(View.GONE);
                        etLoginEmail.setText("");
                        etLoginPassword.setText("");

                        return;


                    }

                    if (jsonResult.equals("You are not a registered user")) {


                        Toast.makeText(LoginOrRegister.this, "Emai is not registered", Toast.LENGTH_LONG).show();

                        bar.setVisibility(View.GONE);


                    }

                }

            }



        }

        private StringBuilder inputStreamToString(InputStream is) {

            String rLine = "";

            StringBuilder answer = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {

                while ((rLine = br.readLine()) != null) {

                    answer.append(rLine);

                }

            } catch (IOException e) {

// TODO Auto-generated catch block

                e.printStackTrace();

            }

            return answer;

        }

    }

    private String returnParsedJsonObject(String result){

        JSONObject resultObject = null;

        String returnedResult = null;

        try {

            resultObject = new JSONObject(result);

            boolean flag = true;



            if (loginRegister==1) {

                resultObject = new JSONObject(result);



                    name = resultObject.getString("name");
                    String email = resultObject.getString("email");
                    String phone = resultObject.getString("number");
                    String password = resultObject.getString("password");
                String userId = resultObject.getString("user_id");

                    SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("phone", phone);
                editor.putString("user_id", userId);
                    editor.clear();
                    editor.commit();


                    Log.e("TAG", "RESULT 111 " + email);
                    Log.e("TAG", "RESULT 111 " + name);
                    Log.e("TAG", "RESULT 111 " + phone);
                    Log.e("TAG", "RESULT 111 " + password);
                Log.e("TAG", "RESULT 111 " + userId);


            }

            if (loginRegister==2) {
                returnedResult = resultObject.getString("status");

                Log.e("TAG", "RESULT 111 " + returnedResult);

            }

            if (loginRegister==3){
                returnedResult = resultObject.getString("status");

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }

    public void hindingViewOfRegistration(){


        ll_register_name.setVisibility(View.GONE);
        ll_register_email.setVisibility(View.GONE);
        ll_register_pass.setVisibility(View.GONE);
        ll_register_phone.setVisibility(View.GONE);
        ll_already_have_an_account.setVisibility(View.GONE);
        rl_register_title_text.setVisibility(View.GONE);
        tv_already_have_account.setVisibility(View.GONE);
        bt_create_account.setVisibility(View.GONE);

        ll_login_email.setVisibility(View.VISIBLE);
        ll_login_pass.setVisibility(View.VISIBLE);
        ll_dont_have_an_account.setVisibility(View.VISIBLE);
        rl_login_title_text.setVisibility(View.VISIBLE);
        tv_dont_have_accont.setVisibility(View.VISIBLE);
        bt_login_now.setVisibility(View.VISIBLE);
    }


    public void forgoetPasswordHandler(){
        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //careating dialog for forgot password

                final Dialog forGetPassword = new Dialog(LoginOrRegister.this);
                forGetPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
                forGetPassword.setContentView(R.layout.dialog_forgot_password);

                final EditText etEmail = (EditText) forGetPassword.findViewById(R.id.dialo_email_forgot_password);
                Button btOk = (Button) forGetPassword.findViewById(R.id.dialog_forgetpassword_bt_ok);

                btOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String dialogEmail = etEmail.getText().toString();
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (dialogEmail.isEmpty()){

                            Toast.makeText(LoginOrRegister.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                        }
                        else if (!dialogEmail.matches(emailPattern)){

                            Toast.makeText(LoginOrRegister.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

                        }else {

                            Log.e("TAG", " Email  " + dialogEmail);
                            forGetPassword.dismiss();

                            loginRegister = 3;

                            AsyncDataClass asyncRequestObject = new AsyncDataClass();

                            asyncRequestObject.execute(serverUrlForgotPassword, dialogEmail);
                        }


                    }
                });

                forGetPassword.show();
            }
        });
    }




}
