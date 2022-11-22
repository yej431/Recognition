package com.cookandroid.flowerdesign.utils;

import com.cookandroid.flowerdesign.model.Article;
import com.cookandroid.flowerdesign.model.SearchRank;
import com.cookandroid.flowerdesign.model.Upload;
import com.cookandroid.flowerdesign.model.User;
import com.cookandroid.flowerdesign.model.UserStore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {
    @GET("android/boardList")
    Call<List<Upload>> getList(@Query("keyword") String keyword);
    //@GET은 @Query로 요청해야 합니다.

    @GET("android/searchRank")
    Call<List<SearchRank>> searchRank();

    @POST("android/signup")
    Call<User>signup(@Body User user); //@Body - 서버에 데이터 추가, @Body 는 post 방식에서 사용

    @POST("android/login")
    Call<User>login(@Body User user);

    @POST("android/articleSave")
    Call<Article> articleSave(@Body Article article);

    @POST("android/articleUpdate")
    Call<Article> articleUpdate(@Body Article article);

    @POST("android/articleDelete")
    Call<Article> articleDelete(@Body Article article);

    @GET("android/articleList")
    Call<List<Article>> articleList();

    @GET("android/storeSave")
    Call<UserStore> storeSave(@Query("flowername") String flowername,
                              @Query("userid") String userid);

    @GET("android/storeList")
    Call<List<UserStore>> userStoreList(@Query("userid") String userid);

    @GET("android/flowerSearch")
    Call<List<Upload>> flowerSearch(@Query("searchtxt") String searchtxt);

    @GET("android/articleSearch")
    Call<List<Article>> articleSearch(@Query("searchtxt") String searchtxt);

    /*@POST("update/{id}")
    Call<Board>update(@Body Board persona, @Path("id") int id);

    @POST("delete/{id}")
    Call<Board>delete(@Path("id")int id);*/
}
