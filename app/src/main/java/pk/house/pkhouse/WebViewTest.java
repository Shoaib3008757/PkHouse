package pk.house.pkhouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.*;

public class WebViewTest extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);

        android.webkit.WebView image = (android.webkit.WebView) findViewById(R.id.web_veiw_image);

        image.setBackgroundColor(0);
        image.loadDataWithBaseURL("", "<img src='http://www.pk.estate/frontend/propertyimages/14860182941.jpg'/>",
                "text/html", "UTF-8", "");
    }
}
