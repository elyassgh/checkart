package irisi.digitalaube.checkart.api.service;

import java.util.List;

import irisi.digitalaube.checkart.api.model.Tapis;
import irisi.digitalaube.checkart.api.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("/checkart/api/user/registration")
    Call<User> register(@Body User user);

    // authUtils = 'Basic email:password'
    @GET("/checkart/api/user/login")
    Call<User> login(@Header("Authorization") String authToken);

    @GET("/checkart/api/admin/tapis-all")
    Call<List<Tapis>> getTapis();



}
