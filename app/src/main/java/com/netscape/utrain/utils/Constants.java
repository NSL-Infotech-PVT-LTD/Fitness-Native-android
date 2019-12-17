package com.netscape.utrain.utils;

public class Constants {
    public static final String BASE_URL = "https://dev.netscapelabs.com/utrain/public/api/";
    public static final String IMAGE_BASE_URL = "https://dev.netscapelabs.com/utrain/public/uploads/athlete/profile_image/";
    public static final String IMAGE_BASE_SESSION = "https://dev.netscapelabs.com/utrain/public/uploads/session/";
    public static final String IMAGE_BASE_PLACE = "https://dev.netscapelabs.com/utrain/public/uploads/spaces/";
    public static final String IMAGE_BASE_EVENT = "https://dev.netscapelabs.com/utrain/public/uploads/events/";
    public static final String COACH_IMAGE_BASE_URL = "https://dev.netscapelabs.com/utrain/public/uploads/coach/profile_image/";
    public static final String ORG_IMAGE_BASE_URL = "https://dev.netscapelabs.com/utrain/public/uploads/organiser/profile_image/";
    public static final String ORG_PORTFOLIO_IMAGE_BASE_URL = "https://dev.netscapelabs.com/utrain/public/uploads/organiser/portfolio_image/";
    public static final String ORG_COACH_IMAGE_BASE_URL = "https://dev.netscapelabs.com/utrain/public/uploads/organiserCoach/profile_image/";

    public static final String ATHLETE_SIGNUP = "athlete/register";
    public static final String ATHLETE_UPDATE = "athlete/update";
    public static final String LOGIN_METHOD = "login";
    public static final String ORG_SIGNUP = "organiser/register";
    public static final String ORG_UPDATE = "organiser/update";
    public static final String SERVICES = "services";
    public static final String COACH_SIGNUP = "coach/register";
    public static final String COACH_UPDATE = "coach/update";
    public static final String TOP_COACH_LIST = "coach/list";
    public static final String TOP_ORG_LIST = "organisers/list";
    public static final String ATHLETE_EVENT_LIST = "events/athlete/list";
    public static final String ATHLETE_SESSION_LIST = "session/athlete/list";
    public static final String ATHLETE_PLACE_LIST = "spaces/athlete/list";
    public static final String EVENTS_STORE = "events/store";
    public static final String UPDATE_EVENTS = "events/update";
    public static final String CREATE_SPACE = "spaces/create";
    public static final String UPDATE_SPACE = "spaces/update";
    public static final String CREATE_SESSION = "session/store";
    public static final String UPDATE_SESSION = "session/update";
    public static final String EVENT_DETAIL = "event/details";
    public static final String SESSION_DETAIL = "session/details";
    public static final String SPACE_DETAIL = "space/details";
    public static final String BOOKING_API = "booking/store";
    public static final String SPACE_BOOKING_API = "booking/space/store";
    public static final String BOOKING_LIST_EVENT = "bookings";
    public static final String BOOK_SPACE = "booking/space/store";
    public static final String ORG_EVENT_LIST = "events/organiser/list";
    public static final String ORG_COACH_ATH_List = "organiser/coach/athlete/list";
    public static final String organiser_coach_store = "organiser/coach/store";
//    public static final String BOOKING_NOTIFICATIONS = "booking/notifications";
    public static final String BOOKING_NOTIFICATIONS = "notifications";
    public static final String BOOKING_RATING = "booking/rating";
    public static final String CHANGE_PASSWORD = "password/change";
    public static final String TERMS_CONDITIONS = "terms";


    public static final String ORG_SESSION_LIST = "session/organiser/list";
    public static final String CO_EVENT_LIST = "events/coach/list";
    public static final String CO_SESSION_LIST = "session/coach/list";
    public static final String A_EVENT_LIST = "events/athlete/list";
    public static final String A_SPACE_LIST = "spaces/athlete/list";
    public static final String A_SESSION_LIST = "session/athlete/list";
    public static final String A_EVENT_BOOKING_LIST = "booking/athlete/list";
    //    public static final String A_EVENT_LIST= "event/athlete/list";
    public static final String O_EVENT_BOOKING_LIST = "booking/organiser/list";
    public static final String COACH_EVENT_BOOKING_LIST = "booking/coach/list";

