package rw.ac.ilpd.mis.shared.config;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 16/07/2025
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PrivilegeConfig {
    CREATE_ROLE("Create RolePriv", "/roles/create"),
    DELETE_ROLE("Delete RolePriv", "/roles/delete/{roleId}"),
    EDIT_ROLE("Edit RolePriv", "/roles/edit/{roleId}"),
    GET_ROLE("Get RolePriv", "/roles/{roleId}");

    private final String key;
    private final String value;

    PrivilegeConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    // Used when serializing enum to JSON
    @JsonValue
    public String toJson() {
        return this.key;
    }

    // Used when deserializing enum from JSON
    @JsonCreator
    public static PrivilegeConfig fromJson(String key) {
        for (PrivilegeConfig privilege : PrivilegeConfig.values()) {
            if (privilege.key.equals(key)) {
                return privilege;
            }
        }
        throw new IllegalArgumentException("Unknown key: " + key);
    }

    public static PrivilegeConfig fromValue(String value) {
        for (PrivilegeConfig privilege : PrivilegeConfig.values()) {
            if (privilege.value.equalsIgnoreCase(value)) {
                return privilege;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    public static Map<String, String> getAll() {
        Map<String, String> map = new LinkedHashMap<>();
        for (PrivilegeConfig privilege : PrivilegeConfig.values()) {
            map.put(privilege.getKey(), privilege.getValue());
        }
        return map;
    }
}
