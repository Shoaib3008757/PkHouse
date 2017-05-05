package pk.house.pkhouse.adapter;



/**
 * Created by User-10 on 28-Mar-17.
 */
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import pk.house.pkhouse.loader.ImageLoader;
import pk.house.pkhouse.R;

public class RecyclicAdapter extends RecyclerView.Adapter<RecyclicAdapter.MyViewHolder> {


    ArrayList<HashMap<String, String>> contactList;
    private String[] data;
    public ImageLoader imageLoader;
    private Activity activity;
    public ImageView singleImage;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
            singleImage = (ImageView) view.findViewById(R.id.single_image);

        }
    }


   // public RecyclicAdapter(Activity a, ArrayList<HashMap<String, String>> contactList1) {
   public RecyclicAdapter(Activity a, String[] contactList1) {
        this.data = contactList1;
        activity = a;
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_image_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       // imageLoader.DisplayImage(contactList.get(position).get("imageurl"), singleImage);
        imageLoader.DisplayImage(data[position], singleImage);
    }

    @Override
    public int getItemCount() {
        return data.length;
        //return contactList.size();
    }
}

