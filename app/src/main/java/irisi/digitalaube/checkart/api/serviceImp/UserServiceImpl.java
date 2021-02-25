package irisi.digitalaube.checkart.api.serviceImp;

import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.Principal;

import irisi.digitalaube.checkart.api.Server;
import irisi.digitalaube.checkart.api.model.User;
import irisi.digitalaube.checkart.api.service.UserService;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserServiceImpl {

    private static UserService userService;

    public static void getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Server.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    public static Result login(String email, String password) {

        getClient();

        String credentials = email + ":" + password;
        final String authToken = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);


        Call<User> call = userService.login(authToken);
        final Result result = new Result();

        // Default status
        result.setStatus(2);

        try
        {
            Log.i("info", "---------------------------  request:  ---------------------------");
            Log.i("info", call.request().toString());
            Log.i("info", call.request().headers().toString());
            Log.i("info", "---------------------------  request.  ---------------------------");

            Response<User> response = call.execute();
            User apiResponse = response.body();
            result.setUser(apiResponse);
            result.setStatus(0);
        }
        catch (Exception ex)
        {
            result.setStatus(2);
        }

        Log.i("info", "---------------------------  .  ---------------------------");

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
