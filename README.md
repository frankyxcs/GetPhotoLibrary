# GetPhotoLibrary

A useful library to get the photo using device camera or from gallery. It provides both options in bottom sheet layout, user can select one of then to get the photo.

<br />

 ![](/get_photo_library_gif.gif)

<br />

 ## Configure

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven {
        url  "https://dl.bintray.com/pkosta/getphotolibrary"
      }
		}
	}


Add the dependency

	dependencies {
	        compile 'com.reusable.getphotolibrary:getphotolibrary:0.1.0'
	}
  
 
 
 ## Usage
```
 GetPhotoBottomDialogFragment getPhotoBottomDialogFragment = GetPhotoBottomDialogFragment.newInstance(
                        ProfileActivity.this,
                        ProfileActivity.this,
                        mBindingLayout.profileImage.getWidth(),
                        mBindingLayout.profileImage.getHeight(),
                        "temp_image",
                        mShowRemoveButton);
			
 getPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                        "add_photo_dialog_fragment");
```
the above code will show the bottom sheet dialog with two options to get the image...camera and gallery.

And you need to implement 'GetPhotoBottomDialogFragment.OnImageReadyListener' interface to get the callback when the images is ready:
```
 @Override
    public void onImageReadyWithBitmap(Bitmap finalBitmap) {
        mBindingLayout.profileImage.setImageBitmap(finalBitmap);
 }
```
Also, if you need to implement 'GetPhotoBottomDialogFragment.OnBottomSheetButtonClickListener' to get the callback when the individual button get clicked, 
```
 @Override
    public void onBottomSheetButtonClick(@AddPhotoBottomSheetButtons String button) {
        switch (button) {
            case AddPhotoBottomSheetButtons.USE_CAMERA:
                Log.d("BUGS", "Use Camera Button Clicked");
                break;
            case AddPhotoBottomSheetButtons.FROM_GALLERY:
                Log.d("BUGS", "From Gallery Button Clicked");
                break;
            case AddPhotoBottomSheetButtons.REMOVE_PHOTO:
                Log.d("BUGS", "Remove Photo Button Clicked");
                mShowRemoveButton = false;
                break;
        }
    }
```

License
=======
Copyright 2017 Palash Kosta

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
