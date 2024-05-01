package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.AuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.auth.FirebaseAuthDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart.CartDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.cart.CartDatabaseDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogApiDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.catalog.CatalogDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryApiDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category.CategoryDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.pref.PrefDataSource
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.pref.PrefDataSourceImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepository
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.repository.CartRepositoryImpl
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
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.AppDatabase
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.database.dao.CartDao
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.pref.UserPreference
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.local.pref.UserPreferenceImpl
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.service.ApiService
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.login.LoginViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.auth.register.RegisterViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.cart.CartViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.checkout.CheckoutViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.detailfood.DetailFoodViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.home.HomeViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.main.MainViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.profile.ProfileViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.splashscreen.SplashViewModel
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {
    private val networkModule =
        module {
            single<ApiService> {
                ApiService.invoke()
            }
        }

    private val localModule =
        module {
            single<AppDatabase> {
                AppDatabase.createInstance(androidContext())
            }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(androidContext(), UserPreferenceImpl.PREF_NANE)
            }
            single<UserPreference> {
                UserPreferenceImpl(get())
            }
        }

    private val firebaseModule =
        module {
            // single { FirebaseAuth.getInstance() }

            single<FirebaseAuth> {
                FirebaseAuth.getInstance()
            }
            single<FirebaseServices> {
                FirebaseServicesImpl(get())
            }
        }

    private val dataSourceModule =
        module {
            single<AuthDataSource> {
                FirebaseAuthDataSource(get())
            }
            single<CartDataSource> {
                CartDatabaseDataSource(get())
            }
            single<CatalogDataSource> {
                CatalogApiDataSource(get())
            }
            single<CategoryDataSource> {
                CategoryApiDataSource(get())
            }
            single<PrefDataSource> {
                PrefDataSourceImpl(get())
            }
        }

    private val repositoryModule =
        module {
            single<CartRepository> {
                CartRepositoryImpl(get())
            }
            single<CatalogRepository> {
                CatalogRepositoryImpl(get())
            }
            single<CategoryRepository> {
                CategoryRepositoryImpl(get())
            }
            single<PrefRepository> {
                PrefRepositoryImpl(get())
            }
            single<UserRepository> {
                UserRepositoryImpl(get())
            }
        }

    private val viewModelModule =
        module {
            viewModel { LoginViewModel(get()) }
            viewModel { RegisterViewModel(get()) }
            viewModel { HomeViewModel(get(), get(), get(), get()) }
            viewModel { CartViewModel(get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { CheckoutViewModel(get(), get(), get()) }
            viewModel { params ->
                DetailFoodViewModel(
                    intent = params.get(),
                    cartRepository = get(),
                )
            }
        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            firebaseModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule,
        )
}
