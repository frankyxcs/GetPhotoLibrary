package com.example.getphotolibrary.addphoto;

/*
 * Created by 849501 on 10/20/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;


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

        addPhotoBottomDialogFragment.mAddPhotoHelper = new AddPhotoHelper(
                addPhotoBottomDialogFragment,
                "123");

        return addPhotoBottomDialogFragment;
    }

    @Override
    protected void onAddPhotoBDFragmentButtonsClick(@AddPhotoBottomSheetButtons String button) {
        switch (button) {
            case AddPhotoBottomSheetButtons.USE_CAMERA:
                Log.d("BUGS", "GetPhotoBottomDialogFragment Use Camera Button Clicked");
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
                mAddPhotoHelper.dispatchPickPictureIntent();
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
        if (AddPhotoHelper.REQUEST_IMAGE_CAPTURE == requestCode &&
                resultCode == Activity.RESULT_OK) {
            if (mAddPhotoHelper != null) {
                Bitmap finalBitmap = mAddPhotoHelper
                        .getFinalBitmap(mAddPhotoHelper.getPhotoPath(),
                                mTargetImageWidth,
                                mTargetImageHeight);
                if (mImageReadyListener != null) {
                    mImageReadyListener.onImageReadyWithBitmap(finalBitmap);
                }
            }
        } else if (AddPhotoHelper.PICK_IMAGE == requestCode &&
                resultCode == Activity.RESULT_OK) {
            if (mAddPhotoHelper != null) {

                Uri selectedImage = data.getData();

                if (selectedImage != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getContext().getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        Bitmap finalBitmap = mAddPhotoHelper
                                .getFinalBitmap(yourSelectedImage,
                                        mTargetImageWidth,
                                        mTargetImageHeight);

                        if (mImageReadyListener != null) {
                            mImageReadyListener.onImageReadyWithBitmap(finalBitmap);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
