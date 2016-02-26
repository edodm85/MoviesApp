package com.example.edoardo.moviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Edoardo on 26/02/2016.
 */
public class FetchMovieTask extends AsyncTask<String, Void, MovieData[]>
{


    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private MainActivityFragment.ImageGridArrayAdapter oImageGridAdapter;



    public FetchMovieTask(MainActivityFragment.ImageGridArrayAdapter oData)
    {
        oImageGridAdapter = oData;
    }


    private MovieData[] getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_RESULTS = "results";
        final String OWM_PATH = "poster_path";
        final String OWM_TITLE = "original_title";
        final String OWM_RELESEDATA = "release_date";
        final String OWM_VOTE = "vote_average";
        final String OWM_OVERVIEW = "overview";
        final String OWM_ID = "id";

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
            String rating;
            String desc;
            String id;

            // Get the JSON object representing the day
            JSONObject moviePosition = movieArray.getJSONObject(i);

            path = moviePosition.getString(OWM_PATH);
            title = moviePosition.getString(OWM_TITLE);
            date = moviePosition.getString(OWM_RELESEDATA);
            rating = moviePosition.getString(OWM_VOTE);
            desc = moviePosition.getString(OWM_OVERVIEW);
            id = moviePosition.getString(OWM_ID);

            resultStrs[i].pathImage = "http://image.tmdb.org/t/p/w185/" + path;
            resultStrs[i].dateMovie = date;
            resultStrs[i].titleMovie = title;
            resultStrs[i].ratingMovie = rating;
            resultStrs[i].descMovie = desc;
            resultStrs[i].idMovie = id;

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
            try {
                urlConnection.connect();
            } catch (Exception e)
            {
                Log.e(LOG_TAG, "Connection Error ", e);
                return null;
            }

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

