package com.mutawalli.challenge5.ui.auth.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mutawalli.challenge5.R
import com.mutawalli.challenge5.databinding.FragmentRegisterBinding
import com.mutawalli.challenge5.model.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel
    private lateinit var dateListener: DatePickerDialog.OnDateSetListener
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        viewModel = ViewModelProvider(requireActivity(), factory)[RegisterViewModel::class.java]

        dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        binding.etBirthRegister.setOnClickListener {
            DatePickerDialog(
                view.context,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        updateDateInView()

        binding.btnRegister.setOnClickListener {
            if (binding.etUsernameRegister.text.isEmpty() || binding.etNameRegister.text.isEmpty() ||
                binding.etEmailRegister.text.isEmpty() || binding.etBirthRegister.text.isEmpty() ||
                binding.etAddressRegister.text.isEmpty() || binding.etPasswordRegister.text.isNullOrEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Tidak boleh ada isian yang kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!binding.etPasswordRegister.text!!.matches(Regex("(?=.*[a-z])(?=.*[A-Z]).+"))) {
                Toast.makeText(requireContext(), R.string.PasswordUpLow, Toast.LENGTH_SHORT).show()
            } else if (binding.etPasswordRegister.text!!.length < 8) {
                Toast.makeText(requireContext(), R.string.PasswordKurang, Toast.LENGTH_SHORT).show()
            // else if (binding.etPasswordRegister.text != binding.etPasswordRegisterVerif.text)
            } else if (binding.etPasswordRegister.text == binding.etPasswordRegisterVerif.text) {
                Toast.makeText(requireContext(), R.string.PasswordVerif, Toast.LENGTH_SHORT).show()
            } else if (!binding.etEmailRegister.text
                    .matches(Regex("^[a-zA-Z0-9_.]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$"))
            ) {
                Toast.makeText(requireContext(), R.string.EmailInvalid, Toast.LENGTH_SHORT).show()
            } else {
                val username = binding.etUsernameRegister.text.toString()
                val fullname = binding.etNameRegister.text.toString()
                val email = binding.etEmailRegister.text.toString()
                val ttl = binding.etBirthRegister.text.toString()
                val address = binding.etAddressRegister.text.toString()
                val password = binding.etPasswordRegister.text.toString()
                viewModel.save(email, username, fullname, ttl, address, password)
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) {
            val check = it.getContentIfNotHandled() ?: return@observe
            if (check) {
                Toast.makeText(requireContext(), "Akun Berhasil Didaftarkan", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(), "Akun Gagal Didaftarkan", Toast.LENGTH_LONG).show()
            }
        }

        binding.ivBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.etBirthRegister.setText(sdf.format(calendar.time).toString())
    }

}