package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class O_UpcEventFragment extends Fragment {
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

    private List<AthleteBookListModel.DataBean> a_eventData;
    private List<AthleteSessionBookList.DataBean> a_sessionData;
    private List<AthleteSpaceBookList.DataBean> a_spaceData;
    private C_EventListAdapter c_EventAdapter;
    private C_SpaceListAdapter c_SpaceAdapter;
    private C_SessionListAdapter c_SessionAdapter;

    private List<C_EventDataListModel> c_eventData;
    private List<C_SessionListModel> c_sessionData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String upcomg = "upcoming";

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

        layoutManager = new LinearLayoutManager(getContext());
        binding.eventListRecycler.setLayoutManager(layoutManager);


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
                getUpcommingSpaces();
            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
                a_getUpcommingSpaces();
        }

        return view;
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
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            eventData.addAll(response.body().getData());
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
        Call<O_SpaceListResponse> call = retrofitinterface.getOrgSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, upcomg);
        call.enqueue(new Callback<O_SpaceListResponse>() {
            @Override
            public void onResponse(Call<O_SpaceListResponse> call, Response<O_SpaceListResponse> response) {
                if (response.body() != null) {
                    spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            spaceData.addAll(response.body().getData());
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
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            sessionData.addAll(response.body().getData());
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
                    a_eventData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            a_eventData.addAll(response.body().getData());
                            a_EventAdapter = new A_EventListAdapter(getContext(), a_eventData);
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
        Call<AthleteSpaceBookList> call = retrofitinterface.getAthleteSpaceBookList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "", upcomg,"space");
        call.enqueue(new Callback<AthleteSpaceBookList>() {
            @Override
            public void onResponse(Call<AthleteSpaceBookList> call, Response<AthleteSpaceBookList> response) {
                if (response.body() != null) {
                    a_spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            a_spaceData.addAll(response.body().getData());
                            a_SpaceAdapter = new A_SpaceListAdapter(getContext(), a_spaceData);
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
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            a_sessionData.addAll(response.body().getData());
                            a_SessionAdapter = new A_SessionListAdapter(getContext(), a_sessionData);
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
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            c_eventData.addAll(response.body().getData());
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
                        if (response.body().getData().size() > 0) {
                            binding.noBookingImg.setVisibility(View.GONE);

//                            data.addAll(response.body().getData());
                            c_sessionData.addAll(response.body().getData());
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
