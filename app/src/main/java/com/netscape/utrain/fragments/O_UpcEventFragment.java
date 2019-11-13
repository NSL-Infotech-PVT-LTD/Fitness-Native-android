package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.A_EventListAdapter;
import com.netscape.utrain.adapters.A_SessionListAdapter;
import com.netscape.utrain.adapters.A_SpaceListAdapter;
import com.netscape.utrain.adapters.C_EventListAdapter;
import com.netscape.utrain.adapters.C_SessionListAdapter;
import com.netscape.utrain.adapters.C_SpaceListAdapter;
import com.netscape.utrain.adapters.O_EventListAdapter;
import com.netscape.utrain.adapters.O_SessionListAdapter;
import com.netscape.utrain.adapters.O_SpaceListAdapter;
import com.netscape.utrain.databinding.FragmentOEventListBinding;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.AthleteSessionBookList;
import com.netscape.utrain.model.AthleteSpaceBookList;
import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.model.C_SessionListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.response.C_EventListResponse;
import com.netscape.utrain.response.C_SessionListResponse;
import com.netscape.utrain.response.O_EventListResponse;
import com.netscape.utrain.response.O_SessionListResponse;
import com.netscape.utrain.response.O_SpaceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class O_UpcEventFragment extends Fragment implements A_SpaceListAdapter.onSpaceClick, A_SessionListAdapter.onSessionClick, A_EventListAdapter.onEventClick {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static int count = 1;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private FragmentOEventListBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private O_EventListAdapter currentEventAdapter;
    private O_SpaceListAdapter currentSpaceAdapter;
    private O_SessionListAdapter currentSessionAdapter;

    private A_EventListAdapter a_EventAdapter;
    private A_SpaceListAdapter a_SpaceAdapter;
    private A_SessionListAdapter a_SessionAdapter;

    private List<O_EventDataModel> eventData;
    private List<O_SessionDataModel> sessionData;
    private List<O_SpaceDataModel> spaceData;

    private List<AthleteBookListModel.DataBeanX.DataBean> a_eventData;
    private List<AthleteSessionBookList.DataBeanX.DataBean> a_sessionData;
    private List<AthleteSpaceBookList.DataBeanX.DataBean> a_spaceData;
    private C_EventListAdapter c_EventAdapter;
    private C_SpaceListAdapter c_SpaceAdapter;
    private C_SessionListAdapter c_SessionAdapter;

    private List<C_EventDataListModel> c_eventData;
    private List<C_SessionListModel> c_sessionData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ConstraintLayout userBottomSheeet;
    private BottomSheetBehavior sheetBehavior;
    private OnFragmentInteractionListener mListener;

    private String upcomg = "upcoming";

    private MaterialTextView userName, bookingIdText, bookingPlaceName, eventText, bookingDateText, ti_locationText, ti_Booking_Ticket,
            ti_TotalTicketPrice, ti_TotalPrice, ti_tax, totalAmount;
    private ImageView customerImage,ti_tickets;

    public O_UpcEventFragment() {
        // Required empty public constructor
    }


    public static O_UpcEventFragment newInstance(String param1, String param2) {
        O_UpcEventFragment fragment = new O_UpcEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_o__event_list, container, false);
        View view = binding.getRoot();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        userName = view.findViewById(R.id.userName);
        bookingIdText = view.findViewById(R.id.bookingIdText);
        bookingPlaceName = view.findViewById(R.id.bookingPlaceName);
        eventText = view.findViewById(R.id.eventText);
        bookingDateText = view.findViewById(R.id.bookingDateText);
        ti_locationText = view.findViewById(R.id.ti_locationText);
        ti_tickets = view.findViewById(R.id.ti_tickets);
        ti_Booking_Ticket = view.findViewById(R.id.ti_Booking_Ticket);
        ti_TotalTicketPrice = view.findViewById(R.id.ti_TotalTicketPrice);
        ti_TotalPrice = view.findViewById(R.id.ti_TotalPrice);
        ti_tax = view.findViewById(R.id.ti_tax);
        totalAmount = view.findViewById(R.id.totalAmount);
        customerImage = view.findViewById(R.id.customerImage);
        layoutManager = new LinearLayoutManager(getContext());
        binding.eventListRecycler.setLayoutManager(layoutManager);
        userBottomSheeet = view.findViewById(R.id.userBottomSheeet);
        sheetBehavior = BottomSheetBehavior.from(userBottomSheeet);
        bottomSheetBehavior_sort();

        if (count == 1) {
            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
                getUpcommingEvents();
            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
                a_getUpcommingEvents();
            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
                getCoachUpcommingEvents();
        } else if (count == 2) {

            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
                getUpcommingSession();
            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
                getCoachUpcommingSession();
            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
                a_getUpcommingSession();
        } else if (count == 3) {

            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
                getCoachSpaceBookings();
            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
                a_getUpcommingSpaces();
            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
                getCoachSpaceBookings();
        }

        return view;
    }

    private void bottomSheetUpDown_address() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void bottomSheetBehavior_sort() {

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

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

    public void getUpcommingEvents() {
        progressDialog.show();
        Call<O_EventListResponse> call = retrofitinterface.getOrgEentList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, upcomg);
        call.enqueue(new Callback<O_EventListResponse>() {
            @Override
            public void onResponse(Call<O_EventListResponse> call, Response<O_EventListResponse> response) {
                if (response.body() != null) {
                    eventData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            eventData.addAll(response.body().getData().getData());
                            currentEventAdapter = new O_EventListAdapter(getContext(), eventData, upcomg);
                            binding.eventListRecycler.setAdapter(currentEventAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_EventListResponse> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUpcommingSpaces() {
        progressDialog.show();
        Call<O_SpaceListResponse> call = retrofitinterface.getOrgSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "");
        call.enqueue(new Callback<O_SpaceListResponse>() {
            @Override
            public void onResponse(Call<O_SpaceListResponse> call, Response<O_SpaceListResponse> response) {
                if (response.body() != null) {
                    spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            spaceData.addAll(response.body().getData().getData());
                            currentSpaceAdapter = new O_SpaceListAdapter(getContext(), spaceData, upcomg);
                            binding.eventListRecycler.setAdapter(currentSpaceAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_SpaceListResponse> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUpcommingSession() {
        progressDialog.show();
        Call<O_SessionListResponse> call = retrofitinterface.getOrgSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, upcomg);
        call.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
                if (response.body() != null) {
                    sessionData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            sessionData.addAll(response.body().getData().getData());
                            currentSessionAdapter = new O_SessionListAdapter(getContext(), sessionData, upcomg);
                            binding.eventListRecycler.setAdapter(currentSessionAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);

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
            public void onFailure(Call<O_SessionListResponse> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void a_getUpcommingEvents() {
        progressDialog.show();
        Call<AthleteBookListModel> call = retrofitinterface.getAthleteBookingList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "", upcomg, "event");
        call.enqueue(new Callback<AthleteBookListModel>() {
            @Override
            public void onResponse(Call<AthleteBookListModel> call, Response<AthleteBookListModel> response) {
                if (response.body() != null) {
                    a_eventData = new ArrayList<AthleteBookListModel.DataBeanX.DataBean>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            a_eventData.addAll(response.body().getData().getData());
                            a_EventAdapter = new A_EventListAdapter(getContext(), a_eventData, O_UpcEventFragment.this);
                            binding.eventListRecycler.setAdapter(a_EventAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);

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
            public void onFailure(Call<AthleteBookListModel> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);

                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void a_getUpcommingSpaces() {
        progressDialog.show();
        Call<AthleteSpaceBookList> call = retrofitinterface.getAthleteSpaceBookList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "", upcomg, "space");
        call.enqueue(new Callback<AthleteSpaceBookList>() {
            @Override
            public void onResponse(Call<AthleteSpaceBookList> call, Response<AthleteSpaceBookList> response) {
                if (response.body() != null) {
                    a_spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            a_spaceData.addAll(response.body().getData().getData());
                            a_SpaceAdapter = new A_SpaceListAdapter(getContext(), a_spaceData, O_UpcEventFragment.this);
                            binding.eventListRecycler.setAdapter(a_SpaceAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<AthleteSpaceBookList> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void a_getUpcommingSession() {
        progressDialog.show();
        Call<AthleteSessionBookList> call = retrofitinterface.getAthleteSessionBookList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "", upcomg, "session");
        call.enqueue(new Callback<AthleteSessionBookList>() {
            @Override
            public void onResponse(Call<AthleteSessionBookList> call, Response<AthleteSessionBookList> response) {
                if (response.body() != null) {
                    a_sessionData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            a_sessionData.addAll(response.body().getData().getData());
                            a_SessionAdapter = new A_SessionListAdapter(getContext(), a_sessionData, O_UpcEventFragment.this);
                            binding.eventListRecycler.setAdapter(a_SessionAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);

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
            public void onFailure(Call<AthleteSessionBookList> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);

                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Coach Methods
    public void getCoachUpcommingEvents() {
        progressDialog.show();
        Call<C_EventListResponse> call = retrofitinterface.getCoachEventList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, upcomg);
        call.enqueue(new Callback<C_EventListResponse>() {
            @Override
            public void onResponse(Call<C_EventListResponse> call, Response<C_EventListResponse> response) {
                if (response.body() != null) {
                    c_eventData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            c_eventData.addAll(response.body().getData().getData());
                            c_EventAdapter = new C_EventListAdapter(getContext(), c_eventData, upcomg);
                            binding.eventListRecycler.setAdapter(c_EventAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);

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
            public void onFailure(Call<C_EventListResponse> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getCoachUpcommingSession() {
        progressDialog.show();
        Call<C_SessionListResponse> call = retrofitinterface.getCoachSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, upcomg);
        call.enqueue(new Callback<C_SessionListResponse>() {
            @Override
            public void onResponse(Call<C_SessionListResponse> call, Response<C_SessionListResponse> response) {
                if (response.body() != null) {
                    c_sessionData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            c_sessionData.addAll(response.body().getData().getData());
                            c_SessionAdapter = new C_SessionListAdapter(getContext(), c_sessionData, upcomg);
                            binding.eventListRecycler.setAdapter(c_SessionAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);

                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);

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
            public void onFailure(Call<C_SessionListResponse> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);

                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getCoachSpaceBookings() {
        progressDialog.show();
        Call<AthleteSpaceBookList> call = retrofitinterface.getCoachSpaceBooking("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "");
        call.enqueue(new Callback<AthleteSpaceBookList>() {
            @Override
            public void onResponse(Call<AthleteSpaceBookList> call, Response<AthleteSpaceBookList> response) {
                if (response.body() != null) {
                    a_spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            a_spaceData.addAll(response.body().getData().getData());
                            a_SpaceAdapter = new A_SpaceListAdapter(getContext(), a_spaceData, O_UpcEventFragment.this);
                            binding.eventListRecycler.setAdapter(a_SpaceAdapter);

                        } else {
                            binding.noBookingImg.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                        binding.noBookingImg.setVisibility(View.VISIBLE);

                    }
                } else {
                    binding.noBookingImg.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<AthleteSpaceBookList> call, Throwable t) {
                binding.noBookingImg.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void eventAmount(AthleteBookListModel.DataBeanX.DataBean list) {
        Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + list.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + list.getUser_details().getProfile_image())).into(customerImage);

        userName.setText(list.getUser_details().getName());
        bookingIdText.setText("Booking ID : " + list.getId());
        bookingPlaceName.setText(list.getEvent().getName());
        eventText.setText("Event");
        String currentStringEnd = list.getEvent().getStart_date();


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
        Date dt = null, dtEnd;


        try {

            dt = sdf.parse(list.getEvent().getStart_time());


            String value = null;
            if (dt != null) {
                value = parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
            }
            bookingDateText.setText(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ti_locationText.setText(list.getEvent().getLocation());
        ti_Booking_Ticket.setText(list.getTickets() + " Attendies & Tickets (1 per person)");
        ti_TotalTicketPrice.setText(list.getTickets() + " Tickets @ $" + list.getEvent().getPrice() + " each");
        ti_TotalPrice.setText("$" + list.getPrice() + ".00");
        ti_tax.setText("$0.00");
        totalAmount.setText("$" + list.getPrice() + ".00");

        bottomSheetUpDown_address();
    }


    @Override
    public void getSpaceAmount(AthleteSpaceBookList.DataBeanX.DataBean spaceData) {
        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach) || CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
        {
            Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + spaceData.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + spaceData.getUser_details().getProfile_image())).into(customerImage);
            userName.setText(spaceData.getUser_details().getName());
            bookingIdText.setText("Booking ID : " + spaceData.getId());
            bookingPlaceName.setText(spaceData.getTarget_data().getName());
            eventText.setText("Space");

        ti_locationText.setText(spaceData.getTarget_data().getLocation());
            ti_Booking_Ticket.setVisibility(View.GONE);
            ti_tickets.setVisibility(View.GONE);

//        ti_Booking_Ticket.setText(spaceData.getTickets() + " Attendies & Tickets (1 per person)");
            ti_TotalTicketPrice.setText("Space @ $" + spaceData.getTarget_data().getPrice_daily() + " /day");
            ti_TotalPrice.setText("$" + spaceData.getPrice() + ".00");
            ti_tax.setText("$0.00");
            totalAmount.setText("$" + spaceData.getPrice() + ".00");
            bottomSheetUpDown_address();
        }else {
        Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + spaceData.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + spaceData.getUser_details().getProfile_image())).into(customerImage);
        userName.setText(spaceData.getUser_details().getName());
        bookingIdText.setText("Booking ID : " + spaceData.getId());
        bookingPlaceName.setText(spaceData.getSpace().getName());
        eventText.setText("Space");

        ti_locationText.setText(spaceData.getSpace().getLocation());
        ti_Booking_Ticket.setVisibility(View.GONE);
        ti_tickets.setVisibility(View.GONE);

//        ti_Booking_Ticket.setText(spaceData.getTickets() + " Attendies & Tickets (1 per person)");
        ti_TotalTicketPrice.setText("Space @ $" + spaceData.getSpace().getPrice_daily() + " /day");
        ti_TotalPrice.setText("$" + spaceData.getPrice() + ".00");
        ti_tax.setText("$0.00");
        totalAmount.setText("$" + spaceData.getPrice() + ".00");
        bottomSheetUpDown_address();
        }
    }

    @Override
    public void sessionAmount(AthleteSessionBookList.DataBeanX.DataBean sessionData) {
        Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + sessionData.getUser_details().getProfile_image()).thumbnail(Glide.with(getContext()).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + sessionData.getUser_details().getProfile_image())).into(customerImage);
        userName.setText(sessionData.getUser_details().getName());
        bookingIdText.setText("Booking ID : " + sessionData.getId());
        bookingPlaceName.setText(sessionData.getSession().getName());
        eventText.setText("Session");
        String currentStringEnd = sessionData.getSession().getStart_date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
        Date dt = null, dtEnd;


        try {
            dt = sdf.parse(sessionData.getSession().getStart_date());
            String value = null;
            if (dt != null) {
                value = parseDateToddMMyyyy(currentStringEnd) + " | " + sdfs.format(dt);
            }
            bookingDateText.setText(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ti_locationText.setText(sessionData.getSession().getLocation());
        ti_Booking_Ticket.setText(sessionData.getTickets() + " Attendies & Tickets (1 per person)");
        ti_TotalTicketPrice.setText(sessionData.getTickets() + " Tickets @ $" + sessionData.getSession().getHourly_rate() + " each");
        ti_TotalPrice.setText("$" + sessionData.getPrice() + ".00");
        ti_tax.setText("$0.00");
        totalAmount.setText("$" + sessionData.getPrice() + ".00");
        bottomSheetUpDown_address();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "EEE, dd MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
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
