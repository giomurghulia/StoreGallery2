package com.example.storegallery2.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storegallery2.Result
import com.example.storegallery2.databinding.FragmentStoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoreFragment : Fragment() {
    private val viewModel: StoreViewModel by viewModels()

    private lateinit var binding: FragmentStoreBinding

    private val adapter = MainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        handleWindowInset()

        binding.mainRecycleView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.mainRecycleView.adapter = adapter

        viewModel.getProduct()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is Result.Success -> {
                            submitList(it)

                            binding.loader.visibility = View.GONE

                        }
                        is Result.Loading -> {
                            binding.loader.visibility = View.VISIBLE
                        }
                        is Result.Error -> {
//                            Toast
//                                .makeText(activity, it.throwable.message, Toast.LENGTH_LONG)
//                                .show()

                            binding.loader.visibility = View.GONE

                            d("error", "${it.throwable.message}")

                            val alert: AlertDialog = AlertDialog.Builder(requireContext()).create()
                            alert.setTitle("Store Gallery")
                            alert.setMessage("Not Found Items")
                            alert.show()
                        }

                    }
                }
            }
        }
    }

    private fun handleWindowInset() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            binding.root.updatePadding(bottom = insets.bottom)

            windowInsets

        }
    }

    private fun submitList(items: Result.Success<List<ListItem>>) {

        if (items.data.isEmpty()) {
            val alert: AlertDialog = AlertDialog.Builder(requireContext()).create()
            alert.setTitle("Store Gallery")
            alert.setMessage("Not Found Items")
            alert.show()
        } else {
            adapter.submitList(items.data)

        }
    }
}