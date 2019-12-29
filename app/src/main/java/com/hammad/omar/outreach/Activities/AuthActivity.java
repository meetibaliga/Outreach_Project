package com.hammad.omar.outreach.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Interfaces.AuthState;
import com.hammad.omar.outreach.Interfaces.CallBackAuth;
import com.hammad.omar.outreach.Interfaces.CallBackDB;
import com.hammad.omar.outreach.Managers.AuthManager;
import com.hammad.omar.outreach.Managers.DynamoDBManager;
import com.hammad.omar.outreach.Managers.LocationManager;
import com.hammad.omar.outreach.Managers.RewardManager;
import com.hammad.omar.outreach.Managers.SharedPreferencesManager;
import com.hammad.omar.outreach.Models.Entry;
import com.hammad.omar.outreach.Models.EntryDO;
import com.hammad.omar.outreach.Models.UserDO;
import com.hammad.omar.outreach.Provider.EntriesDataSource;
import com.hammad.omar.outreach.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * A login screen that offers login via email/password.
 */
public class AuthActivity extends AppCompatActivity implements CallBackAuth,CallBackDB {

    private static final String TAG = AuthActivity.class.getSimpleName();

    // members
    private AuthManager authManager;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mMobileView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mErrorText;

    //temp
    View focusView;
    boolean error;
    String userName;
    String password;

    //state
    private AuthState authState;

    //flags
    private final boolean onlyAuthorizeFromGoogleSheet = false;
    // GoogleSheet Id to verify from :
    // https://docs.google.com/spreadsheets/d/11avOioEaR_cW6lq-3PeCfq5i1Z330hiWkIMOMnrCqds/edit#gid=731841134




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        // Set up the login form.
        setupUI();
        authManager = AuthManager.getInstance(this);

        //default state

