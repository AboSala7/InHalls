package com.tatbiq.abosala7.inhalls.halldetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tatbiq.abosala7.inhalls.HallsActivity;
import com.tatbiq.abosala7.inhalls.R;

import java.util.ArrayList;
import java.util.Arrays;

public class HallDetailsActivity extends AppCompatActivity {

    private TextView hallName,allCapacity,menC,womenC,s1,s2,s3,s4,s5,
            s6,s7,s8,s9,s10,s11,s12;
    private ViewPager hallImages;
    String name,capacity,menCapacity,womenCapacity, photos,services;
    private Intent intent;
    Context context;
    String[]vpImages,allServices;
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_details);

        hallName = (TextView)findViewById(R.id.textview_halldetail);
        hallImages = (ViewPager)findViewById(R.id.viewpager_halldetail);
        allCapacity = (TextView)findViewById(R.id.textcapacity_HallDetail);
        menC =(TextView)findViewById(R.id.textmencapacity_halldetail);
        womenC =(TextView)findViewById(R.id.womenCapacity_halldetail);
        listView =(ListView)findViewById(R.id.listview_halldetails);
        intent = getIntent();
        photos = "";

        name = intent.getStringExtra("name");
        capacity = intent.getStringExtra("capacity");
        menCapacity = intent.getStringExtra("menCapacity");
        womenCapacity = intent.getStringExtra("womenCapacity");
        photos = intent.getStringExtra("photos");
        services = intent.getStringExtra("services");

        //update ui
        hallName.setText(name);

        if (!photos.equals("")){
            vpImages = photos.split(",");
        }
        else {
            vpImages = new String[]{"http://admin.inhalls.com//Content/photos/110/2017_1753822e-c6e4-46a7-b7ab-a2c6f84b577e.jpg"};
        }

        ImageAdapter adapter = new ImageAdapter(this,vpImages); //Here we are defining the Imageadapter object

        hallImages.setAdapter(adapter); // Here we are passing and setting the adapter for the images

        allCapacity.setText("السعه الإجمالية : "+capacity);
        if (menCapacity != null){
            menC.setText("الرجال : "+menCapacity);
        }
        else {
            menC.setText("");
        }

        if (womenCapacity != null){
            womenC.setText("النساء : "+womenCapacity);
        }
        else {
            womenC.setText("");
        }

        allServices = services.split(",");

        ArrayList myArrayList = new ArrayList<>(Arrays.asList(allServices));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                 myArrayList);

        listView.setAdapter(arrayAdapter);




    }

    public class ImageAdapter extends PagerAdapter {
        String[] imags;
        Context context;

        public ImageAdapter(Context context,String[] imags) {
            this.context =context;
            this.imags = imags;
        }

        @Override
        public int getCount() {
            return imags.length;
        }

        @Override
        public boolean isViewFromObject( View view,  Object object) {
            return view == ((ImageView) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);

            if (imags.length > 1) {
                Glide.with(getApplicationContext()).load(imags[position]).into(imageView);
            }
            else {
                Glide.with(getApplicationContext()).load(R.drawable.logoar).into(imageView);
            }

            ((ViewPager) container).addView(imageView, 0);

            return imageView;

             }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}
