package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coremedia.iso.boxes.Container;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.adapters.NotificationsAdapter;
import com.netscape.utrain.databinding.FragmentNotificationBinding;
import com.netscape.utrain.databinding.OFragmentNotificationBinding;
import com.netscape.utrain.model.NotificationDatamodel;
import com.netscape.utrain.response.NotificationResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A_NotificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A_NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A_NotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String dateFormat = "";
    SimpleDateFormat dt1;
    private OFragmentNotificationBinding binding;
    private Retrofitinterface retrofitinterface;
    private RecyclerView notificationRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotificationDatamodel> list = new ArrayList<>();
    private NotificationsAdapter adapter;
    private Context context;
    private ProgressDialog progressDialog;
    private List<NotificationDatamodel> datamodel = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public A_NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A_NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A_NotificationFragment newInstance(String param1, String param2) {
        A_NotificationFragment fragment = new A_NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.o_fragment_notification, container, false);
        View view = binding.getRoot();
        layoutManager = new LinearLayoutManager(context);
        binding.notificationRecycler.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        getNotifications();

        try {
            formatDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        return inflater.inflate(R.layout.fragment_notification, container, false);
        return view;
    }

    private void getNotifications() {

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<NotificationResponse> getNotifications =
                retrofitinterface.notifications(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()));
        getNotifications.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    if (response.body().isStatus())
                        if (response.body() != null) {

                            list = response.body().getData().getData();
                            adapter = new NotificationsAdapter(context, list);
                            binding.notificationRecycler.setAdapter(adapter);
                            datamodel = response.body().getData().getData();

                            for (int i = 0; i < datamodel.size(); i++) {

                                dateFormat = list.get(i).getCreated_at();

                            }

                            if (adapter.getItemCount() > 1) {
                                binding.noNotificationsText.setVisibility(View.VISIBLE);

                            } else {
                                binding.noNotificationsText.setVisibility(View.VISIBLE);
                                binding.noNotificationsText.setText("No Notification to Display");
                            }
                        }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getDisplayableTime(int delta) {
        long difference = (Long.parseLong(dateFormat));
        Long mDate = java.lang.System.currentTimeMillis();

        if (mDate > delta) {
            difference = mDate - delta;
            final long seconds = difference / 1000;
            final long minutes = seconds / 60;
            final long hours = minutes / 60;
            final long days = hours / 24;
            final long months = days / 31;
            final long years = days / 365;

            if (seconds < 0) {
                return "not yet";
            } else if (seconds < 60) {
                return seconds == 1 ? "one second ago" : seconds + " seconds ago";
            } else if (seconds < 120) {
                return "a minute ago";
            } else if (seconds < 2700) // 45 * 60
            {
                return minutes + " minutes ago";
            } else if (seconds < 5400) // 90 * 60
            {
                return "an hour ago";
            } else if (seconds < 86400) // 24 * 60 * 60
            {
                return hours + " hours ago";
            } else if (seconds < 172800) // 48 * 60 * 60
            {
                return "yesterday";
            } else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                return days + " days ago";
            } else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {

                return months <= 1 ? "one month ago" : days + " months ago";
            } else {

                return years <= 1 ? "one year ago" : years + " years ago";
            }
        }
        return null;
    }

    public void formatDate() throws ParseException {

        String dateExp = dateFormat;

        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(dateFormat);

        // *** same for the format String below
        dt1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Date :" + dt1.format(date));

        dt1 = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Time :" + dt1.format(date));
        getDisplayableTime(Integer.parseInt(dateFormat));
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

//    public class FormateDate {
//
//        public void main(String[] args) throws ParseException {
//            dateFormat = "2017-03-08 13:27:00";
//
//            // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
//            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            Date date = dt.parse(dateFormat);
//
//            // *** same for the format String below
//            dt1 = new SimpleDateFormat("yyyy-MM-dd");
//            System.out.println("Date :"+dt1.format(date));
//
//            dt1 = new SimpleDateFormat("HH:mm:ss");
//            System.out.println("Time :"+dt1.format(date));
//        }
//
//    }