        if(getIntent().hasExtra("from")){
            if(getIntent().getExtras().get("from").equals("signout")){
                authState = new AuthState.Login();
            }else{
                authState = new AuthState.Signup();
            }
        }else{
            authState = new AuthState.Signup();
        }
        authState = new AuthState.Login();
        modifyAuthUIAfterSwitch();

    }

    @Override
    public void onBackPressed() {
        //nothing
    }


    private void setupUI() {

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mMobileView = (AutoCompleteTextView) findViewById(R.id.mobile);
        mPasswordView = (EditText) findViewById(R.id.password);
        mErrorText = findViewById(R.id.errorText);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        setPasswordAction();
    }

    private void setPasswordAction(){

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    authState.attemptAuth(AuthActivity.this);
                    return true;
                }
                return false;
            }
        });
    }

    private void attemptAuth(){
        authState.attemptAuth(this);
    }

    public void attemptLogin() {

        Log.d(TAG," attempting login ..");

        checkLoginFormErrors();

        String normalizedMobile = normalizeMobileNumber(mMobileView.getText().toString());

        if(normalizedMobile == null){
            mMobileView.setError(getString(R.string.wrongNumber));
            return;
        }

        if(!error){
            showProgress(true);
            userName = normalizedMobile;
            authManager.signinUser(userName,"12345678",this);
        }else{
            focusView.setFocusable(true);
        }
    }

    public void attemptSignup(){

        Log.d(TAG," attempting signup ..");

        checkLoginFormErrors();

        String normalizedMobile = normalizeMobileNumber(mMobileView.getText().toString());

        if(normalizedMobile == null){
            mMobileView.setError(getString(R.string.wrongNumber));
            return;
        }

        if(!error){
            showProgress(true);
            userName = normalizedMobile;
            authManager.signupUser(userName,"12345678",this);
        }else{
            focusView.setFocusable(true);
        }

    }

    public String normalizeMobileNumber(String mobile){

        if(TextUtils.isEmpty(mobile)){
            return null;
        }

        if ( mobile.substring(0,2).equals("+1") && mobile.length() == 12 ) {

            return mobile;

        } else if (mobile.length() == 10) {

            return "+1" + mobile;

        } else if (mobile.substring(0,3).equals("001") && mobile.length() == 13){

            return "+" + mobile.substring(2);

        } else if (mobile.substring(0,1).equals("1") && mobile.length() == 11){

            return "+" + mobile.substring(1);

        }

        return null;

    }

    private void checkSignupFormErrors(){

        // Reset errors.
        mEmailView.setError(null);

        String email = mEmailView.getText().toString();

        // Check for a valid mobile number

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.requiredField));
            focusView = mEmailView;
            error = true;

        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            error = true;
        }

    }

    private void checkLoginFormErrors(){

        // Reset errors.
        mPasswordView.setError(null);
        mMobileView.setError(null);

        // Store values at the time of the login attempt.
        String mobile = mMobileView.getText().toString();

        error = false;
        focusView = null;

        // Check for a valid mobile number
        if (TextUtils.isEmpty(mobile)) {
            mMobileView.setError(getString(R.string.pleaseEnterMobile));
            focusView = mMobileView;
            error = true;

        } else if (!isMobileValid(mobile)) {
            mMobileView.setError(getString(R.string.incorrectMobileNum));
            focusView = mMobileView;
            error = true;
        }

    }



    @Override
    public void callbackAuth(Object object, int callbackId) {

        if (callbackId == AuthManager.CALL_BACK_ID_LOGIN){
            callbackLogin(object);

        } else if (callbackId == AuthManager.CALL_BACK_ID_SIGNUP){
            callbackSignup(object);

        } else if (callbackId == AuthManager.CALL_BACK_ID_CHALLENGE){
            callbackChallenge(object);
        } else if (callbackId == AuthManager.CALL_BACK_GET_MFA){
            Log.d(TAG," in mfa callback");
            callbackMFA(object);
        }

    }

    private void callbackMFA(Object object) {
        goToMFAuthentication(false);
    }

    private void callbackLogin(Object object) {

        if (object instanceof Exception){

            String errorMessage = "";
            String errorCode = "";

            if(object instanceof AmazonServiceException){

                AmazonServiceException awsException = (AmazonServiceException) object;
                errorMessage = awsException.getErrorMessage();
                errorCode = awsException.getErrorCode();

                if(errorCode.equalsIgnoreCase("UserNotFoundException")) {
                    attemptSignup();
                    return;
                } else if (errorCode.equalsIgnoreCase("CodeMismatchException")) {
                    goToMFAuthentication(true);
                    return;
                } else {
                    goBackToLoginForm(errorMessage);
                }

            }else if (object instanceof AmazonClientException){

                AmazonClientException awsException = (AmazonClientException) object;
                errorMessage = awsException.getMessage();
                Log.d(TAG, "ErrorMessage"+errorMessage);

                if (!App.hasActiveInternetConnection(this)) {
                    errorMessage = getString(R.string.makeSureConnected);
                }

            }

            return;
        }

        // SUCCESS

        Log.d(TAG,"success log in ");
        CognitoUser user = (CognitoUser) object;
        App.USER_ID = user.getUserId();
        loadUserInfo();
    }

    private void goBackToLoginForm(String errorMessage) {

        showProgress(false);
        mLoginFormView.setVisibility(View.VISIBLE);
        mPasswordView.requestFocus();
        mErrorText.setVisibility(View.VISIBLE);

        String modifiedErrorMessage = errorMessage;
        if(errorMessage.equalsIgnoreCase(getString(R.string.user_not_confirmed))){
            modifiedErrorMessage = getString(R.string.you_need_admit);
        }

        mErrorText.setText(modifiedErrorMessage);

    }

    private void loadUserInfo() {

        // load user first form
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DynamoDBManager db = new DynamoDBManager(AuthActivity.this);
                db.getEntries();
            }
        });
    }

    private void callbackChallenge(Object object){

    }

    private void callbackSignup(Object object){

        if (object instanceof Exception){

            String errorMessage = "";

            if(object instanceof AmazonServiceException){

                AmazonServiceException awsException = (AmazonServiceException) object;
                errorMessage = awsException.getErrorMessage();

            }else if (object instanceof AmazonClientException){

                AmazonClientException awsException = (AmazonClientException) object;
                errorMessage = awsException.getMessage();

                if (!App.hasActiveInternetConnection(this)) {
                    errorMessage = getString(R.string.makeSureConnected);
                }

            }

            showProgress(false);
            mLoginFormView.setVisibility(View.VISIBLE);
            mErrorText.setVisibility(View.VISIBLE);
            mErrorText.setText(errorMessage);

            return;
        }

        // success
        Log.d(TAG,"success Signup");
        CognitoUser user = (CognitoUser) object;
        App.USER_ID = user.getUserId();

        authManager.signinUser(userName, "12345678", this);

    }

    private void goToMFAuthentication(boolean isWrongMFA) {
        Intent intent = new Intent(this,ConfirmMobile.class);
        intent.putExtra("Mobile",mMobileView.getText().toString());

        String userId = normalizeMobileNumber(mMobileView.getText().toString());
        intent.putExtra("uid",userId);
        intent.putExtra("isWrongMFA", isWrongMFA);
        startActivity(intent);
    }

    private boolean isMobileValid(String mobile) {

        return (mobile.contains("+1")&&mobile.length() == 12) || mobile.length() == 10 || (mobile.contains("00") && mobile.length() == 13) || (mobile.substring(0,1).equals("1") && mobile.length() == 11);
    }

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });



            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });



        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void goToMainScreen(){

        // check location
        if(LocationManager.isLocationEnabled(this)){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,PermissionsActivity.class);
            startActivity(intent);
        }

    }

    public void modifyAuthUIAfterSwitch(){

        // add the mobile number field
        View emailView = findViewById(R.id.emailView);
        emailView.setVisibility(View.GONE);

        mPasswordView.setVisibility(authState.getExtraViewVisibility());

        TextView clickHere = findViewById(R.id.switchText);
        clickHere.setVisibility(View.GONE);

        Button authButton = findViewById(R.id.email_sign_in_button);
        authButton.setText(authState.getAuthButtonText(this));

        TextView terms = findViewById(R.id.terms);
        Spanned sp = Html.fromHtml(getString(R.string.terms));
        terms.setText(sp);
        terms.setVisibility(View.VISIBLE);

        setPasswordAction();

        changeTitle();

    }

    private void changeTitle() {
        setTitle(authState.getTitle(this));
    }


    public void toggleState(){

        if(authState instanceof AuthState.Login){
            // change state
            authState = new AuthState.Signup();
        }else{
            // change state
            authState = new AuthState.Login();

        }

    }


    public void switchStateLabelClicked(View view) {
        toggleState();
        modifyAuthUIAfterSwitch();
    }

    public void authenticate(View view) {
        authState.attemptAuth(this);
    }

    public void forgotClicked(View view) {

        String userName = normalizeMobileNumber(mMobileView.getText().toString());

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, R.string.pleaseEnterMobile,Toast.LENGTH_SHORT)
                    .show();
            mMobileView.requestFocus();
            return;
        }

        CognitoUser user = authManager.getUser(userName);

        if(user == null){

            Toast.makeText(this, R.string.noUserWithMobile,Toast.LENGTH_SHORT)
                    .show();
            mMobileView.requestFocus();
            return;
        }

        Intent intent = new Intent(this,ResetPasswordActivitiy.class);
        intent.putExtra("userName",userName);
        startActivity(intent);

    }

    @Override
    public void callbackDB(Object object, int callbackId) {

        if (callbackId == DynamoDBManager.CALL_BACK_ID_GET_ENTRIES){
            callBackEntries(object);
        }else if (callbackId == DynamoDBManager.CALL_BACK_ID_GET_USER){
            callbackidUser(object);
        }else{
            // do nothing
        }
    }

    // CALL BACK DB

    private void callBackEntries(Object object) {

        List<Entry> entries = Entry.getEntries((PaginatedList<EntryDO>) object);
        // calculate rewards
        App.NUM_OF_ENTRIES = App.entriesList.size();

        // insert all items in ds
        EntriesDataSource ds = new EntriesDataSource(this);
        ds.insertAllItems(entries);

        //setup the time and date of the entry
        setupDateAndTimeOfEntries(entries);

        goToMainScreen();

    }

    private void setupDateAndTimeOfEntries(List<Entry> entries) {

        if(entries.size() == 0) return;

        int numOfEntriesToday = 0;
        //Todays Day
        String todayDay = new SimpleDateFormat("dd").format(new Date());

        Entry latest = entries.get(entries.indexOf(Collections.max(entries)));
        entries.remove(latest);
        String dayOfLates = new SimpleDateFormat("dd").format(App.getDateFromString(latest.getCreationDate(),App.sourceDateFormat));
        if(todayDay.equals(dayOfLates)){Log.d(TAG,"Adding to numOfEntriesToday"); numOfEntriesToday++;}

        if(entries.size() > 0) {
            Entry beforeLatest = entries.get(entries.indexOf(Collections.max(entries)));
            String dayOfBeforeLatest = new SimpleDateFormat("dd").format(App.getDateFromString(beforeLatest.getCreationDate(),App.sourceDateFormat));
            if(todayDay.equals(dayOfBeforeLatest)){Log.d(TAG,"Adding to numOfEntriesToday"); numOfEntriesToday++;}
        }

        String lastEntryHour = new SimpleDateFormat("HH").format(App.getDateFromString(latest.getCreationDate(),App.sourceDateFormat));

        // update the model
        App.entriesManager.setNumOfDailyUserInteries(numOfEntriesToday);
        App.entriesManager.setLastEntryDateAdded(Integer.parseInt(dayOfLates));
        App.entriesManager.setLastEntryHourAdded(Integer.parseInt(lastEntryHour));
    }

    private void callbackidUser(Object object) {

        PaginatedList<UserDO> results = (PaginatedList<UserDO>) object;
        Intent intent;

        if(results.size() == 0 || results == null){
            Log.d(TAG,"Form not completed");
            intent = new Intent(this,OneTimeForm_1.class);
        } else{
            Log.d(TAG,"Form completed");
            App.user = results.get(0);
            new SharedPreferencesManager(this).setUserFormCompleted(true);
            intent = new Intent(this,MainActivity.class);
        }

        startActivity(intent);


    }
}

