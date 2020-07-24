package kapadokia.nyandoro.dating;

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

import kapadokia.nyandoro.dating.util.PreferenceKeys;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFirstLoggedIn();
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
}