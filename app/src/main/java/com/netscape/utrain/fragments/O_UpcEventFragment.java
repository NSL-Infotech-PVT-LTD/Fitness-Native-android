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
import com.netscape.utrain.adapters.O_EventListAdapter;
import com.netscape.utrain.adapters.O_SessionListAdapter;
import com.netscape.utrain.adapters.O_SpaceListAdapter;
import com.netscape.utrain.databinding.FragmentOEventListBinding;
import com.netscape.utrain.model.A_EventDataListModel;
import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.A_SessionDataModel;
import com.netscape.utrain.model.A_SpaceDataModel;
import com.netscape.utrain.model.A_SpaceListModel;
import com.netscape.utrain.model.CoachDataModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.response.A_EventListResponse;
import com.netscape.utrain.response.A_SpaceListResponse;
import com.netscape.utrain.response.BookingListResponse;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link O_UpcEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link O_UpcEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    private List<A_EventDataListModel> a_eventData;
    private List<A_SessionDataModel> a_sessionData;
    private List<A_SpaceListModel> a_spaceData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public O_UpcEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment O_UpcEventFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        } else if (count == 2) {

            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
                getUpcommingSession();
//            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
//                a_getUpcommingSession();
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
        Call<O_EventListResponse> call = retrofitinterface.getOrgEentList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "upcoming");
        call.enqueue(new Callback<O_EventListResponse>() {
            @Override
            public void onResponse(Call<O_EventListResponse> call, Response<O_EventListResponse> response) {
                if (response.body() != null) {
                    eventData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            eventData.addAll(response.body().getData());
                            currentEventAdapter = new O_EventListAdapter(getContext(), eventData);
                            binding.eventListRecycler.setAdapter(currentEventAdapter);

                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUpcommingSpaces() {
        progressDialog.show();
        Call<O_SpaceListResponse> call = retrofitinterface.getOrgSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE,"upcoming");
        call.enqueue(new Callback<O_SpaceListResponse>() {
            @Override
            public void onResponse(Call<O_SpaceListResponse> call, Response<O_SpaceListResponse> response) {
                if (response.body() != null) {
                    spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            spaceData.addAll(response.body().getData());
                            currentSpaceAdapter = new O_SpaceListAdapter(getContext(), spaceData);
                            binding.eventListRecycler.setAdapter(currentSpaceAdapter);

                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUpcommingSession() {
        progressDialog.show();
        Call<O_SessionListResponse> call = retrofitinterface.getOrgSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE,"upcoming");
        call.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
                if (response.body() != null) {
                    sessionData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            sessionData.addAll(response.body().getData());
                            currentSessionAdapter = new O_SessionListAdapter(getContext(), sessionData);
                            binding.eventListRecycler.setAdapter(currentSessionAdapter);

                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void a_getUpcommingEvents() {
        progressDialog.show();
        Call<A_EventListResponse> call = retrofitinterface.getAthEventList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "distance", "", "10", "", "50");
        call.enqueue(new Callback<A_EventListResponse>() {
            @Override
            public void onResponse(Call<A_EventListResponse> call, Response<A_EventListResponse> response) {
                if (response.body() != null) {
                    a_eventData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            a_eventData.addAll(response.body().getData().getData());
                            a_EventAdapter = new A_EventListAdapter(getContext(), a_eventData);
                            binding.eventListRecycler.setAdapter(a_EventAdapter);

                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<A_EventListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void a_getUpcommingSpaces() {
        progressDialog.show();
        Call<A_SpaceListResponse> call = retrofitinterface.getAthSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "upcoming", "10");
        call.enqueue(new Callback<A_SpaceListResponse>() {
            @Override
            public void onResponse(Call<A_SpaceListResponse> call, Response<A_SpaceListResponse> response) {
                if (response.body() != null) {
                    a_spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            a_spaceData.addAll(response.body().getData().getData());
                            a_SpaceAdapter = new A_SpaceListAdapter(getContext(), a_spaceData);
                            binding.eventListRecycler.setAdapter(currentSpaceAdapter);

                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<A_SpaceListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void a_getUpcommingSession() {
//        progressDialog.show();
//        Call<A_SessionListResponse> call = retrofitinterface.getAthSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE,"upcoming","","10");
//        call.enqueue(new Callback<O_SessionListResponse>() {
//            @Override
//            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
//                if (response.body() != null) {
//                    sessionData = new ArrayList<>();
//                    progressDialog.dismiss();
//                    if (response.body().isStatus()) {
//                        if (response.body().getData().size() > 0) {
////                            binding.topRateRecycler.setVisibility(View.VISIBLE);
////                            binding.noDataImageView.setVisibility(View.GONE);
////                            data.addAll(response.body().getData());
//                            sessionData.addAll(response.body().getData());
//                            currentSessionAdapter = new O_SessionListAdapter(getContext(), sessionData);
//                            binding.eventListRecycler.setAdapter(currentSessionAdapter);
//
//                        } else {
////                            binding.topRateRecycler.setVisibility(View.GONE);
////                            binding.noDataImageView.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
//
//                    }
//                } else {
////                    binding.topRateRecycler.setVisibility(View.GONE);
////                    binding.noDataImageView.setVisibility(View.VISIBLE);
//                    progressDialog.dismiss();
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//
//                        Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<O_SessionListResponse> call, Throwable t) {
////                binding.topRateRecycler.setVisibility(View.GONE);
////                binding.noDataImageView.setVisibility(View.VISIBLE);
//                progressDialog.dismiss();
//                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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
