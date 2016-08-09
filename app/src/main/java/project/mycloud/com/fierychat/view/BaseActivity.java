package project.mycloud.com.fierychat.view;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import project.mycloud.com.fierychat.R;
import project.mycloud.com.fierychat.model.ChatModel;
import project.mycloud.com.fierychat.model.ChatRoomModel;
import project.mycloud.com.fierychat.model.UserModel;
import project.mycloud.com.fierychat.util.EndPoints;

/**
 * Created by admin on 2016-08-05.
 */
public class BaseActivity extends AppCompatActivity {


    private static final String TAG = BaseActivity.class.getSimpleName();

    //firebase
    private FirebaseAuth mFbAuth = null;
    private FirebaseUser mFbUser = null;
    private DatabaseReference mFbDbRef;
    private FirebaseStorage mFbStorage = null;
    // google api
    protected GoogleApiClient mGoogleApiClient;

    // method
    protected FirebaseAuth _getFbAuth() {
        if ( mFbAuth == null ) {
            mFbAuth = FirebaseAuth.getInstance();
        }
        return mFbAuth;
    }
    protected FirebaseUser _getFbUser() {
        if ( mFbUser == null ) {
            mFbUser = _getFbAuth().getCurrentUser();
        }
        return mFbUser;
    }

    protected UserModel _getUserModel() {
        return new UserModel(
                _getFbUser().getDisplayName(),           // name
                _getFbUser().getPhotoUrl().toString(),   // photo url
                _getFbUser().getUid() );                 // uid
    }

    protected void _pushChat(String chatKey, ChatModel chatModel) {
        FirebaseDatabase.getInstance().getReference(chatKey).push().setValue(chatModel);
    }

    protected void _pushRoom(String chatKey, ChatRoomModel crm) {
        FirebaseDatabase.getInstance().getReference(chatKey).push().setValue(crm);
    }

//    protected DatabaseReference _getFbDatabaseRef() {
//        if ( mFbDbRef == null ) {
//            mFbDbRef = FirebaseDatabase.getInstance().getReference();
//        }
//        return mFbDbRef;
//    }

    protected DatabaseReference _getFbDatabaseRef(String key) {
        if ( mFbDbRef == null ) {
            mFbDbRef = FirebaseDatabase.getInstance().getReference(key);
        }
        return mFbDbRef;
    }
    protected StorageReference _getFbStorageRef() {
        if ( mFbStorage == null ) {
            mFbStorage = FirebaseStorage.getInstance();
        }
        return mFbStorage.getReferenceFromUrl(EndPoints.URL_STORAGE_REFERENCE)
                .child(EndPoints.FOLDER_STORAGE_IMG);
    }

    // dialog progress
    private ProgressDialog mProgressDialog;

    protected void _showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            //mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
        }
        mProgressDialog.show();
    }

    protected void _showProgrssDialogMessage(String message) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void _hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    // alert
    protected void _showAlert(String title, String message) {
        // Authenticated failed with error firebaseError
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setMessage( message )
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}