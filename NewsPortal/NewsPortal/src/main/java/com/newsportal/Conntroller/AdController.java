package com.newsportal.Conntroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newsportal.Entity.Ad;
import com.newsportal.Mapper.AdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {
    @Autowired
    AdMapper adMapper;
    @GetMapping//通过ID或Type进行索引，输入list返回所有对象
    public List<Ad> GetAds(@RequestParam(required = false) List<String> ID, @RequestParam(required = false) String Type){
        if (ID==null){
            QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Type", Type);
            List<Ad> ads = adMapper.selectList(queryWrapper);
            return ads;
        }
        if (ID.get(0).equals("list")){
            List<Ad> ads = adMapper.selectList(null);
            return ads;
        }
        for (String s:ID){
            int i=Integer.parseInt(s);
            count(i);
        }
        List<Ad> ads = adMapper.selectBatchIds(ID);
        return ads;
    }
    @PostMapping
    public String CreateAd(@RequestParam String Title, @RequestParam String Content, @RequestParam String Type){
        Ad ad = new Ad();
        ad.setTitle(Title);
        ad.setContent(Content);
        ad.setType(Type);
        adMapper.insert(ad);
        return "Successfully Created!";
    }
    @PutMapping
    public String UpdateAd(@RequestParam String ID, @RequestParam(required = false) String Title, @RequestParam(required = false) String Content, @RequestParam(required = false) String Type){
        Ad ad = adMapper.selectById(ID);
        if (Title!=null)ad.setTitle(Title);
        if (Content!=null)ad.setContent(Content);
        if (Type!=null)ad.setType(Type);
        adMapper.updateById(ad);
        return "Successfully Updated!";
    }
    public void count(int ID){
        Ad ad = adMapper.selectById(ID);
        ad.setViewCount(ad.getViewCount()+1);
        adMapper.updateById(ad);
    }
}
