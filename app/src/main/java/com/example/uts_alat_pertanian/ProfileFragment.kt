package com.example.uts_alat_pertanian

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import androidx.core.widget.addTextChangedListener
import androidx.core.content.edit

class ProfileFragment : Fragment() {

    private val prefs by lazy {
        requireContext().getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupView: View = view.findViewById(R.id.groupViewMode)
        val imgAvatar: ImageView = view.findViewById(R.id.imgAvatar)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)
        val btnClear: MaterialButton = view.findViewById(R.id.btnClear)

        val groupEdit: View = view.findViewById(R.id.groupEditMode)
        val imgPreview: ImageView = view.findViewById(R.id.imgPreview)
        val etName: TextInputEditText = view.findViewById(R.id.etName)
        val etEmail: TextInputEditText = view.findViewById(R.id.etEmail)
        val etAvatarUrl: TextInputEditText = view.findViewById(R.id.etAvatarUrl)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSave)
        val btnCancel: MaterialButton = view.findViewById(R.id.btnCancel)

        fun loadAvatarInto(view: ImageView, url: String?) {
            val src = if (url.isNullOrBlank()) R.mipmap.ic_launcher_round else url
            Glide.with(this)
                .load(src)
                .circleCrop()
                .into(view)
        }

        fun showViewMode() {
            val name = prefs.getString("name", "") ?: ""
            val email = prefs.getString("email", "") ?: ""
            val avatar = prefs.getString("avatar", "") ?: ""

            tvName.text = if (name.isBlank()) "Your Name" else name
            tvEmail.text = if (email.isBlank()) "you@mail.com" else email
            loadAvatarInto(imgAvatar, avatar)

            groupView.visibility = View.VISIBLE
            groupEdit.visibility = View.GONE
        }

        fun showEditMode(prefill: Boolean) {
            if (prefill) {
                etName.setText(prefs.getString("name", "") ?: "")
                etEmail.setText(prefs.getString("email", "") ?: "")
                etAvatarUrl.setText(prefs.getString("avatar", "") ?: "")
            } else {
                etName.setText("")
                etEmail.setText("")
                etAvatarUrl.setText("")
            }
            loadAvatarInto(imgPreview, etAvatarUrl.text?.toString())

            groupView.visibility = View.GONE
            groupEdit.visibility = View.VISIBLE
        }

        etAvatarUrl.addTextChangedListener {
            loadAvatarInto(imgPreview, it?.toString())
        }

        btnEdit.setOnClickListener { showEditMode(prefill = true) }

        btnClear.setOnClickListener {
            prefs.edit {clear() }
            showEditMode(prefill = false)
        }

        btnCancel.setOnClickListener { showViewMode() }

        btnSave.setOnClickListener {
            val name = etName.text?.toString()?.trim().orEmpty()
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val avatar = etAvatarUrl.text?.toString()?.trim().orEmpty()

            if (name.isBlank() || email.isBlank()) {
                if (name.isBlank()) etName.error = "Required"
                if (email.isBlank()) etEmail.error = "Required"
                return@setOnClickListener
            }

            prefs.edit {
                putString("name", name)
                putString("email", email)
                putString("avatar", avatar)
            }
            showViewMode()
        }

        val hasProfile = !TextUtils.isEmpty(prefs.getString("name", "")) ||
                !TextUtils.isEmpty(prefs.getString("email", ""))
        if (hasProfile) showViewMode() else showEditMode(prefill = true)
    }
}