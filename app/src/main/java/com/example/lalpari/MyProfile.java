package com.example.lalpari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class MyProfile extends AppCompatActivity {
    TextView Firstname, Middlename, Lastname, Email, Mobile;
    ImageView profile_image;
    FloatingActionButton camera, camera_picker, gallery;
    private final int CAMERA_REQ_CODE = 101;
    public static final int PICK_IMAGE = 1;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Firstname = findViewById(R.id.tv_firstname);
        Middlename = findViewById(R.id.tv_midlename);
        Lastname = findViewById(R.id.tv_lastname);
        Email = findViewById(R.id.tv_email);
        Mobile = findViewById(R.id.tv_mobile);
        profile_image = findViewById(R.id.profile_image);
        camera = findViewById(R.id.camera);
        dialog = new Dialog(MyProfile.this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_bg));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        camera_picker = dialog.findViewById(R.id.camera_picker);
        gallery = dialog.findViewById(R.id.gallery);

        camera_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(icamera, CAMERA_REQ_CODE);
                dialog.dismiss();

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent igallery = new Intent();
                igallery.setAction(Intent.ACTION_PICK);
                igallery.setType("image/*");
                startActivityForResult(Intent.createChooser(igallery, "Select Picture"), PICK_IMAGE);
                dialog.dismiss();

            }
        });

        // Request for Camera Permission
        if (ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyProfile.this,
                    new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 101);
        }


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(icamera, CAMERA_REQ_CODE);
                dialog.show();
            }

        });


        SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReferenceFromUrl("https://swift-ride-22040-default-rtdb.firebaseio.com/user/" + preferences.getString("Mobile", "NA"));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG", "onDataChange: " + snapshot.toString());
                String fristname = snapshot.child("firstname").getValue().toString();
                Firstname.setText(fristname);
                String middlename = snapshot.child("middlename").getValue().toString();
                Middlename.setText(middlename);
                String lastname = snapshot.child("lastname").getValue().toString();
                Lastname.setText(lastname);
                String email = snapshot.child("email").getValue().toString();
                Email.setText(email);
                String mobile = snapshot.child("mobile_no").getValue().toString();
                Mobile.setText(mobile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQ_CODE) {
                // get capture image
                Bitmap img = (Bitmap) data.getExtras().get("data");
                // set capture image
                profile_image.setImageBitmap(img);

                // GET IMAGE FROM GALLERY

            }

            if (requestCode == PICK_IMAGE) {
                //TODO: action
                if (requestCode == 1 && resultCode == RESULT_OK) {
                    String result;
                    Uri selectedImage = data.getData();
//                        String[] filePathColumn = { MediaStore.Images.Media.DATA }
                    String path = getRealPathFromURI(selectedImage, getApplicationContext());
                    Bitmap myBitmap = BitmapFactory.decodeFile(path);
                    profile_image.setImageBitmap(myBitmap);
/*
                        Context context = null;
                        Cursor cursor = context.getContentResolver().query(selectedImage,
                                null, null, null, null);
                        if (cursor==null){
                          result = selectedImage.getPath();
                        }else{
                            cursor.moveToFirst();
                        }
                        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();

                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                        profile_image.setImageBitmap( bitmap);*/

                }

            }
        }
    }

    public String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
