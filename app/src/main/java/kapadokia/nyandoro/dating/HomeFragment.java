package kapadokia.nyandoro.dating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kapadokia.nyandoro.dating.models.User;
import kapadokia.nyandoro.dating.util.Users;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private static final int NUM_COLUMNS = 2;

    private RecyclerView recyclerView;

    // vars
    private ArrayList<User> mMatches = new ArrayList<>();
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private MainRecyclerViewAdapter mainRecyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,false);
        Log.d(TAG, "onCreateView: started");

        recyclerView = view.findViewById(R.id.recycler_view);
        findMatches();
        return view;
    }

    private void findMatches(){
        Users users  = new Users();
        if (mMatches!=null){
            mMatches.clear();
        }
        for (User user:users.USERS){
            mMatches.add(user);
        }
        if (mainRecyclerViewAdapter==null){
            initRecyclerview();
        }
    }

    private void initRecyclerview() {
        Log.d(TAG, "initRecyclerview: init recyclerview");
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), mMatches);
        recyclerView.setAdapter(mainRecyclerViewAdapter);
    }
}