package irisi.digitalaube.checkart.api.serviceImp;

import android.util.Base64;

import java.io.IOException;

import irisi.digitalaube.checkart.api.Server;
import irisi.digitalaube.checkart.api.model.User;
import irisi.digitalaube.checkart.api.service.UserService;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserServiceImpl {

    private static Retrofit retrofit;
    private static UserService userService;


    public static void getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Server.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    protected static class Result {
        private User user;
        private Integer status;

        public Result() {
        }

        public Result(User user, Integer status) {
            this.user = user;
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    public static Result login(String email, String password) {

        getClient();

        String credentials = email + ":" + password;
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        Call<User> call = userService.login("Basic " + email + ":" + password);
        final Result result = new Result();

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    result.setUser(response.body());
                    result.setStatus(0);
                } else {
                    result.setStatus(-1);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result.setStatus(2);
            }
        });

        return result;
    };


    public static Integer register(String email, String password) {

        getClient();
        User user = new User(null, null, email, password,null);
        Call<User> call = userService.register(user);
        final Integer[] result = new Integer[1];

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    result[0] = 0;
                } else {
                    result[0] = -1;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result[0] = -1;
            }
        });

        return result[0];
    };

}
