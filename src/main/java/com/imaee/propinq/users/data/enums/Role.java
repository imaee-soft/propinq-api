package com.imaee.propinq.users.data.enums;

import java.util.Set;
import java.util.List;

public enum Role {
    TENANT,
    OWNER,
    ADMIN;

    public String getRoleName() {
        return this.name();
    }

    public Set<Permission> getPermissions() {
        return RolePermissionMap.getPermissions(this);
    }

    public List<String> getAuthorities() {
        return getPermissions().stream()
                .map(Permission::name)
                .toList();
    }

    public boolean hasPermission(Permission permission) {
        return getPermissions().contains(permission);
    }
