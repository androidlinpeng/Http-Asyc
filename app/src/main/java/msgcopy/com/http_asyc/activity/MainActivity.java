package msgcopy.com.http_asyc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import msgcopy.com.http_asyc.asynchttp.APIHttp;
import msgcopy.com.http_asyc.R;
import msgcopy.com.http_asyc.asynchttp.ResultData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                autoLogin();
//            }
//        }).start();

    }

    public static ResultData Login() {
        String url = URL_LOGIN;
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("username", "");
        values.put("password", "");
        ResultData data = APIHttp.post(url, values);
        return data;
    }

    public static ResultData autoLogin() {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("type", "auto");
        values.put("reg_ver", "1");
        values.put("device", "00000000");
        ResultData data = APIHttp.post(URL_REG_AUTO, values);
        return data;
    }

    public static boolean isBlank(String s) {
        return (s == null || s.equals("") || s.equals("null"));
    }

    // 上传文件
    public static ResultData uploadFile(File file, String title, String type, String source) throws FileNotFoundException {
        ResultData data = null;
        RequestParams requestParams = new RequestParams();
        requestParams.put("imgFile", file);
        requestParams.put("title", title);
        requestParams.put("ftype", type);
        requestParams.put("source", source);
        data = APIHttp.uploadFile(URL_UPLOAD_FILES, requestParams);
        return data;
    }

    private ResultData put(String id, String title) {
        String url = String.format(URL_UPDATE_ARTICLEGROUP, id);
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("title", title);
        return APIHttp.put(url, values);
    }

    private ResultData delete(String id) {
        String url = String.format(URL_DELETE_ARTICLEGROUP, id);
        return APIHttp.delete(url);
    }

    public final static String URL_REG_AUTO = "http://cloud1.kaoke.me/iapi/user/register/auto/?channel_id=A1G1Z00110CmEA002A001A001A0010000T";
    public final static String URL_LOGIN = "http://cloud1.kaoke.me/iapi/user/login/?channel_id=A1G1Z00110CmEA002A001A001A0010000T";
    public final static String URL_UPLOAD_FILES = "";
    public final static String URL_UPDATE_ARTICLEGROUP = "";
    public final static String URL_DELETE_ARTICLEGROUP = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                startActivity(new Intent(MainActivity.this,CameraActivity.class));
                break;
        }
    }
}
