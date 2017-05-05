package pk.house.pkhouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class LoginAsFranchiser extends AppCompatActivity {


    EditText loginEmail, loginPassword;
    Button loginButton;
    private ProgressBar bar;

    TextView tvSignUp;

    String name = null;

    private final String serverUrlLogin = "http://www.pk.estate/app_webservices/login.php";

    private final String asFranchiser = "franchiser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_franchiser);


        init();
        btLoginHandler();
        singUpScreen();
    }

    public void init(){
        loginEmail  = (EditText) findViewById(R.id.login_email);
        loginPassword  = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.bt_login_now);

        tvSignUp = (TextView) findViewById(R.id.tv_dont_have_account);

        bar = (ProgressBar) this.findViewById(R.id.progressBar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(LoginAsFranchiser.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle(R.string.tv_pk_estate);
    }


    public void btLoginHandler(){

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int login_mail = loginEmail.getText().length();
                int login_pass = loginPassword.getText().length();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String login_email = loginEmail.getText().toString();
                if (login_mail==0){
                    Toast.makeText(LoginAsFranchiser.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(!login_email.matches(emailPattern)){
                    Toast.makeText(LoginAsFranchiser.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                }
                else if (login_pass==0){
                    Toast.makeText(LoginAsFranchiser.this, "Please Enter pass", Toast.LENGTH_SHORT).show();
                }
                else if (login_pass<=4){
                    Toast.makeText(LoginAsFranchiser.this, "Password should be atleast 5 charecters long", Toast.LENGTH_SHORT).show();
                }else {
                    String lEmail = loginEmail.getText().toString();
                    String lPass  = loginPassword.getText().toString();
                    Toast.makeText(LoginAsFranchiser.this, "Loging...", Toast.LENGTH_SHORT).show();



                   AsyncDataClass asyncRequestObject = new AsyncDataClass();

                    asyncRequestObject.execute(serverUrlLogin, lEmail, lPass, asFranchiser);
                }

            }
        });
    }//end of login button handling


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

                    nameValuePairs.add(new BasicNameValuePair("email", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("user_type", params[3]));

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

                Toast.makeText(LoginAsFranchiser.this, "Server connection failed", Toast.LENGTH_LONG).show();

                bar.setVisibility(View.GONE);



                return;

            }

            String jsonResult = returnParsedJsonObject(result);

            Log.e("TAG", "RESULT 123" + result);
            Log.e("TAG", "RESULT 123" + jsonResult);




                if (result!=null){



                    if (name==null){
                        Toast.makeText(LoginAsFranchiser.this, "Invalid Password or Email", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                    }
                    else {

                        Toast.makeText(LoginAsFranchiser.this, "Login Successfully ", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);

                        Intent submitProperty = new Intent(LoginAsFranchiser.this, SubmitProperty.class);
                        submitProperty.putExtra("from", "franchiser");
                        startActivity(submitProperty);
                        finish();

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





                resultObject = new JSONObject(result);



                name = resultObject.getString("name");
                String email = resultObject.getString("email");
                String phone = resultObject.getString("number");
                String password = resultObject.getString("password");
            String userId = resultObject.getString("user_id");


                SharedPreferences sharedPreferences = getSharedPreferences("franchiser", 0);
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





        } catch (JSONException e) {

            e.printStackTrace();

        }

        return returnedResult;

    }

    public void singUpScreen(){
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent franchiserRegistrationScreen =  new Intent(LoginAsFranchiser.this, FranchiserRegistration.class);
                startActivity(franchiserRegistrationScreen);
                finish();
            }
        });
    }



}
