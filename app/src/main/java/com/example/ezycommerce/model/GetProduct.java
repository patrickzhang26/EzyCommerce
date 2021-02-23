package com.example.ezycommerce.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetProduct implements Serializable {

        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("nim")
        @Expose
        private String nim;
        @SerializedName("nama")
        @Expose
        private String nama;
        @SerializedName("productId")
        @Expose
        private Object productId;
        @SerializedName("credits")
        @Expose
        private String credits;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getNim() {
            return nim;
        }

        public void setNim(String nim) {
            this.nim = nim;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public String getCredits() {
            return credits;
        }

        public void setCredits(String credits) {
            this.credits = credits;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

}

