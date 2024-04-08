package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.OnItemClickedListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogApiDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryApiDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CategoryRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CategoryRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.pref.UserPreferenceImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.service.ApiService
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.FragmentHomeBinding
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood.DetailFoodActivity
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter.CatalogMenuAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.adapter.CategoryMenuAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.GenericViewModelFactory
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val service = ApiService.invoke()
        //val categoryDataSource = DummyCategoryDataSource()
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        //val catalogDataSource = DummyCatalogDataSource()
        val catalogDataSource: CatalogDataSource = CatalogApiDataSource(service)
        val catalogRepository: CatalogRepository = CatalogRepositoryImpl(catalogDataSource)
        val userPreference = UserPreferenceImpl(requireContext())
        GenericViewModelFactory.create(HomeViewModel(categoryRepository,catalogRepository, userPreference))
    }

    private lateinit var catalogAdapter: CatalogMenuAdapter
    private val categoryAdapter: CategoryMenuAdapter by lazy {
        CategoryMenuAdapter{
            getCatalogData(it.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListCategoryMenu()
        setClickCatalog()
        setSearchBar()

        getCategoryData()
        getCatalogData(null)

        observeListMode()
    }

    private fun getCategoryData(){
        viewModel.getCategoryList().observe(viewLifecycleOwner){getCategory->
            getCategory.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {data ->
                        setBindCategory(data)
                    }
                }
            )
        }
    }

    private fun getCatalogData(category: String? = null) {
        viewModel.getCatalogList(category).observe(viewLifecycleOwner){getCatalog->
            getCatalog.proceedWhen (
                doOnSuccess = {
                    it.payload?.let{data->
                        setBindCatalog(data)
                    }
                }
            )
        }
    }

    private fun setSearchBar() {
        with(binding){
            layoutHeader.svMenu.setupWithSearchBar(layoutHeader.sbMenu)
        }
    }

    private fun setClickCatalog() {
        binding.layoutListCatalog.ivModeCatalog.setOnClickListener{
            viewModel.changeListMode()
        }
    }

    private fun observeListMode(){
        viewModel.isUsingGridMode.observe(viewLifecycleOwner){isUsingGridMode ->
            setGridOrList(isUsingGridMode)
            setListCatalog(isUsingGridMode)
        }
    }

    private fun setGridOrList(isUsingListMode: Boolean){
        binding.layoutListCatalog.ivModeCatalog.setImageResource(
            if (isUsingListMode){
                R.drawable.ic_list
            } else {
                R.drawable.ic_grid
            }
        )
    }

    private fun setListCatalog(usingListMode: Boolean) {
        val listMode = if (usingListMode) {
            CatalogMenuAdapter.LIST_MODE
        } else {
            CatalogMenuAdapter.GRID_MODE
        }

        catalogAdapter = CatalogMenuAdapter(
            listMode = listMode,
            listener = object: OnItemClickedListener<Catalog> {
                override fun onClicked(item: Catalog) {
                    navigateToDetail(item)
                }
            }
        )

        binding.layoutListCatalog.rvCatalog.apply {
            adapter = this@HomeFragment.catalogAdapter
            layoutManager = if (usingListMode){
                LinearLayoutManager(requireContext())
            } else {
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        //catalogAdapter?.submitData(viewModel.getCatalogList())

    }

    private fun navigateToDetail(item: Catalog) {
        val navController = findNavController()
        val bundleActivityDetailFood = bundleOf(Pair(DetailFoodActivity.EXTRAS_ITEM,item))
        navController.navigate(R.id.action_menu_tab_home_to_detail_food_activity, bundleActivityDetailFood)
    }

    private fun setListCategoryMenu() {
        categoryAdapter.clear()

        binding.layoutCategoryMenu.rvCategoryMenu.apply {
            adapter = this@HomeFragment.categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
        //categoryAdapter.submitDataCategory(viewModel.getCategoryList())
    }

    private fun setBindCategory(data: List<Category>){
        categoryAdapter.submitDataCategory(data)
    }

    private fun setBindCatalog(data: List<Catalog>){
        catalogAdapter.submitData(data)
    }

}