package com.skopisjiwa.presentation.outlet.add

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.skopisjiwa.data.store.StoreModel
import com.skopisjiwa.databinding.ActivityEditOutletBinding
import com.skopisjiwa.presentation.user.store.adapter.StoreAdapter
import com.skopisjiwa.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.UUID

@AndroidEntryPoint
class EditOutletActivity : AppCompatActivity() {

    //    check page start
    private var isEditPage: Boolean = false

    private var dataStoresModel: StoreModel? = null


    private val viewModel: AddEditStoreViewModel by viewModels()

    private var _binding: ActivityEditOutletBinding? = null
    private val binding get() = _binding!!

    //    variable edit stores
    private lateinit var storesModel: StoreModel

    //    variable add image
    private val PICK_IMAGE_REQUEST = 100
    lateinit var filePath: Uri
    private var storageRef: FirebaseStorage? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditOutletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isEditPage = intent.getBooleanExtra("EXTRA_EDIT", false)
        dataStoresModel = intent.getParcelableExtra("EXTRA_DATA")


        storageRef = FirebaseStorage.getInstance()

        binding.containerName2.edtField.setLines(4)

        if (isEditPage) {
            val adapter: StoreAdapter? = viewModel.storeAdapter.value
            observer()
            Picasso.get().load(dataStoresModel?.image).into(binding.ivOutlet)
            binding.containerName.edtField.setText(dataStoresModel?.titleStore)
            binding.containerName1.edtField.setText(dataStoresModel?.open)
            binding.containerName2.edtField.setText(dataStoresModel?.address)
            binding.llSimpan.setOnClickListener {
                    viewModel.updateStore(getUpdateObj(), dataStoresModel?.id.toString())

            }
        } else {
            binding.tvTitle.text = "Add Store"
            binding.btnEdit.text = "Add Store"
            binding.btnUploadPhoto.text = "Upload Photo"
            addStoreAndCheck()
        }

        Log.d("id", dataStoresModel?.id.toString())


        with(binding) {
            containerName.tvTitle.text = "Name Store"
            containerName1.tvTitle.text = "Jam Operasi"
            containerName2.tvTitle.text = "Alamat Store"
            btnUploadPhoto.setOnClickListener {
                openGallery()
            }
            ivBack.setOnClickListener {
                onBackPressed()
            }
//            getImageUrl()

        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun uploadImage(imageBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val progres = ProgressDialog(this)
        progres.setTitle("Uploading")
        val ref = storageRef?.reference?.child("stores")?.child("${UUID.randomUUID()}")
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val image = baos.toByteArray()
        ref?.putBytes(image)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result.let {
                            filePath = it
                            Toast.makeText(this, "image success upload", Toast.LENGTH_SHORT).show()
                            binding.ivOutlet.setImageBitmap(imageBitmap)
                        }
                    }
                }
            }?.addOnProgressListener {
                val totalKb: Long = it.totalByteCount / 1024
                val transferdeKb: Long = it.bytesTransferred / 1024
                progres.setMessage("$transferdeKb / $totalKb")
                if (transferdeKb == totalKb) {
                    progres.cancel()
                }
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                uploadImage(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //    add store
    private fun addStoreAndCheck() {
        binding.llSimpan.setOnClickListener {
            with(binding) {
                val titleStore = containerName.edtField.text?.trim().toString()
                val jamOpen = containerName1.edtField.text?.trim().toString()
                val description = containerName2.edtField.text?.trim().toString()
                Log.d("tes", titleStore)

                when {
                    titleStore.isEmpty() -> containerName.tvTitle.error = "field is required"
                    jamOpen.isEmpty() -> containerName1.tvTitle.error = "field is required"
                    description.isEmpty() -> containerName2.tvTitle.error = "field is required"
                    else ->
                        viewModel.addStore(filePath.toString(), titleStore, jamOpen, description) {
                            Toast.makeText(
                                this@EditOutletActivity,
                                "Success Add Store",
                                Toast.LENGTH_SHORT
                            ).show()
                            onBackPressed()
                        }
                }
            }
        }
    }


    //    edit store
    private fun getUpdateObj(): StoreModel {

        val titleStore = binding.containerName.edtField.text.trim().toString()
        val open = binding.containerName1.edtField.text.trim().toString()
        val address = binding.containerName2.edtField.text.trim().toString()

        val imageUrl = if (::filePath.isInitialized && filePath.toString().isNotEmpty()) {
            filePath.toString() // Use the new photo URL if filePath is initialized and not empty
        } else {
            dataStoresModel?.image ?: "" // Use the previous photo URL if no new photo is uploaded
        }

        storesModel = StoreModel(
            id = UUID.randomUUID().toString(),
            titleStore = titleStore,
            image = imageUrl,
            open = open,
            address = address
        )
        return storesModel
    }

    private fun observer() {
        viewModel.editStore.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Waiting..", Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    onBackPressed()
                }

                is Resource.Error -> {
                    Toast.makeText(this, "${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}