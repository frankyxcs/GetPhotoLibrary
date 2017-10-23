package com.example.getphotolibrary.addphoto;

/*
 * Created by 849501 on 10/20/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


public class GetPhotoBottomDialogFragment extends AddPhotoBottomDialogFragment {

    private OnBottomSheetButtonClickListener mButtonCLickListener;

    private OnImageReadyListener mImageReadyListener;

    private int mTargetImageWidth = 192;    // default 192 px

    private int mTargetImageHeight = 192;   // default 192 px

    private AddPhotoHelper mAddPhotoHelper;

    public static GetPhotoBottomDialogFragment newInstance(
            @Nullable OnBottomSheetButtonClickListener bottomSheetButtonClickListener,
            @Nullable OnImageReadyListener imageReadyListener,
            int targetImageWidth,
            int targetImageHeight) {

        GetPhotoBottomDialogFragment addPhotoBottomDialogFragment
                = new GetPhotoBottomDialogFragment();

        addPhotoBottomDialogFragment.mButtonCLickListener = bottomSheetButtonClickListener;
        addPhotoBottomDialogFragment.mImageReadyListener = imageReadyListener;
        addPhotoBottomDialogFragment.mTargetImageWidth = targetImageWidth;
        addPhotoBottomDialogFragment.mTargetImageHeight = targetImageHeight;

        addPhotoBottomDialogFragment.mAddPhotoHelper =  new AddPhotoHelper(
                addPhotoBottomDialogFragment,
                "123");

        return addPhotoBottomDialogFragment;
    }

    @Override
    protected void onAddPhotoBDFragmentButtonsClick(@AddPhotoBottomSheetButtons String button) {
        switch (button) {
            case AddPhotoBottomSheetButtons.USE_CAMERA:
                Log.d("BUGS","GetPhotoBottomDialogFragment Use Camera Button Clicked");
                if (mButtonCLickListener != null) {
                    mButtonCLickListener
                            .onBottomSheetButtonClick(AddPhotoBottomSheetButtons.USE_CAMERA);
                }
                mAddPhotoHelper.dispatchTakePictureIntent();
                break;
            case AddPhotoBottomSheetButtons.FROM_GALLERY:
                Log.d("BUGS", "GetPhotoBottomDialogFragment From Gallery Button Clicked");
                if (mButtonCLickListener != null) {
                    mButtonCLickListener
                            .onBottomSheetButtonClick(AddPhotoBottomSheetButtons.FROM_GALLERY);
                }
                Toast.makeText(getContext(), "Temp Message", Toast.LENGTH_SHORT).show();
                break;
            case AddPhotoBottomSheetButtons.REMOVE_PHOTO:
                Log.d("BUGS", "GetPhotoBottomDialogFragment Remove Photo Button Clicked");
                if (mButtonCLickListener != null) {
                    mButtonCLickListener
                            .onBottomSheetButtonClick(AddPhotoBottomSheetButtons.REMOVE_PHOTO);
                }
                break;
        }
    }

    public interface OnBottomSheetButtonClickListener {
        void onBottomSheetButtonClick(@AddPhotoBottomSheetButtons String button);
    }

    public interface OnImageReadyListener {
        void onImageReadyWithBitmap(Bitmap finalBitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("BUGS", "GetPhotoBottomDialogFragment - onActivityResult");
        if (mAddPhotoHelper != null) {
            Bitmap finalBitmap = mAddPhotoHelper
                    .getFinalBitmap(mAddPhotoHelper.getPhotoPath(),
                            mTargetImageWidth,
                            mTargetImageHeight);
            if (mImageReadyListener != null) {
                mImageReadyListener.onImageReadyWithBitmap(finalBitmap);
            }
        }
    }
}
