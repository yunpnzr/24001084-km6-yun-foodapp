package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEdit()
        observeEditMode()
    }

    private fun setEditEnabledOrDisabled(isEnabledOrDisabledEdit: Boolean) {
        if(!isEnabledOrDisabledEdit){
            binding.etName.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etPassword.isEnabled = false
            binding.etTelephone.isEnabled = false
        } else {
            binding.etName.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etPassword.isEnabled = true
            binding.etTelephone.isEnabled = true
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

    private fun setClickEdit() {
        binding.layoutTopBarProfile.ivEdit.setOnClickListener{
            viewModel.changeEditMode()
        }
    }
}