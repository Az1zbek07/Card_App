package com.example.cardapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapp.R
import com.example.cardapp.adapter.CardAdapter
import com.example.cardapp.databinding.FragmentCardListBinding
import com.example.cardapp.model.CardList
import com.example.cardapp.model.CardResponse
import com.example.cardapp.network.RetroInstance
import com.example.cardapp.util.NetworkUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardListFragment : Fragment(R.layout.fragment_card_list) {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!
    private val retroInstance by lazy { RetroInstance }
    private val cardAdapter by lazy { CardAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCardListBinding.bind(view)
        val networkUtils = NetworkUtils(requireContext())
        if (networkUtils.isNetworkConnected()){
            attachRv()
            cardAdapter.onDelete = {
                retroInstance.apiService().deleteCard(it).enqueue(object : Callback<Any>{
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        cardAdapter.submitList(listOf())
                        attachRv()
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.d("@@@", "onFailure: $t")
                    }
                })
            }
            cardAdapter.onClick = {
                val bundle = bundleOf("data" to it)
                findNavController().navigate(R.id.action_cardListFragment_to_addCardFragment, bundle)
            }
            binding.btnAdd.setOnClickListener {
                findNavController().navigate(R.id.action_cardListFragment_to_addCardFragment)
            }
        }else{
            Toast.makeText(requireContext(), "Please connect to internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun attachRv(){
        retroInstance.apiService().getCards().enqueue(object : Callback<List<CardResponse>>{
            override fun onResponse(
                call: Call<List<CardResponse>>,
                response: Response<List<CardResponse>>
            ) {
                binding.rv.apply {
                    adapter = cardAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
                cardAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<List<CardResponse>>, t: Throwable) {
                Log.d("@@@", "onFailure: $t")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}