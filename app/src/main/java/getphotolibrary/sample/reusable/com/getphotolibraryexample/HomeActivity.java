package getphotolibrary.sample.reusable.com.getphotolibraryexample;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.getphotolibrary.addphoto.AddPhotoBottomSheetButtons;
import com.example.getphotolibrary.addphoto.GetPhotoBottomDialogFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements GetPhotoBottomDialogFragment.OnImageReadyListener, GetPhotoBottomDialogFragment.OnBottomSheetButtonClickListener {

    private CircleImageView mCircleImageView;

    private boolean mShowRemoveButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mCircleImageView = (CircleImageView) findViewById(R.id.profile_image);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetPhotoBottomDialogFragment getPhotoBottomDialogFragment = GetPhotoBottomDialogFragment.newInstance(
                        HomeActivity.this,
                        HomeActivity.this,
                        mCircleImageView.getWidth(),
                        mCircleImageView.getHeight(),
                        "palash_image",
                        mShowRemoveButton);

                getPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                        "add_photo_dialog_fragment");
            }
        });

    }

    @Override
    public void onImageReadyWithBitmap(Bitmap finalBitmap) {
        mCircleImageView.setImageBitmap(finalBitmap);
        mShowRemoveButton = true;
    }


    @Override
    public void onBottomSheetButtonClick(@AddPhotoBottomSheetButtons String button) {
        if (TextUtils.equals(button, AddPhotoBottomSheetButtons.REMOVE_PHOTO)) {
            Log.d("TAG", "Remove Button Clicked");
            mCircleImageView.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_person_placeholder));
            // you can add the functionality on tapping of the remove button
            mShowRemoveButton = false;
        }
    }
}
