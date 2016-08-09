package project.mycloud.com.fierychat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import project.mycloud.com.fierychat.R;
import project.mycloud.com.fierychat.model.ChatRoomModel;
import project.mycloud.com.fierychat.util.CommonUtil;
import project.mycloud.com.fierychat.util.TextUtil;

/**
 * Created by admin on 2016-08-08.
 */
public class RecyclerViewAdapter
    extends  RecyclerView.Adapter<RecyclerViewHolders> implements RecyclerViewHolders.Callback {

    private Context context;
    private List<ChatRoomModel> roomList;

    public RecyclerViewAdapter(Context context,
                               ArrayList<ChatRoomModel> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        //
        View v = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.item_chat_room , parent, false ) ;
        RecyclerViewHolders rvh =
                new RecyclerViewHolders(v,
                    context, roomList, this);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        String title = roomList.get(position).getTitle() ;

        holder.getTitleTextView().setText( title );
        holder.getRoomIcon().setImageDrawable( TextUtil.getDrawable(title) ) ;

    }

    @Override
    public int getItemCount() {

        return roomList.size();
    }

    @Override
    public void onDeleteProcess(int index) {

    }

    @Override
    public void onClickLinearLayout(int index) {

    }
}
