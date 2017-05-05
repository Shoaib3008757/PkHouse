package pk.house.pkhouse;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        init();
    }

    public void init(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(AboutUs.this ,R.color.colorSkyBlue)));
        getSupportActionBar().setTitle(R.string.tv_about_us);
        getSupportActionBar().setIcon(R.drawable.aratel_logo);
    }
}
