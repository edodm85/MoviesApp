package com.example.edoardo.moviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute();

        // for picasso
        //gridview.setAdapter(new ImageGridArrayAdapter(getContext(), eatFoodyImages));


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




    // Task
    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {


        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        private String[] getMoviesDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_RESULTS = "results";
            final String OWM_PATH = "poster_path";
            final String OWM_TITLE = "original_title";
            final String OWM_RELESEDATA = "release_date";

            JSONObject movieJson = new JSONObject(moviesJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(OWM_RESULTS);



            String[] resultStrs = new String[movieArray.length()];
            for(int i = 0; i < movieArray.length(); i++)
            {
                // For now, using the format "Day, description, hi/low"
                String path;
                String title;
                String date;

                // Get the JSON object representing the day
                JSONObject moviePosition = movieArray.getJSONObject(i);

                path = moviePosition.getString(OWM_PATH);

                title = moviePosition.getString(OWM_TITLE);

                date = moviePosition.getString(OWM_RELESEDATA);

                resultStrs[i] = path + " - " + title + " - " + date;
            }
            return resultStrs;

        }
        @Override
        protected String[] doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            /*if (params.length == 0) {
                return null;
            }*/

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            String format = "popularity.desc";


            try {
                final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_BY = "sort_by";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_BY, format)
                        .appendQueryParameter(APPID_PARAM, Key.keyMovies)
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, builtUri.toString());


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
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
             /*   mForecastAdapter.clear();
                for(String dayForecastStr : result) {
                    mForecastAdapter.add(dayForecastStr);
                }*/
                // New data is back from the server.  Hooray!
            }
        }



    }



}