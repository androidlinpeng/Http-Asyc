package msgcopy.com.http_asyc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

import msgcopy.com.http_asyc.R;
import msgcopy.com.http_asyc.Utils.FileUtils;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int RESULT_GALLERY=300;
    private static final int RESULT_CAMERA=400;
    private static final int RESULT_CROP=500;
    private static final String AVATAR_TMP_FILE="camera.jpg";

    private ImageView imageView,imageView2,imageView3,imageView4 = null;

    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView4 = (ImageView)findViewById(R.id.imageView4);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case RESULT_CAMERA:
                    cropPicture(Uri.fromFile(new File(FileUtils.getTempPath()+AVATAR_TMP_FILE)));
                    break;
                case RESULT_GALLERY:
                    if (data!=null&&data.getData()!=null){
                        cropPicture(data.getData());
                    }
                    break;
                case RESULT_CROP:
                    bitmap = data.getParcelableExtra("data");
                    imageView.setImageBitmap(bitmap);
                    break;

            }
        }
    }

    //裁剪图片意图
    private void cropPicture(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);

        intent.putExtra("outputFormat", "PNG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为RESULT_CROP
        startActivityForResult(intent, RESULT_CROP);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
                picture();
                break;
            case R.id.imageView:
                resizeBitmap(bitmap);
                break;
        }
    }

    private void picture() {
        AlertDialog dialog = new AlertDialog.Builder(this).setItems(new String[]{getResources().getString(R.string.action_pick_pic_from_gallery),getResources().getString(R.string.action_pick_pic_from_camera)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = null;
                switch (which){
                    case 0:
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent,RESULT_GALLERY);
                        break;
                    case 1:
                        File imageFile = FileUtils.createTmpFile(AVATAR_TMP_FILE);
                        if (imageFile!=null){
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                            startActivityForResult(intent,RESULT_CAMERA);
                        }
                        break;
                }
            }
        }).create();
        dialog.show();
    }

    //压缩图片上传
    private Bitmap resizeBitmap(Bitmap bitmap){

        try{
            File imageFileraw= FileUtils.createTmpFile("picture_raw.png");
            Bitmap bitmap150 = bitmap.createScaledBitmap(bitmap,150,150,true);
            if (imageFileraw!=null){
                FileOutputStream fos = new FileOutputStream(imageFileraw);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.flush();
                fos.close();
                imageView2.setImageBitmap(bitmap150);
            }

            File imageFile100= FileUtils.createTmpFile("picture_100.png");
            Bitmap bitmap100 = bitmap.createScaledBitmap(bitmap,100,100,true);
            if (imageFile100!=null){
                FileOutputStream fos = new FileOutputStream(imageFile100);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.flush();
                fos.close();
                imageView3.setImageBitmap(bitmap100);
            }

            File imageFile50= FileUtils.createTmpFile("picture_50.png");
            Bitmap bitmap50 = bitmap.createScaledBitmap(bitmap,50,50,true);
            if (imageFile50!=null){
                FileOutputStream fos = new FileOutputStream(imageFile50);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.flush();
                fos.close();
                imageView4.setImageBitmap(bitmap50);
            }

            //回收Bitmap释放内存
            bitmap150.recycle();
            bitmap100.recycle();
            bitmap50.recycle();
            bitmap.recycle();

        }catch (Exception e){
            e.printStackTrace();
        }

        bitmap.recycle();

        return null;

    }
}
