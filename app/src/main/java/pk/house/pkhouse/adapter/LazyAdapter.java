package pk.house.pkhouse.adapter;

/**
 * Created by User-10 on 27-Mar-17.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import pk.house.pkhouse.loader.ImageLoader;
import pk.house.pkhouse.R;

public class LazyAdapter extends BaseAdapter {

    private int lastPosition = -1;

    private Activity activity;
    private String[] data;
    ArrayList<HashMap<String, String>> contactList;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public LazyAdapter(Activity a,ArrayList<HashMap<String, String>> contactList1) {
        activity = a;
        //data=d;
        contactList = contactList1;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return contactList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.row_listview_item, null);




        TextView tv_property_id = (TextView)vi.findViewById(R.id.tv_protperty_id);
        TextView tv_price = (TextView)vi.findViewById(R.id.tv_price);
        TextView tv_propertyTitle = (TextView)vi.findViewById(R.id.tv_property_title);
        TextView tv_propertyPropertyLandArea = (TextView)vi.findViewById(R.id.tv_land_area);
        TextView tv_propertyCity = (TextView)vi.findViewById(R.id.tv_city);
        TextView tv_propertyLocation = (TextView)vi.findViewById(R.id.tv_location);
        TextView tv_propertyPhone = (TextView)vi.findViewById(R.id.tv_contact);
        TextView tv_protperty_type = (TextView)vi.findViewById(R.id.tv_protperty_type);
        TextView tv_protperty_status = (TextView)vi.findViewById(R.id.tv_protperty_status);
        TextView tv_protperty_description = (TextView)vi.findViewById(R.id.tv_protperty_description);

        TextView tv_propertyEmail = (TextView)vi.findViewById(R.id.tv_email);

        TextView tv_property_rooms = (TextView)vi.findViewById(R.id.tv_protperty_rooms);
        TextView tv_property_floors = (TextView)vi.findViewById(R.id.tv_protperty_floors);
        TextView tv_property_bathrooms = (TextView)vi.findViewById(R.id.tv_protperty_bathrooms);
        TextView tv_property_status_prpoperty = (TextView)vi.findViewById(R.id.tv_protperty_status_property);

        ImageView image=(ImageView)vi.findViewById(R.id.image);



        String propertyID = contactList.get(position).get("property_id").toString();
        String propertyTitle = contactList.get(position).get("property_title").toString();
        String price = contactList.get(position).get("price").toString();
        String city = contactList.get(position).get("city").toString();
        String location = contactList.get(position).get("location").toString();
        String landArea = contactList.get(position).get("landArea").toString();
        String contact = contactList.get(position).get("phone").toString();
        String propertyType = contactList.get(position).get("property_type").toString();
        String propertystatus =  contactList.get(position).get("status").toString();
        String description = contactList.get(position).get("property_description").toString();
        String rooms = contactList.get(position).get("rooms").toString();
        String bathrooms = contactList.get(position).get("bathrooms").toString();
        String floors = contactList.get(position).get("floors").toString();
        String status_property = contactList.get(position).get("status_property").toString();
        String dealer_email = contactList.get(position).get("dealer_email").toString();



        tv_property_id.setText(propertyID);
        tv_propertyTitle.setText(propertyTitle);
        tv_price.setText(price);
        tv_propertyCity.setText(city);
        tv_propertyPhone.setText(contact);
        tv_propertyPropertyLandArea.setText(landArea);
        tv_protperty_type.setText(propertyType);
        tv_protperty_status.setText(propertystatus);
        tv_protperty_description.setText(description);
        tv_propertyLocation.setText(location);

        tv_propertyEmail.setText(dealer_email);

        tv_property_rooms.setText(rooms);
        tv_property_bathrooms.setText(bathrooms);
        tv_property_floors.setText(floors);
        tv_property_status_prpoperty.setText(status_property);

        imageLoader.DisplayImage(contactList.get(position).get("imageurl"), image);


        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ? R.anim.down_from_top : R.anim.wave_scale);
        vi.startAnimation(animation);
        lastPosition = position;

        //imageLoader.DisplayImage(data[position], image);
        return vi;
    }


}