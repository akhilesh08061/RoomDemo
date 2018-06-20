package in.co.allogics.akhilesh.roomdemo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.co.allogics.akhilesh.roomdemo.R;
import in.co.allogics.akhilesh.roomdemo.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.firstName.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName;

        public MyViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.user_first_name);
            lastName = itemView.findViewById(R.id.last_name_text);
        }
    }
}
