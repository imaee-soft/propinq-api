package com.imaee.propinq.rents.data.enums;

import lombok.Getter;

@Getter
public enum RaiseIndex {
    ICL("icl"),
    IPC("ipc"),
    CasaPropia("casa_propia"),
    IS("is");

    private final String rate;

    RaiseIndex(String rate) {
        this.rate = rate;
    }

    public static RaiseIndex getRaiseIndex(String raiseIndex) {
       if ("ICL".equalsIgnoreCase(raiseIndex)) return ICL;
       if ("IPC".equalsIgnoreCase(raiseIndex)) return IPC;
       if ("CasaPropia".equalsIgnoreCase(raiseIndex)) return CasaPropia;
       if ("IS".equalsIgnoreCase(raiseIndex)) return IS;
       throw new RuntimeException();
    }
}