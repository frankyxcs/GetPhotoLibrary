# GetPhotoLibrary

A useful library to get the photo using device camera or from gallery. It provides both options in bottom sheet layout, user can select one of then to get the photo.

<br />

 ![](/get_photo_library_gif.gif)

<br />
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
  
 
