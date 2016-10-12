package com.android.criticos.app;

/**
 * Created by Daniel on 27/09/2016.
 */
public class Constantes {

    private static final String HOST_PORT = "8080";
    private static final String IP = "http://192.168.1.2:";

    public static final String LOGIN_URL = IP + HOST_PORT + "/WebServiceCriticos/User/Login.php";
    public static final String SIGN_IN_URL = IP + HOST_PORT + "/WebServiceCriticos/User/InsertUser.php";
    public static final String MODIFIED_USER_URL = IP + HOST_PORT + "/WebServiceCriticos/User/UpdateUser.php";

    public static final String SEARCH_ACTOR_BY_EMAIL_URL = IP + HOST_PORT + "/WebServiceCriticos/Event/SearchActorByEmail.php";
    public static final String INSERT_EVENT_URL = IP + HOST_PORT + "/WebServiceCriticos/Event/InsertEvent.php";
    public static final String SEARCH_EVENT_BY_NAME_URL = IP + HOST_PORT + "/WebServiceCriticos/Event/SearchEventByName.php";
    public static final String LOAD_INFORMATION_EVENT_URL = IP + HOST_PORT + "/WebServiceCriticos/Event/LoadInformationEvent.php";
    public static final String INSERT_COMMENTS_BY_EVENT_URL = IP + HOST_PORT + "/WebServiceCriticos/Event/InsertCommentsByEvent.php";
    public static final String LOAD_EVENT_BY_USER_LIKE_CREATOR_URL = IP + HOST_PORT + "/WebServiceCriticos/Event/LoadEventsByUserLikeCreator.php";
    public static final String LOAD_EVENT_BY_USER_LIKE_ACTOR_URL = IP + HOST_PORT + "/WebServiceCriticos/Event/LoadEventsByUserLikeActor.php";


}