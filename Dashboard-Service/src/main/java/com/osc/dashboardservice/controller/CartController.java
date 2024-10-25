package com.osc.dashboardservice.controller;

import com.osc.dashboardservice.dto.ApiResponse;
import com.osc.dashboardservice.dto.CartDataRequestDto;
import com.osc.dashboardservice.dto.UpdateCartDto;
import com.osc.dashboardservice.service.GetCartDetailesService;
import com.osc.dashboardservice.service.UpdateCartDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class CartController {
private GetCartDetailesService getCartDetailes;
private UpdateCartDataService updateCartDataService;

    public CartController(GetCartDetailesService getCartDetailes, UpdateCartDataService updateCartDataService) {
        this.getCartDetailes = getCartDetailes;
        this.updateCartDataService = updateCartDataService;
    }

    @PostMapping("/user/cart/view")
    public ResponseEntity<ApiResponse> getProductDetails(@RequestBody CartDataRequestDto cartDataRequestDto){
        ResponseEntity<ApiResponse> response = getCartDetailes.getCartDetails(cartDataRequestDto);
        return response;
    }

    @PostMapping("/user/cart/increase")
    public ApiResponse addCartProducts(@RequestBody UpdateCartDto updateCartDto){
         updateCartDataService.addCartProduct(updateCartDto);
        return new ApiResponse(200,"");

    }

    @PostMapping("/user/cart/decrease")
    public ApiResponse decreaseCartProductQuantity(@RequestBody UpdateCartDto updateCartDto){
        updateCartDataService.decreaseCartProduct(updateCartDto);
        return new ApiResponse(200,"");
    }

    @PostMapping("/user/cart/remove")
    public ApiResponse removeCartProduct(@RequestBody UpdateCartDto updateCartDto){
        updateCartDataService.removeCartProduct(updateCartDto);
        return new ApiResponse(200,"");
    }

}
