package com.example.sample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_info extends Fragment {
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int userId,accessedByUser;

    public Profile_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_info.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_info newInstance(String param1, String param2) {
        Profile_info fragment = new Profile_info();
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
        // Inflate the layout for this fragment
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userId = getArguments().getInt("userId");
                accessedByUser = getArguments().getInt("accessedByUser");
                view = inflater.inflate(R.layout.fragment_profile_info, container, false);
            }
        });
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String method="load";
//        userPic=getActivity().findViewById(R.id.userpic);
//        Glide.with(this).asBitmap().error(R.mipmap.ic_user).load("http://10.0.2.2/konnectit/profilepics/"+Integer.toString(userId)+".png").into(userPic);

        TextView Fullname = getActivity().findViewById(R.id.fullname);
        TextView Username = getActivity().findViewById(R.id.username);
        ProfileBackgroundTask bgTask=new ProfileBackgroundTask(getActivity(),userId, Fullname, Username, this);
        bgTask.execute(method);

//        NewsFeedBackgroundTask bgTask=new NewsFeedBackgroundTask(getActivity(),userId);
//        bgTask.execute(method);
    }
    public void setAge(String newage){
        TextView Age = (TextView) getView().findViewById(R.id.age);
        Age.setText(newage);
    }
    public void setMobile(String newmobile){
        TextView Mobile = (TextView) getView().findViewById(R.id.mobile);
        Mobile.setText(newmobile);
    }public void setEmail(String newemail){
        TextView Email = (TextView) getView().findViewById(R.id.email);
        Email.setText(newemail);
    }public void setGender(String newgender){
        TextView Gender = (TextView) getView().findViewById(R.id.gender);
        Gender.setText(newgender);
    }public void setBio(String newbio){
        TextView Bio = (TextView) getView().findViewById(R.id.bio);
        Bio.setText(newbio);
    }
    public void hideAge(){
        TextView Age = (TextView) getView().findViewById(R.id.age);
        Age.setVisibility(View.GONE);
    }
    public void hideMobile(){
        TextView Mobile = (TextView) getView().findViewById(R.id.mobile);
        Mobile.setVisibility(View.GONE);
    }public void hideEmail(){
        TextView Email = (TextView) getView().findViewById(R.id.email);
        Email.setVisibility(View.GONE);
    }public void hideGender(){
        TextView Gender = (TextView) getView().findViewById(R.id.gender);
        Gender.setVisibility(View.GONE);
    }public void hideBio(){
        TextView Bio = (TextView) getView().findViewById(R.id.bio);
        Bio.setVisibility(View.GONE);
    }



}