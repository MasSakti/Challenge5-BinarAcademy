package com.mutawalli.challenge5.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutawalli.challenge5.R
import com.mutawalli.challenge5.databinding.FragmentHomeBinding
import com.mutawalli.challenge5.model.SharedPreference

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shared = SharedPreference(view.context)

        val adapter = HomeAdapter()
        binding.rvMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovies.adapter = adapter

        binding.tvWelcomeHome.text = getString(R.string.Welcome, shared.getPrefKey("username"))

        viewModel.popular.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("HomeFragment", it.toString())
        }

        viewModel.errorStatus.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Gagal Load Data", Toast.LENGTH_LONG).show()
            }
        }

        binding.ivProfileHome.setOnClickListener() {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        // TODO: action untuk berpindah page TheMovieDB
//        binding.ibLeftHome.setOnClickListener() {
//
//        }
//
//        binding.ibRightHome.setOnClickListener() {
//
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}