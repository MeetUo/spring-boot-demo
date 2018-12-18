package com.rsna.demo.utils;

import java.util.List;

public class LocalRequest {
    private List<LocaResult> localRequests;
    private int picid;
    public List<LocaResult> getLocalRequests(){
        return localRequests;
    }
    public int getPicid(){
        return picid;
    }
}
