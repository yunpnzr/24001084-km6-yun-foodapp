package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.R
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.adapter.CatalogMenuAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.adapter.CategoryMenuAdapter
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.base.OnItemClickedListener
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private var catalogAdapter: CatalogMenuAdapter? = null
    private var categoryAdapter = CategoryMenuAdapter()

    private var isUsingListMode: Boolean = false

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
        setListCatalog(isUsingListMode)
        setClickCatalog()
        setSearchBar()
    }

    private fun setSearchBar() {
        with(binding){
            layoutHeader.svMenu.setupWithSearchBar(layoutHeader.sbMenu)
        }
    }

    private fun setClickCatalog() {
        binding.layoutListCatalog.ivModeCatalog.setOnClickListener{
            isUsingListMode = !isUsingListMode
            setGridOrList(isUsingListMode)
            setListCatalog(isUsingListMode)
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
        val listMode = if (isUsingListMode) {
            CatalogMenuAdapter.LIST_MODE
        } else {
            CatalogMenuAdapter.GRID_MODE
        }

        catalogAdapter = CatalogMenuAdapter(
            listMode = listMode,
            listener = object: OnItemClickedListener<Catalog> {
                override fun onClicked(item: Catalog) {
                    //navigateToDetail(item)
                }
            }
        )

        binding.layoutListCatalog.rvCatalog.apply {
            adapter = this@HomeFragment.catalogAdapter
            if (usingListMode){
                layoutManager = LinearLayoutManager(requireContext())
            } else {
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        catalogAdapter?.submitData(viewModel.getCatalogList())

    }

    /*private fun navigateToDetail(item: Catalog) {
        val navController = findNavController()
        val bundleActivityDetail = bundleOf(Pair(DetailActivity.EXTRAS_ITEM,item))
        navController.navigate(R.id.action_navigation_home_to_detailActivity, bundleActivityDetail)
    }*/

    private fun setListCategoryMenu() {
        categoryAdapter.clear()

        binding.layoutCategoryMenu.rvCategoryMenu.apply {
            adapter = this@HomeFragment.categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
        }
        categoryAdapter.submitDataCategory(viewModel.getCategoryList())
    }

}