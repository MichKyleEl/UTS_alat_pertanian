package com.example.uts_alat_pertanian

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private val productTitle: TextView?
        get() = view?.findViewById(R.id.product_title)
    private val productPrice: TextView?
        get() = view?.findViewById(R.id.product_price)
    private val productDesc: TextView?
        get() = view?.findViewById(R.id.product_desc)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coffeeId = arguments?.getInt(PRODUCT_ID, 0) ?: 0
        setCoffeeData(coffeeId)
    }
    fun setCoffeeData(id: Int){
        when(id){
            R.id.tractor_title -> {
                productTitle?.text = getString(R.string.tractor_title)
                productPrice?.text = getString(R.string.tractor_price)
                productDesc?.text = getString(R.string.tractor_price)
            }
            R.id.excavator_title -> {
                productTitle?.text = getString(R.string.excavator_title)
                productPrice?.text = getString(R.string.excavator_price)
                productDesc?.text = getString(R.string.excavator_price)
            }
        }
    }
    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"
    }
}