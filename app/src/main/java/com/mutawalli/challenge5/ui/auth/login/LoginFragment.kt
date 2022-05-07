package com.mutawalli.challenge5.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mutawalli.challenge5.R
import com.mutawalli.challenge5.databinding.FragmentLoginBinding
import com.mutawalli.challenge5.model.SharedPreference
import com.mutawalli.challenge5.model.Status
import com.mutawalli.challenge5.model.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private var sharedPref: SharedPreference? = null
    private var status = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        viewModel = ViewModelProvider(requireActivity(), factory)[LoginViewModel::class.java]
        sharedPref = SharedPreference(view.context)

        status = sharedPref?.getPrefKeyStatus("login_status") == true

        if (status) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmailLogin.text.isEmpty() && binding.etPasswordLogin.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), R.string.EmailPasswordKosong, Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etEmailLogin.text.isEmpty()) {
                Toast.makeText(requireContext(), R.string.EmailKosong, Toast.LENGTH_SHORT).show()
            } else if (!binding.etEmailLogin.text
                    .matches(Regex("^[a-zA-Z0-9_.]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$"))
            ) {
                Toast.makeText(requireContext(), R.string.EmailInvalid, Toast.LENGTH_SHORT).show()
            } else if (binding.etPasswordLogin.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), R.string.PasswordKosong, Toast.LENGTH_SHORT).show()
            } else if (binding.etPasswordLogin.text!!.length < 8) {
                Toast.makeText(requireContext(), R.string.PasswordKurang, Toast.LENGTH_SHORT).show()
            } else if (!binding.etPasswordLogin.text!!.matches(Regex("(?=.*[a-z])(?=.*[A-Z]).+"))) {
                Toast.makeText(requireContext(), R.string.PasswordUpLow, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(
                    binding.etEmailLogin.text.toString(),
                    binding.etPasswordLogin.text.toString()
                )

                viewModel.loginStatus.observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            if (it.data != null) {
                                sharedPref?.saveKey(it.data)
                                sharedPref?.saveKeyState(true)
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "User Belum Terdaftar",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                        }
                    }
                }
            }
        }

        binding.tvCreate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}