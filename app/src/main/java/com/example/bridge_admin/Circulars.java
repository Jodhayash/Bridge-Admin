package com.example.bridge_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bridge_admin.Model.cmsg;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Circulars extends AppCompatActivity{

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("msg");
    private FirebaseListAdapter<cmsg> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulars);
        displayChatMessages();
        Button fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input =findViewById(R.id.input);
                String msg = input.getText().toString().trim();
                Newmsg( msg,"admin");
                input.setText("");

            }
        });
    }
    private void Newmsg(String msg, String name) {
        cmsg Nmsg = new cmsg(msg,name);
        mDatabase.push().setValue(Nmsg);

    }
    private void displayChatMessages(){
        ListView listOfMessages = findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<cmsg>(this, cmsg.class,
                R.layout.msg, FirebaseDatabase.getInstance().getReference().child("msg")) {
            @Override
            protected void populateView(View v, cmsg model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }

}
