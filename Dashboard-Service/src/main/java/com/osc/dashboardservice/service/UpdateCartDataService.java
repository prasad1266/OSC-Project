package com.osc.dashboardservice.service;

import com.osc.dashboardservice.dto.UpdateCartDto;

public interface UpdateCartDataService {
    void addCartProduct(UpdateCartDto updateCartDto);
    void decreaseCartProduct(UpdateCartDto updateCartDto);

    void removeCartProduct(UpdateCartDto updateCartDto);
}
