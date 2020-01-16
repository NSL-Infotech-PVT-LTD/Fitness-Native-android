package com.netscape.utrain.retrofit;

import com.netscape.utrain.model.AllBookingListModel;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.AthleteResponseEventData;
import com.netscape.utrain.model.AthleteSessionBookList;
import com.netscape.utrain.model.AthleteSpaceBookList;
import com.netscape.utrain.model.BookingConfirmModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.EventBookingModel;
import com.netscape.utrain.model.NotifaicationStateResponse;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.OrgCoachStoreModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.A_BookedEventResponse;
import com.netscape.utrain.response.A_EventListResponse;
import com.netscape.utrain.response.A_SessionResponse;
import com.netscape.utrain.response.A_SpaceListResponse;
import com.netscape.utrain.response.AboutUsResponse;
import com.netscape.utrain.response.AthleteEventListResponse;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.response.AthleteSessionResponse;
import com.netscape.utrain.response.AthleteSignUpResponse;
import com.netscape.utrain.response.BookingListResponse;
import com.netscape.utrain.response.C_EventListResponse;
import com.netscape.utrain.response.C_SessionListResponse;
import com.netscape.utrain.response.ChangePasswordResponse;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.response.CoachOrgCalResponse;
import com.netscape.utrain.response.CoachSignUpResponse;
import com.netscape.utrain.response.EventDetailResponse;
import com.netscape.utrain.response.ForgetPasswordResponse;
import com.netscape.utrain.response.HelpAndSupportResponse;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.response.LogoutResponse;
import com.netscape.utrain.response.NotificationCountResponse;
import com.netscape.utrain.response.NotificationReadResponse;
import com.netscape.utrain.response.NotificationResponse;
import com.netscape.utrain.response.O_AllBookingResponse;
import com.netscape.utrain.response.O_BookedSpaceListResponse;
import com.netscape.utrain.response.O_EventBookedListResponse;
import com.netscape.utrain.response.O_EventListResponse;
import com.netscape.utrain.response.O_SessionBookedListResponse;
import com.netscape.utrain.response.O_SessionListResponse;
import com.netscape.utrain.response.O_SpaceListResponse;
import com.netscape.utrain.response.OrgCreateEventResponse;
import com.netscape.utrain.response.OrgSignUpResponse;
import com.netscape.utrain.response.RatingResponse;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.response.SessionBookingDetails;
import com.netscape.utrain.response.SessionDetailResponse;
import com.netscape.utrain.response.SlotListResponse;
import com.netscape.utrain.response.SpaceBookingDetailResponse;
import com.netscape.utrain.response.SpaceBookingResponse;
import com.netscape.utrain.response.SpaceDetailResponse;
import com.netscape.utrain.response.TermsAndConditionsResponse;
import com.netscape.utrain.response.ViewCoachListResponse;
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
                                              @Header("Content-Type") String contentType,
                                              @Query("sport_id") String sport_id,
                                              @Query("achievements") String achievements,
                                              @Query("experience_detail") String experience_detail);

    @FormUrlEncoded
    @POST(Constants.LOGIN_METHOD)
    Call<LoginResponse> userLogin(@Field("email") String email,
                                  @Field("password") String password,
                                  @Field("device_type") String device_type,
                                  @Field("device_token") String device_token,
                                  @Header("Content-Type") String contentType);


    @POST("reset/password")
    Call<ForgetPasswordResponse> getForgetpassword(@Header("Content-Type") String Content_Type,
                                                   @Query("email") String email);


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
                                          @Query("experience_detail") String experience_detail,
                                          @Query("training_service_detail") String training_service_detail,
                                          @Part MultipartBody.Part profile_image, @Part("name") RequestBody requestBody);

    @Multipart
    @POST(Constants.ATHLETE_SIGNUP)
    Call<AthleteSignUpResponse> registerAthlete(@PartMap Map<String, RequestBody> fields,
                                                @Part MultipartBody.Part image);

    @Multipart
    @POST(Constants.COACH_SIGNUP)
    Call<CoachSignUpResponse> registerCoach(@PartMap Map<String, RequestBody> fields,
                                            @Part List<MultipartBody.Part> files);
    @Multipart
    @POST(Constants.COACH_UPDATE)
    Call<CoachSignUpResponse> updateCoachBasicInfo(@Header("Authorization") String auth,
                                               @PartMap Map<String, RequestBody> fields,
                                               @Part MultipartBody.Part image);

