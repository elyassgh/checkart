package irisi.digitalaube.checkart.api.service;

import irisi.digitalaube.checkart.api.Server;
import irisi.digitalaube.checkart.api.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST(Server.URL + "/api/user/registration")
    Call<User> register(@Body User user);

    // authUtils = 'basic email:password'
    @GET(Server.URL + "/api/user/login/")
    Call<User> login(@Header("Authorization") String authUtils);

}
