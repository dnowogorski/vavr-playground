package org.dnowogorski;

public class Coupon {
    private final String id;
    private final long stake;

    Coupon(String id, long stake) {
        this.id = id;
        this.stake = stake;
    }

    public String getId() {
        return id;
    }

    public long getStake() {
        return stake;
    }
}
