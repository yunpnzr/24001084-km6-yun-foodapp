package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.AuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.FirebaseAuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServices
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServicesImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.FragmentProfileBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login.LoginActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.GenericViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels{
        val service: FirebaseServices = FirebaseServicesImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        observeEditMode()

        showUserData()
    }

    private fun showUserData() {
        viewModel.getCurrentUser()?.let {
            binding.etName.setText(it.name)
            binding.etEmail.setText(it.email)
        }
    }

    private fun setEditEnabledOrDisabled(isEnabledOrDisabledEdit: Boolean) {
        if(!isEnabledOrDisabledEdit){
            binding.etName.isEnabled = false
            //binding.etEmail.isEnabled = false
            //binding.etPassword.isEnabled = false
            //binding.etTelephone.isEnabled = false
        } else {
            binding.etName.isEnabled = true
            //binding.etEmail.isEnabled = true
            //binding.etPassword.isEnabled = true
            //binding.etTelephone.isEnabled = true
        }
    }

    private fun setEnableOrDisableEdit(isEnabledOrDisabledEdit: Boolean) {
        binding.layoutTopBarProfile.ivEdit.setImageResource(
            if (!isEnabledOrDisabledEdit){
                R.drawable.ic_edit
            } else {
                R.drawable.ic_checklist
            }
        )
    }

    private fun observeEditMode() {
        viewModel.isEnableOrDisableEdit.observe(viewLifecycleOwner){ isEnabledOrDisabledEdit ->
            setEnableOrDisableEdit(isEnabledOrDisabledEdit)
            setEditEnabledOrDisabled(isEnabledOrDisabledEdit)
        }
    }

    private fun setClickListener() {
        binding.layoutTopBarProfile.ivEdit.setOnClickListener{
            viewModel.changeEditMode()
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
            //viewModel.doLogout()
            //navigateToLogin()
        }
    }

    private fun doLogout() {
        val dialog = AlertDialog.Builder(requireContext()).setMessage(getString(R.string.validate_logout))
            .setPositiveButton(
                getString(R.string.yes)
            ) { dialog, id ->
                viewModel.doLogout()
                navigateToLogin()
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { dialog, id ->

            }.create()
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}