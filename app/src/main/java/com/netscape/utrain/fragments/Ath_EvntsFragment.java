package com.netscape.utrain.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AllEventsWithMap;
import com.netscape.utrain.adapters.CoachesRecyclerAdapter;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.response.AthleteEventListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ath_EvntsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ath_EvntsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ath_EvntsFragment extends Fragment {
    MaterialButton btnViewAll;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CoachesRecyclerAdapter adapter;
    private List<AthleteEventListModel> data = new ArrayList<>();

    private Context context;
    private Retrofitinterface api;
    private List<AthleteEventListModel> listModels = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private RecyclerView.LayoutManager topCoachesLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Ath_EvntsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoachesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Ath_EvntsFragment newInstance(String param1, String param2) {
        Ath_EvntsFragment fragment = new Ath_EvntsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.athlete_events_fragment, container, false);

        btnViewAll = view.findViewById(R.id.eventsViewAllBtn);
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AllEventsWithMap.class);
                context.startActivity(intent);
            }
        });
        recyclerView = view.findViewById(R.id.coachesRecycler);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");

        getAthleteEventApi();

        return view;
    }

    private void getAthleteEventApi() {

        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<AthleteEventListResponse> call = api.getAthleteEventList( "Bearer "+ CommonMethods.getPrefData(Constants.AUTH_TOKEN, context),Constants.CONTENT_TYPE,"distance","","1000000");
        call.enqueue(new Callback<AthleteEventListResponse>() {
            @Override
            public void onResponse(Call<AthleteEventListResponse> call, Response<AthleteEventListResponse> response) {

                if (response.isSuccessful())
                {
                    if (response.body().isStatus()){
                        listModels.clear();
                        listModels.addAll(response.body().getData());

                        adapter = new CoachesRecyclerAdapter(context, listModels);
                        recyclerView.setAdapter(adapter);

                    }
                }


            }

            @Override
            public void onFailure(Call<AthleteEventListResponse> call, Throwable t) {

                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
