package kapadokia.nyandoro.dating;


import android.os.Bundle;
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

import de.hdodenhof.circleimageview.CircleImageView;
import kapadokia.nyandoro.dating.models.User;
import kapadokia.nyandoro.dating.util.Resources;


public class ViewProfileFragment extends Fragment {

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

        setBackgroundImage(view);

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
}
















