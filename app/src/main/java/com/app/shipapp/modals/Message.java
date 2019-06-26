package com.app.shipapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("billing_email")
    @Expose
    private String billingEmail;
    @SerializedName("billing_name")
    @Expose
    private String billingName;
    @SerializedName("billing_address")
    @Expose
    private String billingAddress;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("billing_province")
    @Expose
    private String billingProvince;
    @SerializedName("billing_postalcode")
    @Expose
    private String billingPostalcode;
    @SerializedName("billing_phone")
    @Expose
    private String billingPhone;
    @SerializedName("billing_name_on_card")
    @Expose
    private String billingNameOnCard;
    @SerializedName("billing_discount")
    @Expose
    private String billingDiscount;
    @SerializedName("billing_discount_code")
    @Expose
    private Object billingDiscountCode;
    @SerializedName("billing_subtotal")
    @Expose
    private String billingSubtotal;
    @SerializedName("billing_tax")
    @Expose
    private String billingTax;
    @SerializedName("billing_total")
    @Expose
    private String billingTotal;
    @SerializedName("payment_gateway")
    @Expose
    private String paymentGateway;
    @SerializedName("shipped")
    @Expose
    private String shipped;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingProvince() {
        return billingProvince;
    }

    public void setBillingProvince(String billingProvince) {
        this.billingProvince = billingProvince;
    }

    public String getBillingPostalcode() {
        return billingPostalcode;
    }

    public void setBillingPostalcode(String billingPostalcode) {
        this.billingPostalcode = billingPostalcode;
    }

    public String getBillingPhone() {
        return billingPhone;
    }

    public void setBillingPhone(String billingPhone) {
        this.billingPhone = billingPhone;
    }

    public String getBillingNameOnCard() {
        return billingNameOnCard;
    }

    public void setBillingNameOnCard(String billingNameOnCard) {
        this.billingNameOnCard = billingNameOnCard;
    }

    public String getBillingDiscount() {
        return billingDiscount;
    }

    public void setBillingDiscount(String billingDiscount) {
        this.billingDiscount = billingDiscount;
    }

    public Object getBillingDiscountCode() {
        return billingDiscountCode;
    }

    public void setBillingDiscountCode(Object billingDiscountCode) {
        this.billingDiscountCode = billingDiscountCode;
    }

    public String getBillingSubtotal() {
        return billingSubtotal;
    }

    public void setBillingSubtotal(String billingSubtotal) {
        this.billingSubtotal = billingSubtotal;
    }

    public String getBillingTax() {
        return billingTax;
    }

    public void setBillingTax(String billingTax) {
        this.billingTax = billingTax;
    }

    public String getBillingTotal() {
        return billingTotal;
    }

    public void setBillingTotal(String billingTotal) {
        this.billingTotal = billingTotal;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}