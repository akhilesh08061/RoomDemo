package in.co.allogics.akhilesh.roomdemo.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import in.co.allogics.akhilesh.roomdemo.R;
import in.co.allogics.akhilesh.roomdemo.adapters.UserAdapter;
import in.co.allogics.akhilesh.roomdemo.database.AppDatabase;
import in.co.allogics.akhilesh.roomdemo.models.User;

public class LoadUserActivity extends AppCompatActivity {


    public UserAdapter userAdapter;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_user);
        RecyclerView recyclerView = findViewById(R.id.user_list);
        userAdapter = new UserAdapter(users);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);
        new LoadUserDataTask().execute(this);
    }

    private static class LoadUserDataTask extends AsyncTask<Object, Void, List<User>> {
        WeakReference<LoadUserActivity> loadUserActivityWeakReference;

        @Override
        protected List<User> doInBackground(Object... objects) {
            AppDatabase appDatabase = AppDatabase.getAppDatabase((LoadUserActivity) objects[0]);
            loadUserActivityWeakReference = new WeakReference<>((LoadUserActivity) objects[0]);
            return appDatabase.userDao().getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> newUsers) {
            LoadUserActivity activity = loadUserActivityWeakReference.get();
            try {
                if (activity != null) {
                    activity.users.addAll(newUsers);
                    activity.userAdapter.notifyDataSetChanged();
                }
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
    }
}
