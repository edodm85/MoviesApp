package com.example.edoardo.moviesapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    public static String[] eatFoodyImages = {
            "http://i.imgur.com/rFLNqWI.jpg",
            "http://i.imgur.com/C9pBVt7.jpg",
            "http://i.imgur.com/rT5vXE1.jpg",
            "http://i.imgur.com/aIy5R2k.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
    };

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7,
            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7
    };

    // ImageListAdapter oImageAdapter;


    public MainActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridView_movies);

        // for picasso
        gridview.setAdapter(new ImageGridArrayAdapter(getContext(), eatFoodyImages));


        //ImageGridAdapter oTest = new ImageGridAdapter(getContext());
        //gridview.setAdapter(oTest);

        return rootView;
    }








    public class ImageGridAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater inflater;

        public ImageGridAdapter(Context c) {
            mContext = c;
            inflater = LayoutInflater.from(mContext);;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;


            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                convertView = inflater.inflate(R.layout.image_view_movies, parent, false);
                imageView = (ImageView) convertView.findViewById(R.id.item_imageview_movies);

              //*  imageView = new ImageView(mContext);
                //imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
               // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
               // imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
    }



    // Adapter for picasso
    public class ImageGridArrayAdapter extends ArrayAdapter {
        private Context context;
        private LayoutInflater inflater;

        private String[] imageUrls;

        public ImageGridArrayAdapter(Context context, String[] imageUrls) {
            super(context, R.layout.image_view_movies, imageUrls);

            this.context = context;
            this.imageUrls = imageUrls;

            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.image_view_movies, parent, false);
            }

            Picasso
                    .with(context)
                    .load(imageUrls[position])
                    .fit()
                    .into((ImageView) convertView);

            return convertView;
        }
    }




}