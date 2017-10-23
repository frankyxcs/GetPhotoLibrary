package getphotolibrary.sample.reusable.com.getphotolibraryexample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.getphotolibrary.addphoto.GetPhotoBottomDialogFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements GetPhotoBottomDialogFragment.OnImageReadyListener, GetPhotoBottomDialogFragment.OnBottomSheetButtonClickListener {

    private CircleImageView mCircleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mCircleImageView = (CircleImageView) findViewById(R.id.profile_image);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPhotoBottomDialogFragment getPhotoBottomDialogFragment
                        = GetPhotoBottomDialogFragment.newInstance(
                        HomeActivity.this,
                        HomeActivity.this,
                        mCircleImageView.getWidth(),
                        mCircleImageView.getHeight());
                getPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                        "add_photo_dialog_fragment");
            }
        });

    }

    @Override
    public void onImageReadyWithBitmap(Bitmap finalBitmap) {
        mCircleImageView.setImageBitmap(finalBitmap);
    }

    @Override
    public void onBottomSheetButtonClick(String button) {

    }
}
