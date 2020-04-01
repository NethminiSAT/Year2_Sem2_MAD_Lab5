package com.example.broadcastproj;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnStart;
    TextView msg;

    public static final String BROADCAST_ACTION = "This is a message";

    Receiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        msg = findViewById(R.id.textView);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundServices.startAction(getApplicationContext());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        localReceiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        this.registerReceiver(localReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(localReceiver);
    }


    public class Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String currentText = msg.getText().toString();
            String message = intent.getStringExtra("value");
            currentText += "\nReceived "+ message;
            msg.setText(currentText);
        }
    }
}
