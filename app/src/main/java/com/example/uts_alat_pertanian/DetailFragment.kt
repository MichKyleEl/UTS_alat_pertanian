package com.example.uts_alat_pertanian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class DetailFragment : Fragment() {

    companion object {
        const val PRODUCT_NAME = "PRODUCT_NAME"
        const val PRODUCT_PRICE = "PRODUCT_PRICE"
        const val PRODUCT_IMAGE = "PRODUCT_IMAGE"
        const val PRODUCT_DESC = "PRODUCT_DESC"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productImage = view.findViewById<ImageView>(R.id.detailImage)
        val productTitle = view.findViewById<TextView>(R.id.detailTitle)
        val productPrice = view.findViewById<TextView>(R.id.detailPrice)
        val productDesc = view.findViewById<TextView>(R.id.detailDesc)
        val btnOrder = view.findViewById<Button>(R.id.btnOrder)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)

        val name = arguments?.getString(PRODUCT_NAME)
        val price = arguments?.getString(PRODUCT_PRICE)
        val image = arguments?.getInt(PRODUCT_IMAGE) ?: R.drawable.tractor
        val desc = arguments?.getString(PRODUCT_DESC)

        productImage.setImageResource(image)
        productTitle.text = name
        productPrice.text = price
        productDesc.text = desc

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnOrder.setOnClickListener {
            // TODO: navigasi ke Cart atau Order
        }
    }
}
