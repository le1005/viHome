package com.example.features.user


import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.core.base.BaseFragment
import com.example.core.utils.prefetcher.HolderPrefetch
import com.example.core.utils.prefetcher.PrefetchRecycledViewPool
import com.example.features.FeatureNavigation
import com.example.features.R
import com.example.features.databinding.FragmentListUserBinding
import com.example.features.databinding.LayoutItemUserBinding
import com.example.setting.adapter.SettingAdapter
import com.example.setting.adapter.UserAdapter
import com.example.setting.adapter.UserHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ListUserFragment : BaseFragment<FragmentListUserBinding, UserViewModel>(), CoroutineScope {
    @Inject
    lateinit var appNavigation: FeatureNavigation

    override val layoutId = R.layout.fragment_list_user

    private val viewModel: UserViewModel by viewModels()
    override fun getVM() = viewModel

    private val mAdapter: SettingAdapter by lazy {
        SettingAdapter(
            requireContext(),
            onClickListener = { position, isChecked ->
                viewModel.onChooseCheckbox(position, isChecked)
            }, onClearListener = {
                viewModel.onClear(it)
            })
    }

    private val mUserAdapter: UserAdapter by lazy {
        UserAdapter(requireContext())
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    private val viewPool: PrefetchRecycledViewPool by lazy {
        PrefetchRecycledViewPool(
            requireActivity(),
            this
        ).apply {
            prepare()
        }
    }

    override fun initView() {
        super.initView()

//        prefetchItems(viewPool)

        binding.listView.setHasFixedSize(true)
        mAdapter.setHasStableIds(true)
        (binding.listView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.listView.adapter = mUserAdapter
//        binding.listView.setRecycledViewPool(viewPool)
    }

    private fun prefetchItems(holderPrefetch: HolderPrefetch) {
        val count = 15
        holderPrefetch.setViewsCount(12, count) { fakeParent, viewType ->
            UserHolder(
                LayoutItemUserBinding.inflate(layoutInflater, fakeParent, false)
            )
        }
    }

    override fun bindingStateView() {

        viewModel.isLoading.observe(viewLifecycleOwner, {
            showHideLoading(it)
        })

        viewModel.listData.observe(viewLifecycleOwner, {
            mAdapter.submitList(it)
        })

        viewModel.listUser.observe(viewLifecycleOwner, {
            mUserAdapter.submitList(it)
        })
    }
}