//    @Multipart
//    @POST(Constants.ORG_SIGNUP)
//    Call<OrgSignUpResponse> registerOrganization(@PartMap Map<String, RequestBody> fields,
//                                                 @Part MultipartBody.Part profileImage,
//                                                 @Part MultipartBody.Part imageOne,
//                                                 @Part MultipartBody.Part imageTwo,
//                                                 @Part MultipartBody.Part imageThree,
//                                                 @Part MultipartBody.Part imageFour);

    @POST(Constants.TOP_COACH_LIST)
    Call<CoachListResponse> getCoachList(@Header("Authorization") String Authorization,
                                         @Query("search") String search,
                                         @Query("limit") String limit,
                                         @Query("page") String page,
                                         @Query("order_by") String order_by
    );


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
                                                       @Query("radius") String radius,
                                                       @Query("coach_id") String coach_id);
    @FormUrlEncoded
    @POST(Constants.ATHLETE_SESSION_LIST)
    Call<AthleteSessionResponse> getAthleteSessionList(@Header("Authorization") String Authorization,
                                                       @Header("Content-Type") String contentType,
                                                       @Field("search") String search,
                                                       @Field("limit") String limit,
                                                       @Field("order_by") String order_by,
                                                       @Field("page") String page,
                                                       @Field("coach_id") String coach_id);

    @POST(Constants.ATHLETE_PLACE_LIST)
    Call<AthletePlaceResponse> getAthletePlacesList(@Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Query("search") String search,
                                                    @Query("limit") String limit,
                                                    @Query("order_by") String order_by,
                                                    @Query("page") String page,
                                                    @Query("organiser_id") String coach_id
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
    @POST(Constants.UPDATE_EVENTS)
    Call<OrgCreateEventResponse> updateEvents(@Header("Authorization") String auth,
                                              @PartMap Map<String, RequestBody> fields,
                                              @Part List<MultipartBody.Part> files);

    @Multipart
    @POST(Constants.ORG_SIGNUP)
    Call<OrgSignUpResponse> registerOrganization(@PartMap Map<String, RequestBody> fields,
                                                 @Part List<MultipartBody.Part> files);

    @Multipart
    @POST(Constants.ORG_UPDATE)
    Call<OrgSignUpResponse> updateOrgBasicInfo(@Header("Authorization") String auth,
                                               @PartMap Map<String, RequestBody> fields,
                                               @Part MultipartBody.Part image);
    @Multipart
    @POST(Constants.ORG_UPDATE)
    Call<OrgSignUpResponse> updateSports(@Header("Authorization") String auth,
                                               @PartMap Map<String, RequestBody> sportsField);

    @Multipart
    @POST(Constants.ORG_UPDATE)
    Call<OrgSignUpResponse> updatePortFolioImages(@Header("Authorization") String auth,
                                               @PartMap Map<String, RequestBody> fields,
                                               @Part List<MultipartBody.Part> files);


    @Multipart
    @POST(Constants.CREATE_SPACE)
    Call<OrgCreateEventResponse> createSpace(@Header("Authorization") String auth,
                                             @PartMap Map<String, RequestBody> fields,
                                             @Part List<MultipartBody.Part> files);

    @Multipart
    @POST(Constants.UPDATE_SPACE)
    Call<OrgCreateEventResponse> updateSpace(@Header("Authorization") String auth,
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

    @Multipart
    @POST(Constants.UPDATE_SESSION)
    Call<OrgCreateEventResponse> updateSession(@Header("Authorization") String auth,
                                               @PartMap Map<String, RequestBody> fields,
                                               @Part List<MultipartBody.Part> files);

    @POST(Constants.EVENT_DETAIL)
    Call<EventBookingModel> eventDetail(@Header("Authorization") String Authorization,
                                        @Header("Content-Type") String contentType,
                                        @Query("id") String id);

    @POST(Constants.SPACE_DETAIL)
    Call<SpaceDetailResponse> spaceDetail(@Header("Authorization") String Authorization,
                                          @Header("Content-Type") String contentType,
                                          @Query("id") String id);

    @POST(Constants.SESSION_DETAIL)
    Call<SessionDetailResponse> getSessionDetails(@Header("Authorization") String Authorization,
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

    @POST(Constants.SPACE_BOOKING_API)
    Call<SpaceBookingResponse> spaceBooking(@Header("Authorization") String Authorization,
                                            @Header("Content-Type") String contentType,
                                            @Query("target_id") String id,
                                            @Query("price") String price,
                                            @Query("token") String token,
                                            @Query("status") String status,
                                            @Query("type") String type,
                                            @Query("booking") String booking);

    @POST(Constants.BOOKING_LIST_EVENT)
    Call<BookingListResponse> getBookingList(@Header("Authorization") String Authorization,
                                             @Header("Content-Type") String contentType,
                                             @Query("type") String type);

    @POST(Constants.BOOK_SPACE)
    Call<BookingListResponse> bookSpace(@Header("Authorization") String Authorization,
                                        @Header("Content-Type") String contentType,
                                        @Query("target_id") String target_id,
                                        @Query("price") String price,
                                        @Query("token") String token,
                                        @Query("status") String status,
                                        @Query("space_date_start") String space_date_start,
                                        @Query("space_date_end") String space_date_end,
                                        @Query("type") String type);

    @POST(Constants.ORG_EVENT_LIST)
    Call<O_EventListResponse> getOrgEentList(@Header("Authorization") String Authorization,
                                             @Header("Content-Type") String contentType,
                                             @Query("page") String page,
                                             @Query("order_by") String order_by);

    @POST(Constants.ORG_SPACE_LIST)
    Call<O_SpaceListResponse> getOrgSpaceList(@Header("Authorization") String Authorization,
                                              @Header("Content-Type") String contentType,
                                              @Query("page") String page,
                                              @Query("order_by") String order_by);
    @FormUrlEncoded
    @POST(Constants.ALL_BOOKING_ATHLETE)
    Call<O_AllBookingResponse> getAllBooking(@Header("Authorization") String Authorization,
                                             @Header("Content-Type") String contentType,
                                             @Field("limit") String limit,
                                             @Field("filter_by") String filter_by);
    @FormUrlEncoded
    @POST(Constants.CALANDER_BOOKINGS_BOTHE)
    Call<O_AllBookingResponse> getBotheCalBookings(@Header("Authorization") String Authorization,
                                             @Header("Content-Type") String contentType,
                                             @Field("limit") String limit,
                                             @Field("filter_by") String filter_by);

    @POST(Constants.SPACE_BOOKING_FOR_ATH_ORG)
    Call<AthleteSpaceBookList> getCoachSpaceBooking(@Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Query("page") String page,
                                                    @Query("limit") String limit);
    @POST(Constants.SPACE_BOOKING_FOR_ATH_ORG)
    Call<O_AllBookingResponse> getTransactionList(@Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Query("page") String page,
                                                    @Query("limit") String limit);

    @FormUrlEncoded
    @POST(Constants.ALL_BOOKING_ORG)
    Call<O_AllBookingResponse> getAllBookingOrg(@Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Field("limit") String limit,
                                                @Field("filter_by") String filter_by);
    @FormUrlEncoded
    @POST(Constants.ALL_TRANSACTIONS_ORG)
    Call<O_AllBookingResponse> allTransactionListOrg(@Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Field("page") String page,
                                                @Field("limit") String limit);
    @FormUrlEncoded
    @POST(Constants.ALL_TRANSACTIONS_COACH)
    Call<O_AllBookingResponse> allTransactionListCoach(@Header("Authorization") String Authorization,
                                                     @Header("Content-Type") String contentType,
                                                     @Field("page") String page,
                                                     @Field("limit") String limit);
    @FormUrlEncoded
    @POST(Constants.ALL_BOOKING_COACH)
    Call<O_AllBookingResponse> getAllBookingCoach(@Header("Authorization") String Authorization,
                                                  @Header("Content-Type") String contentType,
                                                  @Field("limit") String page,
                                                  @Field("filter_by") String filter_by);
    @FormUrlEncoded
    @POST(Constants.ALL_BOOKING_COACH_ORG)
    Call<CoachOrgCalResponse> getCoachOrgCalBooking(@Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Field("filter_by") String filter_by);

    @POST(Constants.ORG_SESSION_LIST)
    Call<O_SessionListResponse> getOrgSessionList(@Header("Authorization") String Authorization,
                                                  @Header("Content-Type") String contentType,
                                                  @Query("page") String page,
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
                                                @Query("page") String page,
                                                @Query("order_by") String order_by);

    @POST(Constants.CO_EVENT_LIST)
    Call<O_EventListResponse> getCoachEvents(@Header("Authorization") String Authorization,
                                                @Header("Content-Type") String contentType,
                                                @Query("page") String page,
                                                @Query("order_by") String order_by);

    @POST(Constants.CO_SESSION_LIST)
    Call<C_SessionListResponse> getCoachSessionList(@Header("Authorization") String Authorization,
                                                    @Header("Content-Type") String contentType,
                                                    @Query("page") String page,
                                                    @Query("order_by") String order_by);

    @POST(Constants.CO_SESSION_LIST)
    Call<O_SessionListResponse> getCoachSessions(@Header("Authorization") String Authorization,
                                                 @Header("Content-Type") String contentType,
                                                 @Query("page") String page,
                                                 @Query("order_by") String order_by);

    @POST(Constants.A_EVENT_BOOKING_LIST)
    Call<AthleteBookListModel> getAthleteBookingList(@Header("Authorization") String Authorization,
                                                     @Header("Content-Type") String contentType,
                                                     @Query("target_id") String target_id,
                                                     @Query("order_by") String order_by,
                                                     @Query("page") String page,
                                                     @Query("type") String type);

    @POST(Constants.ATHLETE_EVENT_LIST)
    Call<AthleteBookListModel> getAthleteEventList(@Header("Authorization") String Authorization,
                                                   @Header("Content-Type") String contentType,
                                                   @Query("target_id") String target_id,
                                                   @Query("order_by") String order_by,
                                                   @Query("type") String type);

    @POST(Constants.A_EVENT_BOOKING_LIST)
    Call<AthleteSessionBookList> getAthleteSessionBookList(@Header("Authorization") String Authorization,
                                                           @Header("Content-Type") String contentType,
                                                           @Query("target_id") String target_id,
                                                           @Query("order_by") String order_by,
                                                           @Query("page") String page,
                                                           @Query("type") String type);

    @POST(Constants.A_EVENT_BOOKING_LIST)
    Call<AthleteSpaceBookList> getAthleteSpaceBookList(@Header("Authorization") String Authorization,
                                                       @Header("Content-Type") String contentType,
                                                       @Query("target_id") String target_id,
                                                       @Query("order_by") String order_by,
                                                       @Query("page") String page,
                                                       @Query("type") String type);

    @POST(Constants.O_EVENT_BOOKING_LIST)
    Call<O_EventBookedListResponse> getOrganiserBookedList(@Header("Authorization") String Authorization,
                                                           @Header("Content-Type") String contentType,
                                                           @Query("target_id") String target_id,
                                                           @Query("order_by") String order_by,
                                                           @Query("search") String search,
                                                           @Query("type") String type);

    @POST(Constants.COACH_EVENT_BOOKING_LIST)
    Call<O_EventBookedListResponse> getCoachEventList(@Header("Authorization") String Authorization,
                                                      @Header("Content-Type") String contentType,
                                                      @Query("target_id") String target_id,
                                                      @Query("order_by") String order_by,
                                                      @Query("search") String search,
                                                      @Query("type") String type);

    @POST(Constants.O_EVENT_BOOKING_LIST)
    Call<O_SessionBookedListResponse> getOrganiserBookedSessionList(@Header("Authorization") String Authorization,
                                                                    @Header("Content-Type") String contentType,
                                                                    @Query("target_id") String target_id,
                                                                    @Query("order_by") String order_by,
                                                                    @Query("search") String search,
                                                                    @Query("type") String type);

    @POST(Constants.COACH_EVENT_BOOKING_LIST)
    Call<O_SessionBookedListResponse> getCoachSessionList(@Header("Authorization") String Authorization,
                                                          @Header("Content-Type") String contentType,
                                                          @Query("target_id") String target_id,
                                                          @Query("order_by") String order_by,
                                                          @Query("search") String search,
                                                          @Query("type") String type);

    @POST(Constants.O_EVENT_BOOKING_LIST)
    Call<O_BookedSpaceListResponse> getOrganiserBookedSpaceList(@Header("Authorization") String Authorization,
                                                                @Header("Content-Type") String contentType,
                                                                @Query("target_id") String target_id,
                                                                @Query("order_by") String order_by,
                                                                @Query("search") String search,
                                                                @Query("type") String type);


    @POST(Constants.SPORTS_LIST)
    Call<SportListModel> getSportList(@Header("Content-Type") String Content_Type,
                                      @Query("search") String search,
                                      @Query("limit") String limit);


    @Multipart
    @POST(Constants.ATHLETE_UPDATE)
    Call<AthleteSignUpResponse> updateProfile(@Header("Authorization") String Authorization,
                                              @Part MultipartBody.Part file,
                                              @PartMap Map<String, RequestBody> fields);


    @POST(Constants.ORG_COACH_ATH_List)
    Call<ViewCoachListResponse> getViewCoachList(@Header("Authorization") String Authorization,
                                                 @Query("search") String search,
                                                 @Query("order_by") String order_by,
                                                 @Query("limit") String limit,
                                                 @Query("organiser_id") String organiser_id
    );


    @Multipart
    @POST(Constants.organiser_coach_store)
    Call<ViewCoachListResponse> getOrgCoachRegister(@Part MultipartBody.Part file,
                                                    @Header("Authorization") String auth,
                                                    @PartMap Map<String, RequestBody> fields
    );


    @POST(Constants.BOOKING_NOTIFICATIONS)
    Call<NotificationResponse> notifications(@Header("Content-Type") String Content_type,
                                             @Query("page") String page,
                                             @Header("Authorization") String Authorization);


    @POST(Constants.BOOKING_RATING)
    Call<RatingResponse> setbookingRating(@Header("Content-Type") String Content_type,
                                          @Header("Authorization") String Authorization,
                                          @Query("booking_id") String booking_id,
                                          @Query("rating") String rating
    );


    @POST(Constants.CHANGE_PASSWORD)
    Call<ChangePasswordResponse> changePassword(@Header("Content-Type") String Content_Type,
                                                @Header("Authorization") String Authorization,
                                                @Query("old_password") String old_password,
                                                @Query("password") String password,
                                                @Query("password_confirmation") String password_confirmation
    );


    @GET(Constants.ABOUT_US)
    Call<AboutUsResponse> aboutUs(@Header("Content-Type") String Content_type,
                                            @Header("Authorization") String Authorization);
    @GET(Constants.TERMS_CONDITIONS)
    Call<TermsAndConditionsResponse> termsConditions(@Header("Content-Type") String Content_type,
                                                    @Header("Authorization") String Authorization);


    @POST(Constants.TERMS_CONDITIONS)
    Call<TermsAndConditionsResponse> termsAndConditions(
            @Query("type") String type);

    @GET(Constants.LOGOUT)
    Call<LogoutResponse> LogoutApi(@Header("Content-Type") String Content_type,
                                   @Header("Authorization") String Authorization);
    @GET(Constants.NOTIFICATION)
    Call<NotifaicationStateResponse> ChangeNotificationSetting(@Header("Authorization") String Authorization);

    @GET(Constants.GET_NEW_NOTIFICATIONS)
    Call<NotificationCountResponse> getNewNotificationCount(@Header("Content-Type") String Content_type,
                                                            @Header("Authorization") String Authorization);

    @GET(Constants.MARK_NOTIFICATIONS_READ)
    Call<NotificationReadResponse> setNewNotificationRead(@Header("Content-Type") String Content_type,
                                                          @Header("Authorization") String Authorization);
    @FormUrlEncoded
    @POST(Constants.AVAILABLE_SLOTS)
    Call<SlotListResponse> getTimeSlots(@Header("Content-Type") String Content_type,
                                        @Header("Authorization") String Authorization,
                                        @Field("target_id")String target_id,
                                        @Field("date")String date);

    @FormUrlEncoded
    @POST(Constants.BOOKING_DETAILS)
    Call<SessionBookingDetails> getBookingSession(@Header("Authorization") String Authorization,
                                                  @Header("Content-Type") String Content_type,
                                                  @Field("id")String target_id);
    @FormUrlEncoded
    @POST(Constants.BOOKING_DETAILS)
    Call<SpaceBookingDetailResponse> getBookingSpace(@Header("Authorization") String Authorization,
                                                     @Header("Content-Type") String Content_type,
                                                     @Field("id")String target_id);
    @FormUrlEncoded
    @POST(Constants.BOOKING_DETAILS)
    Call<EventDetailResponse> getBookingEvent(@Header("Authorization") String Authorization,
                                              @Header("Content-Type") String Content_type,
                                              @Field("id")String target_id);

//    @FormUrlEncoded
//    @POST(Constants.LOGIN_METHOD)
//    Call<HelpAndSupportResponse> helpAndSupport(@Header("Authorization") String Authorization,
//                                                @Header("Content-Type") String Content_type,
//                                                @Field("message")String message);

    @Multipart
    @POST(Constants.HELP_SUPPORT)
    Call<HelpAndSupportResponse> helpAndSupport(@Header("Authorization") String auth,
                                             @Query("message")String message,
                                             @Part MultipartBody.Part file);



}
