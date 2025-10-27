package rw.ac.ilpd.mis.shared.security;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 12/07/2025
 */

import java.security.Principal;

public class MisPrincipal implements Principal {

    private String name;

    public MisPrincipal(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Principal name can not be null");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MisPrincipal that = (MisPrincipal) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
