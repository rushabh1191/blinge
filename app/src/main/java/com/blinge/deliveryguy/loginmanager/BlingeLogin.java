package com.blinge.deliveryguy.loginmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blinge.deliveryguy.BlingeBaseActivity;
import com.blinge.deliveryguy.helpers.BlingeUtilities;
import com.blinge.deliveryguy.helpers.ConfirmationWindow;
import com.blinge.deliveryguy.R;
import com.blinge.deliveryguy.ordermanager.BlingeLandingPage;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * A login screen that offers login via email/password.
 */
public class BlingeLogin extends BlingeBaseActivity {


    @Bind(R.id.et_username)
    EditText mUserName;
    @Bind(R.id.password)
    EditText mPassword;

    @Bind(R.id.til_username)
    TextInputLayout tilUsername;

    @Bind(R.id.til_password)
    TextInputLayout tilPassword;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinge_login);
        ButterKnife.bind(this);




        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    loginUser();
                    return true;
                }
                return false;
            }
        });
    }




    @OnClick(R.id.email_sign_in_button)
    void loginUser(){

        if(isValid()){

            if(BlingeUtilities.isNetworkAvailable(this)) {
                loginUserWithParse(mUserName.getText().toString().trim()
                        , mPassword.getText().toString().trim());
            }
            else {

                BlingeUtilities.showNetworkNotAvailableDialog(this);
            }

        }
    }

    void loginUserWithParse(String username,String password){

        final ProgressDialog pd=ProgressDialog.show(this,"","Logging in...");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                pd.dismiss();
                if (e == null) {
                    Intent intent= BlingeLandingPage.getIntentToStartThisActivity(BlingeLogin.this);
                    finish();
                    startActivity(intent);

                } else {

                    int statusCode = e.getCode();
                    String message;
                    if (statusCode == ParseException.CONNECTION_FAILED) {

                        message = "Check your network connection";
                    } else {
                        message = "Invalid username or password";
                    }
                    new ConfirmationWindow(BlingeLogin.this,"Error",message,"OK","");
                }

            }
        });


    }

    @OnTextChanged(R.id.et_username)
    void removeErrorMessageForUsername(){
        tilUsername.setErrorEnabled(false);
    }

    @OnTextChanged(R.id.password)
    void removeErrorMessageForPassword(){
        tilPassword.setErrorEnabled(false);
    }
    boolean isValid(){

        if(mUserName.getText().toString().trim().equals("")){
            tilUsername.setErrorEnabled(true);
            tilUsername.setError("Enter username");
            return false;
        }
        if(mPassword.getText().toString().trim().equals("")){
            tilPassword.setError("Enter Password");
            tilPassword.setErrorEnabled(true);
            return false;
        }
        return true;
    }



}


