package pk.house.pkhouse;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    EditText contactUsNAme, contactUsEmail, contactUsPhoe;
    EditText contactUsMessage;
    Button btSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        init();
        sendMessageButtonHandler();
    }

    public void init(){

        contactUsNAme = (EditText) findViewById(R.id.et_contact_us_name);
        contactUsEmail = (EditText) findViewById(R.id.et_contact_us_email);
        contactUsPhoe = (EditText) findViewById(R.id.et_contact_us_phone);
        contactUsMessage = (EditText) findViewById(R.id.et_about_us_message) ;
        btSendMessage = (Button) findViewById(R.id.bt_send_message);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ContactUs.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle("Contact Us");
    }

    public void sendMessageButtonHandler(){

        btSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tName = contactUsNAme.getText().toString();
                String tEmail = contactUsEmail.getText().toString();
                String tPhone = contactUsPhoe.getText().toString();
                String tMessage = contactUsMessage.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (tName.length()==0){
                    Toast.makeText(ContactUs.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (tEmail.length()==0){
                    Toast.makeText(ContactUs.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if (tPhone.length()==0){
                    Toast.makeText(ContactUs.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
                }
                else if (tMessage.length()==0){
                    Toast.makeText(ContactUs.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                }
                else if (!tEmail.matches(emailPattern)){

                    Toast.makeText(ContactUs.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

                }
                else {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"aratel1979@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Message From PK.ESTAE APP USER");
                    i.putExtra(Intent.EXTRA_TEXT, "Name: " + tName
                                + "\n" + "Email: = " + tEmail
                                + "\n" + "Phone: = " + tPhone
                                + "\n" + "Message: = " + tMessage);

                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ContactUs.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                    Log.e("TAG", "SENDING MESSAGE...");




                }




            }
        });

    }
}
