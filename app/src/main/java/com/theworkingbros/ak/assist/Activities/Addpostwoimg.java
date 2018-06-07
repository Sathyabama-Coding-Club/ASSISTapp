package com.theworkingbros.ak.assist.Activities;
/*
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theworkingbros.ak.assist.Model.Blog;
import com.theworkingbros.ak.assist.R;

import java.util.HashMap;
import java.util.Map;

public class Addpostwoimg extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpostwoimg);
    }

}
package com.theworkingbros.ak.assist.Activities;
*/
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.theworkingbros.ak.assist.Model.Blog;
        import com.theworkingbros.ak.assist.R;

        import java.util.HashMap;
        import java.util.Map;

public class Addpostwoimg extends AppCompatActivity {
    public String user,uid;

    TextView username;
    private EditText posttitle, postdesc;
    private Button submitbtn,addimg;
    private DatabaseReference mPostDatabase,userref;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpostwoimg);
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("AssistBlog");
        userref= FirebaseDatabase.getInstance().getReference().child("AssistUsers");
        userref.keepSynced(true);
        posttitle = findViewById(R.id.post_titlelistt);
        postdesc = findViewById(R.id.post_desclistt);
        submitbtn = findViewById(R.id.submitt);
        username=findViewById(R.id.usernameid);
        addimg=findViewById(R.id.addimagebtn);
        uid= mUser.getUid();
        final Blog blog=new  Blog();

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pastimg=new Intent(Addpostwoimg.this,Addpost.class);
                startActivity(pastimg);
            }
        });

        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user= dataSnapshot.child(uid).child("name").getValue(String.class);
                username.setText(user);
                blog.setUsername(user);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }



    /////////////////////////////////////////////////////////////





    //////////////////////////////////////////////////////

    private void startPosting() {
        mProgress.setMessage("Posting..");
        mProgress.show();
        final String titleVal = posttitle.getText().toString().trim();
        final String desVal = postdesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(desVal)) {
               /* StorageReference filepath= mStorage.child("Assist_Images").child(ImageUri.getLastPathSegment());
                filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadurl= taskSnapshot.getDownloadUrl();
                 */
                        DatabaseReference newPost= mPostDatabase.push();
                        Map<String,String> dataToSave= new HashMap<>();
                        dataToSave.put("title",titleVal);
                        dataToSave.put("desc",desVal);
                        dataToSave.put("timestamp",String.valueOf(java.lang.System.currentTimeMillis()));
                        dataToSave.put("userid",mUser.getUid());
                        dataToSave.put("username",user);
                        newPost.setValue(dataToSave);
                        mProgress.dismiss();
                        startActivity(new Intent(Addpostwoimg.this, MainActivity.class));
                        finish();


                }
            }
        }






