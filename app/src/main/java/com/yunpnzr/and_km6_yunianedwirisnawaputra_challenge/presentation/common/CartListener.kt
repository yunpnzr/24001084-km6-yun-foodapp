package com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.presentation.common

import com.yunpnzr.and_km6_yunianedwirisnawaputra_challenge.data.model.Cart

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)

    fun onMinusTotalItemCartClicked(cart: Cart)

    fun onRemoveCartClicked(cart: Cart)

    fun onUserDoneEditingNotes(cart: Cart)
}
