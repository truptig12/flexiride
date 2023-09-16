package com.herts.flexiride.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herts.flexiride.R
import com.herts.flexiride.presentation.adapter.AddPhotosAdapter
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.CarViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


class AddPhotosActivity : AppCompatActivity() {

    private lateinit var vm: CarViewModel
    var mCarAdapter: AddPhotosAdapter? = null
    var mLists: ArrayList<String> = ArrayList()
    var mFileList: ArrayList<File> = ArrayList()
    var PERMISSION_REQUEST_CODE = 1240
    val REQUEST_UPLOAD_FRONT = 102
    private val IMAGE_CAPTURE_CODE = 1001
    private val frontPath = java.util.ArrayList<String>(1)
    var part: ArrayList<MultipartBody.Part>? = ArrayList()
    var carId: Int = 0
    var city: String = ""
    private val MEDIA_TYPE_PNG: MediaType = "image/png".toMediaTypeOrNull()!!

    companion object {
        var CAR_ID: String = "CAR_ID"
        var CITY: String = "CITY"
        fun getCallingIntent(context: Context, id: Int, city: String): Intent {
            val intent = Intent(context, AddPhotosActivity::class.java)
            intent.putExtra(AddAvailabilityActivity.CAR_ID, id)
            intent.putExtra(AddAvailabilityActivity.CITY, city)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photos)
        vm = ViewModelProvider(this)[CarViewModel::class.java]

        val bundle = intent.extras
        if (bundle != null) {
            carId = bundle.getInt(AddAvailabilityActivity.CAR_ID, 0)
            city = bundle.getString(AddAvailabilityActivity.CITY, "")
        }

        val btn_photos = findViewById<Button>(R.id.btn_next_photos)
        mLists.clear()
        mFileList.clear()
        initAdapter()
        btn_photos.setOnClickListener {
            if (mLists.size > 1) {
                uploadFiles()
            }
//            Navigator.navigateToAddPackageActivity(this, it.id)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_DENIED
            ) {
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, 112)
            }
        }
        val add_photo_lay = findViewById<LinearLayout>(R.id.add_photo_lay)
        add_photo_lay.setOnClickListener {
            if (mLists.size < 4) {
                selectImage()
            } else {
                showToast("Only 4 photos can be added")
            }
        }
    }

    private fun uploadFiles() {
//        part?.clear()
//        for (item in mFileList) {
//            var fileReqBody: RequestBody? = null
//            fileReqBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), item)
//            part!!.add(MultipartBody.Part.createFormData("files", item.name, fileReqBody))
//        }


        val fileReqBody1: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mFileList.get(0)!!)
        val body1: MultipartBody.Part =
            MultipartBody.Part.createFormData("image1", mFileList.get(0)!!.name, fileReqBody1)


        val fileReqBody2: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mFileList.get(1)!!)
        val body2: MultipartBody.Part =
            MultipartBody.Part.createFormData("image2", mFileList.get(1)!!.name, fileReqBody2)

        val fileReqBody3: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mFileList.get(2)!!)
        val body3: MultipartBody.Part =
            MultipartBody.Part.createFormData("image3", mFileList.get(2)!!.name, fileReqBody3)

        val fileReqBody4: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mFileList.get(3)!!)
        val body4: MultipartBody.Part =
            MultipartBody.Part.createFormData("image4", mFileList.get(3)!!.name, fileReqBody4)

        /* val requestBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
             .addFormDataPart("image1", "image1", RequestBody.create(MEDIA_TYPE_PNG, mFileList.get(1)))
             .build()*/
        setRequestData(body1, body2, body3, body4)
    }

    private fun setRequestData(
        body1: MultipartBody.Part,
        body2: MultipartBody.Part,
        body3: MultipartBody.Part,
        body4: MultipartBody.Part
    ) {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")
        vm.addCarPhotos(token.toString(), carId, body1, body2, body3, body4)
        vm.createPhotosLiveData?.observe(this, Observer {
            if (it.id != null) {
                Navigator.navigateToAddAvailabilityActivity(this, carId, city)
            } else {
                showToast("Cannot add photos at the moment")
            }

        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initAdapter() {
        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        val add_photos_rv = findViewById<RecyclerView>(R.id.add_photos_rv)
        add_photos_rv.layoutManager = manager
        mCarAdapter =
            let {
                AddPhotosAdapter(
                    this,
                    mLists,
                    object : AddPhotosAdapter.OnCountClickListener {
                        override fun onCountClicked(count: Int?, item: String) {
                            super.onCountClicked(count, item)
                            var file = File(item)
                            mFileList.remove(file)
                            if (mFileList.size < 1) {
                                add_photos_rv.visibility = View.GONE

                            }

                        }

                    })
            }
        add_photos_rv.adapter = mCarAdapter
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Camera", "Photo", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Camera" -> {
                    dialog.dismiss()
                    selectCamera(IMAGE_CAPTURE_CODE)
                }

                options[item] == "Photo" -> {
                    dialog.dismiss()
                    openFile(REQUEST_UPLOAD_FRONT)
                }

                options[item] == "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun openFile(requestUploadFront: Int) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryActivityResultLauncher.launch(galleryIntent)
    }

    var galleryActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val image_uri = result.data!!.data
//            imageView.setImageURI(image_uri)
            val finalFile = File(getRealPathFromURI(image_uri))
            mLists.add(finalFile.absolutePath)
            mFileList.add(finalFile)
            mCarAdapter?.notifyDataSetChanged()
        }
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor: Cursor? = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor?.moveToFirst()
        val idx: Int? = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return idx?.let { cursor?.getString(it) }
    }

    private fun selectCamera(imageCaptureCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permission, 121)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
        true
    }

    var image_uri: Uri? = null
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        cameraActivityResultLauncher.launch(cameraIntent)
    }

    var cameraActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode === RESULT_OK) {
                val inputImage: Bitmap? = uriToBitmap(image_uri!!)
//                val rotated: Bitmap? = rotateBitmap(inputImage!!)
                val finalFile = File(getRealPathFromURI(image_uri))
                mLists.add(finalFile.absolutePath)
                mFileList.add(finalFile)
                mCarAdapter?.notifyDataSetChanged()
//                imageView.setImageBitmap(rotated)
            }
        })

    //TODO takes URI of the image and returns bitmap
    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @SuppressLint("Range")
    fun rotateBitmap(input: Bitmap): Bitmap? {
        val orientationColumn =
            arrayOf(MediaStore.Images.Media.ORIENTATION)
        val cur =
            contentResolver.query(image_uri!!, orientationColumn, null, null, null)
        var orientation = -1
        if (cur != null && cur.moveToFirst()) {
            orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]))
        }
        Log.d("tryOrientation", orientation.toString() + "")
        val rotationMatrix = Matrix()
        rotationMatrix.setRotate(orientation.toFloat())
        return Bitmap.createBitmap(input, 0, 0, input.width, input.height, rotationMatrix, true)
    }

}