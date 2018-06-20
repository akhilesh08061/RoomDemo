package in.co.allogics.akhilesh.roomdemo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import in.co.allogics.akhilesh.roomdemo.R;
import in.co.allogics.akhilesh.roomdemo.database.AppDatabase;
import in.co.allogics.akhilesh.roomdemo.models.User;

public class MainActivity extends AppCompatActivity {

    static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button saveUser = findViewById(R.id.save_user_btn);
        Button loadUserList = findViewById(R.id.view_users_btn);
        final EditText firstName, lastName;
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveUserTask().execute(MainActivity.this, firstName.getText().toString(), lastName.getText().toString());
            }
        });

        appDatabase = AppDatabase.getAppDatabase(this);

        loadUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewUsers = new Intent(MainActivity.this, LoadUserActivity.class);
                startActivity(viewUsers);
                finish();
            }
        });
    }

    private static class SaveUserTask extends AsyncTask<Object, Void, User> {
        WeakReference<MainActivity> mainActivityWeakReference;
        int userCount;

        @Override
        protected User doInBackground(Object... params) {
            mainActivityWeakReference = new WeakReference<>((MainActivity) params[0]);
            User newUser = new User((String) params[1], (String) params[2]);
            appDatabase.userDao().insertUsers(newUser);
            userCount = appDatabase.userDao().getUserCount();
            return newUser;
        }

        @Override
        protected void onPostExecute(final User user) {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity != null) {
                if (user != null)
                    Toast.makeText(activity, "user added successfully with count " + userCount, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activity, "unable to add user ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
