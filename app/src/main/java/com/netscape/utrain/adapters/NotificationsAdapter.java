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
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context context;
    private List<NotificationDatamodel> list;

    public NotificationsAdapter(Context context, List<NotificationDatamodel> list) {
        this.context = context;
        this.list = list;
    }
    public NotificationsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_design, parent, false);
//        return new NotificationViewHolder(view);

        NotificationViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);

                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }
    @NonNull
    private   NotificationViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        NotificationViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.notification_design, parent, false);
        viewHolder = new   NotificationViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        NotificationDatamodel datamodel = list.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if(datamodel!=null)

        holder.notificationDatTimeTv.setText(datamodel.getCreated_at());
        holder.notificationBody.setText(datamodel.getBody());
        holder.titleNotification.setText(datamodel.getTitle());

//        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, context);
//        if (!TextUtils.isEmpty(img)) {
//            Glide.with(context).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(holder.notificationImg);
//        }
                break;
            case LOADING:
//                Do nothing
                break;

        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(NotificationDatamodel r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<NotificationDatamodel> moveResults) {
        for (NotificationDatamodel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<NotificationDatamodel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void remove(NotificationDatamodel r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
//        add(new C_ProductsSerial.Datum());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = list.size() - 1;
        NotificationDatamodel result = getItem(position);

        if (result != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public NotificationDatamodel getItem(int position) {
        return list.get(position);
    }



    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        CircleImageView notificationImg;
        MaterialTextView notificationBody, notificationDatTimeTv,titleNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationImg = itemView.findViewById(R.id.notificationImg);
            notificationBody = itemView.findViewById(R.id.notificationBody);
            notificationDatTimeTv = itemView.findViewById(R.id.notificationDatTimeTv);
            titleNotification = itemView.findViewById(R.id.titleNotification);

        }
    }
    protected class LoadingVH extends NotificationViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
