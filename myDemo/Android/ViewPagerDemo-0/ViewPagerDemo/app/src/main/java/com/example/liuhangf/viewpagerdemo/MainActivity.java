package com.example.liuhangf.viewpagerdemo;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.R.attr.bitmap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AddPhotoInterface {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private Button mainBtn;
    private static final int APPLY_PERMISSION = 3;
    private static final int CHOOSE_PHOTO = 2;
    private Bitmap bitmap;
    private List<Bitmap> listBit = new ArrayList<>();
    private ArrayList<String> listpath = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler);
        mainBtn = (Button) findViewById(R.id.activity_main_btn);
        adapter = new RecyclerAdapter(MainActivity.this);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,5);
        recyclerView.setLayoutManager(manager);
        mainBtn.setOnClickListener(this);
        adapter.setDatas(listpath);
        recyclerView.setAdapter(adapter);
        PhotoView photoView = (PhotoView) findViewById(R.id.activity_main_pv);
        photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_main_btn:

                break;
        }


    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
//        Bundle bundle = new Bundle();
//        bundle.putString("updown",up);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK) {

            String path = "";
            if (Build.VERSION.SDK_INT >= 19) {
                //4.4及以上系统使用这个方法处理图片
                path = handleImageOnKitKat(data);
            } else {
                //4.4以下使用这个方法处理图片
                path = handleImageBeforeKitKat(data);
            }
            listpath.add(path);
            adapter.notifyDataSetChanged();




        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitKat(Intent data) {
        String imgPath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imgPath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imgPath = getImagePath(contenturi, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，使用普通方法处理
            imgPath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是File类型的Uri，直接获取图片路径即可
            imgPath = uri.getPath();
        }
        return imgPath;
    }
    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }
    //通过Uri和selection来获取真实的图片路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void setAddPhotoInterface(View v) {




        //Android6.0开始，WRITE_EXTERNAL_STORAGE被认为是危险权限，需要动态申请
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    APPLY_PERMISSION);
        } else {
            openAlbum();
        }

    }
}
