package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.ViewCoachStaffListActivity;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.fragments.O_RegistrationProfile;
import com.netscape.utrain.model.ViewCoachListDataModel;
import com.netscape.utrain.response.ViewCoachListResponse;
import com.netscape.utrain.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewCoachStaffListAdapter extends RecyclerView.Adapter<ViewCoachStaffListAdapter.ViewCoachStaffListHolder> {

    Context context;
    List<ViewCoachListDataModel> list;

    public ViewCoachStaffListAdapter(Context context, List<ViewCoachListDataModel> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewCoachStaffListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_coach_list, parent, false);
        return new ViewCoachStaffListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewCoachStaffListHolder holder, int position) {

        final ViewCoachListDataModel data = list.get(position);
        Glide.with(context).load(Constants.ORG_COACH_IMAGE_BASE_URL + data.getProfile_image()).thumbnail(Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + data.getProfile_image())).into(holder.coachStaffImg);
        holder.coachStaffName.setText(data.getName());
        holder.coachStaffProfession.setText(data.getProfession());
        holder.coachStaffAdpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewCoachStaffListHolder extends RecyclerView.ViewHolder {

        CircleImageView coachStaffImg;
        MaterialTextView coachStaffName;
        MaterialTextView coachStaffProfession;
        MaterialCardView coachStaffAdpLayout;

        public ViewCoachStaffListHolder(@NonNull View itemView) {
            super(itemView);

            coachStaffImg = itemView.findViewById(R.id.coachStaffImg);
            coachStaffName = itemView.findViewById(R.id.coachStaffName);
            coachStaffProfession = itemView.findViewById(R.id.coachStaffProfession);
            coachStaffAdpLayout = itemView.findViewById(R.id.coachStaffAdpLayout);
        }
    }

}
