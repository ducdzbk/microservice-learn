package com.fds.orderservice.config;

import com.fds.flex.common.ultility.Validator;
import com.fds.flex.common.utility.string.StringPool;
import com.fds.flex.common.utility.string.StringUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author trungnt
 */
public class KeycloakRealmScopeRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public Collection<GrantedAuthority> convert(Jwt jwt) {
        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        String[] scopes = Validator.isNotNull((String) jwt.getClaim("scope"))
                ? StringUtil.split((String) jwt.getClaim("scope"), StringPool.SPACE)
                : new String[0];
        List<GrantedAuthority> grantedAuthoritys = Arrays.stream(scopes).map(scope -> "SCOPE_" + scope)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        grantedAuthoritys.addAll(((List<String>) realmAccess.get("roles")).stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return grantedAuthoritys;
    }

}
