package prescilla.com.eurisko.SnappyDb;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import prescilla.com.eurisko.model.LoginModel;
import prescilla.com.eurisko.utils.MyLogs;

import static prescilla.com.eurisko.utils.Config.SNAPPY_DATE_LOGIN;
import static prescilla.com.eurisko.utils.Config.SNAPPY_LOGIN;

public class SnappyHelper {
    public static void setLoginModel(Context context, LoginModel loginModel) {
        DB db = null;
        try {
            db = DBFactory.open(context);
            db.put(SNAPPY_LOGIN, loginModel);

        } catch (Exception x) {
            MyLogs.error(x.getMessage());
        } finally {
            if (db != null)
                try {
                    db.close();
                } catch (SnappydbException e) {
                }
        }
    }


    public static String getTimeLoggedIn(Context context) {

        String date = null;
        DB db = null;
        try {
            db = DBFactory.open(context);
            date = db.get(SNAPPY_DATE_LOGIN);

        } catch (Exception x) {
            MyLogs.error(x.getMessage());
        } finally {
            if (db != null)
                try {
                    db.close();
                } catch (SnappydbException e) {
                }
        }
        return date;
    }

    public static void setTimeLoggedin(Context context, String date) {
        DB db = null;
        try {
            db = DBFactory.open(context);
            db.put(SNAPPY_DATE_LOGIN, date);

        } catch (Exception x) {
            MyLogs.error(x.getMessage());
        } finally {
            if (db != null)
                try {
                    db.close();
                } catch (SnappydbException e) {
                }
        }
    }


    public static LoginModel getLoginModel(Context context) {

        LoginModel loginModel = null;
        DB db = null;
        try {
            db = DBFactory.open(context);
            loginModel = db.getObject(SNAPPY_LOGIN, LoginModel.class);

        } catch (Exception x) {
            MyLogs.error(x.getMessage());
        } finally {
            if (db != null)
                try {
                    db.close();
                } catch (SnappydbException e) {
                }
        }
        return loginModel;
    }

}
