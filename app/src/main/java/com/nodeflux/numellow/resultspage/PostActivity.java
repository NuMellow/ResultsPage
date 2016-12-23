package com.nodeflux.numellow.resultspage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private ImageButton imageSelect;
    private EditText editTitle;
    private EditText editDesc;
    private Button submitButton;
    private Uri imageUri = null;
    private static final int GALLERY_REQUEST =1;
    private StorageReference storage;
    private ProgressDialog progress;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        storage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("Blog");

        //assign button to image select variable
        imageSelect = (ImageButton) findViewById(R.id.buttonImageSelect);

        editTitle = (EditText) findViewById(R.id.textEditTitle);
        editDesc = (EditText) findViewById(R.id.textEditDesc);
        submitButton = (Button) findViewById(R.id.buttonSubmit);

        progress = new ProgressDialog(this);

        //create an on click listener
        imageSelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    private void startPosting()
    {
        progress.setMessage("Posting to Blog...");


        final String title_val = editTitle.getText().toString().trim();
        final String desc_val = editDesc.getText().toString().trim();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && imageUri != null)
        {
            progress.show();

            StorageReference filepath = storage.child("Blog_Images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = database.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("description").setValue(desc_val);
                    newPost.child("image").setValue(downloadUrl.toString());

                    progress.dismiss();

                    startActivity(new Intent(PostActivity.this, MainActivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            imageSelect.setImageURI(imageUri);
        }
    }
}
