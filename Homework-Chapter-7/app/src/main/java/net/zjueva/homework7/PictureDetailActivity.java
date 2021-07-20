package net.zjueva.homework7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureDetailActivity extends AppCompatActivity {

    String mockUrl="https://pic.raynor.top/images/2021/07/20/image-20210720131143433.png";
    String errorMockUrl="https://pic.raynor.top/images/2021/07/20/image-20210720131143433/1.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);


        ImageView imageView = findViewById(R.id.iv_detail);
        Button btnSuccess = findViewById(R.id.btn_load_success);
        Button btnFail = findViewById(R.id.btn_load_fail);
        Button btnRoundedCorners = findViewById(R.id.btn_rounded_corners);
        Button btnTransition=findViewById(R.id.btn_transition);

        btnSuccess.setOnClickListener( v -> {
            Glide.with(this).load(mockUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(imageView);
        });

        btnFail.setOnClickListener(v->{
            Glide.with(this).load(errorMockUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(imageView);
        });

        btnRoundedCorners.setOnClickListener(v->{
            Glide.with(this).load(mockUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                    .into(imageView);

        });

        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(600).setCrossFadeEnabled(true).build();

        btnTransition.setOnClickListener(v->{
            Glide.with(this).load(mockUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .transition(withCrossFade(drawableCrossFadeFactory))
                    .into(imageView);

        });
    }
}
