package com.example.edoardo.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

        oImageGridAdapter = new ImageGridArrayAdapter(getContext());


        MovieData[] oTemp = new MovieData[eatFoodyImages.length];

        for(int i = 0; i < eatFoodyImages.length; i++) {
            oTemp[i] = new MovieData();
            oTemp[i].titleMovie = "";
            oTemp[i].dateMovie = "";
            oTemp[i].pathImage = eatFoodyImages[i];

            oImageGridAdapter.add(oTemp[i]);
        }
        // for picasso
        gridview.setAdapter(oImageGridAdapter);



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {

               // Toast.makeText(getContext(), "Ciao", Toast.LENGTH_LONG).show();

                String test = oImageGridAdapter.oListData.get(position).titleMovie;
                Intent intent = new Intent(getActivity(), MoviesDetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, test);
                startActivity(intent);
            }
        });



        //ImageGridAdapter oTest = new ImageGridAdapter(getContext());
        //gridview.setAdapter(oTest);

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }



    private void updateMovies()
    {
        FetchMovieTask movieTask = new FetchMovieTask();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = prefs.getString(getString(R.string.pref_sort_order_key), "");

        movieTask.execute(sort);
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




    public class MovieData
    {
        String pathImage;
        String titleMovie;
        String dateMovie;
    };



    // Task
    public class FetchMovieTask extends AsyncTask<String, Void, MovieData[]>
    {


        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        private MovieData[] getMoviesDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_RESULTS = "results";
            final String OWM_PATH = "poster_path";
            final String OWM_TITLE = "original_title";
            final String OWM_RELESEDATA = "release_date";

            JSONObject movieJson = new JSONObject(moviesJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);



            MovieData[] resultStrs = new MovieData[movieArray.length()];
            for(int i = 0; i < movieArray.length(); i++)
            {
                resultStrs[i] = new MovieData();

                // For now, using the format "Day, description, hi/low"
                String path;
                String title;
                String date;

                // Get the JSON object representing the day
                JSONObject moviePosition = movieArray.getJSONObject(i);

                path = moviePosition.getString(OWM_PATH);
                title = moviePosition.getString(OWM_TITLE);
                date = moviePosition.getString(OWM_RELESEDATA);

                resultStrs[i].pathImage = "http://image.tmdb.org/t/p/w185/" + path;
                resultStrs[i].dateMovie = date;
                resultStrs[i].titleMovie = title;

                Log.d(LOG_TAG, resultStrs[i].pathImage + " - " + date + " - " + title);
            }
            return resultStrs;

        }
        @Override
        protected MovieData[] doInBackground(String... params)
        {
            if(params.length == 0)
                return null;


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;


            try {
                final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_BY = "sort_by";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_BY, params[0])
                        .appendQueryParameter(APPID_PARAM, Key.keyMovies)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMoviesDataFromJson(movieJsonStr);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(MovieData[] result)
        {
            if (result != null)
            {
                try {
                    oImageGridAdapter.clear();
                    for (int i = 0; i < result.length; i++)
                    {
                        oImageGridAdapter.add(result[i]);
                    }

                }catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
                // New data is back from the server.  Hooray!
            }
        }



    }



}