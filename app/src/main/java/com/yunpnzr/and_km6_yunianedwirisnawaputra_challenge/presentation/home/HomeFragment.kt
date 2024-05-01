package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.OnItemClickedListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.FragmentHomeBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood.DetailFoodActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter.CatalogMenuAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter.CategoryMenuAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var catalogAdapter: CatalogMenuAdapter

    private val categoryAdapter: CategoryMenuAdapter by lazy {
        CategoryMenuAdapter {
            getCatalogData(it.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setSearchBar()

        setListCategoryMenu()
        setModeCatalog()
        observeListMode()

        getCategoryData()
        // getCatalogData(null)

        showUserData()
    }

    private fun showUserData() {
        if (!homeViewModel.isLoggedIn()) {
            binding.layoutHeader.tvHeaderName.text = getString(R.string.greetings)
        } else {
            homeViewModel.getUser()?.let { user ->
                binding.layoutHeader.tvHeaderName.text = getString(R.string.hello_user, user.name)
            }
        }
    }

    private fun getCategoryData() {
        homeViewModel.getCategoryList().observe(viewLifecycleOwner) { getCategory ->
            getCategory.proceedWhen(
                doOnSuccess = {
                    binding.layoutCategoryMenu.tvNotFoundCategory.isVisible = false
                    binding.layoutCategoryMenu.pbCategory.isVisible = false
                    it.payload?.let { data ->
                        setBindCategory(data)
                    }
                },
                doOnLoading = {
                    binding.layoutCategoryMenu.tvNotFoundCategory.isVisible = false
                    binding.layoutCategoryMenu.pbCategory.isVisible = true
                },
                doOnError = {
                    binding.layoutCategoryMenu.tvNotFoundCategory.isVisible = true
                    binding.layoutCategoryMenu.pbCategory.isVisible = false
                },
            )
        }
    }

    private fun getCatalogData(category: String? = null) {
        homeViewModel.getCatalogList(category).observe(viewLifecycleOwner) { getCatalog ->
            getCatalog.proceedWhen(
                doOnSuccess = {
                    binding.layoutListCatalog.tvNotFoundCatalog.isVisible = false
                    binding.layoutListCatalog.pbCatalog.isVisible = false
                    it.payload?.let { data ->
                        setBindCatalog(data)
                    }
                },
                doOnLoading = {
                    binding.layoutListCatalog.tvNotFoundCatalog.isVisible = false
                    binding.layoutListCatalog.pbCatalog.isVisible = true
                },
                doOnError = {
                    binding.layoutListCatalog.tvNotFoundCatalog.isVisible = true
                    binding.layoutListCatalog.pbCatalog.isVisible = false
                },
            )
        }
    }

    private fun setSearchBar() {
        with(binding) {
            layoutHeader.svMenu.setupWithSearchBar(layoutHeader.sbMenu)
        }
    }

    private fun setModeCatalog() {
        binding.layoutListCatalog.ivModeCatalog.setOnClickListener {
            homeViewModel.changeListMode()
            /*viewModel.setUsingGridMode(!viewModel.isUsingGridMode())
            setGridOrList(!viewModel.isUsingGridMode())
            setListCatalog(!viewModel.isUsingGridMode())*/
        }
    }

    private fun observeListMode() {
        homeViewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            getCatalogData(null)
            setGridOrList(isUsingGridMode)
            setListCatalog(isUsingGridMode)
        }
    }

    private fun setGridOrList(isUsingListMode: Boolean) {
        binding.layoutListCatalog.ivModeCatalog.setImageResource(
            if (isUsingListMode) {
                R.drawable.ic_list
            } else {
                R.drawable.ic_grid
            },
        )
    }

    private fun setListCatalog(usingListMode: Boolean) {
        val listMode =
            if (usingListMode) {
                CatalogMenuAdapter.LIST_MODE
            } else {
                CatalogMenuAdapter.GRID_MODE
            }

        catalogAdapter =
            CatalogMenuAdapter(
                listMode = listMode,
                listener =
                    object : OnItemClickedListener<Catalog> {
                        override fun onClicked(item: Catalog) {
                            navigateToDetail(item)
                        }
                    },
            )

        binding.layoutListCatalog.rvCatalog.apply {
            adapter = this@HomeFragment.catalogAdapter
            layoutManager =
                if (usingListMode) {
                    LinearLayoutManager(requireContext())
                } else {
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                }
        }
    }

    private fun navigateToDetail(item: Catalog) {
        val navController = findNavController()
        val bundleActivityDetailFood = bundleOf(Pair(DetailFoodActivity.EXTRAS_ITEM, item))
        navController.navigate(R.id.action_menu_tab_home_to_detail_food_activity, bundleActivityDetailFood)
    }

    private fun setListCategoryMenu() {
        categoryAdapter.clear()

        binding.layoutCategoryMenu.rvCategoryMenu.apply {
            adapter = this@HomeFragment.categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }

    private fun setBindCategory(data: List<Category>) {
        categoryAdapter.submitDataCategory(data)
    }

    private fun setBindCatalog(data: List<Catalog>) {
        catalogAdapter.submitData(data)
    }
}
