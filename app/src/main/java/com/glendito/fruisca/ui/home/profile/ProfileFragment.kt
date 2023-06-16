package com.glendito.fruisca.ui.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.FragmentProfileBinding
import com.glendito.fruisca.ui.editprofile.EditProfileActivity
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var viewModelFactory: ProfileViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.txtName.text = user.name
                binding.txtNameDetail.text = user.name

                binding.txtEmail.text = user.email
                binding.txtEmailDetail.text = user.email

                binding.txtAddress.text = user.address
                binding.txtPhone.text = user.phone

                binding.btnEditProfile.setOnClickListener {
                    startActivity(Intent(requireContext(), EditProfileActivity::class.java))
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}