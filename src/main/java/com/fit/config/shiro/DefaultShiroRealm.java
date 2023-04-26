package com.fit.config.shiro;

import com.fit.entity.PageData;
import com.fit.service.system.user.UserManager;
import com.fit.util.Const;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;
import java.util.HashSet;

/**
 * Shiro身份认证
 */
public class DefaultShiroRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserManager usersService;

    /**
     * 登录认证,登录信息和用户验证信息验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();                //得到用户名
        String password = new String((char[]) token.getCredentials());  //得到密码
        if (null != username && null != password) {
            return new SimpleAuthenticationInfo(username, password, getName());
        } else {
            return null;
        }
    }

    /*
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String USERNAME = (String) super.getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Session session = Jurisdiction.getSession();
        Collection<String> shiroSet = new HashSet<String>();
        shiroSet = (Collection<String>) session.getAttribute(USERNAME + Const.SHIROSET);
        if (null != shiroSet) {
            info.addStringPermissions(shiroSet);
            return info;
        } else {
            return null;
        }
    }
}
