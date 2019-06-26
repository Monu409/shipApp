package com.app.shipapp.app_utils;

public interface AppAPis {
    String BASE_URL = "http://bstwebdemo.co.in/client/development/mantiseye/mantiseye_api/v1/";
    String LOGIN_API = BASE_URL+"auth";//{email,password}
    String SIGN_UP_API = BASE_URL+"register_data";//{name,email,password,password_confirmation}
    String SOCIAL_LOGIN = BASE_URL+"register_social";//{name,email,social_id,login_with}
    String SHIP_LIST = BASE_URL+"ship?token=";// get api
    String SEARCH_SHIP_LIST = BASE_URL+"search_ship?token=";//{ship_name}
    String USER_PROFILE = BASE_URL+"profile?token=";//for get profile data(will be get api) as well as for update profile (will be post){name,mobile,email,password}
    String SAVE_ORDER = BASE_URL+"checkout?token=";//{user_id,email,name,address,city,province,postalcode,phone,name_on_card,subtotal}
    String MY_ORDERS = BASE_URL+"order?token=";//get api
}