package com.osc.productdataservice.service;

import java.util.List;

public interface SimilarProductsService {
    List<String> fetchSimilarProductsIds(List< String> recentViewdProdutIds);
}
