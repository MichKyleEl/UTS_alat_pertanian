package com.example.uts_alat_pertanian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class OrderFragment : Fragment() {

    private var currentStep = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_order, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val step1Icon: ImageView = view.findViewById(R.id.step1Icon)
        val step2Icon: ImageView = view.findViewById(R.id.step2Icon)
        val step3Icon: ImageView = view.findViewById(R.id.step3Icon)
        val step4Icon: ImageView = view.findViewById(R.id.step4Icon)

        val step1Label: TextView = view.findViewById(R.id.step1Label)
        val step2Label: TextView = view.findViewById(R.id.step2Label)
        val step3Label: TextView = view.findViewById(R.id.step3Label)
        val step4Label: TextView = view.findViewById(R.id.step4Label)

        val con1: View = view.findViewById(R.id.connector1)
        val con2: View = view.findViewById(R.id.connector2)
        val con3: View = view.findViewById(R.id.connector3)

        val rv: RecyclerView = view.findViewById(R.id.rvTimeline)
        val btn: MaterialButton = view.findViewById(R.id.btnMarkReceived)

        val adapter = OrderTimelineAdapter()
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        val baseTimeline = listOf(
            OrderTimelineItem(
                "System • Today",
                "Payment verified. Your order has been forwarded to the seller.",
                "15:11"
            ),
            OrderTimelineItem(
                "Seller • Today",
                "Order is being processed by the seller.",
                "15:30"
            ),
            OrderTimelineItem(
                "Courier • Today",
                "Package is on delivery.",
                "18:05"
            ),
            OrderTimelineItem(
                "System • Today",
                "Package has arrived. Please confirm receipt.",
                "20:40"
            )
        )

        fun renderTimeline(step: Int) {
            val slice = baseTimeline.take(step + 1)
            adapter.submit(slice)
        }

        fun renderStepper(step: Int) {
            val active = ContextCompat.getColor(requireContext(), R.color.brand_primary)
            val inactive = ContextCompat.getColor(requireContext(), R.color.brand_muted)

            fun tint(iv: ImageView, on: Boolean) = iv.setColorFilter(if (on) active else inactive)
            fun label(tv: TextView, on: Boolean) = tv.setTextColor(if (on) active else inactive)
            fun bar(v: View, on: Boolean) = v.setBackgroundColor(if (on) active else inactive)

            tint(step1Icon, step >= 0); label(step1Label, step >= 0)
            bar(con1, step >= 1)
            tint(step2Icon, step >= 1); label(step2Label, step >= 1)
            bar(con2, step >= 2)
            tint(step3Icon, step >= 2); label(step3Label, step >= 2)
            bar(con3, step >= 3)
            tint(step4Icon, step >= 3); label(step4Label, step >= 3)

            btn.isEnabled = step < 3
            btn.text = when (step) {
                0 -> "Next: Process Order"
                1 -> "Next: Deliver Order"
                2 -> "Next: Mark as Arrived"
                else -> "All Steps Complete"
            }
        }

        fun renderAll() {
            renderStepper(currentStep)
            renderTimeline(currentStep)
        }

        renderAll()

        btn.setOnClickListener {
            if (currentStep < 3) {
                currentStep += 1
                renderAll()
            }
        }
    }
}