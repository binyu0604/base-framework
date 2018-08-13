package com.liugh.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liugh.config.SpringContextBean;
import com.liugh.entity.Role;
import com.liugh.entity.User;
import com.liugh.entity.UserToRole;
import com.liugh.exception.UnauthorizedException;
import com.liugh.service.IMenuService;
import com.liugh.service.IRoleService;
import com.liugh.service.IUserService;
import com.liugh.service.IUserToRoleService;
import com.liugh.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liugh
 * @since 2018-05-03
 */
public class MyRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(MyRealm.class);
    private IUserService userService;
    private IUserToRoleService userToRoleService;
    private IMenuService menuService;
    private IRoleService roleService;
    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (userToRoleService == null) {
            this.userToRoleService = SpringContextBean.getBean(IUserToRoleService.class);
        }
        if (menuService == null) {
            this.menuService = SpringContextBean.getBean(IMenuService.class);
        }
        if (roleService == null) {
            this.roleService = SpringContextBean.getBean(IRoleService.class);
        }

        String userNo = JWTUtil.getUserNo(principals.toString());
        User user = userService.selectById(userNo);
        UserToRole userToRole = userToRoleService.selectByUserNo(user.getUserNo());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        ArrayList<String> pers = new ArrayList<>();
        Set<String> roleNameSet = new HashSet<>();
        Role role = roleService.selectOne(new EntityWrapper<Role>().eq("role_code", userToRole.getRoleCode()));
        roleNameSet.add(role.getRoleName());
        //添加控制角色级别的权限
        simpleAuthorizationInfo.addRoles(roleNameSet);
        /*
        //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
        ArrayList<String> pers = new ArrayList<>();
        List<Menu> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
        for (Menu per : menuList) {
             if (!ComUtil.isEmpty(per.getCode())) {
                  pers.add(String.valueOf(per.getCode()));
              }
        }
        Set<String> permission = new HashSet<>(pers);
        simpleAuthorizationInfo.addStringPermissions(permission);
        */

        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (userService == null) {
            this.userService = SpringContextBean.getBean(IUserService.class);
        }
        String token = (String) auth.getCredentials();

        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            //throw new UnauthorizedException("token invalid");
            throw new UnauthorizedException("token认证失败！");
        }
        User userBean = userService.selectById(userNo);
        if (userBean == null) {
            throw new UnauthorizedException("该用户不存在！");
            //throw new UnauthorizedException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userNo, userBean.getPassWord())) {
            throw new UnauthorizedException("用户名或密码错误！");
            //throw new UnauthorizedException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }

//    /**
//     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String username = JWTUtil.getUsername(principals.toString());
//        UserBean user = userService.getUser(username);
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.addRole(user.getRole());
//        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
//        simpleAuthorizationInfo.addStringPermissions(permission);
//        return simpleAuthorizationInfo;
//    }
//
//    /**
//     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
//        String token = (String) auth.getCredentials();
//        // 解密获得username，用于和数据库进行对比
//        String username = JWTUtil.getUsername(token);
//        if (username == null) {
//            throw new AuthenticationException("token invalid");
//        }
//
//        UserBean userBean = userService.getUser(username);
//        if (userBean == null) {
//            throw new AuthenticationException("User didn't existed!");
//        }
//
//        if (! JWTUtil.verify(token, username, userBean.getPassword())) {
//            throw new AuthenticationException("Username or password error");
//        }
//
//        return new SimpleAuthenticationInfo(token, token, "my_realm");
//    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
