package com.liugh.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liugh.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
public interface RoleMapper extends BaseMapper<Role> {
    Role getRoleByRoleName(@Param("roleName") String roleName);
}
