package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.OnItemClickedListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.AuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.FirebaseAuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogApiDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryApiDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.pref.PrefDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.pref.PrefDataSourceImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CatalogRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CategoryRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CategoryRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.PrefRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.PrefRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.UserRepositoryImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServices
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.firebase.FirebaseServicesImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.pref.UserPreference
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
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)

        val catalogDataSource: CatalogDataSource = CatalogApiDataSource(service)
        val catalogRepository: CatalogRepository = CatalogRepositoryImpl(catalogDataSource)
        //val userPreference = UserPreferenceImpl(requireContext())

        val userPreference: UserPreference = UserPreferenceImpl(requireContext())
        val prefDataSource: PrefDataSource = PrefDataSourceImpl(userPreference)
        val prefRepository: PrefRepository = PrefRepositoryImpl(prefDataSource)

        val firebaseService: FirebaseServices = FirebaseServicesImpl()
        val userDataSource: AuthDataSource = FirebaseAuthDataSource(firebaseService)
        val userRepository: UserRepository = UserRepositoryImpl(userDataSource)
        GenericViewModelFactory.create(HomeViewModel(
            categoryRepository,
            catalogRepository,
            prefRepository,
            userRepository
        ))
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
        setSearchBar()

        setListCategoryMenu()
        setModeCatalog()
        observeListMode()

        getCategoryData()
        //getCatalogData(null)

        showUserData()

    }

    private fun showUserData() {
        if (!viewModel.isLoggedIn()) {
            binding.layoutHeader.tvHeaderName.text = getString(R.string.greetings)
        } else {
            viewModel.getUser()?.let {user ->
                binding.layoutHeader.tvHeaderName.text = getString(R.string.hello_user, user.name)
            }
        }
    }

    private fun getCategoryData(){
        viewModel.getCategoryList().observe(viewLifecycleOwner){getCategory->
            getCategory.proceedWhen(
                doOnSuccess = {
                    binding.layoutCategoryMenu.tvNotFound.isVisible = false
                    binding.layoutCategoryMenu.pbCategory.isVisible = false
                    it.payload?.let {data ->
                        setBindCategory(data)
                    }
                },
                doOnLoading = {
                    binding.layoutCategoryMenu.tvNotFound.isVisible = false
                    binding.layoutCategoryMenu.pbCategory.isVisible = true
                },
                doOnError = {
                    binding.layoutCategoryMenu.tvNotFound.isVisible = true
                    binding.layoutCategoryMenu.pbCategory.isVisible = false
                }
            )
        }
    }

    private fun getCatalogData(category: String? = null) {
        viewModel.getCatalogList(category).observe(viewLifecycleOwner){getCatalog->
            getCatalog.proceedWhen (
                doOnSuccess = {
                    binding.layoutListCatalog.tvNotFound.isVisible = false
                    binding.layoutListCatalog.pbCatalog.isVisible = false
                    it.payload?.let{data->
                        setBindCatalog(data)
                    }
                },
                doOnLoading = {
                    binding.layoutListCatalog.tvNotFound.isVisible = false
                    binding.layoutListCatalog.pbCatalog.isVisible = true
                },
                doOnError = {
                    binding.layoutListCatalog.tvNotFound.isVisible = true
                    binding.layoutListCatalog.pbCatalog.isVisible = false
                }
            )
        }
    }

    private fun setSearchBar() {
        with(binding){
            layoutHeader.svMenu.setupWithSearchBar(layoutHeader.sbMenu)
        }
    }

    private fun setModeCatalog() {
        binding.layoutListCatalog.ivModeCatalog.setOnClickListener{
            viewModel.changeListMode()
            /*viewModel.setUsingGridMode(!viewModel.isUsingGridMode())
            setGridOrList(!viewModel.isUsingGridMode())
            setListCatalog(!viewModel.isUsingGridMode())*/
        }
    }

    private fun observeListMode(){
        viewModel.isUsingGridMode.observe(viewLifecycleOwner){isUsingGridMode ->
            getCatalogData(null)
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
    }

    private fun setBindCategory(data: List<Category>){
        categoryAdapter.submitDataCategory(data)
    }

    private fun setBindCatalog(data: List<Catalog>){
        catalogAdapter.submitData(data)
    }

}