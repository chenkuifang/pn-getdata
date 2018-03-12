package com.example.user.mapper;

import com.example.user.entity.KmData;
import com.example.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户表映射接口
 *
 * @author QuiFar
 * @version V1.0
 */
@Mapper
public interface KmDataMapper {
    /**
     * 根据主键删除单个对象
     *
     * @param id 主键
     * @return
     */
    Integer remove(Integer id);

    /**
     * 根据主键数据批量删除用户
     *
     * @param ids 主键
     * @return
     */
    Integer removeBatch(String[] ids);

    /**
     * 根据主键ID更新单个对象
     *
     * @param kmData 需要更新的对象
     * @return
     */
    Integer update(KmData kmData);

    /**
     * 新增
     *
     * @param kmData
     * @return
     */
    Integer add(KmData kmData);


    /**
     * 批量增加数据
     *
     * @param list
     * @return
     */
    Integer addBacth(List<KmData> list);

    /**
     * 根据主键获取单个对象
     *
     * @param id
     * @return
     */
    KmData get(Integer id);


    /**
     * 根据条件获取列表
     *
     * @param params
     * @return
     */
    List<KmData> list(Map<String, Object> params);


    /**
     * 根据条件获取用户列表总行数(一般提供分页使用)
     *
     * @param params
     * @return
     */
    Integer countPage(Map<String, Object> params);

}
