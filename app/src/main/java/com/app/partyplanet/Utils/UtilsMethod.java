package com.app.partyplanet.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.app.partyplanet.Activity.GetLocation;
import com.app.partyplanet.Activity.Login;
import com.app.partyplanet.Activity.OtpVerification;
import com.app.partyplanet.Activity.SignInEmail;
import com.app.partyplanet.Activity.SplashActivity;
import com.app.partyplanet.DashBoad.BookingReview;
import com.app.partyplanet.DashBoad.DashBoad;
import com.app.partyplanet.DashBoad.FinalBookingActivity;
import com.app.partyplanet.DashBoad.RoomActivity;
import com.app.partyplanet.DashBoad.RoomsHistry;
import com.app.partyplanet.DashBoad.SuccessFull;
import com.app.partyplanet.DashBoad.WriteReview;
import com.app.partyplanet.Data.BookingHistoryModel;
import com.app.partyplanet.Data.DasboadModel;
import com.app.partyplanet.Data.Data;
import com.app.partyplanet.Data.DataRespone;
import com.app.partyplanet.Data.ReviewDataModel;
import com.app.partyplanet.Data.RoomDataModel;
import com.app.partyplanet.Data.RoomGetByCarId;
import com.app.partyplanet.Data.RoomHistoryModel;
import com.app.partyplanet.Data.Rooms;
import com.app.partyplanet.Data.secureLoginResponse;
import com.app.partyplanet.Lounges.LoungeHistory;
import com.app.partyplanet.RestaurantData.CartViewModel;
import com.app.partyplanet.RestaurantData.Cartmenu;
import com.app.partyplanet.RestaurantData.ResturantModel;
import com.app.partyplanet.RestaurantData.TimeSlotModel;
import com.app.partyplanet.Restaurants.BookMenuActivity;
import com.app.partyplanet.Restaurants.FoodMenuHistory;
import com.app.partyplanet.Restaurants.RestauMenuBooking;
import com.app.partyplanet.Restaurants.Restaurantbooking;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public enum UtilsMethod {
    INSTANCE;

    public void setLoginrespose(Context context, String Loginrespose, String one) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.Loginrespose, Loginrespose);
        editor.putString(ApplicationConstant.INSTANCE.one, one);
        editor.commit();

    }
    public void BookingHistory(Context context, String bookinghistry, String one)
    {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.History, bookinghistry);
        editor.commit();

    }

    public void storelist(Context context, String storelist) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.storelist, storelist);
        editor.commit();
    }
    public void services(Context context, String service) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.service, service);
        editor.commit();
    }
    public void bannerlist(Context context, String bannerlist)
    {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.bannerlist, bannerlist);
        editor.commit();
    }

    public void getHistrory(Context context,String module_id,String booking_id,String moduleType,String date)
    {  Log.d("djkfhdfjfgkjdfg",module_id+"    "+booking_id+"   "+moduleType);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<RoomHistoryModel> call = api.getRoosOrFoodMenu(header,module_id,booking_id);
        call.enqueue(new Callback<RoomHistoryModel>()
        {
            @Override
            public void onResponse(Call<RoomHistoryModel> call, Response<RoomHistoryModel> response)
            {
                Log.d("dsgdfgdfgh",""+response.body().getStatus());


                if (response.body().getStatus()==1)
                {
                    if (moduleType.equalsIgnoreCase("Hotels"))
                    {
                        Log.d("dhfhf",new Gson().toJson(response.body().getBanerData()));
                        context.startActivity(new Intent(context, RoomsHistry.class).putExtra("data", new Gson().toJson(response.body()).toString()).putExtra("date",date));
                    }
                    else if(moduleType.equalsIgnoreCase("Restaurants"))
                    {
                        Log.d("dhfhf",new Gson().toJson(response.body().getBanerData()));

                        context.startActivity(new Intent(context, FoodMenuHistory.class).putExtra("data", new Gson().toJson(response.body()).toString()).putExtra("date",date));
                    }
                    else if (moduleType.equalsIgnoreCase("Lounges"))
                    {
                        context.startActivity(new Intent(context, FoodMenuHistory.class).putExtra("data", new Gson().toJson(response.body()).toString()).putExtra("date",date));

                    }
                }
            }

            @Override
            public void onFailure(Call<RoomHistoryModel> call, Throwable t) {
                Log.d("ddgdfg",""+t.getMessage());
            }
        });


    }















    public void restaurantPayment(Context context,String cartid,String payment_type,String txn_id,String status,String amount,String payment_note,String response )
    {
        Log.d("iueryegsgue",cartid+"  "+payment_type+"  "+txn_id+"   "+status+"   "+amount+" "+payment_note+"    "+response);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<RoomGetByCarId> call = api.restaurantPayment(header,cartid,payment_type,txn_id,amount,status,payment_note,response);
        call.enqueue(new Callback<RoomGetByCarId>() {
            @Override
            public void onResponse(Call<RoomGetByCarId> call, Response<RoomGetByCarId> response)
            {
                Log.d("324sdjdfhjfhdff",""+response.body().getStatus());
                if(response.body().getStatus()==1)
                {
                    //  sweetAlertBox(context,"Payment Successful","jhd");
                    context.startActivity(new Intent(context,SuccessFull.class));
                }
                else
                {
                    alertBox("","Payment Failed",context);
                }
            }

            @Override
            public void onFailure(Call<RoomGetByCarId> call, Throwable t)
            {
                Log.d("jdkhfjh",t.getMessage());
            }
        });
    }






    public void showFoodMenuList(Context context,String listingId,String userId)
    {   Log.d("rywuert43", "UserId  " + userId +" listingId   "+listingId );
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<CartViewModel> call=api.showViewFoodMenuList(header,listingId,userId);
        call.enqueue(new Callback<CartViewModel>()
        {
            @Override
            public void onResponse(Call<CartViewModel> call, Response<CartViewModel> response)
            {
                Log.d("showFoodMenuList--",""+response.body().getStatus());
                  if (response.body().getStatus()==1)
                  {
                   //  context.startActivity(new Intent(context, RestauMenuBooking.class).putExtra("restaurantId",listingId));
                      FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("ShowFoodMenu",
                              "" + new Gson().toJson(response.body()).toString());
                      GlobalBus.getBus().post(fragmentActivityMessage);

                  }
                  else
                  {
                      alertBox("","please add food menu  in menu list ",context);
                  }


            }
            @Override
            public void onFailure(Call<CartViewModel> call, Throwable t)
            {

            }
        });
    }

  public void timeSlot(Context context)
  {
      String header = ApplicationConstant.INSTANCE.Headertoken;
      AllAPIs api = APIClient.getClient().create(AllAPIs.class);
      Call<TimeSlotModel> call=api.bookTimeSlot(header);
      call.enqueue(new Callback<TimeSlotModel>() {
          @Override
          public void onResponse(Call<TimeSlotModel> call, Response<TimeSlotModel> response)
          {
              Log.d("timeSlot--",""+new Gson().toJson(response.body().getData()));
              if (response.body().getStatus()==1)
              {
                  FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("TimeSlot",
                          "" + new Gson().toJson(response.body()).toString());
                  GlobalBus.getBus().post(fragmentActivityMessage);
              }
              else
              {

              }

          }

          @Override
          public void onFailure(Call<TimeSlotModel> call, Throwable t) {

          }
      });

  }



    public void checkSlotAvailability(Context context,String userId,String date,String guest,String time_slot_id,String listing_id,String total_price)
    {
        Log.d("fbmcxjjsuerrr", "UserId  " + userId + "  Menu Id " + date+" "+guest+"  "+time_slot_id+"  "+listing_id+"  "+total_price);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<Cartmenu> call=api.checkSlot(header,userId,date,guest,time_slot_id,listing_id,total_price);
        call.enqueue(new Callback<Cartmenu>() {
            @Override
            public void onResponse(Call<Cartmenu> call, Response<Cartmenu> response)
            {
                Log.d("uttyyufyug",""+response.body().getStatus());
                if (response.body().getStatus()==1)
                {
                   context.startActivity(new Intent(context, BookMenuActivity.class).putExtra("data", new Gson().toJson(response.body()).toString()).putExtra("total_price",total_price));
                  }
                else
                {
                    alertBox("","This time Slot is not Available please select another time slot",context);
                }

            }

            @Override
            public void onFailure(Call<Cartmenu> call, Throwable t)
            {
                Log.d("isufvknv",""+t.getMessage());

            }
        });
    }






    public void deleteMenu(Context context,String userId,String menuId)
    {
        Log.d("fbmcxjjsuerrr", "UserId  " + userId + "  Menu Id " + menuId);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<Cartmenu> call=api.delete_cart_menu(header,userId,menuId);
        call.enqueue(new Callback<Cartmenu>() {
            @Override
            public void onResponse(Call<Cartmenu> call, Response<Cartmenu> response)
            {
                Log.d("dkfsjhdhfgnddjhh",""+response.body().getStatus());
                if (response.body().getStatus()==1)
                {
                    alertBox("Menu List","This menu is delete from menu list",context);

                }
                else
                {
                    alertBox("Menu List",response.body().getMessage(),context);

                }
            }

            @Override
            public void onFailure(Call<Cartmenu> call, Throwable t) {

            }
        });
    }

    public void addMenu(Context context,String id,String price,String userId,String quantity,String listingId)
    {    Log.d("fbmcxjjsuerrr","id  "+id+"  price "+price+"  user "+userId+"   quantity  "+quantity+" listingId      "+listingId );
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<Cartmenu> call=api.add_menu(header,id,price,userId,quantity,listingId);
        call.enqueue(new Callback<Cartmenu>() {
            @Override
            public void onResponse(Call<Cartmenu> call, Response<Cartmenu> response)
            {
                Log.d("fbmcdfgfgxjjsuerrr",""+response.body().getStatus());
                if (response.body().getStatus()==1)
                {
                    alertBox("Menu List", "This Menu  is add for menu list", context);
                }
                else
                {
                    alertBox("Menu List", response.body().getMessage(), context);
                }
            }

            @Override
            public void onFailure(Call<Cartmenu> call, Throwable t) {
                Log.d("fbmcdfgfgxjjsuerrr",""+t.getMessage());
            }
        });
    }




   public void restaurantList(Context context,String listId,String data,String listingId)
{      Log.d("csdfsdf",""+listId);
    String header = ApplicationConstant.INSTANCE.Headertoken;
    AllAPIs api = APIClient.getClient().create(AllAPIs.class);
    Call<ResturantModel> call=api.restaurantList(header,listId);
    call.enqueue(new Callback<ResturantModel>()
    {
        @Override
        public void onResponse(Call<ResturantModel> call, Response<ResturantModel> response)
        {
            Log.d("stautsvalue",""+response.body().getStatus());
            if (response.body().getStatus()==1)
            {
                context.startActivity(new Intent(context, Restaurantbooking.class).putExtra("data", new Gson().toJson(response.body()).toString()).putExtra("datum",data));
            }
            else
            {
                alertBox("","data  not available",context);
            }
        }

        @Override
        public void onFailure(Call<ResturantModel> call, Throwable t)
        {
            Log.d("stautsvalue",""+t.getMessage());
        }
    });

}





    public void bookingHistory(Context context,String userId)
    {
        Log.d("sdfkjhdsvbvv",""+userId);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<BookingHistoryModel> call=api.bookingHistory(header,userId);
        call.enqueue(new Callback<BookingHistoryModel>()
        {
            @Override
            public void onResponse(Call<BookingHistoryModel> call, Response<BookingHistoryModel> response)
            {
                Log.d("324urdyfgkjsdff",""+response.body().getStatus());
                if(response.body().getStatus()==1)
                {
                    Log.d("revdgiewjf",""+new Gson().toJson(response.body()).toString());
                  /*  FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("bookingHistory",
                            "" + new Gson().toJson(response.body().getBanerData()).toString());
                    GlobalBus.getBus().post(fragmentActivityMessage);*/
                   UtilsMethod.INSTANCE.BookingHistory(context, "" + new Gson().toJson(response.body()), null);


                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<BookingHistoryModel> call, Throwable t)
            {
                Log.d("jdkfbbbhfjh",t.getMessage());
            }
        });


    }


    public void payment(Context context,String cart_id,String payment_type,String txn_id,String status,String amount,String note,String response )
    {
        Log.d("iueryegsgue",cart_id+"  "+payment_type+"  "+txn_id+"   "+status+"   "+amount+" "+note+"    "+response);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<RoomGetByCarId> call = api.payment(header,cart_id,payment_type,txn_id,amount,status,note,response);
        call.enqueue(new Callback<RoomGetByCarId>() {
            @Override
            public void onResponse(Call<RoomGetByCarId> call, Response<RoomGetByCarId> response)
            {
                Log.d("324sdff",""+response.body().getStatus());
                 if(response.body().getStatus()==1)
                 {
                  //  sweetAlertBox(context,"Payment Successful","jhd");
                    context.startActivity(new Intent(context,SuccessFull.class));
                 }
                 else
                 {
                     alertBox("","Payment Failed",context);
                 }
            }

            @Override
            public void onFailure(Call<RoomGetByCarId> call, Throwable t)
            {
                Log.d("jdkhfjh",t.getMessage());
            }
        });
    }


    public void RoomDeatailsByCartid(Context context,String cartId,String userId,String userName)
    {
        Log.d("erterdfgh",cartId+"  "+userId+"    "+userName);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<RoomGetByCarId> call = api.getroomDeatailsByCartid(header,cartId,userId);
        call.enqueue(new Callback<RoomGetByCarId>()
        {
            @Override
            public void onResponse(Call<RoomGetByCarId> call, Response<RoomGetByCarId> response)
            {
                Log.d("iewuruiu489",""+response.body().getStatus());
                if (response.body().getStatus()==1)
                {
                    Log.d("wey73467",new Gson().toJson(response.body()).toString());
                    Intent i = new Intent(context, BookingReview.class);

                    i.putExtra("data",new Gson().toJson(response.body()).toString());
                    i.putExtra("userName",userName);
                    i.putExtra("cartId",cartId);

                    context.startActivity(i);
                }
                else
                {
                    alertBox("","data not available",context);
                }

            }

            @Override
            public void onFailure(Call<RoomGetByCarId> call, Throwable t)
            {
            Log.d("uyubdsfuh",t.getMessage());
            }
        });

    }



    public void reserveRoom(Context context,
                            String list_id,String checkindate
            ,String checkoutdate,String maxadult,String maxchild,
                            String oldoffer_price,String old_price,
                            String user_id,ArrayList<String> roomid,ArrayList<String> selectroom )
    {
        Log.d("jdfj",list_id+"   "+ checkindate+"  "+checkoutdate+"  "+maxadult+"  "+oldoffer_price+""+old_price+" "+user_id+"  "+ roomid+"  "+selectroom);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<secureLoginResponse> call = api.reserveRoom(header,list_id,checkindate,checkoutdate,maxadult,maxchild,oldoffer_price,old_price,user_id,roomid,selectroom);
        call.enqueue(new Callback<secureLoginResponse>() {
           @Override
           public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response)
           {

               if (response.body().getStatus()==1)
               {     Log.d("weuiwert4ruyyiuy73467",new Gson().toJson(response.body()).toString());
                     Intent i = new Intent(context, FinalBookingActivity.class);
                     i.putExtra("data",new Gson().toJson(response.body()).toString());
                     i.putExtra("old_price",old_price);
                     i.putExtra("offer_price",oldoffer_price);
                     i.putExtra("roomcount",oldoffer_price);
                     context.startActivity(i);
               }
               else
               {
                   Log.d("hcjgcjxzhgcvj", "" + response.body().getStatus());
               }
           }

           @Override
           public void onFailure(Call<secureLoginResponse> call, Throwable t) {
               Log.d("hcjgcjxzhgcvdddj",""+t.getMessage());

           }
       });

    }









     public void roomslist(Context context,String roomId)
     {
         String header = ApplicationConstant.INSTANCE.Headertoken;
         AllAPIs api = APIClient.getClient().create(AllAPIs.class);
         Call<Rooms> call = api.roomNumber(header,"","",roomId);
         call.enqueue(new Callback<Rooms>() {
             @Override
             public void onResponse(Call<Rooms> call, Response<Rooms> response)
             {
                 Log.d("roomlist",""+response.body().getStatus());

             }

             @Override
             public void onFailure(Call<Rooms> call, Throwable t) {

             }
         });

     }

      public void searchRooms(Context context,String hotelId,String checkIn,String checkOut,String adults,String child)
      {
          Log.d("valuesjdhfuygf", hotelId+"  "+checkIn+"  "+checkOut+"  "+adults+"  "+child);

          String header = ApplicationConstant.INSTANCE.Headertoken;
          AllAPIs api = APIClient.getClient().create(AllAPIs.class);
          Call<RoomDataModel> call = api.searchRooms(header, hotelId,checkIn,checkOut,adults,child);
          call.enqueue(new Callback<RoomDataModel>()
          {
              @Override
              public void onResponse(Call<RoomDataModel> call, Response<RoomDataModel> response)
              {
                  Log.d("rsgsdfgdbdgfdffew",""+response.body().getStatus());
                  if (response.body().getStatus()==1)
                  {

                   /*   FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Roomlist",
                              "" + new Gson().toJson(response.body()).toString());
                      GlobalBus.getBus().post(fragmentActivityMessage);*/
                      Log.d("weuiry73467",new Gson().toJson(response.body()).toString());
                      Intent i = new Intent(context, RoomActivity.class);
                      i.putExtra("data",new Gson().toJson(response.body()).toString());
                      i.putExtra("hotelId",hotelId);
                      i.putExtra("checkIn",checkIn);
                      i.putExtra("checkOut",checkOut);
                      context.startActivity(i);
                      Log.d("rsgsdfgdbdgf3dffew",""+response.body().getMessage());


                  }
                  else {
                      alertBox("","Con't be available rooms please select other's date",context);
                  }

              }

              @Override
              public void onFailure(Call<RoomDataModel> call, Throwable t) {

              }
          });

      }

    public void getRoomsList(Context context ,String hotelId)
    {
         Log.d("valuesjdhfuygf", hotelId);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<RoomDataModel> call = api.getAllRoomList(header, hotelId);
        call.enqueue(new Callback<RoomDataModel>()
        {  @Override
            public void onResponse(Call<RoomDataModel> call, Response<RoomDataModel> response)
            {
                Log.d("cbcvbbvcbb",""+response.body().getStatus());
                //  Log.d("reviewjf",""+response.body().getStatus());
              /*  FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Roomlist",
                        "" + new Gson().toJson(response.body()).toString());
                GlobalBus.getBus().post(fragmentActivityMessage);*/

            }
            @Override
            public void onFailure(Call<RoomDataModel> call, Throwable t)
            {
                Log.d("dsjw",""+t.getMessage());

            }
        });
    }


    public void showReview(Context context, String hotelId) {
        Log.d("valuesjdhfuygf", hotelId);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<secureLoginResponse> call = api.showReview(header, hotelId);
        call.enqueue(new Callback<secureLoginResponse>()
        {
            @Override
            public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response)
            {

                 if (response.body().getStatus()==1)
                 {
                     Log.d("reviewjf",""+response.body().getStatus());
                     FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("showReview",
                             "" + new Gson().toJson(response.body()).toString());
                     GlobalBus.getBus().post(fragmentActivityMessage);
                 }
                 else
                {
                    alertBox("","Data not found ",context);
                }
            }

            @Override
            public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                Log.d("ruyure87rfuf", t.getMessage());

            }
        });
    }


    public void logoutfromServer(Context context) {
        try {
            SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String secureloginResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            Data securelogincheckResponse = new Gson().fromJson(secureloginResponse, Data.class);
            String userId = securelogincheckResponse.getId();
            String header = ApplicationConstant.INSTANCE.Headertoken;
            AllAPIs api = APIClient.getClient().create(AllAPIs.class);
            Call<secureLoginResponse> call = api.userLogOut(header, userId);
            call.enqueue(new Callback<secureLoginResponse>() {
                @Override
                public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {
                    Log.d("mzbchusgfdguyyuyye8", response.body().getMessage().toString());
                    if (response != null) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show();
                            UtilsMethod.INSTANCE.setLoginrespose(context, "", "");
                            Intent startIntent = new Intent(context, SplashActivity.class);
                            startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(startIntent);
                        } else {
                            sweetAlertBoxFailed(context, response.body().getMessage(), "");
                        }
                    }


                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    sweetAlertBoxFailed(context, t.getMessage(), "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void review(Loader loader, WriteReview review, String hotelId, String userId, String cleanliness, String staff_and_services, String amenities, String eco_friendliness, String facilities, String comments) {
        Log.d("uycfgfdbhc", "" + userId + "   jhgczv h   " + hotelId);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<ReviewDataModel> call = api.writeReview(header, hotelId, userId, cleanliness, staff_and_services, amenities, eco_friendliness, facilities, comments);
        call.enqueue(new Callback<ReviewDataModel>() {
            @Override
            public void onResponse(Call<ReviewDataModel> call, Response<ReviewDataModel> response) {
                Log.d("vsd4wreg", "" + response.body().getMessage());
                Toast.makeText(review, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                if (response != null) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body().getStatus() == 1)
                        {
                            //sweetAlertBox(review,"Thank You! Your review help us to serve you better","Showdeatails");
                            new SweetAlertDialog(review, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("")
                                    .setContentText("" + "Thank You! Your review help us to serve you better")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            review.finish();
                                        }
                                    }).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ReviewDataModel> call, Throwable t) {
                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }

            }
        });
    }


    public void viewMore(Context context, String moduleId) {

        Log.d("uycbhc", "" + moduleId);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<DasboadModel> call = api.viewMore(header, moduleId, "0", "20");
        call.enqueue(new Callback<DasboadModel>() {
            @Override
            public void onResponse(Call<DasboadModel> call, Response<DasboadModel> response) {
                Log.d("vjkhjxzvkx", "" + response.body().getStatus());
                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("searchingmoduls",
                        "" + new Gson().toJson(response.body()).toString());
                GlobalBus.getBus().post(fragmentActivityMessage);
            }

            @Override
            public void onFailure(Call<DasboadModel> call, Throwable t) {

            }
        });
    }

    public void deleteCart(Context context, String userId, String listId) {
        try {
            Log.d("jsfdge8rryfg", userId + "     " + listId);
            String header = ApplicationConstant.INSTANCE.Headertoken;
            AllAPIs api = APIClient.getClient().create(AllAPIs.class);
            Call<secureLoginResponse> call = api.deleteCart(header, userId, listId);
            call.enqueue(new Callback<secureLoginResponse>()
            {
                @Override
                public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response)
                {
                    Log.d("jsfdge8rryfgyyuysdv", "" + response.body().getStatus());
                    try {
                        if (response.body().getStatus() == 1)
                        {
                            //sweetAlertBox(context,"Successfully Deleted","");
                            UtilsMethod.INSTANCE.viewCartDetails(context, userId, null);
                            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            sweetAlertBoxFailed(context, response.body().getMessage(), "");
                        }
                    } catch (Exception e) {
                        sweetAlertBoxFailed(context, "" + e, "");
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    sweetAlertBoxFailed(context, "" + t.getMessage(), "");

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewCartDetails(Context context, String userId, Loader loader) {
        try {
            String header = ApplicationConstant.INSTANCE.Headertoken;
            AllAPIs api = APIClient.getClient().create(AllAPIs.class);
            Call<secureLoginResponse> call = api.viewCart(header, userId);
            call.enqueue(new Callback<secureLoginResponse>() {
                @Override
                public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            if (response.body().getStatus() == 1)
                            {
                                Log.d("sdfhjsddezcxcvdvbh", userId + "  dbvjdbv  " + response.body().getMessage());
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("viewcart",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                            } else {
                                sweetAlertBoxFailed(context, response.body().getMessage(), "");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    Log.d("sdfhjsddedvbh", userId + "  dbvjdbv  " + t.getMessage());
                }
            });
        } catch (Exception e) {
            alertBox("", "" + e, context);
        }
    }

    public void addToCart(Context context, String userId, String listId, String name) {

        Log.d("sdfhjsdvbh", userId + "  dbvjdbv  " + listId);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<secureLoginResponse> call = api.addTocart(header, userId, listId);
        call.enqueue(new Callback<secureLoginResponse>() {
            @Override
            public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {
                try {
                    if (response.body().getStatus() == 1)
                    {
                        Log.d("dsf2gdt",new Gson().toJson(response.body().getData()));

                        sweetAlertBox(context, " " + name + " is added your cart ", "onlyshow");
                    } else {
                        sweetAlertBoxFailed(context, " " + name + " is already added in your cart", "onlyshow");
                    }
                } catch (Exception e) {
                    Log.e("error: ", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<secureLoginResponse> call, Throwable t)
            {

                Log.d("dfdhnnb", "" + t);
                alertBox(" ", t.getMessage(), context);

            }
        });

    }

    public void searching(Context context, String keyword) {
        try {
            String header = ApplicationConstant.INSTANCE.Headertoken;
            AllAPIs api = APIClient.getClient().create(AllAPIs.class);
            Log.d("ssvhccddff", "" + keyword);
            Call<DasboadModel> call = api.searchingAllmoduls(header, keyword);
            call.enqueue(new Callback<DasboadModel>() {
                @Override
                public void onResponse(Call<DasboadModel> call, Response<DasboadModel> response) {

                    try {
                        if (response.body().getStatus() == 1) {
                            FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("searchingmoduls",
                                    "" + new Gson().toJson(response.body()).toString());
                            GlobalBus.getBus().post(fragmentActivityMessage);
                        } else {
                            sweetAlertBoxFailed(context, response.body().getMessage(), "");

                        }
                    } catch (Exception e) {
                        sweetAlertBoxFailed(context, "" + e, "");
                    }
                }

                @Override
                public void onFailure(Call<DasboadModel> call, Throwable t) {
                    Log.d("xds", t + "");
                    alertBox(" ", t.getMessage(), context);
                }
            });
        } catch (Exception e) {
            alertBox(" ", "" + e, context);
        }


    }

    public void userServices(Context context) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Log.d("ssvhdffhgh", "" + header);
        Call<DasboadModel> call = api.userServices(header);
        call.enqueue(new Callback<DasboadModel>() {
            @Override
            public void onResponse(Call<DasboadModel> call, Response<DasboadModel> response) {
                Log.d("weoriehhf", response.body().getStatus() + "");
                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("userServices",
                        "" + new Gson().toJson(response.body()).toString());
                GlobalBus.getBus().post(fragmentActivityMessage);
                UtilsMethod.INSTANCE.services(context, "" +  new Gson().toJson(response.body()).toString());


            }

            @Override
            public void onFailure(Call<DasboadModel> call, Throwable t) {

            }
        });

    }

    public void listingAllModule(Context context) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Log.d("ssvhdff", "" + header);
        Call<DasboadModel> call = api.listingallModule(header, "0", "4");
        call.enqueue(new Callback<DasboadModel>() {
            @Override
            public void onResponse(Call<DasboadModel> call, Response<DasboadModel> response) {
                Log.d("mfgdjfdfhgiuru", response.body().getStatus() + "");

                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("allmodulelisted",
                        "" + new Gson().toJson(response.body()).toString());
                GlobalBus.getBus().post(fragmentActivityMessage);
                UtilsMethod.INSTANCE.storelist(context, "" + new Gson().toJson(response.body()).toString());


            }

            @Override
            public void onFailure(Call<DasboadModel> call, Throwable t) {
                Log.d("wqesdfr34", "" + t);

            }
        });


    }

    public void bannerSliderImage(Context context)
    {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Log.d("sdff", "hdfsddfeader" + header);
        Call<DasboadModel> call = api.bannerSlider(header, "0", "2");
        call.enqueue(new Callback<DasboadModel>() {
            @Override
            public void onResponse(Call<DasboadModel> call, Response<DasboadModel> response)
            {
                Log.d("wqer343", response.body().getStatus() + "");
                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("bannerList", "" + new Gson().toJson(response.body()).toString());
                GlobalBus.getBus().post(fragmentActivityMessage);
                UtilsMethod.INSTANCE.bannerlist(context, "" +  new Gson().toJson(response.body()).toString());
            }

            @Override
            public void onFailure(Call<DasboadModel> call, Throwable t) {
                Log.d("wqer34", "" + t);

            }
        });


    }


    public void createAccount(Context context, Loader loader, String fname, String lname, String email, String password, String cpassword) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Log.d("", "header" + header);

        Call<secureLoginResponse> call = api.createAccount(header, fname, lname, email, password, cpassword);
        call.enqueue(new Callback<secureLoginResponse>() {
            @Override
            public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {
                Toast.makeText(context, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                System.out.print("response" + response.body().getStatus());
                if (response != null) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body().getStatus() == 1) { /*Intent i = new Intent(context, Login.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);*/
                            sweetAlertBox(context, response.body().getMessage(), "Login");
                        } else {
                            alertBox(" ", response.body().getMessage(), context);
                        }
                    } catch (Exception e) {
                        Log.e("error: ", e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                sweetAlertBoxFailed(context, t.getMessage(), "");
                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }

            }
        });

    }


    public void signIn(Context context, Loader loader, String email, String password) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<secureLoginResponse> call = api.signIn(header, email, password, null, null);
        call.enqueue(new Callback<secureLoginResponse>() {
            @Override
            public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {
                Log.d("status", String.valueOf(response.body().getStatus()));
                if (response != null) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body().getStatus() == 1) {
                            // Toast.makeText(context, ""+response.body().getData().getFirstName(), Toast.LENGTH_SHORT).show();
                            UtilsMethod.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()), "1");
                                 /* Intent i = new Intent(context, GetLocation.class);
                                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 context.startActivity(i);*/
                            sweetAlertBox(context, response.body().getMessage(), "GetLocation");

                        } else {
                            sweetAlertBoxFailed(context, "Please Enter valid e-mail or password", "");
                        }
                    } catch (Exception e) {
                        Log.e("error: ", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                sweetAlertBoxFailed(context, t.getMessage(), "");
                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }
            }
        });

    }

    public void UpdateProfile(Context context, Loader loader, String id, String fname, String lname, String phone_no, String city, String state, String address_1, String address_2, String zipcode, String latitude, String longitude, String firebase_token, String image) {

        File file = new File(image);
        Log.e("profileimage", "" + file);
        MultipartBody.Part fileToUpload1;
        if (image.equalsIgnoreCase("1")) {
            fileToUpload1 = MultipartBody.Part.createFormData("image", "");

        } else {
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*image/*"), file);
            fileToUpload1 = MultipartBody.Part.createFormData("image", file.getName(), requestBody1);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), fname);
        RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"), lname);
        RequestBody contact = RequestBody.create(MediaType.parse("text/plain"), phone_no);
        RequestBody city_name = RequestBody.create(MediaType.parse("text/plain"), city);
        RequestBody state_name = RequestBody.create(MediaType.parse("text/plain"), state);
        RequestBody address1_name = RequestBody.create(MediaType.parse("text/plain"), address_1);
        RequestBody address2_name = RequestBody.create(MediaType.parse("text/plain"), address_2);
        RequestBody z_zipcode = RequestBody.create(MediaType.parse("text/plain"), zipcode);
        RequestBody la_latitude = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody lo_longitude = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody token_firebase = RequestBody.create(MediaType.parse("text/plain"), firebase_token);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        //Call<DataRespone> call=api.upDateProfile(header,id+"",fname+"",lname+"",phone_no+"",city+"",state+"",address_1+"",address_2+"",zipcode+"", latitude+"",longitude+"",firebase_token+"",image+"");
        Call<DataRespone> call = api.upDateProfile(header, user_id, first_name, last_name, contact, city_name, state_name, address1_name, address2_name, z_zipcode, la_latitude, lo_longitude, token_firebase, fileToUpload1);
        Log.e("UserProfileuploadres", "is : " + call.toString());
        call.enqueue(new Callback<DataRespone>() {
            @Override
            public void onResponse(Call<DataRespone> call, Response<DataRespone> response)
            {
                Toast.makeText(context, "" + response.body().getData1().getFirstName(), Toast.LENGTH_SHORT).show();
                Log.d("sdfjhhghsdf", new Gson().toJson(response.body()));

                if (response != null)
                {
                    if (loader != null)
                    {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body().getStatus() == 1) {
                            sweetAlertBox(context, response.body().getMessage(), "DashBoad");
                            UtilsMethod.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData1()), "1");

                        } else
                        {
                            sweetAlertBoxFailed(context, response.body().getMessage(), "");
                        }

                    } catch (Exception e) {
                        Log.d("Exception", "" + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataRespone> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                sweetAlertBoxFailed(context, "Please select image", "");
                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }
            }
        });

    }

    public void forgetpasswor(Context context, Loader loader, String email) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<secureLoginResponse> call = api.forgetpasswor(header, email);
        call.enqueue(new Callback<secureLoginResponse>() {
            @Override
            public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (response != null) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body().getStatus() == 1) {
                            sweetAlertBox(context, response.body().getMessage(), "OtpVerification");
                        } else {
                            sweetAlertBoxFailed(context, "This E-mail is Not a registered", "");
                        }
                    } catch (Exception e) {
                        Log.d("error", "" + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                sweetAlertBoxFailed(context, t.getMessage(), "");
                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }

            }
        });

    }

    public void changePassword(Context context, Loader loader, String userId, String currentpassword, String newpassword, String cnewpassword) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<secureLoginResponse> call = api.changePassword(header, userId, currentpassword, newpassword, cnewpassword);
        call.enqueue(new Callback<secureLoginResponse>() {
            @Override
            public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {

                if (response != null) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body().getStatus() == 1) {
                            sweetAlertBox(context, response.body().getMessage(), "DashBoad");
                        } else {
                            sweetAlertBoxFailed(context, response.body().getMessage(), "");
                        }
                    } catch (Exception e) {
                        Log.d("error", "" + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                sweetAlertBoxFailed(context, t.getMessage(), "");
                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }


            }
        });

    }

    public void otpVerify(Context context, Loader loader, String email, String otp, String newpassword, String cnewpassword) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        AllAPIs api = APIClient.getClient().create(AllAPIs.class);
        Call<secureLoginResponse> call = api.otpVerify(header, email, otp, newpassword, cnewpassword);
        call.enqueue(new Callback<secureLoginResponse>() {
            @Override
            public void onResponse(Call<secureLoginResponse> call, Response<secureLoginResponse> response) {
                if (response != null) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body().getStatus() == 1) {
                            sweetAlertBox(context, response.body().getMessage(), "SignInEmail");
                        } else {
                            sweetAlertBoxFailed(context, response.body().getMessage(), "");
                        }
                    } catch (Exception e) {
                        Log.d("error", "" + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
                sweetAlertBoxFailed(context, t.getMessage(), "");
                if (loader != null) {
                    if (loader.isShowing())
                        loader.dismiss();
                }
            }
        });

    }


    public void alertBox(String title, String Message, Context context) {
        try {
            final AlertDialog dialog1 = new AlertDialog.Builder(context).create();
            dialog1.setTitle(title);
            dialog1.setMessage(Message);
            dialog1.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog1.dismiss();
                    //System.exit(1);
                }
            });
            dialog1.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sweetAlertBox(Context context, String message, String openspage) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("")
                .setContentText("" + "" + message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (openspage.equalsIgnoreCase("DashBoad")) {
                            Intent i = new Intent(context, DashBoad.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(i);
                        } else if (openspage.equalsIgnoreCase("Login")) {
                            Intent i = new Intent(context, Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(i);

                        } else if (openspage.equalsIgnoreCase("OtpVerification")) {
                            Intent i = new Intent(context, OtpVerification.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(i);

                        } else if (openspage.equalsIgnoreCase("GetLocation")) {
                            Intent i = new Intent(context, GetLocation.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(i);

                        } else if (openspage.equalsIgnoreCase("SignInEmail")) {
                            Intent i = new Intent(context, SignInEmail.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(i);

                        } else if (openspage.equalsIgnoreCase("Showdeatails")) {
                          /*  Intent i = new Intent(context, DashBoad.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(i);*/
                            ((WriteReview) context.getApplicationContext()).finish();
                        }
                        else if (openspage.equalsIgnoreCase("successful")) {
                            Intent i = new Intent(context, SuccessFull.class);

                            context.startActivity(i);

                        }


                    }
                }).show();
    }

    public void sweetAlertBoxFailed(Context context, String message, String openspage) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("")
                .setContentText("" + "" + message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                }).show();
    }


    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;

        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public String dateForamterer(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(date);
        return currentDateandTime;
    }

}
