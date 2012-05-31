package edu.eltech.mobview.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface TrackServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see edu.eltech.mobview.client.service.TrackService
     */
    void getTrackPoints( int userid, AsyncCallback<java.util.List<edu.eltech.mobview.client.data.track.TrackPointV2>> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see edu.eltech.mobview.client.service.TrackService
     */
    void getPlaces( int userid, AsyncCallback<java.util.List<edu.eltech.mobview.client.data.track.Place>> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static TrackServiceAsync instance;

        public static final TrackServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (TrackServiceAsync) GWT.create( TrackService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "track" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
