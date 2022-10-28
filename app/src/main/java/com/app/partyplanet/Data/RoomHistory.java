package com.app.partyplanet.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RoomHistory {
    public String id;
    public String room_type;
    public String room_size;
    public String price;
    public String offer_price;
    public Object image;
    public String availability;
    public String bed_type;
    public String max_adult;
    public String max_child;
    public Object totalroomsno;
    public String ac_type;
    public String extra_bed_charge;
    public String facilities;
    public String services;
    public String about_us;
    public String status;
    public String is_delete;
    public String listing_id;
    public String created_at;
    public String booking_id;
    ////////////////////////////
    @SerializedName("galleryimages")
    @Expose
    public ArrayList<Galleryimages> galleryimages;

    public ArrayList<Galleryimages> getGalleryimages() {
        return galleryimages;
    }

    public void setGalleryimages(ArrayList<Galleryimages> galleryimages) {
        this.galleryimages = galleryimages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_size() {
        return room_size;
    }

    public void setRoom_size(String room_size) {
        this.room_size = room_size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBed_type() {
        return bed_type;
    }

    public void setBed_type(String bed_type) {
        this.bed_type = bed_type;
    }

    public String getMax_adult() {
        return max_adult;
    }

    public void setMax_adult(String max_adult) {
        this.max_adult = max_adult;
    }

    public String getMax_child() {
        return max_child;
    }

    public void setMax_child(String max_child) {
        this.max_child = max_child;
    }

    public Object getTotalroomsno() {
        return totalroomsno;
    }

    public void setTotalroomsno(Object totalroomsno) {
        this.totalroomsno = totalroomsno;
    }

    public String getAc_type() {
        return ac_type;
    }

    public void setAc_type(String ac_type) {
        this.ac_type = ac_type;
    }

    public String getExtra_bed_charge() {
        return extra_bed_charge;
    }

    public void setExtra_bed_charge(String extra_bed_charge) {
        this.extra_bed_charge = extra_bed_charge;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getListing_id() {
        return listing_id;
    }

    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getVendor_food_menu_id() {
        return vendor_food_menu_id;
    }

    public void setVendor_food_menu_id(String vendor_food_menu_id)
    {
        this.vendor_food_menu_id = vendor_food_menu_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title)
    {
        this.sub_title = sub_title;
    }

    public String vendor_food_menu_id;
    public String type;
    public String qty;
    public String total_amt;
    public String menu_id;
    public String title;
    public String sub_title;


}
