package chess.core.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import chess.application.account.representation.AccountRepresentation;
import chess.application.auth.IAuthAppService;
import chess.application.permission.representation.PermissionRepresentation;
import chess.application.role.representation.RoleRepresentation;
import chess.core.common.Constants;
import chess.core.enums.EnableStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by YJH on 2016/4/5 0005.
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IAuthAppService authAppService;

    /**
     * 权限验证
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ExtendSimplePrincipalCollection principalCollection = (ExtendSimplePrincipalCollection) principals;
        String userName = (String) principalCollection.getPrimaryPrincipal();
        AccountRepresentation user = authAppService.searchByAccountName(userName);
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
        authenticationInfo.setRoles(this.getAllRoles(user));
        authenticationInfo.setStringPermissions(this.getAllPermissions(user));
        return authenticationInfo;
    }

    /**
     * 获取用户所有角色
     *
     * @param user
     * @return
     */
    private Set<String> getAllRoles(AccountRepresentation user) {
        Set<String> roles = new HashSet<String>();
        List<RoleRepresentation> roleList = user.getRoles();
        if (!roleList.isEmpty()) {
            for (RoleRepresentation role : roleList) {
                if (role.getStatus() == EnableStatus.ENABLE) {
                    roles.add(role.getName());
                }
            }
        }
        return roles;
    }

    /**
     * 获取用户所有权限
     *
     * @param user
     * @return
     */
    private Set<String> getAllPermissions(AccountRepresentation user) {
        Set<String> permissions = new HashSet<String>();
        List<RoleRepresentation> roleList = user.getRoles();
        if (!roleList.isEmpty()) {
            for (RoleRepresentation role : roleList) {
                if (role.getStatus() == EnableStatus.ENABLE) {
                    List<PermissionRepresentation> permissionList = role.getPermissions();
                    if (!permissionList.isEmpty()) {
                        for (PermissionRepresentation permission : permissionList) {
                            if (permission.getStatus() == EnableStatus.ENABLE) {
                                permissions.add(permission.getName());
                            }
                        }
                    }
                }
            }
        }
        return permissions;
    }

    /**
     * 登陆验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AccountRepresentation user = authAppService.searchByAccountName(token.getPrincipal().toString());

        if (null == user) {
            throw new UnknownAccountException(); //用户不存在
        } else {
            if (user.getStatus() == EnableStatus.DISABLE) {
                throw new LockedAccountException(); //用户被禁用
            }
        }

        ExtendSimpleAuthenticationInfo authenticationInfo = new ExtendSimpleAuthenticationInfo(
                user.getUserName(),
                user.getPassword(),
                new ExtendSimpleByteSource(user.getSalt()),
                getName());

        return authenticationInfo;
    }
}
