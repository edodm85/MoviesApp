package com.example.edoardo.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ed on 25/02/2016.
 */
public class MovieData implements Parcelable
{
    String pathImage;
    String titleMovie;
    String dateMovie;
    String ratingMovie;
    String descMovie;


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
