package com.netscape.utrain.fragments;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.OfferSpaceActivity;
import com.netscape.utrain.activities.ViewCoachStaffListActivity;
import com.netscape.utrain.databinding.OFragmentRegistrationProfileBinding;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link O_RegistrationProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link O_RegistrationProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class O_RegistrationProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ChipGroup cChipGroup;
    private OFragmentRegistrationProfileBinding binding;
    private Context context;
    private ArrayList<ServiceListDataModel> sList = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public O_RegistrationProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A_EditorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static O_RegistrationProfile newInstance(String param1, String param2) {
        O_RegistrationProfile fragment = new O_RegistrationProfile();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.o_fragment_registration_profile, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment

        // Setting values to the  views on Organizatio profile.
        String path = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, context);
        Glide.with(context).load(Constants.ORG_IMAGE_BASE_URL + path).into(binding.oDashProImage);
        Glide.with(context).load(path).into(binding.oDashProImage);
        binding.viewCoachStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Going To viewCoachStaffActivity...
                Intent oRegistrationIntent = new Intent(context, ViewCoachStaffListActivity.class);
                oRegistrationIntent.putExtra("oRegisterIntent","");
                startActivity(oRegistrationIntent);
            }
        });


        binding.oNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, context));
        binding.cYearsOfExpTv.setText(CommonMethods.getPrefData(PrefrenceConstant.EXPERTISE_YEAR,context) + "+ Years");
        binding.orgBioTv.setText(CommonMethods.getPrefData(PrefrenceConstant.BIO, context));
        binding.detailPriceTv.setText(CommonMethods.getPrefData(PrefrenceConstant.PRICE,context));
        binding.eventTimeDetailTv.setText(CommonMethods.getPrefData(PrefrenceConstant.BUSINESS_HOUR_START,context));
        binding.oExpTv.setText(CommonMethods.getPrefData(PrefrenceConstant.EXPERTISE_YEAR,context) + "+ Years");

        getService();
        cChipGroup = new ChipGroup(context);
        cChipGroup.setSingleSelection(false);


        for (int i = 0; i < sList.size(); i++) {
            final Chip chip = new Chip(context);
            chip.setEnabled(false);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setTextColor(getResources().getColor(R.color.colorBlack));

            chip.setText(sList.get(i).getName());
            chip.setTextSize(12);
            cChipGroup.addView(chip);
            cChipGroup.setChipSpacingVertical(30);

        }
        cChipGroup.setEnabled(false);
        binding.constraintChipGroup.addView(cChipGroup);
        binding.oProfileDirectionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:19.076,72.8777"));
                startActivity(intent);
            }
        });

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

    private void getService() {
        String serviceName = CommonMethods.getPrefData(PrefrenceConstant.SERVICE_IDS, getApplicationContext());
        Gson gson = new Gson();

        if (serviceName != null) {
            if (serviceName.isEmpty()) {
                Toast.makeText(context, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<ServiceListDataModel>>() {
                }.getType();
                sList = gson.fromJson(serviceName, type);

                StringBuilder builder = new StringBuilder();
                for (ServiceListDataModel details : sList) {
                    builder.append(details.getName() + "\n");

                }

            }
        }
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
