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

public class SessionFragmentAdapter extends RecyclerView.Adapter<SessionFragmentAdapter.SessionRecyclerView> {

    Context context;
    private List<SessionFragmentModel> sessionList;

    public SessionFragmentAdapter(Context context, List<SessionFragmentModel> sessionList) {

        this.context = context;
        this.sessionList = sessionList;
    }

    @NonNull
    @Override
    public SessionRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_fragment_design, parent, false);

        return new SessionRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionRecyclerView holder, int position) {

        holder.sportName.setText(sessionList.get(position).getSportsName());

    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public class SessionRecyclerView extends RecyclerView.ViewHolder {

        MaterialTextView sportName;

        public SessionRecyclerView(@NonNull View itemView) {
            super(itemView);

            sportName = itemView.findViewById(R.id.sessionFragmentSportsName);

        }
    }
}
