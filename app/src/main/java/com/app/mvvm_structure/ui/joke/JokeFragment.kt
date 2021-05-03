package com.app.mvvm_structure.ui.joke

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.app.mvvm_structure.R
import com.app.mvvm_structure.databinding.FragmentJokeBinding
import com.app.mvvm_structure.ui.common.BaseFragment
import com.app.mvvm_structure.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokeFragment: BaseFragment<FragmentJokeBinding, JokeViewModel>() {

    private val jokeViewModel: JokeViewModel by viewModels()

    override fun onInflateLayout(): Int {
        return R.layout.fragment_joke
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = jokeViewModel

        jokeViewModel.getJoke()

        jokeViewModel.state.observe(viewLifecycleOwner, EventObserver{
            when(it) {
                is SuccessState -> {
                    hideProgress()
                    //CommonUtils.showToast(requireContext(), "Success")
                }

                is ErrorState -> {
                    hideProgress()
                    CommonUtils.showToast(requireContext(), it.error.message!!)
                }

                is LoadingState -> {
                    showProgress(R.layout.custom_progress)
                }
            }
        })

        dataBinding.btnRefreshJoke.setOnClickListener {
            jokeViewModel.getJoke()
        }
    }

}