package project.mycloud.com.fierychat.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import project.mycloud.com.fierychat.R;
import project.mycloud.com.fierychat.model.UserModel;
import project.mycloud.com.fierychat.util.CommonUtil;

public class RoomListActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = RoomListActivity.class.getSimpleName();
    //
    private UserModel userModel;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        Loading();
    }

    private void Loading() {

        showProgrssDialogMessage("Loading...");
        // get User Information

        userModel = getUserModel();

        // set Google ApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this )
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        // get Chat Messages
        //getMessages( EndPoints.CHAT_REFERENCE ) ;

        Log.d(TAG,"checkAuthAndLoading : getMessages()");

    }

    /**
     * GoogleApiClient
     * @param connectionResult : result value
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        // TODO : change alert
        //CommonUtil.initToast( this, "Google Play Services error.");
        showAlert("Error", "Google API [" + connectionResult.toString() +"]" );
    }
}
