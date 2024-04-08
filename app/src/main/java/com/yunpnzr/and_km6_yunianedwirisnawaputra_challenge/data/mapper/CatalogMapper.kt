package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.mapper

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Catalog
import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.source.network.model.catalog.CatalogItemResponse

fun CatalogItemResponse?.toCatalog() =
    Catalog(
        imageUrl = this?.imageUrl.orEmpty(),
        name = this?.nama.orEmpty(),
        price = this?.harga?.toDouble() ?: 0.0,
        desc = this?.detail.orEmpty(),
        marketAddress = this?.alamatResto.orEmpty(),
        mapUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
    )

fun Collection<CatalogItemResponse>?.toCatalogs() =
    this?.map { it.toCatalog() } ?: listOf()