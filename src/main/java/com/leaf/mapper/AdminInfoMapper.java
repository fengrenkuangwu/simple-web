package com.leaf.mapper;

import com.leaf.entity.AdminInfo;

public interface AdminInfoMapper {
    int deleteByPrimaryKey(Long adminId);

    int insert(AdminInfo record);

    int insertSelective(AdminInfo record);

    AdminInfo selectByPrimaryKey(Long adminId);

    int updateByPrimaryKeySelective(AdminInfo record);

    int updateByPrimaryKey(AdminInfo record);
    /**
     * 登录查询
     * @param record
     * @return
     */
    AdminInfo selectForLogin(AdminInfo record);
}