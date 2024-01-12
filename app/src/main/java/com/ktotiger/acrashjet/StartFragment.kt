package com.ktotiger.acrashjet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ktotiger.acrashjet.databinding.FragmentStartBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {
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

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater,container,false)
        binding.play.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.navigate(R.id.action_startFragment_to_gameFragment)
        }
        binding.privacy.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            startActivity(Intent(requireActivity(),WebActivity::class.java).apply {
                putExtra("tmp","https://docs.google.com/document/d/1tomQ5DMm6P-X5vBdEBZQcu7e8B0TOORLtgBuEu4CwBE")
            })
        }
        var sound = requireActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE)
            .getBoolean("sounds",false)
        binding.sound.setImageResource(if(sound) R.drawable.sound_on else R.drawable.sound_off)
        binding.sounds1.setOnClickListener {
            SoundsManager.getInstance().startClickSound()
            sound = !sound
            SoundsManager.getInstance().changeMusic(sound)
            binding.sound.setImageResource(if(sound) R.drawable.sound_on else R.drawable.sound_off)
            requireActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE)
                .edit().putBoolean("sounds",sound).apply()
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
         * @return A new instance of fragment StartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}