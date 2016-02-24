package com.example.edoardo.moviesapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesDetailActivityFragment extends Fragment
{


    private String moviesStr;

    public MoviesDetailActivityFragment()
    {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_movies_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            moviesStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) root.findViewById(R.id.textViewTitle))
                    .setText(moviesStr);
        }

        return root;
    }
}
