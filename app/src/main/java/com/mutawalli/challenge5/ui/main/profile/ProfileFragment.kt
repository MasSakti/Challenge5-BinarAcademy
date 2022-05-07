package com.mutawalli.challenge5.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mutawalli.challenge5.R
import com.mutawalli.challenge5.databinding.FragmentProfileBinding
import com.mutawalli.challenge5.model.SharedPreference

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shared = SharedPreference(view.context)
        val factory = ProfileViewModelProvider(shared)
        viewModel = ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]

        viewModel.apply {
            getUserData()
            fullname.observe(viewLifecycleOwner) {
                binding.etNameProfile.setText(it)
            }
            username.observe(viewLifecycleOwner) {
                binding.etUsernameProfile.setText(it)
            }

            date.observe(viewLifecycleOwner) {
                binding.etBirthProfile.setText(it)
            }

            address.observe(viewLifecycleOwner) {
                binding.etAddresProfile.setText(it)
            }

            email.observe(viewLifecycleOwner) {
                binding.etEmailProfile.setText(it)
            }
        }

        binding.ivBacktoList2.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnLogoutProfile.setOnClickListener {
            shared.clearUsername()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        binding.btnUpdateProfile.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(
                    viewModel.sendDataToUpdate()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}