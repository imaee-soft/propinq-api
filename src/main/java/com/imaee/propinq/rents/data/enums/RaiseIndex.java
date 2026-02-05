package com.imaee.propinq.rents.data.enums;

public enum RaiseIndex {
    ICL,
    IPC,
    CasaPropia,
    IS;

    public static RaiseIndex getRaiseIndex(String raiseIndex) {
       if ("ICL".equalsIgnoreCase(raiseIndex)) return ICL;
       if ("IPC".equalsIgnoreCase(raiseIndex)) return IPC;
       if ("CasaPropia".equalsIgnoreCase(raiseIndex)) return CasaPropia;
       if ("IS".equalsIgnoreCase(raiseIndex)) return IS;
       throw new RuntimeException();
    }
}