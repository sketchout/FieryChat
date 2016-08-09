package project.mycloud.com.fierychat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import project.mycloud.com.fierychat.R;
import project.mycloud.com.fierychat.model.ChatRoomModel;

/**
 * Created by admin on 2016-08-08.
 */
public class RecyclerViewHolders
 extends RecyclerView.ViewHolder {

    private final LinearLayout linearLayout;
    private final TextView titleTextView;
    private final TextView messageTextView;

    //
    private final ImageView roomIcon;
    private Context context;
    private Callback callback;
    private List<ChatRoomModel> roomList;

    public RecyclerViewHolders(View itemView , Context context,
                               final List<ChatRoomModel> roomList,
                               RecyclerViewAdapter callback) {
        super(itemView);
        //
        this.context = context;
        this.callback = callback;
        this.roomList = roomList;
        //
        linearLayout = (LinearLayout)itemView
                .findViewById(R.id.linear_layout_chat_room);
        roomIcon = (ImageView)itemView
                .findViewById(R.id.iv_chat_room_icon);
        titleTextView = (TextView)itemView
                .findViewById(R.id.tv_chat_room_title);
        messageTextView = (TextView)itemView
                .findViewById(R.id.tv_chat_last_message);
    }

    public interface Callback {
        void onDeleteProcess(int index);
        void onClickLinearLayout(int index);
    }
    public TextView getTitleTextView() {
        return titleTextView;
    }
    public ImageView getRoomIcon() {
        return roomIcon;
    }
}
