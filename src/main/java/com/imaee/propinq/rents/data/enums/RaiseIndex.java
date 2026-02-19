package com.imaee.propinq.rents.data.enums;

import lombok.Getter;

@Getter
public enum RaiseIndex {
    ICL("icl"),
    IPC("ipc"),
    CasaPropia("casa_propia"),
    CAC("cac"),
    CER("cer"),
    IS("is"),
    IPIM("ipim"),
    UVA("uva");

    private final String rate;

    RaiseIndex(String rate) {
        this.rate = rate;
    }

    public static RaiseIndex getRaiseIndex(String raiseIndex) {
       if ("ICL".equalsIgnoreCase(raiseIndex)) return ICL;
       if ("IPC".equalsIgnoreCase(raiseIndex)) return IPC;
       if ("CasaPropia".equalsIgnoreCase(raiseIndex)) return CasaPropia;
       if ("CAC".equalsIgnoreCase(raiseIndex)) return CAC;
       if ("CER".equalsIgnoreCase(raiseIndex)) return CER;
       if ("IS".equalsIgnoreCase(raiseIndex)) return IS;
       if ("IPIM".equalsIgnoreCase(raiseIndex)) return IPIM;
       if ("UVA".equalsIgnoreCase(raiseIndex)) return UVA;
       throw new RuntimeException();
    }
}