package com.osc.recentlyviewservice.grpcService;

import com.google.protobuf.Empty;
import com.osc.recentlyviewservice.service.CheckRecentHistoryService;
import com.osc.recentlyviewservice.service.UpdateRecentlyViewService;
import com.osc.recentlyviewservice.service.UpdateViewHistoryInDatabaseService;
import com.recentHistory.RecentlyViewedRequest;
import com.recentHistory.RecentlyViewedResponse;
import com.recentHistory.RecentlyViewedServiceGrpc;
import com.recentHistory.UpdateRecentViewedRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class RecentlyViewGrpcService extends RecentlyViewedServiceGrpc.RecentlyViewedServiceImplBase {

    private CheckRecentHistoryService checkRecentHistoryService;
    private UpdateRecentlyViewService updateRecentlyViewService;
    private UpdateViewHistoryInDatabaseService updateViewHistoryInDatabaseService;

    public RecentlyViewGrpcService(CheckRecentHistoryService checkRecentHistoryService, UpdateRecentlyViewService updateRecentlyViewService, UpdateViewHistoryInDatabaseService updateViewHistoryInDatabaseService) {
        this.checkRecentHistoryService = checkRecentHistoryService;
        this.updateRecentlyViewService = updateRecentlyViewService;
        this.updateViewHistoryInDatabaseService = updateViewHistoryInDatabaseService;
    }



    @Override
    public void fetchRecentlyViewedHistory(RecentlyViewedRequest request, StreamObserver<RecentlyViewedResponse> responseObserver) {
        RecentlyViewedResponse recentlyViewedResponse =    checkRecentHistoryService.checkRecentHistorySattusinKtable(request);

        responseObserver.onNext(recentlyViewedResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void updateRecentlyViewedProducts(UpdateRecentViewedRequest request, StreamObserver<Empty> responseObserver) {
       updateRecentlyViewService.updateRecentViewdProductsList(request);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void updateViewHistoryInDatabase(RecentlyViewedRequest request, StreamObserver<Empty> responseObserver) {
        updateViewHistoryInDatabaseService.updateRecentViewHistory(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
