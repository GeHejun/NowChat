package com.ghj.common.util;

public class MachineSerialNumber {
    public static Long get() {
        int serialNumber = (int)Math.random() * 31;
        String machineSerialNumber = PropertiesUtil.getInstance().getValue("machine.serial.number", String.valueOf(serialNumber));
        return Long.valueOf(machineSerialNumber);
    }
}
