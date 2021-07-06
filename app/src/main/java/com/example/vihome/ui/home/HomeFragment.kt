package com.example.vihome.ui.home

import androidx.fragment.app.viewModels
import com.example.core.base.BaseFragment
import com.example.vihome.R
import com.example.vihome.databinding.FragmentHomeBinding
import com.example.vihome.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: HomeViewModel by viewModels()

    override val layoutId = R.layout.fragment_home

    override fun setOnClick() {
        super.setOnClick()

        binding.btnRecyclerview.text = "Hello pe de"
        binding.btnRecyclerview.setOnClickListener {
            appNavigation.openSplashToLoginScreen()
        }
    }

    override fun getVM(): HomeViewModel = viewModel

}