package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * An object which can be set in the {@link ThreadLocal} context for the NetworkDownloader to use for session sharing.
 * Created by brad on 5/13/17.
 */
public class SessionResolver {

    public static final ThreadLocal<SessionResolver> SESSION_RESOLVER_LOCAL = new ThreadLocal<>();

    public static void setSessionResolver(String id){
        SESSION_RESOLVER_LOCAL.set(new SessionResolver(id));
    }
    public static void unsetSessionResolver(){
        SESSION_RESOLVER_LOCAL.remove();
    }
    public static SessionResolver getSessionResolver(){
        return SESSION_RESOLVER_LOCAL.get();
    }



    private String sessionId;

    public SessionResolver(){}
    public SessionResolver(String id){
        this.sessionId = id;
    }


    /**
     * Allows the calling application to share a session.  Use this method to set the identifier.
     */
    public void setSessionId(String id){
        this.sessionId = id;
    }

    /**
     * Returns the session id for the NetworkDownloader to use.
     */
    public String getSessionId(){
        return this.sessionId;
    }

}
