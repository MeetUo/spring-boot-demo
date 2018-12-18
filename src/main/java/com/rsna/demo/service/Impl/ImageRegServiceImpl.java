package com.rsna.demo.service.Impl;

import com.rsna.demo.dao.UserPicMapper;
import com.rsna.demo.entity.UserPic;
import com.rsna.demo.service.IImageRegService;
import com.rsna.demo.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageRegServiceImpl implements IImageRegService {
    @Resource
    private UserPicMapper userPicMapper;
    private LocaResult locaResult;
    private static ImageToBox imageToBox;
    public ImageRegServiceImpl(){
        String rootPath = this.getClass().getResource("/").getPath();
        rootPath = rootPath.replaceAll("%20", " ");//空格转换的问题，显示的问题。 
//        String webInfoPath = new File(rootPath).getParent();// WEB-INF 目录的物理路径
//        String filepath = new File(webInfoPath).getParent();// WebRoot 目录的物理路径
        String filepath =new File(rootPath).getPath();
        filepath = filepath.replaceAll("\\\\", "/")+"/static/model";
        File file = new File(filepath);
        if (!file.exists()){
            file.mkdir();
        }
        try{
            imageToBox = new ImageToBox(filepath+"/model_best.pb");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    private List<String> getLoca(List<LocaResult> res){
        List<String> ans = new ArrayList<>();
        String xmin = "";
        String ymin = "";
        String xmax = "";
        String ymax = "";
        for (LocaResult locaResult : res) {
            if (xmin.length()==0) xmin = xmin+locaResult.getLeft_x();
            else xmin = xmin+" "+locaResult.getLeft_x();
            if (ymin.length()==0) ymin = ymin+locaResult.getLeft_y();
            else ymin = ymin+" "+locaResult.getLeft_y();
            if (xmax.length()==0) xmax = xmax+locaResult.getRight_x();
            else xmax = xmax+" "+locaResult.getRight_x();
            if (ymax.length()==0) ymax = ymax+locaResult.getRight_y();
            else ymax = ymax+" "+locaResult.getRight_y();
        }
        ans.add(xmin);
        ans.add(ymin);
        ans.add(xmax);
        ans.add(ymax);
        return ans;
    }
    private int add( List<LocaResult> res,String username,String path){
        UserPic userPic = new UserPic();
        userPic.setPath(path);
        userPic.setUsername(username);
        List<String> ans = getLoca(res);
        userPic.setConf(false);
        userPic.setXmin(ans.get(0));
        userPic.setYmin(ans.get(1));
        userPic.setXmax(ans.get(2));
        userPic.setYmax(ans.get(3));
        userPicMapper.insert(userPic);
        return userPic.getPicid();
    }
    @Override
    public RSNAResult getRes(File file, String username, String path){
        List<LocaResult> res = new ArrayList<>();
        try{
            float[][][] pred = imageToBox.predict(ImageProcess.readImage(file));
            for (int j =0;j<200;j++)
                if (pred[0][j][1]>0.35){
                    LocaResult x = new LocaResult();
                    x.setConf(Double.valueOf(pred[0][j][1]));
                    x.setLeft_x(Double.valueOf(pred[0][j][2]));
                    x.setLeft_y(Double.valueOf(pred[0][j][3]));
                    x.setRight_x(Double.valueOf(pred[0][j][4]));
                    x.setRight_y(Double.valueOf(pred[0][j][5]));
                    res.add(x);
                }
        }catch (Exception e){
            System.out.println(e);
        }
        int picid = add(res,username,path);
        return RSNAResult.build(picid,"OK",res);
    }

    public boolean addNewRes(LocalRequest localRequest){
        List<String> ans = getLoca(localRequest.getLocalRequests());
        UserPic userPic = userPicMapper.selectByPrimaryKey(localRequest.getPicid());
        if (userPic==null) return false;
        userPic.setConf(true);
        userPic.setXmin(ans.get(0));
        userPic.setYmin(ans.get(1));
        userPic.setXmax(ans.get(2));
        userPic.setYmax(ans.get(3));
        userPicMapper.updateByPrimaryKey(userPic);
        return true;
    }

    public LocaResult getRes(){
        locaResult = new LocaResult();
        locaResult.setLeft_x(20.0);
        locaResult.setLeft_y(50.0);
        locaResult.setRight_x(100.0);
        locaResult.setRight_y(100.0);
        return locaResult;
    }
}
