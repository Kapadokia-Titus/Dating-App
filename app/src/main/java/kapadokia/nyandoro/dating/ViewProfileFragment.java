package kapadokia.nyandoro.dating;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import kapadokia.nyandoro.dating.models.User;
import kapadokia.nyandoro.dating.util.PreferenceKeys;
import kapadokia.nyandoro.dating.util.Resources;


public class ViewProfileFragment extends Fragment implements OnLikeListener {

    private static final String TAG = "ViewProfileFragment";

    //widgets
    private TextView mFragmentHeading, mName, mGender, mInterestedIn,mStatus;
    private LikeButton mLikeButton;
    private RelativeLayout mBackArrow;
    private CircleImageView mProfileImage;

    //vars
    private User mUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle !=null){
            mUser = bundle.getParcelable(getString(R.string.intent_user));
            Log.d(TAG, "onCreate: incoming bundle "+mUser.getName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);
        Log.d(TAG, "onCreateView: started.");

        mBackArrow = view.findViewById(R.id.back_arrow);
        mFragmentHeading = view.findViewById(R.id.fragment_heading);
        mProfileImage = view.findViewById(R.id.profile_image);
        mInterestedIn = view.findViewById(R.id.interested_in);
        mName = view.findViewById(R.id.name);
        mGender = view.findViewById(R.id.gender);
        mLikeButton = view.findViewById(R.id.heart_button);
        mStatus = view.findViewById(R.id.status);

        mLikeButton.setOnLikeListener(this);
        setBackgroundImage(view);
        checkIfConnected();

        init();
        return view;
    }

    private void init(){
        Log.d(TAG, "init: initializing ViewProfileFragment");
        if (mUser !=null){
            Glide.with(getActivity())
                    .load(mUser.getProfile_image())
                    .into(mProfileImage);

            mName.setText(mUser.getName());
            mGender.setText(mUser.getGender());
            mInterestedIn.setText(mUser.getInterested_in());
            mStatus.setText(mUser.getStatus());
        }
    }

    private void setBackgroundImage(View view){
        ImageView backgroundImage = view.findViewById(R.id.background);
        Glide.with(getActivity())
                .load(mUser.getProfile_image())
                .centerCrop()
                .into(backgroundImage);
    }
    // checking if the user is connected
    private void checkIfConnected(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS, new HashSet<String>());

        if (savedNames.contains(mUser.getName())){
            mLikeButton.setLiked(true);
        }else {
            mLikeButton.setLiked(false);
        }
    }

    @Override
    public void liked(LikeButton likeButton) {
        Log.d(TAG, "liked: Liked:)");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS, new HashSet<String>());
        savedNames.add(mUser.getName());

        editor.putStringSet(PreferenceKeys.SAVED_CONNECTIONS,savedNames);
        editor.commit();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Log.d(TAG, "unLiked: (:");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.SAVED_CONNECTIONS, new HashSet<String>());
        savedNames.remove(mUser.getName());
        editor.remove(PreferenceKeys.SAVED_CONNECTIONS);
        editor.commit();

        editor.putStringSet(PreferenceKeys.SAVED_CONNECTIONS,savedNames);
        editor.commit();
    }
}
















