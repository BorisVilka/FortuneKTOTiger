package com.ktotiger.acrashjet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ktotiger.acrashjet.databinding.FragmentGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.max

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentGameBinding
    private var bet = 10
    private var win = 0
    private var tmp = 0
    private var auto = false
    private var speed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater,container,false)
        var balance = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
            .getInt("balance",100_000)
        binding.score.text = balance.toString()
        binding.plus.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            bet += 10
            if(bet>1000) bet = 0
            binding.score.text = balance.toString()
            binding.bet.text = bet.toString()
        }
        binding.minus.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            bet -= 10
            if(bet<=0) bet = 1000
            binding.score.text = balance.toString()
            binding.bet.text = bet.toString()
        }
        var ad1 = SpinAdapter(3)
        var ad2 = SpinAdapter(3)
        var ad3 = SpinAdapter(3)
        binding.list1.adapter = ad1
        binding.list2.adapter = ad2
        binding.list3.adapter = ad3
        binding.list1.setOnTouchListener { v, event -> true }
        binding.list2.setOnTouchListener { v, event -> true }
        binding.list3.setOnTouchListener { v, event -> true }
        var spin = false
        binding.turbo.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            if(!spin) speed = !speed
        }
        binding.auto.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            if(!spin) auto = !auto
            if(auto) binding.spin.callOnClick()
        }
        binding.list3.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState== RecyclerView.SCROLL_STATE_IDLE) {
                    spin = false
                    try {
                        if(
                            ad1.data[ad1.data.size-2]==ad2.data[ad2.data.size-2]
                            && ad1.data[ad1.data.size-2]==ad3.data[ad3.data.size-2]
                            ||
                            ad1.data[ad1.data.size-1]==ad2.data[ad2.data.size-1]
                            && ad1.data[ad1.data.size-1]==ad3.data[ad3.data.size-1]
                            ||
                            ad1.data[ad1.data.size-3]==ad2.data[ad2.data.size-3]
                            && ad1.data[ad1.data.size-3]==ad3.data[ad3.data.size-3]
                            ||
                            ad1.data[ad1.data.size-1]==ad1.data[ad2.data.size-2]
                            && ad1.data[ad1.data.size-1]==ad1.data[ad3.data.size-3]
                            ||
                            ad2.data[ad1.data.size-1]==ad2.data[ad2.data.size-2]
                            && ad2.data[ad1.data.size-1]==ad2.data[ad3.data.size-3]
                            ||
                            ad3.data[ad1.data.size-1]==ad3.data[ad2.data.size-2]
                            && ad3.data[ad1.data.size-1]==ad3.data[ad3.data.size-3]
                        ) {
                            balance += bet*2
                            binding.score.text = balance.toString()
                            requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                                .edit().putInt("balance",balance).apply()
                            // binding.win.visibility = View.VISIBLE
                            win += tmp
                            binding.win.text = win.toString()
                            binding.win1.visibility = View.VISIBLE
                            lifecycleScope.launch {
                                delay(1000)
                                binding.win1.visibility = View.INVISIBLE
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    if(auto) {
                        lifecycleScope.launch {
                            delay(1000)
                            binding.spin.callOnClick()
                        }
                    }
                }
            }
        })
        binding.spin.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            if(spin) return@setOnClickListener
            spin = true
            balance -= bet
            binding.win1.visibility = View.INVISIBLE
            tmp = bet*2
            balance = max(0,balance)
            binding.score.text = balance.toString()
            //binding.win.visibility = View.INVISIBLE
            requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                .edit().putInt("balance",balance).apply()
            ad1 = SpinAdapter(if(speed) 75 else 150)
            ad2 = SpinAdapter(if(speed) 150 else 300)
            ad3 = SpinAdapter(if(speed) 225 else 450)
            binding.list1.adapter = ad1
            binding.list2.adapter = ad2
            binding.list3.adapter = ad3
            binding.list1.smoothScrollToPosition(ad1.data.size-1)
            binding.list2.smoothScrollToPosition(ad2.data.size-1)
            binding.list3.smoothScrollToPosition(ad3.data.size-1)
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}