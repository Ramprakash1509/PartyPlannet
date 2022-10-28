package com.app.partyplanet.Utils;

import android.util.Log;

import com.app.partyplanet.Data.BookingHistoryModel;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.DataRespone;
import com.app.partyplanet.Data.ReviewDataModel;
import com.app.partyplanet.Data.RoomDataModel;
import com.app.partyplanet.Data.RoomGetByCarId;
import com.app.partyplanet.Data.RoomHistoryModel;
import com.app.partyplanet.Data.Rooms;
import com.app.partyplanet.Data.secureLoginResponse;
import com.app.partyplanet.RestaurantData.CartViewModel;
import com.app.partyplanet.RestaurantData.Cartmenu;
import com.app.partyplanet.RestaurantData.ResturantModel;
import com.app.partyplanet.RestaurantData.TimeSlotModel;
import com.app.partyplanet.Restaurants.Restaurantbooking;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllAPIs
{
    @FormUrlEncoded
    @POST("/api/v1/user/signup")
    Call<secureLoginResponse> createAccount(@Header("X-API-KEY") String authorization,
                                            @Field("fname") String fname,
                                            @Field("lname") String lname,
                                            @Field("email") String email,
                                            @Field("password") String password,
                                            @Field("cpassword") String cpassword


                                            );
    @FormUrlEncoded
    @POST("/api/v1/user/signin")
    Call<secureLoginResponse> signIn(@Header("X-API-KEY") String authorization,
                                            @Field("email") String email,
                                            @Field("password") String password,
                                            @Field("device_id") String device_id,
                                            @Field("firebase_token") String firebase_token

                                           );

    @Multipart
    @POST("/api/v1/user/update")
    Call<DataRespone> upDateProfile(
                                     @Header("X-API-KEY") String authorization,
                                     @Part("id") RequestBody id,
                                     @Part("fname") RequestBody fname,
                                     @Part("lname") RequestBody lname,
                                     @Part("phone_no") RequestBody phone_no,
                                     @Part("city") RequestBody city,
                                     @Part("state") RequestBody state,
                                     @Part("address_1") RequestBody address_1,
                                     @Part("address_2") RequestBody address_2,
                                     @Part("zipcode") RequestBody zipcode,
                                     @Part("lattitude") RequestBody lattitude,
                                     @Part("longitude") RequestBody longitude,
                                     @Part("firebase_token") RequestBody firebase_token,
                                     @Part MultipartBody.Part file5


    );
    @FormUrlEncoded
    @POST("/api/v1/user/forget-password")
    Call<secureLoginResponse> forgetpasswor(
                                        @Header("X-API-KEY") String authorization,
                                        @Field("email") String email

    );
    @FormUrlEncoded
    @POST("/api/v1/user/reset-password")
    Call<secureLoginResponse> changePassword(
                                      @Header("X-API-KEY") String authorization,
                                      @Field("user_id") String user_id,
                                      @Field("old_password") String old_password,
                                      @Field("new_password") String new_password,
                                      @Field("conf_password") String conf_password
    );
    @FormUrlEncoded
    @POST("/api/v1/user/otp-verification")
    Call<secureLoginResponse> otpVerify(@Header("X-API-KEY") String authorization,
                                        @Field("email") String email,
                                        @Field("verification_code") String verification_code,
                                        @Field("new_password") String new_password,
                                        @Field("conf_password") String conf_password
    );

    @FormUrlEncoded
    @POST("/api/v1/user/allmodules")
    Call<DasboadModel> bannerSlider(@Header("X-API-KEY") String authorization,
                                    @Field("start") String start,
                                    @Field("next") String next
   );
    @FormUrlEncoded
    @POST("/api/v1/user/listing")
    Call<DasboadModel> listingallModule(@Header("X-API-KEY") String authorization,
                                        @Field("start") String start,
                                        @Field("next") String next
    );


    @POST("/api/v1/user/services")
    Call<DasboadModel> userServices(@Header("X-API-KEY") String authorization

    );
    @FormUrlEncoded
    @POST("/api/v1/user/searching")
    Call<DasboadModel> searchingAllmoduls(@Header("X-API-KEY") String authorization,
                                    @Field("keyword") String keyword

    );
    @FormUrlEncoded
    @POST("/api/v1/user/add-to-cart")
    Call<secureLoginResponse> addTocart(@Header("X-API-KEY") String authorization,
                                          @Field("user_id") String user_id,
                                          @Field("listing_id") String listing_id


    );
    @FormUrlEncoded
    @POST("/api/v1/user/view-cart")
    Call<secureLoginResponse> viewCart(@Header("X-API-KEY") String authorization,
                                        @Field("user_id") String user_id



    );
    @FormUrlEncoded
    @POST("/api/v1/user/remove-from-cart")
    Call<secureLoginResponse> deleteCart(@Header("X-API-KEY") String authorization,
                                        @Field("user_id") String user_id,
                                        @Field("listing_id") String listing_id


    );
    @FormUrlEncoded
    @POST("/api/v1/user/all-listing-by-module-id")
    Call<DasboadModel> viewMore(@Header("X-API-KEY") String authorization,
                                         @Field("module_id") String module_id,
                                         @Field("start") String start,
                                         @Field("next") String next
    );

    @FormUrlEncoded
    @POST("/api/v1/user/review")
    Call<ReviewDataModel> writeReview(@Header("X-API-KEY") String authorization,
                                      @Field("listing_id") String listing_id,
                                      @Field("rated_by") String rated_by,
                                      @Field("cleanliness") String cleanliness,
                                      @Field("staff_and_services") String staff_and_services,
                                      @Field("amenities") String amenities,
                                      @Field("eco_friendliness") String eco_friendliness,
                                      @Field("facilities") String facilities,
                                      @Field("comments") String comments
    );
    @FormUrlEncoded
    @POST("/api/v1/user/signout")
    Call<secureLoginResponse> userLogOut(@Header("X-API-KEY") String authorization,
                                       @Field("user_id") String user_id



    );
    @FormUrlEncoded
    @POST("/api/v1/user/Allreviews")
    Call<secureLoginResponse> showReview(@Header("X-API-KEY") String authorization,
                                         @Field("listing_id") String listing_id



    );
    @FormUrlEncoded
    @POST("/Api/User/GetRoomListBylistid")
    Call<RoomDataModel> getAllRoomList(@Header("X-API-KEY") String authorization,
                                       @Field("list_id") String list_id



    );
    @FormUrlEncoded
    @POST("/api/v1/user/search-rooms")
    Call<RoomDataModel> searchRooms(@Header("X-API-KEY") String authorization,
                                       @Field("listid") String listid,
                                       @Field("checkindate") String checkindate,
                                       @Field("checkout_date") String checkout_dat,
                                       @Field("adult_no") String adult_no,
                                       @Field("children_no") String children_no



    );
    @FormUrlEncoded
    @POST("/api/v1/user/check-room-availibility")
    Call<Rooms> roomNumber(@Header("X-API-KEY") String authorization,
                           @Field("date_in") String date_in,
                           @Field("date_out") String date_out,
                           @Field("roomtypeid") String roomtypeid



    );
    @FormUrlEncoded
    @POST("/api/v1/user/addtocartdata")
    Call<secureLoginResponse> reserveRoom(@Header("X-API-KEY") String authorization,
                           @Field("list_id") String list_id,
                           @Field("checkindate") String checkindate,
                           @Field("checkoutdate") String checkoutdate,
                           @Field("maxadult") String maxadult,
                           @Field("maxchild") String maxchild,
                           @Field("oldoffer_price") String oldoffer_price,
                           @Field("old_price") String old_price,
                           @Field("user_id") String user_id,
                           @Field("roomid[]") ArrayList<String> roomid,
                           @Field("selectroom[]") ArrayList<String> selectroom


    );
    @FormUrlEncoded
    @POST("/api/v1/user/GetroomDeatailsByCartid")
    Call<RoomGetByCarId> getroomDeatailsByCartid(@Header("X-API-KEY") String authorization,
                                                 @Field("cart_id") String cart_id,
                                                 @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("/api/v1/user/addbooking")
    Call<RoomGetByCarId> payment(@Header("X-API-KEY") String authorization,
                                                 @Field("cart_id") String cart_id,
                                                 @Field("payment_type") String payment_type,
                                                 @Field("txn_id") String txn_id,
                                                 @Field("amount") String amount,
                                                 @Field("status") String status,
                                                 @Field("note") String note,
                                                 @Field("response") String response
    );
    @FormUrlEncoded
    @POST("/api/v1/user/GetBookinghistoryByuserid")
    Call<BookingHistoryModel> bookingHistory(@Header("X-API-KEY") String authorization,
                                             @Field("user_id") String user_id

    );
    @FormUrlEncoded
    @POST("/api/v1/user/Get_food_menu_bylistid")
    Call<ResturantModel> restaurantList(@Header("X-API-KEY") String authorization,
                                        @Field("listing_id") String listing_id

    );
    @FormUrlEncoded
    @POST("/api/v1/user/addrestorentitems")
    Call<Cartmenu> add_menu(@Header("X-API-KEY") String authorization,
                            @Field("id") String id,
                            @Field("size") String size,
                            @Field("user_id") String user_id,
                            @Field("qty") String qty,
                            @Field("listing_id") String listing_id

    );
    @FormUrlEncoded
    @POST("/api/v1/user/delete_foodmenu_by_cart")
    Call<Cartmenu> delete_cart_menu(@Header("X-API-KEY") String authorization,
                                    @Field("user_id") String user_id,
                                    @Field("food_menu_id") String food_menu_id

    );
    @FormUrlEncoded
    @POST("/api/v1/user/check_slot_availability")
    Call<Cartmenu> checkSlot(@Header("X-API-KEY") String authorization,
                                    @Field("user_id") String user_id,
                                    @Field("date") String date,
                                    @Field("guest") String guest,
                                    @Field("time_slot_id") String time_slot_id,
                                    @Field("listing_id") String listing_id,
                                    @Field("total_price") String total_price

    );
    @POST("/api/v1/user/getalltimeslot")
    Call<TimeSlotModel> bookTimeSlot(@Header("X-API-KEY") String authorization


    );

    @FormUrlEncoded
    @POST("/api/v1/user/getrestorentitems")
    Call<CartViewModel> showViewFoodMenuList(@Header("X-API-KEY") String authorization,
                                             @Field("listing_id") String listing_id,
                                             @Field("user_id") String user_id

    );
    @FormUrlEncoded
    @POST("/api/v1/user/restorent_addbooking")
    Call<RoomGetByCarId> restaurantPayment(@Header("X-API-KEY") String authorization,
                                 @Field("cartid") String cartid,
                                 @Field("payment_type") String payment_type,
                                 @Field("txn_id") String txn_id,
                                 @Field("amount") String amount,
                                 @Field("status") String status,
                                 @Field("payment_note") String payment_note,
                                 @Field("response") String response
    );
    @FormUrlEncoded
    @POST("/api/v1/user/Getbookingitems")
    Call<RoomHistoryModel> getRoosOrFoodMenu(@Header("X-API-KEY") String authorization,
                                             @Field("module_id") String module_id,
                                             @Field("booking_id") String booking_id

    );









}
