package com.ccplay.atm

import android.app.AlertDialog
import android.content.Context
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
    var remember = false
    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireContext().getSharedPreferences("atm", Context.MODE_PRIVATE)
        val prefUser = pref.getString("USER", "")
        val checked = pref.getBoolean("rem_username", false)//宣告取得是否勾選的布林值
        // binding.cbRemember.isChecked = checked
        binding.checkBox.isChecked = checked//勾選了記憶密碼
        binding.checkBox.setOnCheckedChangeListener { compoundButton, checked ->
            remember = checked
            pref.edit().putBoolean("rem_username", remember).apply()
            if (!checked) {
                pref.edit().putString("USER", "").apply()
            }
        }
        if (prefUser != "") {//要寫在外面
            binding.editTextTextPersonName.setText(prefUser)
        }//如果不為空 幫把打字上去
        binding.buttonLogin.setOnClickListener {
            // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val username = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            if (username == "cc" &&
                password == "1234"
            ) {
                //val pref = requireContext().getSharedPreferences("atm",
                // Context.MODE_PRIVATE)//設定atm物件 要獲得使用者帳號密碼的記憶

                if (remember) {//如果打勾記憶帳號
                    pref.edit()
                        .putString("USER", username)//放使用者資料到USER
                        .putInt("LEVEL", 3)
                        .apply() //.commit()不是馬上要用 用apply，也可用commit 如果馬上要用的話//會放到右下角模擬器資料的data/data/pref map裏面
                }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            } else {
                AlertDialog.Builder(requireContext())//原本可以用this 但這邊不行Fragment需要有繼承別人的得到
                    .setTitle("登入失敗")
                    .setMessage("請重新嘗試")
                    .setPositiveButton("ok", null)
                    .show()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}