package project.agile.StatModel;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Guure on 2017/6/7.
 */

public interface IStatRequest {

    void init(LinearLayout content, Context context);

    void addChart();

    void requestData();

    String getName();

    void setName(String name);

    int getPosition();

    void setPosition(int position);

}
