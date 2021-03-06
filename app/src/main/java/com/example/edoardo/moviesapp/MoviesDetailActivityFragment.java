package com.example.edoardo.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesDetailActivityFragment extends Fragment
{


    //private String moviesStr;
    MovieData moviesData;

    public MoviesDetailActivityFragment()
    {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_movies_detail, container, false);
        TextView textViewTitle = (TextView) root.findViewById(R.id.textViewTitle);
        TextView textViewData = (TextView) root.findViewById(R.id.textViewDate);
        TextView textViewVote = (TextView) root.findViewById(R.id.textViewRating);
        TextView textViewDesc = (TextView) root.findViewById(R.id.textViewDescription);
        ImageView imageViewT = (ImageView) root.findViewById(R.id.imageViewThumbnail);



        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();

        moviesData = (MovieData)intent.getParcelableExtra("com.package.MovieData");

        String rating = moviesData.ratingMovie  + "/10";

        textViewTitle.setText(moviesData.titleMovie);
        textViewData.setText(moviesData.dateMovie);
        textViewVote.setText(rating);
        textViewDesc.setText(moviesData.descMovie);

        String pathImage = moviesData.pathImage;



        Picasso.with(getContext())
                .load(pathImage)
                .fit()
                .into(imageViewT);


        return root;
    }
}
