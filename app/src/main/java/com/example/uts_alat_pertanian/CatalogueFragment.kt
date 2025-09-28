package com.example.uts_alat_pertanian

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatalogueFragment : Fragment() {

    private lateinit var rvAlatTangan: RecyclerView
    private lateinit var rvAlatBerat: RecyclerView
    private lateinit var rvPupuk: RecyclerView
    private lateinit var rvBenih: RecyclerView

    private lateinit var rvSearchResults: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var contentContainer: View

    private lateinit var allProducts: List<Product>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_catalogue, container, false)

        root.findViewById<ImageView>(R.id.btnMenu)?.setOnClickListener {
            (requireActivity() as MainActivity).openDrawer()
        }

        // ====== Setup kategori ======
        rvAlatTangan = root.findViewById(R.id.rvAlatTangan)
        rvAlatTangan.layoutManager = GridLayoutManager(requireContext(), 2)
        val alatTangan = listOf(
            Product("Steel Hoe", "Rp 85.000", R.drawable.cangkul, "Strong and durable hoe"),
            Product("Planting Tool", "Rp 200.000", R.drawable.alat_tanam, "Modern planting tool")
        )
        rvAlatTangan.adapter = CatalogueAdapter(alatTangan) { navigateToDetail(it) }

        rvAlatBerat = root.findViewById(R.id.rvAlatBerat)
        rvAlatBerat.layoutManager = GridLayoutManager(requireContext(), 2)
        val alatBerat = listOf(
            Product("Tractor", "Rp 75.000.000", R.drawable.tractor, "Modern agricultural tractor"),
            Product("Excavator", "Rp 50.000.000", R.drawable.excavator, "Excavator for land work")
        )
        rvAlatBerat.adapter = CatalogueAdapter(alatBerat) { navigateToDetail(it) }

        rvPupuk = root.findViewById(R.id.rvPupuk)
        rvPupuk.layoutManager = GridLayoutManager(requireContext(), 2)
        val pupuk = listOf(
            Product("Organic Fertilizer", "Rp 150.000", R.drawable.pupuk, "High quality organic fertilizer"),
            Product("Sprayer Machine", "Rp 450.000", R.drawable.mesin_semprot, "Agricultural sprayer machine")
        )
        rvPupuk.adapter = CatalogueAdapter(pupuk) { navigateToDetail(it) }

        rvBenih = root.findViewById(R.id.rvBenih)
        rvBenih.layoutManager = GridLayoutManager(requireContext(), 2)
        val benih = listOf(
            Product("Corn Seeds", "Rp 35.000", R.drawable.bibit_jagung, "Premium quality corn seeds"),
            Product("Rice Seeds", "Rp 50.000", R.drawable.benih_padi, "Premium rice seeds")
        )
        rvBenih.adapter = CatalogueAdapter(benih) { navigateToDetail(it) }

        // semua produk untuk search
        allProducts = alatTangan + alatBerat + pupuk + benih

        // ====== Search UI ======
        etSearch = root.findViewById(R.id.etSearch)
        rvSearchResults = root.findViewById(R.id.rvSearchResults)
        contentContainer = root.findViewById(R.id.contentContainer)

        rvSearchResults.layoutManager = GridLayoutManager(requireContext(), 2)
        rvSearchResults.adapter = CatalogueAdapter(allProducts) { navigateToDetail(it) }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterProducts(s?.toString().orEmpty())
            }
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scrollView = view as? ScrollView
        fun scrollTo(target: View) { scrollView?.post { scrollView.smoothScrollTo(0, target.top) } }

        when (arguments?.getString("CATEGORY_KEY")) {
            "Hand Tools" -> scrollTo(rvAlatTangan)
            "Heavy Equipment" -> scrollTo(rvAlatBerat)
            "Fertilizer" -> scrollTo(rvPupuk)
            "Seeds" -> scrollTo(rvBenih)
        }

        // Query dari Home
        val q = arguments?.getString("SEARCH_QUERY").orEmpty()
        if (q.isNotBlank()) {
            etSearch.setText(q)
            etSearch.setSelection(q.length)
        }
    }

    private fun filterProducts(query: String) {
        if (query.isBlank()) {
            rvSearchResults.isGone = true
            contentContainer.isVisible = true
            return
        }
        val lowered = query.lowercase()
        val filtered = allProducts.filter {
            it.name.lowercase().contains(lowered) || it.description.lowercase().contains(lowered)
        }
        rvSearchResults.isVisible = true
        contentContainer.isGone = true
        rvSearchResults.adapter = CatalogueAdapter(filtered) { navigateToDetail(it) }
    }

    private fun navigateToDetail(p: Product) {
        val b = Bundle().apply {
            putString(DetailFragment.PRODUCT_NAME, p.name)
            putString(DetailFragment.PRODUCT_PRICE, p.price)
            putInt(DetailFragment.PRODUCT_IMAGE, p.imageRes)
            putString(DetailFragment.PRODUCT_DESC, p.description)
        }
        findNavController().navigate(R.id.detailFragment, b)
    }
}
