package com.example.uts_alat_pertanian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.uts_alat_pertanian.api.RetrofitClient
import kotlinx.coroutines.launch



class HomeFragment : Fragment() {

    private lateinit var rvRecommendation: RecyclerView
    private lateinit var rvTerbaru: RecyclerView
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var terbaruAdapter: RecommendationAdapter

    private lateinit var weatherText: TextView
    private val apiKey = "YOUR_API_KEY"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<ImageView>(R.id.btnMenu)?.setOnClickListener {
            (requireActivity() as MainActivity).openDrawer()
        }

        // 🔎 Search -> navigate ke Catalogue dengan SEARCH_QUERY
        val etHomeSearch = view.findViewById<EditText>(R.id.etHomeSearch)
        etHomeSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val q = etHomeSearch.text?.toString()?.trim().orEmpty()
                val bundle = Bundle().apply { putString("SEARCH_QUERY", q) }
                findNavController().navigate(R.id.catalogueFragment, bundle)
                true
            } else false
        }

        // Rekomendasi
        rvRecommendation = view.findViewById(R.id.rvRecommendation)
        rvRecommendation.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val recommendedProducts = listOf(
            Product("Excavator", "Rp 50.000.000", R.drawable.excavator, "Excavator for land work"),
            Product("Tractor", "Rp 75.000.000", R.drawable.tractor, "Modern agricultural tractor")
        )
        recommendationAdapter = RecommendationAdapter(recommendedProducts) { navigateToDetail(it) }
        rvRecommendation.adapter = recommendationAdapter

        // Terbaru
        rvTerbaru = view.findViewById(R.id.rvTerbaru)
        rvTerbaru.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val terbaruProducts = listOf(
            Product("Steel Hoe", "Rp 85.000", R.drawable.cangkul, "Strong & durable"),
            Product("Sprayer Machine", "Rp 450.000", R.drawable.mesin_semprot, "Practical for land work"),
            Product("Corn Seeds", "Rp 35.000", R.drawable.bibit_jagung, "Premium quality")
        )
        terbaruAdapter = RecommendationAdapter(terbaruProducts) { navigateToDetail(it) }
        rvTerbaru.adapter = terbaruAdapter

        setupCategoryClicks(view)
        weatherText = view.findViewById(R.id.weather_info)
        loadWeather("Tangerang")

        return view
    }

    private fun setupCategoryClicks(view: View) {
        val catHandTool = view.findViewById<TextView>(R.id.catHandTool)
        val catHeavyTool = view.findViewById<TextView>(R.id.catHeavyTool)
        val catFertilizer = view.findViewById<TextView>(R.id.catFertilizer)
        val catSeed = view.findViewById<TextView>(R.id.catSeed)

        catHandTool.setOnClickListener { openCatalogue("Hand Tools") }
        catHeavyTool.setOnClickListener { openCatalogue("Heavy Equipment") }
        catFertilizer.setOnClickListener { openCatalogue("Fertilizer") }
        catSeed.setOnClickListener { openCatalogue("Seeds") }
    }

    private fun openCatalogue(category: String) {
        val bundle = Bundle().apply { putString("CATEGORY_KEY", category) }
        findNavController().navigate(R.id.catalogueFragment, bundle)
    }

    private fun navigateToDetail(p: Product) {
        val bundle = Bundle().apply {
            putString(DetailFragment.PRODUCT_NAME, p.name)
            putString(DetailFragment.PRODUCT_PRICE, p.price)
            putInt(DetailFragment.PRODUCT_IMAGE, p.imageRes)
            putString(DetailFragment.PRODUCT_DESC, p.description)
        }
        findNavController().navigate(R.id.detailFragment, bundle)
    }

    private fun loadWeather(city: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.weatherService.getWeather("78ac852c9f43470c84995510252809", city)
                val info = "${response.location.name}: ${response.current.temp_c}°C, ${response.current.condition.text}"
                weatherText.text = info
            } catch (e: Exception) {
                weatherText.text = "Failed to load weather"
            }
        }
    }

}
