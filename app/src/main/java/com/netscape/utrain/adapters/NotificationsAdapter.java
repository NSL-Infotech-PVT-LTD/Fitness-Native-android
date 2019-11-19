package com.netscape.utrain.adapters;

import android.content.Context;
import android.icu.text.Transliterator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.model.NotificationDatamodel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private Context context;
    private List<NotificationDatamodel> list;

    public NotificationsAdapter(Context context, List<NotificationDatamodel> list) {

        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_design, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        NotificationDatamodel datamodel = list.get(position);
        holder.notificationBody.setText(datamodel.getBody());

        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, context);
        if (!TextUtils.isEmpty(img)) {
            Glide.with(context).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(holder.notificationImg);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        CircleImageView notificationImg;
        MaterialTextView notificationBody, justNowText;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationImg = itemView.findViewById(R.id.notificationImg);
            notificationBody = itemView.findViewById(R.id.notificationBody);
            justNowText = itemView.findViewById(R.id.justNowText);
        }
    }
}
