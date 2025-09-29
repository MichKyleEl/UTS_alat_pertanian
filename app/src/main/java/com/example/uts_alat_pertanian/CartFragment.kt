package com.example.uts_alat_pertanian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_alat_pertanian.api.ApiClient
import com.example.uts_alat_pertanian.model.CartItem
import com.example.uts_alat_pertanian.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment() {

    private lateinit var cartContainer: LinearLayout
    private lateinit var checkoutBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        cartContainer = view.findViewById(R.id.cartContainer)
        checkoutBtn = view.findViewById(R.id.btnCheckout)

        // load dummy / API data
        loadCart()

        checkoutBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Checkout berhasil!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun loadCart() {
        cartContainer.removeAllViews()

        // contoh data dummy
        val products = listOf(
            Pair("Tractor", "Rp 75.000.000"),
            Pair("Sprayer Machine", "Rp 450.000")
        )

        for (p in products) {
            val tv = TextView(requireContext())
            tv.text = "${p.first} - ${p.second}"
            tv.textSize = 16f
            cartContainer.addView(tv)
        }
    }
}

