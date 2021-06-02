package com.example.rideshareandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mSearchField = findViewById(R.id.search_field);
        mSearchBtn = findViewById(R.id.search_btn);

        mResultList = findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);
            }
        });


    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(SearchActivity.this, "Searching...",Toast.LENGTH_SHORT).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("status").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                Users.class,
                R.layout.users_single_layout,
                UsersViewHolder.class,
                firebaseSearchQuery


        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {

                viewHolder.setDetails(getApplicationContext(),model.getName(), model.getStatus(), model.getThumb_image());
            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName, String userStatus, String thunb_Image){
            TextView user_name = mView.findViewById(R.id.user_single_name);
            TextView user_status = mView.findViewById(R.id.user_single_status);
            CircleImageView thumb_image = mView.findViewById(R.id.user_single_image);


            user_name.setText(userName);
            user_status.setText(userStatus);
            // ahi drawable user add karvo
            Picasso.get().load(thunb_Image).placeholder(R.drawable.crop_image_menu_flip).into(thumb_image);

        }

    }
}