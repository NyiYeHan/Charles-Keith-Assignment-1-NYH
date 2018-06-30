package com.padcmyanmar.charleskeith.network;

public interface DataAgent {
    void loadProductsList(int page , String accessToken,boolean isForceRefresh);
}
