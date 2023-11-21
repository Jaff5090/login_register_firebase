package com.example.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.login_register_firebase.databinding.ActivityFragmentCheckEntretienBinding
import com.example.models.Client
import com.example.models.Entretien
import com.example.repository.EntretienRepository
import com.example.ui.adapters.ClientAdapter
import com.example.ui.viewmodels.ClientViewModel
import com.example.ui.viewmodels.EntretienViewModel
import com.example.ui.viewmodels.EntretienViewModelFactory
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FragmentCheckEntretien : DialogFragment() {

    var _binding: ActivityFragmentCheckEntretienBinding? = null
    private val binding get() = _binding!!
    private val CAMERA_REQUEST_CODE = 101
    private lateinit var entretienViewModel: EntretienViewModel
    private val photoUrls = mutableListOf<String>()
    private var capturedImage: Bitmap? = null
    val repository = EntretienRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityFragmentCheckEntretienBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = EntretienViewModelFactory(repository)

        entretienViewModel = ViewModelProvider(this, factory).get(EntretienViewModel::class.java)

        binding.addAvisPassage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE
                )
            } else {
                openCamera()
            }
        }

        binding.btnEnvoyer.setOnClickListener {
            val clientName = binding.ClientName.text.toString()
            val observations = binding.observation.text.toString()

            val entretien = Entretien(clientName, observations, photoUrls)
            entretienViewModel.sauvegarderEntretien(entretien) {
                dismiss()
            }
        }



        arguments?.getString("client_name")?.let { clientName ->
            binding.ClientName.text = "Client: $clientName"
        }

    }

    private fun openCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        resultLauncher.launch(intent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                capturedImage = data?.extras?.get("data") as Bitmap

                val imageName = generatePhotoName()
                addImageName(imageName)

                uploadImageToFirebaseStorage(capturedImage!!, imageName,
                    onSuccess = { imageUrl ->
                        photoUrls.add(imageUrl)
                    },
                    onFailure = { exception ->
                    }
                )
            }
        }

    private fun generatePhotoName(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return "JPEG_${dateFormat.format(Date())}_"
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
            }
        }
    }

    private fun uploadImageToFirebaseStorage(
        imageBitmap: Bitmap,
        imageName: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val storageRef = FirebaseStorage.getInstance().getReference("photos/$imageName")
        storageRef.putBytes(data)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    private fun addImageName(imageName: String) {
        val text = TextView(context)
        text.text = imageName
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        text.layoutParams = layoutParams
        binding.layoutImages.addView(text)
    }


}
