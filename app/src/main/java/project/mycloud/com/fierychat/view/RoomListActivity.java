package project.mycloud.com.fierychat.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import project.mycloud.com.fierychat.R;
import project.mycloud.com.fierychat.model.ChatRoomModel;
import project.mycloud.com.fierychat.model.UserModel;
import project.mycloud.com.fierychat.util.AppDefines;
import project.mycloud.com.fierychat.util.EndPoints;
import project.mycloud.com.fierychat.util.Network;

public class RoomListActivity extends BaseActivity  {

    private static final String TAG = RoomListActivity.class.getSimpleName();

    // Define
    private UserModel userModel;
    private ArrayList<ChatRoomModel> listRoom;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    // Ref
    private DatabaseReference roomsRef;
    private RecyclerViewAdapter recyclerViewAdapter;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_room_list);

        if ( !Network.isNormal(this) ) {
            _showAlert("Information", "Please Check Internet!");
            finish();
        } else {
            Loading();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_room_list, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.menu_search:
                //
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Loading() {

        _showProgrssDialogMessage("Loading...");
        // get User Information

        userModel = _getUserModel();

        // get Chat Messages
        getRoomList( EndPoints.ROOMS_REFERENCE ) ;
        setToolbar();
        setFloatingActionButton();

        Log.d(TAG,"checkAuthAndLoading : getRoomList()");

    }

    private void setToolbar() {

        Toolbar tb =  (Toolbar)findViewById(R.id.toolbar_room_list);
        tb.setTitle("Room List");
        setSupportActionBar(tb);

    }

    private void setFloatingActionButton() {
        FloatingActionButton fab
                = (FloatingActionButton)findViewById(R.id.fab_room_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCreateRoomPopup();
            }
        });
    }

    private void setCreateRoomPopup() {

        // http://stackoverflow.com/questions/35861081/custom-popup-dialog-with-input-field
        // http://techblogon.com/alert-dialog-with-edittext-in-android-example-with-source-code/
        LayoutInflater li = LayoutInflater.from(this);
        View v = li.inflate( R.layout.new_chat_room, null );

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(v);
        //adb.setTitle("New Chat Room");
        //adb.setMessage("Enter Room Name Here");

        final EditText et = (EditText)v.findViewById(R.id.et_new_chat_room);
        final TextView tvt = (TextView)v.findViewById(R.id.tv_new_chat_room_title);
        //final TextView tvm = (TextView)v.findViewById(R.id.tv_new_chat_room_message);
        tvt.setText("New Chat Room");
        //tvm.setText("Name : ");

        adb.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nameNewRoom = et.getText().toString();
                        makeRoom(nameNewRoom);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog ad = adb.create();
        ad.show();

    }

    private void makeRoom(String nameNewRoom) {

        Log.d(TAG, "make Room : " + nameNewRoom );

        ChatRoomModel crm = new ChatRoomModel();
        crm.setTitle(nameNewRoom);
        //
        Long tsLong = System.currentTimeMillis()/1000;
        crm.setTimeStamp( tsLong.toString() );

        crm.setUserModel( _getUserModel() );

        _pushRoom( EndPoints.ROOMS_REFERENCE, crm );
    }

    private void getRoomList(String roomsReference) {

        listRoom = new ArrayList<ChatRoomModel>();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView)findViewById(R.id.list_view_room_item);
        recyclerView.setLayoutManager(linearLayoutManager);

        roomsRef = _getFbDatabaseRef(EndPoints.ROOMS_REFERENCE);

        roomsRef.limitToLast(AppDefines.MAX_ROOMS_TO_SHOW);

        roomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //
                _hideProgressDialog();
                Log.d(TAG, "Loading done..." + dataSnapshot.getChildrenCount() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        roomsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getSingleRoom(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getSingleRoom(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeSingleRoom(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void getSingleRoom(DataSnapshot dataSnapshot) {

        String title = null;
        String timeStamp = null;
        UserModel userModel = null;
        for (DataSnapshot item : dataSnapshot.getChildren()) {

            String key = item.getKey();
            Log.d(TAG," key to get : " + key);
            if ( key.equals("title") ) title = item.getValue(String.class);
            if ( key.equals("timeStamp") ) timeStamp = item.getValue(String.class);
            if ( key.equals("userModel") ) userModel = item.getValue(UserModel.class);
        }

        ChatRoomModel room = new ChatRoomModel();

        room.setTitle( title );
        room.setTimeStamp( timeStamp );
        room.setUserModel(userModel);

        Log.d(TAG," add to listRoom : " + room.toString());
        listRoom.add(room);
        recyclerViewAdapter = new RecyclerViewAdapter(
                RoomListActivity.this,listRoom );
        recyclerView.setAdapter( recyclerViewAdapter );
        recyclerView.scrollToPosition( recyclerViewAdapter.getItemCount() -1 );

    }

    private void removeSingleRoom(DataSnapshot dataSnapshot) {

        for ( DataSnapshot item : dataSnapshot.getChildren() ) {

            String key = item.getKey();
            Log.d(TAG," key to remove : " + key);

            if ( key.equals("title")) {

                String title = item.getValue( String.class );
                for ( int i =0 ; i < listRoom.size(); i++ ) {
                    if ( listRoom.get(i).getTitle().equals(title) ) {
                        listRoom.remove(i);
                        Log.d(TAG, "removeSingleRoom : " + i);
                    }
                }
            }
            recyclerViewAdapter.notifyDataSetChanged();

            recyclerViewAdapter = new RecyclerViewAdapter(
                    RoomListActivity.this, listRoom );
            recyclerView.setAdapter( recyclerViewAdapter );
            recyclerView.scrollToPosition( recyclerViewAdapter.getItemCount() -1 );
        }
    }

}
