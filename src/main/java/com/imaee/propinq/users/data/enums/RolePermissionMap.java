package com.imaee.propinq.users.data.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class RolePermissionMap {

    private static final Map<Role, Set<Permission>> ROLE_PERMISSIONS_MAP = new EnumMap<>(Role.class);

    static {
        ROLE_PERMISSIONS_MAP.put(Role.TENANT, EnumSet.of(
                Permission.CHAT_WITH_OWNER,
                Permission.VIEW_OWN_RENTALS,
                Permission.UPLOAD_RENTAL_DOCUMENTS
        ));

        ROLE_PERMISSIONS_MAP.put(Role.OWNER, EnumSet.of(
                Permission.PUBLISH_PROPERTY,
                Permission.EDIT_PROPERTY,
                Permission.DELETE_PROPERTY,
                Permission.MANAGE_OWN_RENTALS,
                Permission.VIEW_RENTAL_REQUESTS,
                Permission.MARK_RENTAL_AS_RENTED,
                Permission.RENEW_RENTAL,
                Permission.UPLOAD_EXPENSE_DOCUMENTS,
                Permission.CHAT_WITH_OWNER,
                Permission.VIEW_OWN_RENTALS,
                Permission.UPLOAD_RENTAL_DOCUMENTS
        ));

        ROLE_PERMISSIONS_MAP.put(Role.ADMIN, EnumSet.of(
                Permission.ADMIN_MANAGE_USERS,
                Permission.ADMIN_ASSIGN_ROLES,
                Permission.ADMIN_MANAGE_PERMISSIONS,
                Permission.ADMIN_MANAGE_PROPERTY_TYPES,
                Permission.ADMIN_MANAGE_ZONES,
                Permission.ADMIN_MANAGE_RENTAL_STATES,
                Permission.ADMIN_MANAGE_DOCUMENT_TYPES,
                Permission.PUBLISH_PROPERTY,
                Permission.EDIT_PROPERTY,
                Permission.DELETE_PROPERTY,
                Permission.MANAGE_OWN_RENTALS,
                Permission.VIEW_RENTAL_REQUESTS,
                Permission.MARK_RENTAL_AS_RENTED,
                Permission.RENEW_RENTAL,
                Permission.UPLOAD_EXPENSE_DOCUMENTS,
                Permission.CHAT_WITH_OWNER,
                Permission.VIEW_OWN_RENTALS,
                Permission.UPLOAD_RENTAL_DOCUMENTS
        ));
    }

    public static Set<Permission> getPermissions(Role role) {
        return ROLE_PERMISSIONS_MAP.getOrDefault(role, EnumSet.noneOf(Permission.class));
    }

    public static boolean hasPermission(Role role, Permission permission) {
        Set<Permission> permissions = getPermissions(role);
        return permissions.contains(permission);
    }

    public static void addPermission(Role role, Permission permission) {
        ROLE_PERMISSIONS_MAP
                .computeIfAbsent(role, k -> EnumSet.noneOf(Permission.class))
                .add(permission);
    }

    public static void removePermission(Role role, Permission permission) {
        Set<Permission> permissions = ROLE_PERMISSIONS_MAP.get(role);
        if (permissions != null) {
            permissions.remove(permission);
        }
    }

    public static Set<Role> getRolesWithPermission(Permission permission) {
        return ROLE_PERMISSIONS_MAP
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(permission))
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toSet());
    }

    public static Map<Role, Set<Permission>> getAllRolePermissions() {
        return Map.copyOf(ROLE_PERMISSIONS_MAP);
    }
}
