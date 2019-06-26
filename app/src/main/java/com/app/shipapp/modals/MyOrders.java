package com.app.shipapp.modals;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrders {

@SerializedName("result")
@Expose
private Boolean result;
@SerializedName("message")
@Expose
private List<Message> message = null;

public Boolean getResult() {
return result;
}

public void setResult(Boolean result) {
this.result = result;
}

public List<Message> getMessage() {
return message;
}

public void setMessage(List<Message> message) {
this.message = message;
}

}