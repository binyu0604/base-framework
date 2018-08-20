package com.liugh.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liugh.base.Constant;
import com.liugh.entity.Menu;
import com.liugh.entity.User;
import com.liugh.entity.UserToRole;
import com.liugh.mapper.UserMapper;
import com.liugh.service.IMenuService;
import com.liugh.service.IRoleService;
import com.liugh.service.IUserService;
import com.liugh.service.IUserToRoleService;
import com.liugh.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserToRoleService userToRoleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService roleService;

    @Override
//    @Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
    public User getUserByUserName(String username) {
        System.out.println("执行getUserByUserName方法了.....");
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("user_name={0}", username);
        return this.selectOne(ew);
    }

    @Override
    public User getUserByMobile(String mobile) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("mobile", mobile);
        return this.selectOne(ew);
    }

    @Override
    public boolean register(User user, String  roleCode) {
        //user.setUserNo(GenerationSequenceUtil.generateUUID("user"));
        user.setCreateTime(System.currentTimeMillis());
        boolean result = this.insert(user);
        if (result) {
            UserToRole userToRole  = new UserToRole();
            userToRole.setUserNo(user.getUserNo());
            userToRole.setRoleCode(roleService.getRoleByRoleName(roleCode).getRoleCode());
            result = userToRoleService.insert(userToRole);
        }
        return result;
    }

    @Override
    public Map<String, Object> getLoginUserAndMenuInfo(User user) {
        Map<String, Object> result = new HashMap<>();
        UserToRole userToRole = userToRoleService.selectByUserNo(user.getUserNo());
        user.setToken(JWTUtil.sign(user.getUserNo(), user.getPassWord()));
        result.put("user",user);
        List<Menu> buttonList = new ArrayList<Menu>();
        //根据角色主键查询启用的菜单权限
        List<Menu> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
        List<Menu> retMenuList = menuService.treeMenuList(Constant.ROOT_MENU, menuList);
        for (Menu buttonMenu : menuList) {
            if(buttonMenu.getMenuType() == Constant.TYPE_BUTTON){
                buttonList.add(buttonMenu);
            }
        }
        result.put("menuList",retMenuList);
        result.put("buttonList",buttonList);
        return result;
    }

    @Override
    public boolean deleteByUserNo(String userNo) {
        EntityWrapper<UserToRole> ew = new EntityWrapper<>();
        ew.eq("user_no", userNo);
        boolean resultRole = userToRoleService.delete(ew);
        boolean  resultUser = this.deleteById(userNo);
        return resultRole && resultUser;
    }


}
