package com.blinge.deliveryguy.ordermanager;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by rushabh on 19/03/16.
 */

@ParseClassName("OrdersInformation")
public class OrderInformation extends ParseObject {

    public static String TYPE_PICKUP="pickup";
    public static String TYPE_DELIVER="deliver";

    public static String STATUS_PENDING="pending";
    public static String STATUS_COMPLETED="completed";

    String customerName;
    String customerNumber;
    String customerAddress;
    String orderStatus;
    String orderType;
    private int productName;

    public String getCustomerName() {
        return getData("customerName");
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        put("customerName", customerName);
    }

    public String getCustomerNumber() {
        return getData("customerNumber");
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerAddress() {
        return getData("customerAddress");
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getOrderStatus() {
        return getData("orderStatus");
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return getData("orderType");
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getData(String key) {
        Object data = get(key);
        if (data != null) {
            return data.toString();
        }

        return null;
    }

    public String getProductName() {
        return getData("orderName");
    }
}
