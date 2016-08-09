package project.mycloud.com.fierychat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import project.mycloud.com.fierychat.util.CommonUtil;
import project.mycloud.com.fierychat.util.Network;
import project.mycloud.com.fierychat.view.BaseActivity;
import project.mycloud.com.fierychat.view.LoginActivity;
import project.mycloud.com.fierychat.view.RoomListActivity;

/**
 *
 * begin date : 5 Aug 2016
 *
 */
public class MainActivity extends BaseActivity {

    //
    private static final String TAG = MainActivity.class.getSimpleName();

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( !Network.isNormal(this)) {
            CommonUtil.initToast(this, "Please Check Internet Status!");
            finish();

        } else {
            //CommonUtil.initToast(this, "findViews()!");
            //findViews();
            checkAuthAndLoading();
        }

    }

    private void checkAuthAndLoading() {

        if ( _getFbUser() == null ) {

            Log.d(TAG,"checkAuthAndLoading : mFirebaseAuth is null, start Loginactivity");

            startActivity(new Intent(this, LoginActivity.class));
            finish();

        } else {

            startActivity(new Intent(this, RoomListActivity.class));
            finish();

        }
    }
}
