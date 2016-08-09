package project.mycloud.com.fierychat.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import project.mycloud.com.fierychat.R;
import project.mycloud.com.fierychat.util.CommonUtil;
import project.mycloud.com.fierychat.util.Network;


/**
 *
 *  http://www.androidhive.info/2016/06/android-getting-started-firebase-simple-login-registration-auth/
 *
 */
public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {


    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = LoginActivity.class.getSimpleName();
    // UI
    private SignInButton mSignInButton;

    // firebase and GoogleApiClient
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if( !Network.isNormal(this) ) {
            CommonUtil.initToast(this,"Please try again later !");
            finish();
        } else {

            mSignInButton =(SignInButton)findViewById(R.id.button_sign_in);
            mSignInButton.setOnClickListener(this);

            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            // Initialize FirebaseAuth
            mFirebaseAuth = FirebaseAuth.getInstance();

            Log.d(TAG,"Login end");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == RC_SIGN_IN ) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if ( result.isSuccess() ) {
                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);
            } else {
                Log.e(TAG,"Google Sign In failed");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d(TAG,"firebaseAuthWithGoogle:"+ account.getId() );

        AuthCredential credential
                = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG,"signInWithCredential:onComplete:" + task.isSuccessful());

                        if ( !task.isSuccessful() ) {
                            Log.w(TAG, "signInWithCredential", task.getException() );
                            CommonUtil.initToast( LoginActivity.this, "Authentication failed");
                        } else {

                            startActivity(new Intent(LoginActivity.this, RoomListActivity.class));
                            finish();
                        }
                    }
                });

    }

    // setOnClickListener
    @Override
    public void onClick(View view) {

        switch( view.getId() ) {
            case R.id.button_sign_in:
                signIn();
                break;
            default:
                return;
        }

    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult( signInIntent, RC_SIGN_IN );

    }

    // Google Api
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed :" + connectionResult );
        CommonUtil.initToast( this, "Google Play Service error. Please try again later");
    }
}