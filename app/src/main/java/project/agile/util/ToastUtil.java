package project.agile.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Oneplus on 2017/4/21.
 */

public class ToastUtil {
    private static Toast sToast;

    public static void showToast(Context context, int msg_id){
        if(sToast == null){
            // 通过context获取Application
            sToast = Toast.makeText(context.getApplicationContext(), msg_id, Toast.LENGTH_SHORT);

        }
        sToast.setText(msg_id);
        sToast.show();
    }
}
