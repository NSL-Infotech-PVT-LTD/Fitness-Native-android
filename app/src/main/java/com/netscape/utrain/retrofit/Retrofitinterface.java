package com.netscape.utrain.retrofit;

import com.netscape.utrain.model.BookingConfirmModel;
import com.netscape.utrain.model.EventBookingModel;
import com.netscape.utrain.response.A_BookedEventResponse;
import com.netscape.utrain.response.A_EventListResponse;
import com.netscape.utrain.response.A_SessionResponse;
import com.netscape.utrain.response.A_SpaceListResponse;
import com.netscape.utrain.response.AthleteEventListResponse;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.response.AthleteSessionResponse;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.response.BookingListResponse;
import com.netscape.utrain.response.C_EventListResponse;
import com.netscape.utrain.response.C_SessionListResponse;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.response.ForgetPasswordResponse;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.response.O_BookedSpaceListResponse;
import com.netscape.utrain.response.O_EventBookedListResponse;
import com.netscape.utrain.response.O_EventListResponse;
import com.netscape.utrain.response.O_SessionBookedListResponse;
import com.netscape.utrain.response.O_SessionListResponse;
import com.netscape.utrain.response.O_SpaceListResponse;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.response.OrgSignUpResponse;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Retrofitinterface {

    @Multipart
    @POST(Constants.ATHLETE_SIGNUP)
    Call<AthleteSignUpResponse> athleteSignUp(@Part MultipartBody.Part file,
                                              @Query("name") String name,
                                              @Query("email") String email,
                                              @Query("password") String password,
                                              @Query("phone") String phone,
                                              @Query("address") String address,
                                              @Query("latitude") String latitude,
                                              @Query("longitude") String longitude,
                                              @Query("device_type") String device_type,
                                              @Query("device_token") String device_token,
                                              @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST(Constants.LOGIN_METHOD)
    Call<LoginResponse> userLogin(@Field("email") String email,
                                  @Field("password") String password,
                                  @Field("device_type") String device_type,
                                  @Field("device_token") String device_token,
                                  @Header("Content-Type") String contentType);

    @FormUrlEncoded
    @POST("reset-password")
    Call<ForgetPasswordResponse> getForgetpassword(@Header("Content-Type") String Content_Type,
                                                   @Field("email") String email);


    @POST(Constants.SERVICES)
    Call<ServiceListResponse> getServiceList(@Header("Content-Type") String Content_Type);


    @FormUrlEncoded
    @POST(Constants.COACH_SIGNUP)
    Call<CoachSignUpResponse> coachSignUp(@Header("Content-Type") String Content_Type,
                                          @Field("name") String name,
                                          @Field("email") String email,
                                          @Field("password") String password,
                                          @Field("phone") String phone,
                                          @Field("location") String location,
                                          @Field("latitude") String latitude,
                                          @Field("longitude") String longitude,
                                          @Field("business_hour_starts") String business_hour_starts,
                                          @Field("business_hour_ends") String business_hour_ends,
                                          @Field("bio") String bio,
                                          @Field("service_ids") String service_ids,
                                          @Field("expertise_years") String expertise_years,
                                          @Field("hourly_rate") String hourly_rate,
                                          @Field("device_type") String device_type,
                                          @Field("device_token") String device_token);

    @Multipart
    @POST(Constants.ORG_SIGNUP)
    Call<OrgSignUpResponse> orgSignup(@Part MultipartBody.Part profile_image,
                                      @Part MultipartBody.Part portfolio_image_1,
                                      @Part MultipartBody.Part portfolio_image_2,
                                      @Part MultipartBody.Part portfolio_image_3,
                                      @Part MultipartBody.Part portfolio_image_4,
                                      @Query("name") String name,
                                      @Query("email") String email,
                                      @Query("password") String password,
                                      @Query("phone") String phone,
                                      @Query("location") String location,
                                      @Query("latitude") String latitude,
                                      @Query("longitude") String longitude,
                                      @Query("bio") String bio,
                                      @Query("service_ids") JSONArray serviceIds,
                                      @Query("expertise_years") String expertiesYears,
                                      @Query("hourly_rate") String hourlyRate,
                                      @Query("business_hour_starts") String businessStartTime,
                                      @Query("business_hour_ends") String businessEndTime,
                                      @Query("device_type") String device_type,
                                      @Query("device_token") String device_token,
                                      @Header("Content-Type") String contentType);

    @Multipart
    @POST(Constants.COACH_SIGNUP)
    Call<CoachSignUpResponse> coachSignup(@Header("Content-Type") String contentType,
                                          @Query("name") String name,
                                          @Query("email") String email,
                                          @Query("password") String password,
                                          @Query("phone") String phone,
                                          @Query("location") String location,
                                          @Query("latitude") String latitude,
                                          @Query("longitude") String longitude,
                                          @Query("business_hour_starts") String businessStartTime,
                                          @Query("business_hour_ends") String businessEndTime,
                                          @Query("bio") String bio,
                                          @Query("service_ids") String serviceIds,
                                          @Query("expertise_years") String expertiesYears,
                                          @Query("hourly_rate") String hourlyRate,
                                          @Query("device_type") String device_type,
                                          @Query("device_token") String device_token,
                                          @Part MultipartBody.Part profile_image, @Part("name") RequestBody requestBody);

    @Multipart
    @POST(Constants.ATHLETE_SIGNUP)
    Call<AthleteSignUpResponse> registerAthlete(@PartMap Map<String, RequestBody> fields,
                                                @Part MultipartBody.Part image);

    @Multipart
    @POST(Constants.COACH_SIGNUP)
    Call<CoachSignUpResponse> registerCoach(@PartMap Map<String, RequestBody> fields,
                                            @Part MultipartBody.Part image);

    @Multipart
    @POST(Constants.ORG_SIGNUP)
    Call<OrgSignUpResponse> registerOrganization(@PartMap Map<String, RequestBody> fields,
                                                 @Part MultipartBody.Part profileImage,
                                                 @Part MultipartBody.Part imageOne,
                                                 @Part MultipartBody.Part imageTwo,
                                                 @Part MultipartBody.Part imageThree,
                                                 @Part MultipartBody.Part imageFour);

    @POST(Constants.TOP_COACH_LIST)
    Call<CoachListResponse> getCoachList(@Header("Authorization") String Authorization,
                                         @Query("search") String search,
                                         @Query("limit") String Limit,
                                         @Query("page") String page);

    @POST(Constants.TOP_ORG_LIST)
    Call<CoachListResponse> getTopOrgList(@Header("Authorization") String Authorization,
                                          @Query("search") String search,
                                          @Query("limit") String Limit,
                                          @Query("page") String page);

    @POST(Constants.ATHLETE_EVENT_LIST)
    Call<AthleteEventListResponse> getAthleteEventList(@Header("Authorization") String Authorization,
                                                       @Header("Content-Type") String contentType,
                                                       @Query("order_by") String order_by,
                                                       @Query("search") String search,
                                                       @Query("limit") String limit,
                                                       @Query("page") String page,
                                                       @Query("radius") String radius);

    @POST(Constants.ATHLETE_SESSION_LIST)
    Call<AthleteSessionResponse> getAthleteSessionList(@Header("Authorization") String Authorization,
                                                       @Header("Content-Type") String contentType,
                                                       @Query("search") String search,
                                                       @Query("limit") String limit,
                                                       @Query("order_by") String order_by
    );

    @POST(Constants.ATHLETE_PLACE_LIST)
    Call<AthletePlaceResponse> getAthletePlacesList(@Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Query("search") String search,
                                                    @Query("limit") String limit,
                                                    @Query("order_by") String order_by
    );

//    @Multipart
//    @POST(Constants.EVENTS_STORE)
//    Call<OrgCreateEventResponse> createEvent(@Header("Authorization") String auth,
//                                             @PartMap Map<String, RequestBody> fields,
//                                             @Part MultipartBody.Part imageOne,
//                                             @Part MultipartBody.Part imageTwo,
//                                             @Part MultipartBody.Part imageThree,
//                                             @Part MultipartBody.Part imageFour);
@Multipart
@POST(Constants.EVENTS_STORE)
Call<OrgCreateEventResponse> createEvent(@Header("Authorization") String auth,
                                         @PartMap Map<String, RequestBody> fields,
                                         @Part List<MultipartBody.Part> files);


    @Multipart
    @POST(Constants.CREATE_SPACE)
    Call<OrgCreateEventResponse> createSpace(@Header("Authorization") String auth,
                                             @PartMap Map<String, RequestBody> fields,
                                                 @Part List<MultipartBody.Part> files);

//    @Multipart
//    @POST(Constants.CREATE_SPACE)
//    Call<OrgCreateEventResponse> createSpace(@Header("Authorization") String auth,
//                                             @PartMap Map<String, RequestBody> fields,
//                                             @Part MultipartBody.Part imageOne,
//                                             @Part MultipartBody.Part imageTwo,
//                                             @Part MultipartBody.Part imageThree,
//                                             @Part MultipartBody.Part imageFour);

    @Multipart
    @POST(Constants.CREATE_SESSION)
    Call<OrgCreateEventResponse> createSession(@Header("Authorization") String auth,
                                               @PartMap Map<String, RequestBody> fields,
                                               @Part List<MultipartBody.Part> files);

    @POST(Constants.EVENT_DETAIL)
    Call<EventBookingModel> eventDetail(@Header("Authorization") String Authorization,
                                        @Header("Content-Type") String contentType,
                                        @Query("id") String id);

    @POST(Constants.BOOKING_API)
    Call<BookingConfirmModel> bookingConfrim(@Header("Authorization") String Authorization,
                                          @Header("Content-Type") String contentType,
                                          @Query("type") String type,
                                          @Query("target_id") String id,
                                          @Query("tickets") String tickets,
                                          @Query("price") String price,
                                          @Query("status") String status,
                                          @Query("token") String token);

    @POST(Constants.BOOKING_LIST_EVENT)
    Call<BookingListResponse> getBookingList(@Header("Authorization") String Authorization,
                                             @Header("Content-Type") String contentType,
                                             @Query("type") String type);
    @POST(Constants.ORG_EVENT_LIST)
    Call<O_EventListResponse> getOrgEentList(@Header("Authorization") String Authorization,
                                             @Header("Content-Type") String contentType,
                                             @Query("order_by") String order_by);
    @POST(Constants.ORG_SPACE_LIST)
    Call<O_SpaceListResponse> getOrgSpaceList(@Header("Authorization") String Authorization,
                                              @Header("Content-Type") String contentType,
                                              @Query("order_by") String order_by);
    @POST(Constants.ORG_SESSION_LIST)
    Call<O_SessionListResponse> getOrgSessionList(@Header("Authorization") String Authorization,
                                                  @Header("Content-Type") String contentType,
                                                  @Query("order_by") String order_by);

    @POST(Constants.A_EVENT_LIST)
    Call<A_EventListResponse> getAthEventList(@Header("Authorization") String Authorization,
                                              @Header("Content-Type") String contentType,
                                              @Query("order_by") String order_by,
                                              @Query("search") String search,
                                              @Query("limit") String limit,
                                              @Query("page") String page,
                                              @Query("radius") String radius);
    @POST(Constants.A_SPACE_LIST)
    Call<A_SpaceListResponse> getAthSpaceList(@Header("Authorization") String Authorization,
                                              @Header("Content-Type") String contentType,
                                              @Query("order_by") String order_by,
                                              @Query("limit") String limit);
    @POST(Constants.A_SESSION_LIST)
    Call<A_SessionResponse> getAthSessionList(@Header("Authorization") String Authorization,
                                              @Header("Content-Type") String contentType,
                                              @Query("order_by") String order_by,
                                              @Query("search") String search,
                                              @Query("limit") String limit);

    @POST(Constants.CO_EVENT_LIST)
    Call<C_EventListResponse> getCoachEventList(@Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Query("order_by") String order_by);
    @POST(Constants.CO_SESSION_LIST)
    Call<C_SessionListResponse> getCoachSessionList(@Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Query("order_by") String order_by);

    @POST(Constants.A_EVENT_BOOKING_LIST)
    Call<A_BookedEventResponse> getAthleteBookingList(@Header("Authorization") String Authorization,
                                                      @Header("Content-Type") String contentType,
                                                      @Query("target_id") String target_id,
                                                      @Query("type") String type);

    @POST(Constants.O_EVENT_BOOKING_LIST)
    Call<O_EventBookedListResponse> getOrganiserBookedList(@Header("Authorization") String Authorization,
                                                           @Header("Content-Type") String contentType,
                                                           @Query("target_id") String target_id,
                                                           @Query("type") String type);
    @POST(Constants.O_EVENT_BOOKING_LIST)
    Call<O_SessionBookedListResponse> getOrganiserBookedSessionList(@Header("Authorization") String Authorization,
                                                             @Header("Content-Type") String contentType,
                                                             @Query("target_id") String target_id,
                                                             @Query("type") String type);
    @POST(Constants.O_EVENT_BOOKING_LIST)
    Call<O_BookedSpaceListResponse> getOrganiserBookedSpaceList(@Header("Authorization") String Authorization,
                                                                @Header("Content-Type") String contentType,
                                                                @Query("target_id") String target_id,
                                                                @Query("type") String type);



}
