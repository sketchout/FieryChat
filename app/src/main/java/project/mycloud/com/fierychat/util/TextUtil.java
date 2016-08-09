package project.mycloud.com.fierychat.util;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * Created by admin on 2016-08-09.
 */
public class TextUtil {

    private static int getColor(String iconString) {
        //Random rnd = new Random();
        //int num = Integer.parseInt(iconString);
        //int color = Color.argb(255,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
        // com.amulyakhare.textdrawable
        ColorGenerator generater = ColorGenerator.MATERIAL;
        int color = generater.getColor(iconString);
        return color;
    }

    public static TextDrawable getDrawable(String text) {
        //String iconString = roomList.get(position).getTitle().substring(0,1);
        String iconString = text.substring(0,1);
        if ( iconString.isEmpty() || iconString == null ) iconString = "_";
        int color = TextUtil.getColor(iconString);
        TextDrawable td = TextDrawable.builder()
                //.buildRoundRect(iconString, color, 30 );
                .buildRound(iconString, color);
        return td;
    }
}
