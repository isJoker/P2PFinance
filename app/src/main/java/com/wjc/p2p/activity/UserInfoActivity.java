package com.wjc.p2p.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjc.p2p.MainActivity;
import com.wjc.p2p.R;
import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.common.MyActivityManager;
import com.wjc.p2p.uitls.BitmapUtils;
import com.wjc.p2p.uitls.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${万嘉诚} on 2016/11/16.
 * WeChat：wjc398556712
 * Function：用户头像信息界面
 */

public class UserInfoActivity extends BaseActivity {
    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_change_head)
    TextView tvChangeHead;
    @Bind(R.id.loginout)
    Button loginout;

    private static final int CAMERA = 1;
    private static final int PICTURE = 2;
    private boolean isChangedIcon;
    private String path;

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("用户信息");
    }

    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    //返回按钮的退出操作
    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        if(!isChangedIcon) {
            closeCurrentActivity();
        } else {
            MyActivityManager.getInstance().removeAll();
            goToActivity(MainActivity.class,null);
        }
    }

    //退出登录的回调
    @OnClick(R.id.loginout)
    public void logout(View view) {
        //1、清空本地保存的数据，文件还在，里面的内容清空
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sp.edit().clear().commit();

        //2、用户头像文件的清除
        String filePath = getCacheDir() + "/tx.png";
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }

        //3、移除栈中所有的activity
        MyActivityManager.getInstance().removeAll();
        //4、重新加载首页面
        goToActivity(MainActivity.class, null);
    }

    @OnClick(R.id.tv_change_head)
    public void changeIcon(View view) {
        new AlertDialog.Builder(this)
                .setTitle("图片来源")
                .setItems(new String[]{"相机", "图库"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case  0://相机
                                //打开系统拍照程序，选择拍照图片
                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, CAMERA);
                                break;
                            case  1://图库
                                // 打开系统图库程序，选择图片
                                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(picture, PICTURE);
                                break;
                        }
                    }
                })
        .show();
    }

    //带回调的启动新的acitivity之后的回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //确认用户修改过头像
        isChangedIcon = true;

        //保存本地的路径（手机内部存储）
        path = getCacheDir() + "/tx.png";
        if (requestCode == CAMERA && resultCode == RESULT_OK && data != null) {
            //拍照
            Bundle bundle = data.getExtras();
            // 获取相机返回的数据，并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            //bitmap圆形裁剪
            bitmap = BitmapUtils.zoom(bitmap, UIUtils.dp2Px(62), UIUtils.dp2Px(62));
            Bitmap circleImage = BitmapUtils.circleBitmap(bitmap);

            //真是项目当中，是需要上传到服务器的..这步我们就不做了。
            ivHead.setImageBitmap(circleImage);
            //将图片保存在本地
            saveImage(circleImage);

        } else if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            //图库
            Uri selectedImage = data.getData();
            //这里返回的uri情况就有点多了
            //**:在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file://....在4.4.2返回的是content://com.android.providers.media.documents/document/image:3951或者
            //总结：uri的组成，eg:content://com.example.project:200/folder/subfolder/etc
            //content:--->"scheme"
            //com.example.project:200-->"host":"port"--->"authority"[主机地址+端口(省略) =authority]
            //folder/subfolder/etc-->"path" 路径部分
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,所以要保证无论是哪个系统版本都能正确获取到图片资源的话
            //就需要针对各种情况进行一个处理了
            String pathResult = getPath(selectedImage);
            if (!TextUtils.isEmpty(path)) {
                Bitmap decodeFile = BitmapFactory.decodeFile(pathResult);
                Bitmap zoomBitmap = BitmapUtils.zoom(decodeFile, UIUtils.dp2Px(62), UIUtils.dp2Px(62));
                //bitmap圆形裁剪
                Bitmap circleImage = BitmapUtils.circleBitmap(zoomBitmap);
                saveImage(circleImage);
            }
        }
    }

    //将修改后的图片保存在本地存储中：（内存到本地）
    private void saveImage(Bitmap circleImage) {

        try {
            FileOutputStream fos = new FileOutputStream(path);
            //bitmap压缩(压缩格式、质量、压缩文件保存的位置)
            circleImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            //真是项目当中，是需要上传到服务器的..这步我们就不做了。
            ivHead.setImageBitmap(circleImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
