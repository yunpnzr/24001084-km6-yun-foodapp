package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.datasource.category

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Category

class DummyCategoryDataSource: CategoryDataSource {
    override fun getCategoryDataSource(): List<Category> {
        return listOf(
            Category(
                imageUrl = "https://github.com/yunpnzr/foodapp-asset/blob/main/category_img/img_drink.jpg?raw=true",
                name = "Nasi"
            ),
            Category(
                imageUrl = "https://github.com/yunpnzr/foodapp-asset/blob/main/category_img/img_noodle.jpg?raw=true",
                name = "Mie"
            ),
            Category(
                imageUrl = "https://raw.githubusercontent.com/yunpnzr/foodapp-asset/main/category_img/img_rice.webp",
                name = "Snack"
            ),
            Category(
                imageUrl = "https://github.com/yunpnzr/foodapp-asset/blob/main/category_img/img_snack.jpg?raw=true",
                name = "Minuman"
            )
        )
    }
}