package in.co.allogics.akhilesh.roomdemo.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import in.co.allogics.akhilesh.roomdemo.models.User;

@Dao
public interface UserDao {

    @Query("select * from user")
    List<User> getAllUsers();

    @Query("select * from user where first_name like :firstName and last_name like :lastName")
    User findByName(String firstName, String lastName);

    @Query("select count(*) from user")
    int getUserCount();

    @Insert
    void insertUsers(User... users);

    @Delete
    void delete(User user);
}
