package com.example.salonspace;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MypageDesignerActivity extends Fragment {
    Button Select_profile;
    ImageView Image_profile;
    String[] permission_list = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Uri uri;
    String ID="";
    View v1;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v1 = (View) inflater.inflate(R.layout.activity_mypage_designer, container, false);
        Image_profile = v1.findViewById(R.id.profile_image);
        Select_profile = v1.findViewById(R.id.btn_select);

        //내부 DB오픈
        DBHelper helper =new DBHelper(getContext());
        SQLiteDatabase db=helper.getReadableDatabase();

        String LoadID="select ID from Login";
        Cursor c=db.rawQuery(LoadID,null);
        ID="";
        while(c.moveToNext()){
            // 가져올 컬럼의 인덱스 번호 추출
            int index=c.getColumnIndex("ID");
            ID=c.getString(index);
            Log.d("testID",""+ID);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        }
        Select_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 1);
            }
        });
        return v1;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //사진을 선택하고 왓을때만 처리
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1: {
                uri = data.getData();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 300);
                intent.putExtra("aspectX", 2);
                intent.putExtra("aspectY", 3);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);


                //Log.e("test22","aa");
                startActivityForResult(intent, 2);

                break;
            }
            case 2: {
                Log.e("test223", "bb2");
                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";

                Log.e("test223", "bb");
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    Bitmap test = photo.createScaledBitmap(photo, 720, 1024, true);
                    //Image_profile.setImageBitmap(test);

                    storeCropImage(photo, filePath);
                    Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
                    Image_profile.setImageBitmap(myBitmap);
                    //getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()))); // 갤러리를 갱신하기 위해..
                }
                // 임시 파일 삭제
                File f = new File(uri.getPath());
                if (f.exists()) {
                   // f.delete();
                }
                break;
            }

        }


    }
    private void storeCropImage(Bitmap bitmap, String filePath) {

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}