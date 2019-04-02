package com.srs.common;

import java.io.Serializable;

public class PairValueAlarm implements Serializable {
    private Integer value;
    private Integer error;

    public PairValueAlarm(Integer value, Integer error) {
        this.value = value;
        this.error = error;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getError() {
        return error;
    }
}
