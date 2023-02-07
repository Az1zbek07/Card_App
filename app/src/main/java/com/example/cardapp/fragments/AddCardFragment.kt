package com.example.cardapp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.cardapp.R
import com.example.cardapp.databinding.FragmentAddCardBinding
import com.example.cardapp.model.CardResponse
import com.example.cardapp.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCardFragment : Fragment(R.layout.fragment_add_card) {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private val retroInstance by lazy { RetroInstance }
    private var cardResponse: CardResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddCardBinding.bind(view)
        cardResponse = arguments?.getParcelable<CardResponse>("data")

        if (cardResponse == null){
            binding.btnSubmit.setText("Add")
            addCard()
        }else{
            binding.btnSubmit.setText("Update")
            updateCard()
        }
    }

    private fun addCard(){
        listenerEditText()
        binding.btnSubmit.setOnClickListener {
            val number = binding.editNumber.text.toString().trim()
            val holder = binding.editHolderName.text.toString().trim()
            val date1 = binding.editDate1.text.toString().trim()
            val date2 = binding.editDate2.text.toString().trim()

            if (number.isNotBlank() && holder.isNotBlank() && date1.isNotBlank() && date2.isNotBlank()){
                val data = CardResponse(0, "Walmart", number.toInt(), date1.toInt(), date2.toInt(), holder, "")
                retroInstance.apiService().postCard(data).enqueue(object : Callback<CardResponse>{
                    override fun onResponse(
                        call: Call<CardResponse>,
                        response: Response<CardResponse>
                    ) {
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Card added", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                        Log.d("@@@", "onFailure: $t")
                    }
                })
            }
        }
    }

    private fun updateCard(){
        listenerEditText()
        binding.editHolderName.setText(cardResponse?.cardHolderName)
        binding.editNumber.setText(cardResponse?.number.toString())
        binding.editDate1.setText(cardResponse?.date1.toString())
        binding.editDate2.setText(cardResponse?.date2.toString())
        binding.editCvv.setText(cardResponse?.cvv)

        binding.btnSubmit.setOnClickListener {
            val number = binding.editNumber.text.toString().trim()
            val holder = binding.editHolderName.text.toString().trim()
            val date1 = binding.editDate1.text.toString().trim()
            val date2 = binding.editDate2.text.toString().trim()

            if (number.isNotBlank() && holder.isNotBlank() && date1.isNotBlank() && date2.isNotBlank()){
                val data = CardResponse(0, "Walmart", number.toInt(), date1.toInt(), date2.toInt(), holder, "")
                retroInstance.apiService().updateCard(cardResponse!!.id, data).enqueue(object : Callback<CardResponse>{
                    override fun onResponse(
                        call: Call<CardResponse>,
                        response: Response<CardResponse>
                    ) {
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Card updated", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                        Log.d("@@@", "onFailure: $t")
                    }
                })
            }
        }
    }

     private fun onTextChange(editText: AppCompatEditText, textView: TextView){
        editText.addTextChangedListener {
            textView.text = editText.text
        }
    }

    private fun listenerEditText(){
        onTextChange(binding.editNumber, binding.itemLayout.textNumber)
        onTextChange(binding.editHolderName, binding.itemLayout.textHolderName)
        onTextChange(binding.editDate1, binding.itemLayout.textDate1)
        onTextChange(binding.editDate2, binding.itemLayout.textDate2)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}