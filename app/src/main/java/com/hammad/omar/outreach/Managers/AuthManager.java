package com.hammad.omar.outreach.Managers;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.NewPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;
import com.hammad.omar.outreach.Interfaces.CallBackAuth;

import java.util.HashMap;
import java.util.Map;

public class AuthManager {

    private static final String TAG = AuthManager.class.getSimpleName();

    public static AuthManager instance;

    public static AuthManager getInstance(Context context) {

        if (instance == null){
            instance = new AuthManager(context);
        }

        return instance;
    }

    // context
    public static Context context;

    //cognito
    private static CognitoUserPool userPool; // you can a lot of stuff from this
    private static CognitoUser user; // if register success
    private static CognitoUserSession session;
    private static CognitoUserDetails userDetails;
    private static CognitoCachingCredentialsProvider credentialsProvider;

    //Constants
    private static String userPoolId = "us-east-1_wgPxciger";
    private static String clientId = "2ovm654bbe5f3ejdol26uunt0s";
    private static String clientSecret = "1uoiobva1j1sqep01336ofjvd760vd73v63q51lc1d1jigsi4qfr";
    private static Regions cognitoRegion = Regions.US_EAST_1;
    private static String identityPoolId = "us-east-1:7047bdd8-af02-485b-bc3a-b73cb9eee3f8";
    private static Regions identityRegion = Regions.US_EAST_1;
    private String tokenKey = "cognito-idp.us-east-1.amazonaws.com/us-east-1_wgPxciger";

    // call back
    public static final int CALL_BACK_ID_LOGIN = 0;
    public static final int CALL_BACK_ID_SIGNUP = 1;
    public static final int CALL_BACK_ID_CHALLENGE = 2;

    public static String getIdentityPoolId(){
        return identityPoolId;
    }

    public static Regions getCognitoRegion(){
        return cognitoRegion;
    }

    private AuthManager(Context context){
        this.context = context;
        this.userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);
        this.credentialsProvider = new CognitoCachingCredentialsProvider(context, identityPoolId, identityRegion);
    }

    public void signupUser(String username, String password, String phone_number, String email, String birthdate, String address, final CallBackAuth callBackAuth){


        // Create a CognitoUserAttributes object and add user attributes
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        // required attrs
        userAttributes.addAttribute("phone_number", phone_number);
        userAttributes.addAttribute("email", email);
        userAttributes.addAttribute("birthdate", birthdate);
        userAttributes.addAttribute("address", address);


        SignUpHandler signupCallback = new SignUpHandler() {

            @Override
            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {

                user = cognitoUser;
                callBackAuth.callbackAuth(user,CALL_BACK_ID_SIGNUP);

                if(!userConfirmed) {
                    Log.d(TAG,"User needs confirmation");
                }
                else {
                    Log.d(TAG,"confirmed");
                }
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d(TAG,"failed Signup");
                callBackAuth.callbackAuth(exception,CALL_BACK_ID_SIGNUP);
            }
        };

        // signup
        userPool.signUpInBackground(username, password, userAttributes, null, signupCallback);

    }

    public void signinUser(final String userId, final String password, final CallBackAuth callBackAuth){

        final CognitoUser cognitoUser = userPool.getUser(userId);

        // Callback handler for the sign-in process
        AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                session = userSession;
                user = cognitoUser;
                setupCredintialsProvider(session.getIdToken().getJWTToken());

                // save them in cache
                cachedUserNameAndPassword(userId,password);
                callBackAuth.callbackAuth(user,CALL_BACK_ID_LOGIN);
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {

                AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, password, null);
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {

                Log.d(TAG,"MFA Required");
                multiFactorAuthenticationContinuation.setMfaCode("0000");
                multiFactorAuthenticationContinuation.continueTask();
            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {

                Log.d(TAG,"Challenge");
                Log.d(TAG,continuation.getChallengeName());


                if (continuation.getChallengeName().equals("NEW_PASSWORD_REQUIRED")){

                    NewPasswordContinuation newPassCont = (NewPasswordContinuation) continuation;
                    newPassCont.setPassword("12345678");
                    newPassCont.continueTask();

                }


                //callBackAuth.callbackAuth(null,CALL_BACK_ID_CHALLENGE);
            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-in failed, check exception for the cause
                callBackAuth.callbackAuth(exception,CALL_BACK_ID_LOGIN);

            }
        };

        // Sign in the user
        cognitoUser.getSessionInBackground(authenticationHandler);

    }

    private void setupCredintialsProvider(String token){
        // Set up as a credentials provider.
        Map<String, String> logins = new HashMap<String, String>();
        logins.put(tokenKey,token);
        credentialsProvider.setLogins(logins);
    }

    private void cachedUserNameAndPassword(String username, String password){

        SharedPreferencesManager pref = SharedPreferencesManager.getInstance(context);
        pref.setUserId(username);
        pref.setUserPassword(password);

    }

    private void clearUserCredentialsFromCache(){
        SharedPreferencesManager pref = SharedPreferencesManager.getInstance(context);
        pref.clearCachedUserCredentials();
    }

    public void confirmUser(CognitoUser cognitoUser, String confirmationCode){

        //         Callback handler for confirmSignUp API
        GenericHandler confirmationCallback = new GenericHandler() {

            @Override
            public void onSuccess() {
                // User was successfully confirmed
            }

            @Override
            public void onFailure(Exception exception) {
                // User confirmation failed. Check exception for the cause.
            }
        };

        // This will cause confirmation to fail if the user attribute has been verified for another user in the same pool
        boolean forcedAliasCreation = false;

        // Call API to confirm this user
        cognitoUser.confirmSignUpInBackground(confirmationCode, forcedAliasCreation, confirmationCallback);

    }

    public void getUserDetails(CognitoUser cognitoUser){

        // Implement callback handler for getting details
        GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {
            @Override
            public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                // The user detail are in cognitoUserDetails
                Log.d(TAG,"Success getting user detials");
                Log.d(TAG,"User Detials : " + cognitoUserDetails);
                userDetails = cognitoUserDetails;
            }

            @Override
            public void onFailure(Exception exception) {
                // Fetch user details failed, check exception for the cause
            }
        };

// Fetch the user details
        cognitoUser.getDetailsInBackground(getDetailsHandler);

    }

    public boolean signout(){

        if(user == null){
            return false;
        }

        user.signOut();
        clearUserCredentialsFromCache();

        return true;
    }

    public CognitoUser getUser(){

        CognitoUser user = userPool.getUser();
        return user;

    }

    public CognitoUser getUser(String uid){

        CognitoUser user = userPool.getUser(uid);
        return user;

    }

    public CognitoCachingCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public boolean checkCachedLogin(){

        SharedPreferencesManager pref = SharedPreferencesManager.getInstance(context);

        String userName = pref.getUserId();
        String pass = pref.getUserPassword();

        if (userName == null || pass == null) {
            return false;
        }

        return true;
    }

    public void attemptLoginUsingCachedCredentials(CallBackAuth callBackAuth){

        SharedPreferencesManager pref = SharedPreferencesManager.getInstance(context);

        String userName = pref.getUserId();
        String pass = pref.getUserPassword();

        if (userName == null || pass == null) {
            return;
        }

        signinUser(userName,pass,callBackAuth);

    }

    public static CognitoUserSession getSession() {
        return session;
    }

    public static boolean isSignedIn(){
        return session != null && session.isValid();
    }
}
