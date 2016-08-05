package project.mycloud.com.fierychat.model;

/**
 * Created by admin on 2016-08-05.
 *
 * Firebase security rules for a simple chat room model
 *   - https://gist.github.com/katowulf/4741111
 *   /chat/$key - a chat conversation
     /chat/$key/users - list of users allowed to participate
     /chat/$key/last - last time each user checked the chat (for marking messages new)
     /chat/$key/messages - the chat history
 *
 */
public class ChatRoomModel {

    // variable
    private String id;
    private String title;
    private String timeStamp;
    private String lastMessage;

    private UserModel ownerModel;

    // Method
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


}
