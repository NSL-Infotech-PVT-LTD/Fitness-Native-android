package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coremedia.iso.boxes.Container;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.BookingDetails;
import com.netscape.utrain.activities.SpaceBookingActivity;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.adapters.NotificationsAdapter;
import com.netscape.utrain.adapters.O_EventListAdapter;
import com.netscape.utrain.databinding.FragmentNotificationBinding;
import com.netscape.utrain.databinding.OFragmentNotificationBinding;
import com.netscape.utrain.model.A_SpaceListModel;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.model.AthleteSpaceBookList;
import com.netscape.utrain.model.DaysModel;
import com.netscape.utrain.model.EventBookingModel;
import com.netscape.utrain.model.NotificationDatamodel;
import com.netscape.utrain.model.NotificationPageModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.response.NotificationResponse;
import com.netscape.utrain.response.O_EventListResponse;
import com.netscape.utrain.response.SessionDetailResponse;
import com.netscape.utrain.response.SpaceDetailResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PaginationScrollListener;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class A_NotificationFragment extends Fragment implements NotificationsAdapter.SendToSelected {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private int TOTAL_PAGES;
    int page=0;
    private String currentPage="1";
    private int getItemPerPage;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String dateFormat = "";
    SimpleDateFormat dt1;
    private OFragmentNotificationBinding binding;
    private Retrofitinterface retrofitinterface;
    private RecyclerView notificationRecycler;
    private LinearLayoutManager layoutManager;
    private List<NotificationDatamodel> list = new ArrayList<>();
    private NotificationsAdapter adapter;
    private Context context;
    private ProgressDialog progressDialog;
    private List<NotificationDatamodel> datamodel = new ArrayList<>();
    private ArrayList<String>data;
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
        recyclerFunc(layoutManager);

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
                retrofitinterface.notifications(Constants.CONTENT_TYPE, currentPage,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()));
        getNotifications.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    if (response.body().isStatus())
                        if (response.body() != null) {
                            list = response.body().getData().getData();
                            if (list !=null && list.size()>0){
                                binding.noImgNotification.setVisibility(View.GONE);

                                adapter = new NotificationsAdapter(getContext(),A_NotificationFragment.this);
                                binding.notificationRecycler.setAdapter(adapter);
                                List<NotificationDatamodel> results = fetchNotificationResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = response.body().getData().getPer_page();
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                adapter.addAll(results);
                                if (page < TOTAL_PAGES)
                                    adapter.addLoadingFooter();
                                else isLastPage = true;

                            }else {
                                binding.noImgNotification.setVisibility(View.VISIBLE);
                            }


//                            adapter = new NotificationsAdapter(context, list);
//                            binding.notificationRecycler.setAdapter(adapter);
//                            datamodel = response.body().getData().getData();



//                            data.addAll(response.body().getData());
//                            eventData.addAll(response.body().getData().getData());
//                            currentEventAdapter = new O_EventListAdapter(getContext(), eventData, upcomg);
//                            binding.eventListRecycler.setAdapter(currentEventAdapter);








//                            for (int i = 0; i < datamodel.size(); i++) {
//
//                                dateFormat = list.get(i).getCreated_at();
//
//                            }

//                            if (adapter.getItemCount() > 1) {
//                                binding.noNotificationsText.setVisibility(View.VISIBLE);
//
//                            } else {
//                                binding.noNotificationsText.setVisibility(View.VISIBLE);
//                                binding.noNotificationsText.setText("No Notification to Display");
//                            }
                        }else {
                            binding.noImgNotification.setVisibility(View.VISIBLE);

                        }
                }else {
                    binding.noImgNotification.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject jsonObject=jObjError.getJSONObject("error");
                        String code=jsonObject.getString("code");
                        if (!TextUtils.isEmpty(code)) {
                            if (Integer.parseInt(code) == 401) {
                                CommonMethods.invalidAuthToken(getContext(), getActivity());
                            }
                        }
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

    }
    private void getNotificationsNextPage() {

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<NotificationResponse> getNotifications =
                retrofitinterface.notifications(Constants.CONTENT_TYPE, currentPage,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()));
        getNotifications.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    if (response.body().isStatus())
                        if (response.body() != null) {
                            list = response.body().getData().getData();
                            if (list !=null && list.size()>0){
                                binding.noImgNotification.setVisibility(View.GONE);

                                adapter.removeLoadingFooter();
                                isLoading = false;

                                List<NotificationDatamodel> results = fetchNotificationResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = response.body().getData().getPer_page();

                                adapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page != TOTAL_PAGES)
                                    adapter.addLoadingFooter();
                                else isLastPage = true;





                            }else {
                                binding.noImgNotification.setVisibility(View.VISIBLE);
                            }


//                            adapter = new NotificationsAdapter(context, list);
//                            binding.notificationRecycler.setAdapter(adapter);
//                            datamodel = response.body().getData().getData();



