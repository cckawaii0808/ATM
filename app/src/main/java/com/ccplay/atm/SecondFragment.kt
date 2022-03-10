package com.ccplay.atm

import android.content.ContentValues
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ccplay.atm.databinding.FragmentSecondBinding
import com.google.gson.Gson
import okhttp3.*
import okio.ByteString
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val TAG = "SecondFragment"
    lateinit var socket: WebSocket

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

        }
        val client = OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)//最多等延遲１０秒
            .build()
        val request = Request.Builder()
            .url("wss://lott-dev.lottcube.asia/ws/chat/chat:app_test?nickname=CC")
            .build()


       socket= client.newWebSocket(request, object : WebSocketListener() {
            //crtl+o可以複寫//要求寫一個傾聽器 他會自己去處理
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(TAG,"onClosed")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d(TAG,"onClosing")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG,"onFailure")

            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(TAG,"onMessage:=$text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d(TAG,"onMessage=${bytes.hex()}")
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(TAG,"onOpen")
            }

        }
       )
        binding.sendmessagebotton.setOnClickListener {
            val message=binding.chatbox.text.toString()
            val json = "{\"action\": \"N\", \"content\": \"$message\" }"
            socket.send(json)
        }






        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //websocket製作
}
data class Message (val id: Long, val name: String)

//private fun webtest() {
//    thread {//避免main和thread 重複用
//        val data = URL("https://github.com/cckawaii0808")//通訊協定，位置，目錄//要s要加密的網頁
//            .readText()
//        Log.d(TAG, "data:$data")
//
//
//    }
//}