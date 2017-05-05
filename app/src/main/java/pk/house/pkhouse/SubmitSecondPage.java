package pk.house.pkhouse;

/**
 * Created by User-10 on 16-Mar-17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class SubmitSecondPage extends Fragment {


    public static ImageView image1, image2, image3, image4, image5, image6;
    public static Button btImage1, btImage2, btImage3, btImage4, btImage5, btImage6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.submit_second_page, container, false);



        image1 = (ImageView) view.findViewById(R.id.image_view1);
        image2 = (ImageView) view.findViewById(R.id.image_view2);
        image3 = (ImageView) view.findViewById(R.id.image_view3);
        image4 = (ImageView) view.findViewById(R.id.image_view4);
        image5 = (ImageView) view.findViewById(R.id.image_view5);
        image6 = (ImageView) view.findViewById(R.id.image_view6);

        btImage1 = (Button) view.findViewById(R.id.bt_select_image1);
        btImage2 = (Button) view.findViewById(R.id.bt_select_image2);
        btImage3 = (Button) view.findViewById(R.id.bt_select_image3);
        btImage4 = (Button) view.findViewById(R.id.bt_select_image4);
        btImage5 = (Button) view.findViewById(R.id.bt_select_image5);
        btImage6 = (Button) view.findViewById(R.id.bt_select_image6);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btImage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return  view;
    }




}
