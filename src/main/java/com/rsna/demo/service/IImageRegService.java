package com.rsna.demo.service;

import com.rsna.demo.utils.LocaResult;
import com.rsna.demo.utils.LocalRequest;
import com.rsna.demo.utils.RSNAResult;

import java.io.File;

public interface IImageRegService {
    public LocaResult getRes();
    public RSNAResult getRes(File file, String username, String path);
    public boolean addNewRes(LocalRequest localRequest);
}