    public static final String ALL_BOOKING_ATHLETE = "booking/list/all";
    public static final String SPACE_BOOKING_FOR_ATH_ORG = "booking/space/list";
    public static final String ALL_BOOKING_ORG = "booking/organiser/list/all";
    public static final String ALL_TRANSACTIONS_ORG = "all/organiser/bookings";
    public static final String ALL_BOOKING_COACH = "booking/coach/list/all";
    public static final String ALL_TRANSACTIONS_COACH = "all/coach/bookings";
    public static final String SPORTS_LIST = "sports";
    public static final String ABOUT_US = "about/us";
    public static final String LOGOUT= "logout";
    public static final String NOTIFICATION= "notify/toggle";
    public static final String GET_NEW_NOTIFICATIONS= "dashboard";
    public static final String MARK_NOTIFICATIONS_READ= "notification/read";



    public static final String ORG_SPACE_LIST = "spaces/organiser/list";
    public static final String COACH_EVENT_LIST = "events/coach/list";
    public static final String COACH_SPACE_LIST = "spaces/coach/list";
    public static final String COACH_SESSION_LIST = "session/coach/list";
    public static final String AUTH_TOKEN = "auth token";
    public static final String SPACE_DATA = "spaceData";
    public static final String THUMBNAILS = "thumbnail_";
    public static final String Coach = "coach";
    public static final String OrgSignUpIntent = "orgIntent";
    public static final String AthSignUpIntent = "athIntent";
    public static final String JsonArrayIntent = "arrayIntent";
    public static final String TypeCoach = "coachActive";
    public static final String LoginToCoach = "coach";
    public static final String ActiveUserType = "activeType";
    public static final String LoginFor = "loginFor";
    public static final String Athlete = "athlete";
    public static final String TypeAthlete = "athleteActive";
    public static final String Organization = "organization";
    public static final String Organizer = "organizer";
    public static final String TypeOrganization = "organizationActive";
    public static final String LoginToOrg = "organizer";
    public static final String SELECTED_SERVICE = "selectedService";
    public static final String SERVICE_LIST = "serviceList";
    public static final String TOP_COACHES = "topCoaches";
    public static final String TOP_ORG = "topOrg";
    public static final String TOP_TYPE_INTENT = "topTypIntent";
    public static final String TOP_DATA_INTENT = "topDataIntent";
    public static final String TOP_FROM_INTENT = "topFromIntent";
    public static final String SELECTED_ID = "selectedId";
    public static final String SELECTED_TYPE = "selectedType";
    public static final String EVENT = "event";
    public static final String SESSION = "session";
    public static final String SPACE = "space";
    public static final int WRITE_PERMISSION = 3;
    public static final int CAMERA_PERMISSION = 5;
    public static final int REQUEST_CAMERA_CAPTURE = 4;
    public static final int REQUEST_CODE_GALLERY = 2;
    public static final int MY_PERMISSION_GALLERY = 1;
    public static final String APP_FOLDER_NAME = "UTrain";//3 seconds
    public static final int ACCESS_FINE_LOCATION_REQUEST = 6;
    public static final String SIGN_UP_FLOW = "sign_up_flow";
    public static final int REQUEST_CODE_GOOGLE_PLACE_SEARCH = 7;
    public static final String LONGITUDE = "longitude";
    public static final String ADDRESS = "address";
    public static final String SELECTED_SLOT = "selectedSlot";
    public static final String SLOT_DATE = "slotDate";
    public static final String SLOT_TIME = "slotTime";
    public static final String LOCATION_LAT = "locationLat";
    public static final String LOCATION_LONG = "locationLong";
    public static final String STATUS = "";
    public static final String DEVICE_TYPE = "ANDROID";
    public static final String DEVICE_TOKEN = "KFGLKFSDFJKJSDF";
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String ORGANIZER_COACH_STORE = "organiser/coach/store";
    public static final String TypeOrgCoach = "OrgCoachActive";
    public static final String AVAILABLE_SLOTS ="availability" ;


    public static String ROLE_PLAY = "";
    public static int CHECKBOX_IS_CHECKED = 0;
    public static String SocialProfile = "";

}
