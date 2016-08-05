package project.mycloud.com.fierychat.util;

/**
 * Created by admin on 2016-08-05.
 */
public class EndPoints {

    //
    public static final String URL_STORAGE_REFERENCE = "gs://fierychat.appspot.com";
    public static final String URL_DATABASE_REFERENCE = "https://fierychat.firebaseio.com/ ";
    //
    public static final String FOLDER_STORAGE_IMG = "images";
    //
    public static final String USERS_REFERENCE = "umetadata";
    // umetadata/<user-id>/id,name,invites,muted,rooms
    //
    public static final String ROOMS_REFERENCE = "rmetadata";
    // rmetadata/<room-id>/createAt,id,type
    //
    public static final String ROOM_MESSAGES_REFERENCE = "rmessages";
    // rmessages/<room-id>/userid,name,message,timestamp
    //
    public static final String ROOM_USERS_REFERENCE = "rusers";
    // rusers/<room-id>/
    //
}
