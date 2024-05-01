package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.FragmentProfileBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login.LoginActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        observeEditMode()

        showUserData()
    }

    private fun showUserData() {
        profileViewModel.getCurrentUser()?.let {
            binding.etName.setText(it.name)
            binding.etEmail.setText(it.email)
        }
    }

    private fun setEditEnabledOrDisabled(isEnabledOrDisabledEdit: Boolean) {
        /*if(!isEnabledOrDisabledEdit){
            binding.etName.isEnabled = false
            binding.etPassword.isEnabled = false
        } else {
            binding.etName.isEnabled = true
            binding.etPassword.isEnabled = true
        }*/
        // binding.etPassword.isEnabled = true

        // binding.etPassword.isEnabled = false
        binding.etName.isEnabled = isEnabledOrDisabledEdit
    }

    private fun setEnableOrDisableEdit(isEnabledOrDisabledEdit: Boolean) {
        binding.layoutTopBarProfile.ivEdit.setImageResource(
            if (!isEnabledOrDisabledEdit) {
                R.drawable.ic_edit
            } else {
                R.drawable.ic_checklist
            },
        )
    }

    private fun observeEditMode() {
        profileViewModel.isEnableOrDisableEdit.observe(viewLifecycleOwner) { isEnabledOrDisabledEdit ->
            setEnableOrDisableEdit(isEnabledOrDisabledEdit)
            setEditEnabledOrDisabled(isEnabledOrDisabledEdit)
        }
    }

    private fun setClickListener() {
        binding.layoutTopBarProfile.ivEdit.setOnClickListener {
            profileViewModel.changeEditMode()

            val newName = binding.etName.text.toString()

            if (profileViewModel.isEnableOrDisableEdit.value == true) {
                profileViewModel.doChangeProfile(newName).observe(viewLifecycleOwner) { result ->
                    result.proceedWhen(
                        doOnSuccess = {
                            binding.pbProfile.isVisible = false
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.change_name_success),
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                        doOnLoading = {
                            binding.pbProfile.isVisible = true
                        },
                        doOnError = {
                            binding.pbProfile.isVisible = false
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.change_name_failed),
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    )
                }
            }
        }

        binding.tvChangePassword.setOnClickListener {
            profileViewModel.doChangePasswordByEmail()
            changePasswordDialog()
        }

        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun changePasswordDialog() {
        val dialog =
            AlertDialog.Builder(requireContext()).setMessage(getString(R.string.check_email_change_password))
                .setPositiveButton(
                    getString(R.string.yes),
                ) { _, _ ->
                    profileViewModel.doLogout()
                    navigateToLogin()
                }.create()
        dialog.show()
    }

    private fun doLogout() {
        val dialog =
            AlertDialog.Builder(requireContext()).setMessage(getString(R.string.validate_logout))
                .setPositiveButton(
                    getString(R.string.yes),
                ) { _, _ ->
                    profileViewModel.doLogout()
                    navigateToLogin()
                }
                .setNegativeButton(
                    getString(R.string.no),
                ) { _, _ ->
                }.create()
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}
