package com.ccplay.atm

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ccplay.atm.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
           // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            if(binding.editTextTextPersonName.text.toString()=="cc"&&
                    binding.editTextTextPassword.text.toString()=="1234"){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
            else{
                AlertDialog.Builder(requireContext())//原本可以用this 但這邊不行Fragment需要有繼承別人的得到
                    .setTitle("登入失敗")
                    .setMessage("請重新嘗試")
                    .setPositiveButton("ok",null)
                    .show()

            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}