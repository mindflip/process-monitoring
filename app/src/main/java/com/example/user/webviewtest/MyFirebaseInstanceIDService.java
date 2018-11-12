package com.example.user.webviewtest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //  Received token on 180517
        //  c8pVzJgh5hc:APA91bEmVha20DHGQTDxUb7Tt8DBJj-iIHZe3ZoYOjpFKgyYfKTINfkaU070NcUNxYUu_gjX-faW5BnBCIuq6e-rSjAWJQa6SBrqSAYeJRZAwrHjDoCk8UuDhamdcVrXfpMduyajzQaM

        //  fOi28Oq1BFs:APA91bFsuYxnw0Vd_t9t3JzUWEckHD1tiwWeP59CRWW_yF2zrkOVoQobWyeYoAVcY7UsktBZJaxKSDbdnqGSN3lZJ-4AvM_7rMjw_-H2jG9m-k88cAYNQ6cbm9chnBiizrFACH1ZneIq
        //  for Emulator 180615

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}