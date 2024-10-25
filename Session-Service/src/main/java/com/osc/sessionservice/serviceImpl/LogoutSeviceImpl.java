package com.osc.sessionservice.serviceImpl;

import com.cart.CartServiceGrpc;
import com.cart.ProductQuantityRequest;
import com.osc.sessionservice.constants.StatusCodes;
import com.osc.sessionservice.dto.Logoutdto;
import com.osc.sessionservice.mapper.SessionMapper;
import com.osc.sessionservice.response.Response;
import com.osc.sessionservice.service.LogoutSevice;
import com.recentHistory.RecentlyViewedRequest;
import com.recentHistory.RecentlyViewedServiceGrpc;
import com.session.LogoutRequest;
import com.session.LogoutResponse;
import com.session.SessionServiceGrpc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LogoutSeviceImpl implements LogoutSevice {
    private SessionMapper sessionMapper;
    private SessionServiceGrpc.SessionServiceBlockingStub sessionServiceBlockingStub;
    private RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub;
    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;

    public LogoutSeviceImpl(SessionMapper sessionMapper,
                            @Qualifier("sessionServiceBlockingStub") SessionServiceGrpc.SessionServiceBlockingStub sessionServiceBlockingStub,
                            RecentlyViewedServiceGrpc.RecentlyViewedServiceBlockingStub recentlyViewedServiceBlockingStub,
                            CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub) {
        this.sessionMapper = sessionMapper;
        this.sessionServiceBlockingStub = sessionServiceBlockingStub;
        this.recentlyViewedServiceBlockingStub = recentlyViewedServiceBlockingStub;
        this.cartServiceBlockingStub = cartServiceBlockingStub;
    }

    public Response logoutUser(Logoutdto logoutdto) {

        LogoutRequest logoutRequest = sessionMapper.mapToLogoutRequest(logoutdto);
        LogoutResponse logoutResponse = sessionServiceBlockingStub.sessionLogout(logoutRequest);

        if (logoutResponse.getIsSessionLogout()) {
            RecentlyViewedRequest recentlyViewedRequest = sessionMapper.mapToRecentlyViewedRequest(logoutdto);
            recentlyViewedServiceBlockingStub.updateViewHistoryInDatabase(recentlyViewedRequest);

            ProductQuantityRequest productQuantityRequest = sessionMapper.mapToProductQuantityRequest(logoutdto);
            cartServiceBlockingStub.updateProductQuantity(productQuantityRequest);
        }

        return new Response(logoutResponse.getIsSessionLogout(), StatusCodes.LOGOUT_SUCCESS);
    }

}
