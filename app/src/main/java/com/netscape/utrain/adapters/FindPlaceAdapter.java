package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;

import java.util.List;

public class FindPlaceAdapter extends RecyclerView.Adapter<FindPlaceAdapter.FindPlaceHolder> {

    Context context;
    List<String> places;
    public FindPlaceAdapter(Context context, List<String> places){
        this.context = context;
        this.places = places;
    }


    @NonNull
    @Override
    public FindPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_a_place_layout_design, parent, false);

        return new FindPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindPlaceHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class FindPlaceHolder extends RecyclerView.ViewHolder {

        MaterialTextView placeName;


        public FindPlaceHolder(@NonNull View itemView) {
            super(itemView);

            placeName = itemView.findViewById(R.id.placeNameInfoTv);

        }
    }
}
