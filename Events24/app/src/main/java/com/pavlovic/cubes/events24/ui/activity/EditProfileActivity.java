package com.pavlovic.cubes.events24.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.pavlovic.cubes.events24.data.DataContainer;
import com.pavlovic.cubes.events24.data.model.User;
import com.pavlovic.cubes.events24.ui.view.EventsMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.databinding.ActivityEditProfileBinding;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends BasicActivity{

    private final int PICK_IMAGE_GALLERY = 1111,PICK_IMAGE_CAMERA = 1112;
    private ActivityEditProfileBinding binding;
    private String documentId;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("id",userId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() == 1) {

                    User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                    documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                    updateUserUI(user);
                    DataContainer.user = user;
                }
            }
        });

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage == null){
                    uploadUserInfoToCloud();
                }
                else{
                    uploadImageToCloud();
                }


            }
        });

        binding.imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Pick image:");
                builder.setMessage("Please select image destination");
                builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //kreirati intent koji selektuje sliku iz galerije

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), PICK_IMAGE_GALLERY);
                    }
                });
                builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //kreirati inent koji kreira sliku i vraca url do te slike
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, PICK_IMAGE_CAMERA);
                    }
                });
                builder.setCancelable(true);
                builder.show();

            }
        });

    }

    private void uploadUserInfoToCloud() {
        User user = DataContainer.user;

        user.name = binding.editTextName.getText().toString();
        user.surname = binding.editTextSurname.getText().toString();
        user.phone = binding.editTextPhone.getText().toString();
        user.city = binding.editTextCity.getText().toString();
        user.address = binding.editTextAddress.getText().toString();

        FirebaseFirestore.getInstance().collection("users")
                .document(documentId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                EventsMessage.showMessage(getApplicationContext(),getString(R.string.text_success_update_profile));
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_GALLERY && resultCode==RESULT_OK){
            selectedImage = data.getData();
            Picasso.get().load(selectedImage).into(binding.imageViewProfile);

        }
        else if(requestCode == PICK_IMAGE_CAMERA && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImage =  getImageUri(getApplicationContext(),imageBitmap);
            Picasso.get().load(selectedImage).into(binding.imageViewProfile);
        }
    }

    private void uploadImageToCloud(){

        String profileFolder = FirebaseAuth.getInstance().getUid().toString();
        String imageName = System.currentTimeMillis()+".jpg";

        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images/profile/"+profileFolder+"/"+imageName);
        imageRef.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DataContainer.user.imageUrl = uri.toString();
                        uploadUserInfoToCloud();
                    }
                });
                EventsMessage.showMessage(getApplicationContext(), "Succes uploaded image");

            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                EventsMessage.showMessage(getApplicationContext(),"Error on upload message... "+e.getLocalizedMessage());
            }
        });

    }

    private void updateUserUI(User user) {
        binding.editTextName.setText(user.name);
        binding.editTextSurname.setText(user.surname);
        binding.editTextCity.setText(user.city);
        binding.editTextPhone.setText(user.phone);
        binding.editTextAddress.setText(user.address);
        if(user.imageUrl !=null && user.imageUrl.length()>0){
            Picasso.get().load(user.imageUrl).into(binding.imageViewProfile);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}