package com.example.bin.filterinterceptordemo.service;

public interface UserPrivilegeService {

    public boolean isInBlackList(String userId);
    public boolean isVIP(String userId);

}
