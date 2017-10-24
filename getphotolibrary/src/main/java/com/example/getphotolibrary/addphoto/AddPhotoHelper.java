package com.example.getphotolibrary.addphoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.util.Size;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/*
 * Created by 849501 on 10/19/2017.
 */

public class AddPhotoHelper {

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final int PICK_IMAGE = 2;

    private Context mContext;

    private String mUserId;

    private DialogFragment mParentDialogFragment;

    private String mPhotoPath;

    public AddPhotoHelper(Context aContext, @NonNull String userId) {
        mContext = aContext;
        mUserId = userId;
    }

    public AddPhotoHelper(DialogFragment dFragment, @NonNull String userId) {
        mParentDialogFragment = dFragment;
        mUserId = userId;
    }

    @Nullable String dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mParentDialogFragment.getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mParentDialogFragment.getActivity(),
                        mParentDialogFragment.getContext().getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                mParentDialogFragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                // Save a file: path for use with ACTION_VIEW intents
                mPhotoPath = photoFile.getAbsolutePath();
                return mPhotoPath;
            }
        }
        return null;
    }

    void dispatchPickPictureIntent() {

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");
        //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        mParentDialogFragment.startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    private File createImageFile() throws IOException{
        String imageFileName = "JPEG_" + mUserId + "_";
        File storageDir = mParentDialogFragment.getActivity()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public Bitmap getFinalBitmap(String photoPath,
                                 int width,
                                 int height) {
        // Get the dimensions of the View
        // Width and Height are the image view size to be displayed.

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/width, photoH/height);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    public Bitmap getFinalBitmap(Bitmap original,
                                 int width,
                                 int height) {

        return Bitmap.createScaledBitmap(original, width, height, false);

    }

}
