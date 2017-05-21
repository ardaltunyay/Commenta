package tr.edu.dogus.commenta.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tr.edu.dogus.commenta.model.SearchResponse;

/**
 * Created by ardaltunyay on 20.05.2017.
 */

public interface RetroInterface {

    @GET("venues/search")
    Call<SearchResponse> getVenueJson(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("v") String version,
            @Query("ll") String ll);
}