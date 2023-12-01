package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList {

        @SerializedName("products")
        @Expose
        public List<Product> products;

        @SerializedName("total")
        @Expose
        public int total;

        @SerializedName("skip")
        @Expose
        public int skip;

        @SerializedName("limit")
        @Expose
        public int limit;

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSkip() {
            return skip;
        }

        public void setSkip(int skip) {
            this.skip = skip;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
   public class Product {
        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("title")
        @Expose
        public String title;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("price")
        @Expose
        public double price;

        @SerializedName("discountPercentage")
        @Expose
        public double discountPercentage;

        @SerializedName("rating")
        @Expose
        public double rating;

        @SerializedName("stock")
        @Expose
        public int stock;

        @SerializedName("brand")
        @Expose
        public String brand;

        @SerializedName("category")
        @Expose
        public String category;

        @SerializedName("thumbnail")
        @Expose
        public String thumbnail;

        @SerializedName("images")
        @Expose
        public List<String> images;

        // Add getters and setters for each field



    }


    }


