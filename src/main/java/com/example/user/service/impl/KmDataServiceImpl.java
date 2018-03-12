package com.example.user.service.impl;

import com.example.user.entity.KmData;
import com.example.user.entity.User;
import com.example.user.mapper.KmDataMapper;
import com.example.user.mapper.UserMapper;
import com.example.user.service.KmDataService;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户服务层接口实现类
 *
 * @author QuiFar
 * @version V1.0
 */
@Service
public class KmDataServiceImpl implements KmDataService {


    @Autowired
    private KmDataMapper kmDataMapper;

    @Override
    public Integer remove(Integer id) {
        return kmDataMapper.remove(id);
    }

    @Override
    public Integer removeBatch(String[] ids) {
        return kmDataMapper.removeBatch(ids);
    }

    @Override
    public Integer update(KmData kmData) {
        return kmDataMapper.update(kmData);
    }

    @Override
    public Integer add(KmData kmData) {
        return kmDataMapper.add(kmData);
    }

    @Override
    public Integer addBacth(List<KmData> list) {
        return kmDataMapper.addBacth(list);
    }

    @Override
    public KmData get(Integer id) {
        return kmDataMapper.get(id);
    }

    @Override
    public List<KmData> list(Map<String, Object> params) {
        return kmDataMapper.list(params);
    }

    @Override
    public Integer countPage(Map<String, Object> params) {
        return kmDataMapper.countPage(params);
    }
}
