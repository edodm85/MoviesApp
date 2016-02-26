package com.example.edoardo.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edo on 25/02/2016.
 */
public class MovieData implements Parcelable
{
    String pathImage;
    String titleMovie;
    String dateMovie;
    String ratingMovie;
    String descMovie;
    String idMovie;
    String runtimeMovie;


    public MovieData()
    {

    }

    public MovieData(Parcel in)
    {
        readFromParcel(in);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // We just need to write each field into the
        // parcel. When we read from parcel, they
        // will come back in the same order
        dest.writeString(pathImage);
        dest.writeString(titleMovie);
        dest.writeString(dateMovie);
        dest.writeString(ratingMovie);
        dest.writeString(descMovie);
        dest.writeString(idMovie);
        dest.writeString(runtimeMovie);
    }

    /**
     *
     * Called from the constructor to create this
     * object from a parcel.
     *
     * @param in parcel from which to re-create object
     */
    private void readFromParcel(Parcel in) {

        // We just need to read back each
        // field in the order that it was
        // written to the parcel
        pathImage = in.readString();
        titleMovie = in.readString();
        dateMovie = in.readString();
        ratingMovie = in.readString();
        descMovie = in.readString();
        idMovie = in.readString();
        runtimeMovie = in.readString();
    }


    /**
     *
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays.
     *
     * This also means that you can use use the default
     * constructor to create the object and use another
     * method to hyrdate it as necessary.
     *
     * I just find it easier to use the constructor.
     * It makes sense for the way my brain thinks ;-)
     *
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

}




/*

http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[KEY]

  {
   "page":1,
   "results":[
      {
         "poster_path":"\/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",
         "adult":false,
         "overview":"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.",
         "release_date":"2016-02-09",
         "genre_ids":[
            28,
            12,
            35
         ],
         "id":293660,
         "original_title":"Deadpool",
         "original_language":"en",
         "title":"Deadpool",
         "backdrop_path":"\/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg",
         "popularity":64.839752,
         "vote_count":1318,
         "video":false,
         "vote_average":7.33
      },



http://api.themoviedb.org/3/movie/293660?api_key=[KEY]

    {
       "adult":false,
       "backdrop_path":"/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg",
       "belongs_to_collection":null,
       "budget":58000000,
       "genres":[
          {
             "id":28,
             "name":"Action"
          },
          {
             "id":12,
             "name":"Adventure"
          },
          {
             "id":35,
             "name":"Comedy"
          }
       ],
       "homepage":"http://www.foxmovies.com/movies/deadpool",
       "id":293660,
       "imdb_id":"tt1431045",
       "original_language":"en",
       "original_title":"Deadpool",
       "overview":"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.",
       "popularity":63.839752,
       "poster_path":"/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",
       "production_companies":[
          {
             "name":"Twentieth Century Fox Film Corporation",
             "id":306
          },
          {
             "name":"Marvel Entertainment, LLC",
             "id":325
          },
          {
             "name":"Marvel Enterprises",
             "id":19551
          }
       ],
       "production_countries":[
          {
             "iso_3166_1":"US",
             "name":"United States of America"
          }
       ],
       "release_date":"2016-02-09",
       "revenue":0,
       "runtime":108,
       "spoken_languages":[
          {
             "iso_639_1":"en",
             "name":"English"
          }
       ],
       "status":"Released",
       "tagline":"Witness the beginning of a happy ending",
       "title":"Deadpool",
       "video":false,
       "vote_average":7.3,
       "vote_count":1324
    }


    http://api.themoviedb.org/3/movie/293660/videos?api_key=[KEY]

    {
       "id":293660,
       "results":[
          {
             "id":"56c4cb4bc3a3680d57000560",
             "iso_639_1":"en",
             "key":"7jIBCiYg58k",
             "name":"Trailer",
             "site":"YouTube",
             "size":1080,
             "type":"Trailer"
          }
       ]
    }

 */