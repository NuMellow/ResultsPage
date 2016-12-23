package com.nodeflux.numellow.resultspage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //recycler view and reference to database
    private RecyclerView blogList;
    private DatabaseReference myDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get database table. In this case the blog database
        myDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");




        blogList = (RecyclerView) findViewById(R.id.blog_list);
        blogList.setHasFixedSize(true);
        blogList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        //connects to database
        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(

                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                myDatabase.orderByChild("title").equalTo("Table Toppers")
        ) {
            //populates card view
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDescription());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.setChild(model.getChild());

            }
        };

        blogList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View myView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            myView = itemView;
        }

        //sets values to interface
        public  void setTitle(String title)
        {
            TextView post_title = (TextView) myView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc)
        {
            TextView post_desc = (TextView) myView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image)
        {
            ImageView post_image = (ImageView) myView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }

        public void setChild(Child child)
        {
            TextView number = (TextView) myView.findViewById(R.id.textNumbers);
            number.setText(child.getWord());

        }

    }

    //I'm not entirely sure what inflater does. The entire tutorial on retrieving from firebase didn't have sound.
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_add)
        {
            startActivity(new Intent(MainActivity.this, PostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
