package com.app.shipapp.app_utils;

public interface AppAPis {
    String BASE_URL = "http://bstwebdemo.co.in/client/development/mantiseye/mantiseye_api/v1/";
    String LOGIN_API = BASE_URL+"auth";
    String SIGN_UP_API = BASE_URL+"register_data";
    String SHIP_LIST = BASE_URL+"ship?token=";
    String SEARCH_SHIP_LIST = BASE_URL+"search_ship?token=";
    String USER_PROFILE = BASE_URL+"profile?token=";
    String SAVE_ORDER = BASE_URL+"checkout?token=";
}
