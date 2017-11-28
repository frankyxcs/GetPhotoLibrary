package com.example.getphotolibrary.addphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

/*
 * Created by 849501 on 10/19/2017.
 */

class AddPhotoHelper {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    static final int PICK_IMAGE = 2;

    private String mImageName;

    private DialogFragment mParentDialogFragment;

    private String mPhotoPath;

    AddPhotoHelper(DialogFragment dFragment, @NonNull String imageName) {
        mParentDialogFragment = dFragment;
        mImageName = imageName;
    }

    /**
     * method to dispatch the take picture intent to capture the photos using the camera.
     */
    void dispatchTakePictureIntent() {
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
            }
        }
    }

    /**
     * method to dispatch the pick picture intent to pick the photos from the gallery and other options.
     */
    void dispatchPickPictureIntent() {

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");

        mParentDialogFragment.startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    private File createImageFile() throws IOException{
        //String imageFileName = "JPEG_" + mImageName + "_";
        File storageDir = mParentDialogFragment.getActivity()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                mImageName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    // Getter Methods //

    /**
     * method to get the photo path currently stored.
     * @return path of the photo which is used to store the photo.
     */
    String getPhotoPath() {
        return mPhotoPath;
    }

    /**
     * method to get the resized bitmap to set to the image view based on the photo path and
     * size of image view.
     * @param photoPath path of the photo into which it is stored.
     * @param width width of the target image view
     * @param height height of the target image view
     * @return Bitmap -- final bitmap to set to the image view
     */
    Bitmap getFinalBitmap(String photoPath,
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

    /**
     * method to get the resized bitmap to set to the image view based on the original bitmap and
     * size of image view.
     * @param original -- original bitmap
     * @param desWidth width of the target image view
     * @param desHeight height of the target image view
     * @return Bitmap -- final bitmap to set to the image view
     */
    Bitmap getFinalBitmap(Bitmap original,
                          int desWidth,
                          int desHeight) {

        int width = original.getWidth();
        int height = original.getHeight();
        float scaleWidth = ((float) desWidth) / width;
        float scaleHeight = ((float) desHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                original, 0, 0, width, height, matrix, false);
        original.recycle();
        return resizedBitmap;

    }

}
