package com.umeng.soexample.mvp.presenter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.umeng.soexample.MainActivity;
import com.umeng.soexample.R;
import com.umeng.soexample.activity.AddressActivity;
import com.umeng.soexample.activity.ShezhiActivity;
import com.umeng.soexample.model.SuccessBean;
import com.umeng.soexample.mvp.view.AppDelegate;
import com.umeng.soexample.net.HttpHelper;
import com.umeng.soexample.services.BaseService;
import com.umeng.soexample.services.HttpListener;
import com.umeng.soexample.services.UploadService;
import com.umeng.soexample.utils.SharedPreferencesUtils;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static android.app.Activity.RESULT_OK;

public class ShezhiActivityPresenter extends AppDelegate implements View.OnClickListener {

    private Builder builder;
    private String nick;
    private String token;
    private String uid;
    private static String path;//sd路径
    private Bitmap head;//头像Bitmap
    private SimpleDraweeView simpleDraweeView;
    private AlertDialog alertDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shezhi;
    }

    @Override
    public void initData() {
        super.initData();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        builder = new Builder(context);
        get(R.id.shezhirela_image).setOnClickListener(this);
        get(R.id.shezhirela_nick).setOnClickListener(this);
        get(R.id.shezhirela_user).setOnClickListener(this);
        get(R.id.shezhirela_back).setOnClickListener(this);
        get(R.id.shezhirela_address).setOnClickListener(this);
        simpleDraweeView = get(R.id.head);
        String user_name = SharedPreferencesUtils.getString(context, "user_name");
        String user_pass = SharedPreferencesUtils.getString(context, "user_pass");
        doHttp1(user_name, user_pass);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shezhirela_address:
                Intent intent = new Intent(context, AddressActivity.class);
                context.startActivity(intent);
                break;
            case R.id.shezhirela_image:
                View view2 = View.inflate(context, R.layout.camera, null);
                builder.setTitle("设置头像")
                        .setIcon(R.drawable.shezhi)
                        .setView(view2)
                        .setPositiveButton("", null)
                        .setNegativeButton("", null);
                alertDialog = builder.create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                WindowManager windowManager = ((ShezhiActivity) context).getWindowManager();
                Display defaultDisplay = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams attributes = alertDialog.getWindow().getAttributes();
                attributes.width = defaultDisplay.getWidth();
                alertDialog.getWindow().setAttributes(attributes);
                view2.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                "head.png")));
                        ((ShezhiActivity) context).startActivityForResult(intent2, 2);//采用ForResult打开
                    }
                });
                view2.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        ((ShezhiActivity) context).startActivityForResult(intent1, 1);
                    }
                });
                break;
            case R.id.shezhirela_nick:
                View view = View.inflate(context, R.layout.edit, null);
                final EditText editText = view.findViewById(R.id.nickname_edit);
                builder.setTitle("设置昵称")
                        .setView(view)
                        .setIcon(R.drawable.shezhinick)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nick = editText.getText().toString().trim();
                                doHttp(nick, token, uid);
                            }
                        });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
            case R.id.shezhirela_user:
                View view1 = View.inflate(context, R.layout.layout_user, null);
                doHttp2(token, uid, view1);
                builder.setTitle("用户信息")
                        .setIcon(R.drawable.ueser)
                        .setView(view1)
                        .setPositiveButton("", null)
                        .setNegativeButton("", null)
                        .show();
                break;
            case R.id.shezhirela_back:
                SharedPreferencesUtils.putString(context, "user_id", "");
                SharedPreferencesUtils.putString(context, "user_nickname", "");
                SharedPreferencesUtils.putString(context, "token", "");
                SharedPreferencesUtils.putString(context, "user_name", "");
                SharedPreferencesUtils.putString(context, "user_pass", "");
                SharedPreferencesUtils.putString(context, "uid", "");
                SharedPreferencesUtils.putString(context, "backLogin", "2");
                toast("退出成功");
                break;
        }

    }

    private void doHttp2(final String token, final String uid, final View view1) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("token", token);
        new HttpHelper().get("/user/getUserInfo", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("<")) {
                    doHttp2(token, uid, view1);
                }
                SuccessBean successBean = new Gson().fromJson(data, SuccessBean.class);
                String code = successBean.getCode();
                if ("0".equals(code)) {
                    TextView uname = view1.findViewById(R.id.username);
                    TextView mobile = view1.findViewById(R.id.mobile);
                    TextView nick = view1.findViewById(R.id.nick);
                    String mobile1 = successBean.getData().getMobile();
                    String username = successBean.getData().getUsername();
                    String nickname = successBean.getData().getNickname();
                    mobile.setText(mobile1);
                    uname.setText(username);
                    nick.setText(nickname);
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void doHttp1(String user_name, String user_pass) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", user_name);
        map.put("password", user_pass);
        new HttpHelper().get("/user/login", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                SuccessBean successBean = new Gson().fromJson(data, SuccessBean.class);
                String code = successBean.getCode();
                if ("0".equals(code)) {
                    token = successBean.getData().getToken();
                    uid = successBean.getData().getUid();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    private void doHttp(final String nick, String token, final String uid) {
        //https://www.zhaoapi.cn/user/updateNickName
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("token", token);
        map.put("nickname", nick);
        new HttpHelper().get("/user/updateNickName", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                SuccessBean successBean = new Gson().fromJson(data, SuccessBean.class);
                String code = successBean.getCode();
                if ("0".equals(code)) {
                    toast(successBean.getMsg());
                    SharedPreferencesUtils.putString(context, "user_nickname", nick);
                } else {
                    toast(successBean.getMsg());
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.png");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    head = extras.getParcelable("data");
                    if (head != null) {

//                        imageView.setImageBitmap(head);
                        String fileName = path + "/head.png";//图片名字
                        setPicToView(head);//保存在SD卡中
                        File file1 = new File(fileName);
//                        uploadPic(file1);
                        uploadImage(fileName);
                    }
                }
                break;
            default:
                break;
        }
    }

    //Retrofit上传
    private void uploadPic(File file1) {
        RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "head.png", file);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.zhaoapi.cn/").build();
        retrofit.create(UploadService.class)
                .upload("21014", part).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Log.i("ResponseBody", responseBody.string());
                        String string = responseBody.string();
                        if (string.contains("文件上传成功")) {
                            SharedPreferencesUtils.putString(context, "upload", "7");
                            Toast.makeText(context, "修改头像成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 127);
        intent.putExtra("outputY", 127);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", false);//不启用人脸识别
        intent.putExtra("return-data", true);
        ((ShezhiActivity) context).startActivityForResult(intent, 3);
    }


    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/head.png";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //上传
    private void uploadImage(String path) {
        String uid = SharedPreferencesUtils.getString(context, "uid");
        MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");
        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(path);
        final MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(mediaType)
                .addFormDataPart("file", "head.png",
                        RequestBody.create(MediaType.parse("image/png"), file))
                .addFormDataPart("uid", uid);

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url("http://www.zhaoapi.cn/file/upload")
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                ((ShezhiActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG", e.getMessage());
                        Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ((ShezhiActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferencesUtils.putString(context, "upload", "7");
                        Toast.makeText(context, "修改头像成功", Toast.LENGTH_SHORT).show();
                        doHttp3();
                    }
                });
            }
        });
    }

    private void doHttp3() {
        String user_name = SharedPreferencesUtils.getString(context, "user_name");
        String user_pass = SharedPreferencesUtils.getString(context, "user_pass");
//        Toast.makeText(context, user_name + "" + user_pass, Toast.LENGTH_SHORT).show();
        Map<String, String> map = new HashMap<>();
        map.put("mobile", user_name);
        map.put("password", user_pass);
        new HttpHelper().get("/user/login", map).result(new HttpListener() {
            @Override
            public void success(String data) {
                SuccessBean successBean = new Gson().fromJson(data, SuccessBean.class);
                String icon = successBean.getData().getIcon();
                String replace = icon.replace("https", "http");
                SharedPreferencesUtils.putString(context, "icon", replace);
                simpleDraweeView.setImageURI(Uri.parse(replace));
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String replace = SharedPreferencesUtils.getString(context, "replace");
        String icon = SharedPreferencesUtils.getString(context, "icon");
//        Toast.makeText(context, replace + "" + icon, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(icon)) {
            simpleDraweeView.setImageURI(Uri.parse(icon));
        }
        if (!TextUtils.isEmpty(replace)) {
            simpleDraweeView.setImageURI(Uri.parse(replace));
        }
    }
}
