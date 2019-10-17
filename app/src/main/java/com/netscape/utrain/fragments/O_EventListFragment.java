package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.netscape.utrain.adapters.AthleteTopRatedAdapter;
import com.netscape.utrain.adapters.O_EventListAdapter;
import com.netscape.utrain.databinding.FragmentOEventListBinding;
import com.netscape.utrain.model.CoachDataModel;
import com.netscape.utrain.response.BookingListResponse;
import com.netscape.utrain.response.BookingListResponse;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link O_EventListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link O_EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class O_EventListFragment extends Fragment {
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentOEventListBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private O_EventListAdapter currentEventAdapter;
    private List<CoachDataModel> data = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public O_EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment O_EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static O_EventListFragment newInstance(String param1, String param2) {
        O_EventListFragment fragment = new O_EventListFragment();
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
        layoutManager = new LinearLayoutManager(getContext());
        binding.eventListRecycler.setLayoutManager(layoutManager);
        


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
    private void getBookedEventList()  {
        progressDialog.show();
        Call<BookingListResponse> call = retrofitinterface.getBookingList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN,getContext()), Constants.CONTENT_TYPE,"event");
        call.enqueue(new Callback<BookingListResponse>() {
            @Override
            public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size()>0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            currentEventAdapter = new O_EventListAdapter(getContext(), data);
                            binding.eventListRecycler.setAdapter(currentEventAdapter);
                            
                        }else{
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Toast.makeText(getContext(), "No Data Found" , Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<BookingListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
