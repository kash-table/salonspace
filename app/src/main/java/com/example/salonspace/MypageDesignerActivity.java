package com.example.salonspace;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class MypageDesignerActivity extends Fragment {
    Button Select_profile;
    ImageView Image_profile;
    String[] permission_list = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Uri uri;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v1 = (View) inflater.inflate(R.layout.activity_mypage_designer, container, false);
        Image_profile = v1.findViewById(R.id.profile_image);
        Select_profile = v1.findViewById(R.id.btn_select);

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

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + System.currentTimeMillis() + ".jpg";

                Log.e("test223", "bb");
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    Bitmap test = photo.createScaledBitmap(photo, 720, 1024, true);
                    Image_profile.setImageBitmap(test);
                    // storeCropImage(photo, filePath);

                    // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()))); // 갤러리를 갱신하기 위해..
                }
                // 임시 파일 삭제
                File f = new File(uri.getPath());
                if (f.exists()) {
                    f.delete();
                }
                break;
            }
        }
    }
}