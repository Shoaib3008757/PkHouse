package pk.house.pkhouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

/**
 * Created by User-10 on 16-Mar-17.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;


public class SubmitThirldPage extends Fragment {

    public static EditText m_ed_location, m_ed_city, m_ed_phoneNumber, m_ed_Name, m_ed_email;
    public static Spinner sp_country, sp_city;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.submit_third_page, container, false);


        m_ed_location = (EditText) view.findViewById(R.id.m_ed_location);
        m_ed_phoneNumber = (EditText) view.findViewById(R.id.m_ed_phone_number);
        m_ed_Name = (EditText) view.findViewById(R.id.m_ed_name);
        m_ed_email = (EditText) view.findViewById(R.id.m_ed_email);
        sp_country = (Spinner) view.findViewById(R.id.sp_chose_country);
        sp_city = (Spinner) view.findViewById(R.id.sp_chose_city);

        spinnerValuesOnSelection();




        return view;
    }


    public void spinnerValuesOnSelection(){
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.chose_a_city, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(adapter);


                }

                if (position==2){



                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.chose_a_city_uae, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(adapter);


                }

                if (position==3){


                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.chose_a_city_oman, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_city.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//end of spinner values on Selection

}