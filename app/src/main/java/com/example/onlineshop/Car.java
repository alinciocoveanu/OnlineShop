package com.example.onlineshop;

class Car {
    private String name;
    private Integer year;
    private Integer price;
    private Integer imgUrl;

    String getName() {
        return name;
    }

    Integer getImgUrl() {
        return imgUrl;
    }

    Integer getYear() {
        return year;
    }

    Integer getPrice() {
        return price;
    }

    Car(String name, Integer year, Integer price, Integer imgUrl) {
        this.name = name;
        this.year = year;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}
