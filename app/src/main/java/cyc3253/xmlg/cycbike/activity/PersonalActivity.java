package cyc3253.xmlg.cycbike.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import cyc3253.xmlg.cycbike.dialog.CustomDialog;
import cyc3253.xmlg.cycbike.dialog.InputDialog;
import cyc3253.xmlg.cycbike.R;
import cyc3253.xmlg.cycbike.bean.User;
import cyc3253.xmlg.cycbike.tools.MultiImageSelector;
import cyc3253.xmlg.cycbike.utils.CheckInputUtils;

import rx.functions.Action1;

public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_SELECT_IMAGE = 0;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_CAMERA = 3;

    private ViewGroup nickName, sex, identity, phoneNum, mail, account;
    private TextView accountName, accountNum, tvNickName, tvSex, tvIdentity, tvPhoneNum, tvMail, tvRigiDate;
    private ImageView headImg;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setStatusBar();
        initView();
        initData();
    }

    private void initView() {
        nickName = (ViewGroup) findViewById(R.id.person_nickName);
        nickName.setOnClickListener(this);
        sex = (ViewGroup) findViewById(R.id.person_sex);
        sex.setOnClickListener(this);
        identity = (ViewGroup) findViewById(R.id.person_identity);
        phoneNum = (ViewGroup) findViewById(R.id.person_phone);
        phoneNum.setOnClickListener(this);
        mail = (ViewGroup) findViewById(R.id.person_mail);
        mail.setOnClickListener(this);
        account = (ViewGroup) findViewById(R.id.accountLayout);

        tvNickName = (TextView) findViewById(R.id.person_nickName_value);
        tvSex = (TextView) findViewById(R.id.person_sex_value);
        tvIdentity = (TextView) findViewById(R.id.person_identity_value);
        tvPhoneNum = (TextView) findViewById(R.id.person_phone_value);
        tvMail = (TextView) findViewById(R.id.person_mail_value);
        tvRigiDate = (TextView) findViewById(R.id.person_regidate_value);
        accountName = (TextView) findViewById(R.id.user_name);
        accountNum = (TextView) findViewById(R.id.user_account);

        headImg = (ImageView) findViewById(R.id.user_photo);
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalActivity.this, "选择照片跳转", Toast.LENGTH_SHORT).show();
                select();
            }
        });
        user = BmobUser.getCurrentUser(User.class);
        if (user.getHeadImg() != null)
            Glide.with(this).load(user.getHeadImg().getFileUrl()).into(headImg);
    }

    private void initData() {
        accountNum.setText(user.getUsername());
        tvPhoneNum.setText(user.getUsername());
        if (user.getNickName() != null) {
            accountName.setText(user.getNickName());
            tvNickName.setText(user.getNickName());
        }
        if (user.getSex() != null) tvSex.setText(user.getSex());
        if (user.getEmail() != null) tvMail.setText(user.getEmail());
        tvRigiDate.setText(user.getCreatedAt());
    }

    public void select() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            MultiImageSelector.create().showCamera(true) // show camera or not. true by default
                    .count(9) // max select image size, 9 by default. used width #.multi()
                    .multi() // multi mode, default mode;
                    .start(this, REQUEST_CODE_SELECT_IMAGE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "您已经同意了读取外置存储器权限", Toast.LENGTH_SHORT).show();
                    MultiImageSelector.create().showCamera(true) // show camera or not. true by default
                            .count(9) // max select image size, 9 by default. used width #.multi()
                            .multi() // multi mode, default mode;
                            .start(this, REQUEST_CODE_SELECT_IMAGE);
                } else {
                    Toast.makeText(this, "您已经拒绝了读取外置存储器权限", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "您已经同意了写入外置存储器权限", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(this, "您已经拒绝了写入外置存储器权限", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "您已经同意了照相机权限", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(this, "您已经拒绝了照相机权限", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                final List<String> paths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                Glide.with(this).load(paths.get(0)).into(headImg);
                final BmobFile bmobFile = new BmobFile(new File(paths.get(0)));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.i("上传成功");
                            Toast.makeText(PersonalActivity.this, "上传头像成功", Toast.LENGTH_SHORT).show();
                            User user = BmobUser.getCurrentUser(User.class);
                            if (user == null) return;
                            user.setHeadImg(bmobFile);
                            user.updateObservable().subscribe(new Action1<Void>() {
                                @Override
                                public void call(Void aVoid) {
                                    Toast.makeText(PersonalActivity.this, "更新头像成功", Toast.LENGTH_SHORT).show();
                                    Logger.d("更新头像成功");
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Toast.makeText(PersonalActivity.this, "更新头像失败：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    Logger.e("更新头像失败：" + throwable.getMessage());
                                }
                            });
                        } else {
                            Toast.makeText(PersonalActivity.this, "上传头像失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Logger.e("上传失败：" + e.getMessage());
                        }
                    }
                });
                for (String s : paths) {
                    Log.e("path", s);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_nickName:
                new InputDialog(this, "昵称", R.mipmap.nickname, new InputDialog.OnCloseListener() {
                    @Override
                    public void onClose(String input) {
                        String checkResult = CheckInputUtils.checkNickName(input);
                        if (checkResult != null) inputAlert(checkResult);
                        else {
                            user.setNickName(input);
                            updateInfo();
                        }
                    }
                }).show();
                break;
            case R.id.person_sex:
                new CustomDialog(this, new CustomDialog.OnCloseListener() {
                    @Override
                    public void onClose(boolean confirm) {
                        if (confirm) user.setSex("女");
                        else user.setSex("男");
                        updateInfo();
                    }
                }) {
                    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.dialog_sex);
                        findViewById(R.id.submit).setOnClickListener(this);
                        findViewById(R.id.cancel).setOnClickListener(this);
                    }
                }.show();
                break;
            case R.id.person_mail:
                new InputDialog(this, "邮箱", R.mipmap.email, new InputDialog.OnCloseListener() {
                    @Override
                    public void onClose(String input) {
                        String checkResult = CheckInputUtils.checkEmailBox(input);
                        if (checkResult != null) inputAlert(checkResult);
                        else {
                            user.setEmail(input);
                            updateInfo();
                        }
                    }
                }).show();
                break;
        }
    }

    private void inputAlert(String checkResult) {
        new AlertDialog.Builder(this).setMessage(checkResult).create().show();
    }

    private void updateInfo() {
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(PersonalActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    initData();
                } else
                    Toast.makeText(PersonalActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}