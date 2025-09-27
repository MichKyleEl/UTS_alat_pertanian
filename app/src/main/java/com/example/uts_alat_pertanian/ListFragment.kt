package com.example.uts_alat_pertanian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private lateinit var rvRecommendation: RecyclerView
    private lateinit var adapter: CatalogueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Setup RecyclerView Rekomendasi
        rvRecommendation = view.findViewById(R.id.rvRecommendation)
        rvRecommendation.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val recommendedProducts = listOf(
            Product("Tractor", "Rp 75.000.000", R.drawable.tractor, "Modern agricultural tractor"),
            Product("Steel Hoe", "Rp 85.000", R.drawable.cangkul, "Strong and durable hoe"),
            Product("Sprayer Machine", "Rp 450.000", R.drawable.mesin_semprot, "Agricultural sprayer machine")
        )

        adapter = CatalogueAdapter(recommendedProducts) { product ->
            navigateToDetail(product)
        }
        rvRecommendation.adapter = adapter

        return view
    }

    private fun navigateToDetail(product: Product) {
        val bundle = Bundle().apply {
            putString(DetailFragment.PRODUCT_NAME, product.name)
            putString(DetailFragment.PRODUCT_PRICE, product.price)
            putInt(DetailFragment.PRODUCT_IMAGE, product.imageRes)
            putString(DetailFragment.PRODUCT_DESC, product.description)
        }

        findNavController().navigate(R.id.detailFragment, bundle)
    }
}
