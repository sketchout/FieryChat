package project.mycloud.com.fierychat.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import project.mycloud.com.fierychat.R;
import project.mycloud.com.fierychat.model.UserModel;
import project.mycloud.com.fierychat.util.CommonUtil;

public class ChatRoomActivity extends BaseActivity {

    private static final String TAG = ChatRoomActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


    }

}
