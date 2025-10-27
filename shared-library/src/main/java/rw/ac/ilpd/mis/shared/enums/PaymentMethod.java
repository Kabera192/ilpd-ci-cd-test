/*
 * PaymentMethod.java
 *
 * Authors: Mohamed Gaye, Kabera Clapton(ckabera6@gmail.com)
 *
 * This is an enum that contains all the possible payment methods that can
 * be used by a user
 * */
package rw.ac.ilpd.mis.shared.enums;

public enum PaymentMethod {
    CASH("Cash"),
    ONLINE("Online");

    private final String _name;

    PaymentMethod(String name) {
        _name = name;
    }

    public boolean equels(String name) {
        return _name.equals(name);
    }

    public String toString()
    {
        return _name;
    }
}
