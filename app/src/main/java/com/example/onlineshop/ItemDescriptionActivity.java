package com.example.onlineshop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description);

        getIncomingIntent();
    }

    private void getIncomingIntent()
    {
        if(getIntent().hasExtra("imageView") && getIntent().hasExtra("textView")
                && getIntent().hasExtra("textView1") && getIntent().hasExtra("textView2"))
        {
            int imageUrl = getIntent().getIntExtra("imageView", R.mipmap.ic_launcher);
            String name = getIntent().getStringExtra("textView");
            String price = getIntent().getStringExtra("textView1");
            String year = getIntent().getStringExtra("textView2");

            setImage(imageUrl, name, price, year);
        }
    }

    private void setImage(int imageUrl, String name, String price, String year)
    {
        ImageView img = findViewById(R.id.imageView);
        img.setImageResource(imageUrl);

        TextView textView = findViewById(R.id.textView);
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);

        textView.setText(name);
        textView1.setText(year);
        textView2.setText(price);
    }
}
