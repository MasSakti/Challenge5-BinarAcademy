package com.mutawalli.challenge5.ui.main.profile.updateprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mutawalli.challenge5.R
import com.mutawalli.challenge5.data.local.UserEntity
import com.mutawalli.challenge5.databinding.FragmentUpdateProfileBinding
import com.mutawalli.challenge5.model.SharedPreference
import com.mutawalli.challenge5.model.ViewModelFactory

class UpdateProfileFragment : Fragment() {

    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UpdateProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shared = SharedPreference(view.context)
        val factory = ViewModelFactory(view.context)
        viewModel =
            ViewModelProvider(requireActivity(), factory)[UpdateProfileViewModel::class.java]

        val userData = arguments?.getParcelable<UserEntity>("user")

        if (userData != null) {
            binding.apply {
                etEmailUpdate.setText(userData.email)
                etUsernameUpdate.setText(userData.username)
                etBirthUpdate.setText(userData.ttl)
                etAddressUpdate.setText(userData.address)
                etNameUpdate.setText(userData.fullname)
            }
        }

        binding.btnSaveUpdate.setOnClickListener {
            val email = binding.etEmailUpdate.text.toString()
            val username = binding.etUsernameUpdate.text.toString()
            val fullname = binding.etNameUpdate.text.toString()
            val ttl = binding.etBirthUpdate.text.toString()
            val address = binding.etAddressUpdate.text.toString()
            val password = userData?.password
            val id = userData?.id

            val user = UserEntity(
                id = id!!,
                email = email,
                username = username,
                fullname = fullname,
                ttl = ttl,
                address = address,
                password = password!!
            )
            viewModel.update(id.toInt(), email, username, fullname, ttl, address, password)
            shared.saveKey(user)
        }

        binding.ivBacktoProfile.setOnClickListener {
            findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
        }

        viewModel.saved.observe(viewLifecycleOwner) {
            val check = it.getContentIfNotHandled() ?: return@observe
            if (check) {
                Toast.makeText(requireContext(), "Update akun berhasil", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
            } else {
                Toast.makeText(requireContext(), "Gagal update akun", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}