package rest;


import model.ListCategories;
import model.ProductList;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {



    @GET("/categories")
    Call<ListCategories> getFilterList();

    @GET("/category")
    Call<ProductList> getFetchFilterList(@Url String ss);

}
