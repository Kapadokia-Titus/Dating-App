package kapadokia.nyandoro.dating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import kapadokia.nyandoro.dating.models.Message;
import kapadokia.nyandoro.dating.models.User;
import kapadokia.nyandoro.dating.util.PreferenceKeys;

public class MainActivity extends AppCompatActivity implements IMainActivity, BottomNavigationViewEx.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    private BottomNavigationViewEx bottomNavigationViewEx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        isFirstLoggedIn();
        init();
        initBottomNavigationView();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.bottom_nav_home:{
                Log.d(TAG, "onNavigationItemSelected: HomeFragment");
                HomeFragment fragment = new HomeFragment();
                // using fragment transaction to inflate the transactions
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.main_content_frame, fragment, getString(R.string.tag_fragment_home));
                // add the fragment to the backstack
                transaction.addToBackStack(getString(R.string.tag_fragment_home));
                //commit the transaction
                transaction.commit();
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_connections:{
                Log.d(TAG, "onNavigationItemSelected: ConnectionFragment");
                SavedConnectionsFragment savedConnectionsFragment = new SavedConnectionsFragment();
                // using fragment transaction to inflate the transactions
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.main_content_frame, savedConnectionsFragment, getString(R.string.tag_fragment_saved_connections));
                // add the fragment to the backstack
                transaction.addToBackStack(getString(R.string.tag_fragment_saved_connections));
                //commit the transaction
                transaction.commit();
                item.setChecked(true);
                break;
            }
            case R.id.bottom_nav_messages:{
                Log.d(TAG, "onNavigationItemSelected: MessageFragment");
                MessagesFragment messagesFragment = new MessagesFragment();
                // using fragment transaction to inflate the transactions
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.main_content_frame, messagesFragment, getString(R.string.tag_fragment_messages));
                // add the fragment to the backstack
                transaction.addToBackStack(getString(R.string.tag_fragment_messages));
                //commit the transaction
                transaction.commit();
                item.setChecked(true);
                break;
            }
        }
        return false;
    }

    private void initBottomNavigationView(){
        Log.d(TAG, "initBottomNavigationView: initialising bottom navigation view ....");
        bottomNavigationViewEx.enableAnimation(false);

    }
    // init method
    private void init(){
        Fragment fragment = new HomeFragment();
        // using fragment transaction to inflate the transactions
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_content_frame, fragment, getString(R.string.tag_fragment_home));
        // add the fragment to the backstack
        transaction.addToBackStack(getString(R.string.tag_fragment_home));
        //commit the transaction
        transaction.commit();
    }

    private void isFirstLoggedIn(){
        Log.d(TAG, "isFirstLoggedIn: checking if this is the first login");

        // to determine if the user is logged in we are going to save a parameter to the shared preference.
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFistTimeLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);

        if (isFistTimeLogin){
            Log.d(TAG, "isFirstLoggedIn: launching alert dialog");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(getString(R.string.first_time_user_message));

            // implement the onclick listener on the dialog interface
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "onClick: closing of the dialog");

                    // getting the shared preference editor object
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                    editor.commit();
                }
            });

            alertDialogBuilder.setIcon(R.drawable.tabian_dating);
            alertDialogBuilder.setTitle(" "); // this enables the icon above to show
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void inflateViewProfileFragment(User user) {
        ViewProfileFragment fragment = new ViewProfileFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.intent_user), user);
        fragment.setArguments(args);
        // using fragment transaction to inflate the transactions
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_content_frame, fragment, getString(R.string.tag_fragment_view_profile));
        // add the fragment to the backstack
        transaction.addToBackStack(getString(R.string.tag_fragment_view_profile));
        //commit the transaction
        transaction.commit();
    }

    @Override
    public void onMessageSelected(Message message) {

    }


}