package com.example.edoardo.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {



    ImageGridArrayAdapter oImageGridAdapter;


    public MainActivityFragment()
    {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviesfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh)
        {
            updateMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridView_movies);
        final ImageView oImageView = (ImageView) rootView.findViewById(R.id.item_imageview_movies);

        oImageGridAdapter = new ImageGridArrayAdapter(getContext());

        // for picasso
        gridview.setAdapter(oImageGridAdapter);



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {

               // Toast.makeText(getContext(), "Ciao", Toast.LENGTH_LONG).show();

                //String test = oImageGridAdapter.oListData.get(position).titleMovie;
                MovieData test = oImageGridAdapter.oListData.get(position);
                Intent intent = new Intent(getActivity(), MoviesDetailActivity.class)
                        .putExtra("com.package.MovieData", test);
                try {
                    startActivity(intent);
                }catch (Exception ex)
                {
                    Log.e("ciao", ex.getMessage());
                }

            }
        });

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }



    private void updateMovies()
    {
        FetchMovieTask movieTask = new FetchMovieTask(oImageGridAdapter);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = prefs.getString(getString(R.string.pref_sort_order_key), "");

        movieTask.execute(sort);
    }




    // Adapter for picasso
    public class ImageGridArrayAdapter extends ArrayAdapter {
        private Context context;
        private LayoutInflater inflater;

        private List<MovieData> oListData;


        public ImageGridArrayAdapter(Context context)
        {
            this(context, new ArrayList<MovieData>());
        }

        public ImageGridArrayAdapter(Context context, List<MovieData> data) {
            super(context, R.layout.image_view_movies, data);

            this.context = context;
            this.oListData = data;

            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.image_view_movies, parent, false);
            }

            Picasso
                    .with(context)
                    .load(oListData.get(position).pathImage)
                    .fit()
                    .into((ImageView) convertView);

            return convertView;
        }
    }

}