//                            data.addAll(response.body().getData());
//                            eventData.addAll(response.body().getData().getData());
//                            currentEventAdapter = new O_EventListAdapter(getContext(), eventData, upcomg);
//                            binding.eventListRecycler.setAdapter(currentEventAdapter);








//                            for (int i = 0; i < datamodel.size(); i++) {
//
//                                dateFormat = list.get(i).getCreated_at();
//
//                            }

//                            if (adapter.getItemCount() > 1) {
//                                binding.noNotificationsText.setVisibility(View.VISIBLE);
//
//                            } else {
//                                binding.noNotificationsText.setVisibility(View.VISIBLE);
//                                binding.noNotificationsText.setText("No Notification to Display");
//                            }
                        }else {
                            binding.noImgNotification.setVisibility(View.VISIBLE);

                        }
                }else {
                    binding.noImgNotification.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

    }

    private List<NotificationDatamodel> fetchNotificationResults(Response<NotificationResponse> response) {
        NotificationResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
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


    private void recyclerFunc(LinearLayoutManager layoutManager) {
        binding.notificationRecycler.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (! TextUtils.isEmpty(currentPage)) {
                    page = Integer.parseInt(currentPage);
                }
                page += 1;
                currentPage=page+"";

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getNotificationsNextPage();

//                        if (count==1)
//                            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
//                                a_NextPageUpcommingEvents();
//                            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
//                                getCoachUpcommingEventsNextPage();
//                            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
//                                getUpcommingEventsNextPage();
//                        if (count==2)
//                            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
//                                a_GetNextPageUpcommingSession();
//                            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
//                                getNextUpcommingSession();
//                            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
//                                getCoachUpcommingSessionNextPage();
//
////                        getNextUpcommingSession();
//                        if (count==3)
//                            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
//                                a_NextPageUpcommingSpaces();
//                            else
//                                getCoachSpaceBookingsNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }
    @Override
    public void sendType(String event,String id,String targetType) {

        if (event.equalsIgnoreCase("event")){
            hitEventDetailAPI(id);
        }
        if (event.equalsIgnoreCase("session")){
            getSessionDetails(id);
        }
        if (event.equalsIgnoreCase("space")){
            hitSpaceDetailAPI(id);
        }
        if (event.equalsIgnoreCase("booking")){
            if (targetType.equalsIgnoreCase("event")){
//                hitEventDetailAPI(id);
                Intent topCoachesDetails = new Intent(context, BookingDetails.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID, id);
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.EVENT);
//                        topCoachesDetails.putExtra(Constants.STATUS, status);
                context.startActivity(topCoachesDetails);
            }
            if (targetType.equalsIgnoreCase("session")){
//                getSessionDetails(id);
                Intent topCoachesDetails = new Intent(context, BookingDetails.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID, id);
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SESSION);
//                topCoachesDetails.putExtra(Constants.STATUS, status);
                context.startActivity(topCoachesDetails);
            }
            if (targetType.equalsIgnoreCase("space")){
                Intent topCoachesDetails = new Intent(context, BookingDetails.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID, id);
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SPACE);
//                topCoachesDetails.putExtra(Constants.STATUS, status);
                context.startActivity(topCoachesDetails);
            }



//            navView.getMenu().findItem(R.id.navigation_home).setChecked(true);
//            O_HistoryFragment fragment=new O_HistoryFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Organizer)) {
//                fragmentTransaction.replace(R.id.org_fragment, fragment);
//                ((OrgHomeScreen) context).orgNavView.getMenu().findItem(R.id.navigation_running).setChecked(true);
//            }
//            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Athlete)) {
//                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
//                ((AthleteHomeScreen) context).navView.getMenu().findItem(R.id.navigation_running).setChecked(true);
//            }
//            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Coach)) {
//                fragmentTransaction.replace(R.id.coachNavFragment, fragment);
//                ((CoachDashboard) context).orgNavView.getMenu().findItem(R.id.navigation_running).setChecked(true);
//            }
//
//            fragmentTransaction.commit();
        }



    }
    public void hitEventDetailAPI(String id) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<EventBookingModel> signUpAthlete = retrofitinterface.eventDetail("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, id );
        signUpAthlete.enqueue(new Callback<EventBookingModel>() {
            @Override
            public void onResponse(Call<EventBookingModel> call, Response<EventBookingModel> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {

                            Intent intent = new Intent(context, EventDetail.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("eventName", response.body().getData().getName());
                            intent.putExtra("eventVenue", response.body().getData().getLocation());
                            intent.putExtra("event_id", response.body().getData().getId()+"");
                            intent.putExtra("guest_allowed", response.body().getData().getGuest_allowed() + "");
                            intent.putExtra("guest_allowed_left", response.body().getData().getGuest_allowed_left() + "");
                            intent.putExtra("eventDate", response.body().getData().getStart_date());
                            intent.putExtra("eventEndDate", response.body().getData().getEnd_time());

                            intent.putExtra("eventTime", response.body().getData().getStart_time());
                            intent.putExtra("eventEndTime", response.body().getData().getEnd_time());
                            intent.putExtra("eventDescription", response.body().getData().getDescription());
                            intent.putExtra("image_url", Constants.IMAGE_BASE_EVENT);
                            intent.putExtra("from", "events");
                            intent.putExtra("capacity", response.body().getData().getGuest_allowed());
                            intent.putExtra("gmapLat", response.body().getData().getLatitude()+"");
                            intent.putExtra("gmapLong", response.body().getData().getLongitude()+"");
                            Bundle b = new Bundle();
                            b.putString("Array", response.body().getData().getImages());
                            intent.putExtras(b);
                            context.startActivity(intent);

                        }
                    }
                } else {
                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.maineventBooking, errorMessage, BaseTransientBottomBar.LENGTH_LONG).show();
                        Toast.makeText(getContext(), ""+ errorMessage, Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                        Toast.makeText(getContext(), ""+ e, Toast.LENGTH_SHORT).show();

//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<EventBookingModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), ""+ getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }

    public void getSessionDetails(String id) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<SessionDetailResponse> signUpAthlete = retrofitinterface.getSessionDetails("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, id);
        signUpAthlete.enqueue(new Callback<SessionDetailResponse>() {
            @Override
            public void onResponse(Call<SessionDetailResponse> call, Response<SessionDetailResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Intent intent = new Intent(context, EventDetail.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("eventName", response.body().getData().getName());
                            intent.putExtra("guest_allowed", response.body().getData().getGuest_allowed() + "");
                            intent.putExtra("guest_allowed_left", response.body().getData().getGuest_allowed_left() + "");
                            intent.putExtra("eventVenue", response.body().getData().getLocation());
                            intent.putExtra("eventTime", response.body().getData().getStart_time());
                            intent.putExtra("eventEndTime", response.body().getData().getEnd_time());
                            intent.putExtra("eventDate", response.body().getData().getStart_date());
                            intent.putExtra("eventEndDate", response.body().getData().getEnd_date());
                            intent.putExtra("eventDescription", response.body().getData().getDescription());
                            intent.putExtra("image_url", Constants.IMAGE_BASE_SESSION);
                            intent.putExtra("event_id", response.body().getData().getId()+"");
                            intent.putExtra("from", "sessions");
                            intent.putExtra("gmapLat", response.body().getData().getLatitude()+"");
                            intent.putExtra("gmapLong", response.body().getData().getLongitude()+"");
                            Bundle b = new Bundle();
                            b.putString("Array", response.body().getData().getImages());
                            intent.putExtras(b);
                            context.startActivity(intent);

                        }
                    }
                } else {
//                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.maineventBooking, errorMessage, BaseTransientBottomBar.LENGTH_LONG).show();
                        Toast.makeText(getContext(), ""+errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<SessionDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                Toast.makeText(getContext(), ""+ getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public  void hitSpaceDetailAPI(String id) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<SpaceDetailResponse> signUpAthlete = retrofitinterface.spaceDetail("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, id);
        signUpAthlete.enqueue(new Callback<SpaceDetailResponse>() {
            @Override
            public void onResponse(Call<SpaceDetailResponse> call, Response<SpaceDetailResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Intent intent = new Intent(context, EventDetail.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("eventName", response.body().getData().getName());
                            intent.putExtra("eventVenue", response.body().getData().getLocation());
                            intent.putExtra("eventTime", response.body().getData().getOpen_hours_from());
                            intent.putExtra("eventALLImages", response.body().getData().getImages());
                            intent.putExtra("eventEndTime", response.body().getData().getOpen_hours_to());
//                            intent.putExtra("eventDate", response.body().getData().getAvailability_week());
                            intent.putExtra("image_url", Constants.IMAGE_BASE_PLACE);
                            intent.putExtra("event_id", response.body().getData().getId() + "");
                            intent.putExtra("from", "places");
                            intent.putExtra("desc", response.body().getData().getDescription());
                            intent.putExtra("gmapLat", response.body().getData().getLatitude()+"");
                            intent.putExtra("gmapLong", response.body().getData().getLongitude()+"");
                            Bundle b = new Bundle();
                            b.putString("Array", response.body().getData().getImages());
                            intent.putExtras(b);
                            data=new ArrayList<>();
                            data=(ArrayList<String>) response.body().getData().getAvailability_week();
                            intent.putStringArrayListExtra(Constants.SPACE_DATA,data);
                            context.startActivity(intent);

                        }
                    }
                } else {
                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.maineventBooking, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                        Toast.makeText(context, ""+errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(context, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();

//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SpaceDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }
}



