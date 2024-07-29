package com.apibanking.accountopening.fixeddeposit.dto;

public class MaturityInstruction {
    private boolean doNotRenew;
    private boolean renewPrincipalAndInterest;
    private boolean renewPrincipalAndPayInterest;
    public boolean isDoNotRenew() {
        return doNotRenew;
    }
    public void setDoNotRenew(boolean doNotRenew) {
        this.doNotRenew = doNotRenew;
    }
    public boolean isRenewPrincipalAndInterest() {
        return renewPrincipalAndInterest;
    }
    public void setRenewPrincipalAndInterest(boolean renewPrincipalAndInterest) {
        this.renewPrincipalAndInterest = renewPrincipalAndInterest;
    }
    public boolean isRenewPrincipalAndPayInterest() {
        return renewPrincipalAndPayInterest;
    }
    public void setRenewPrincipalAndPayInterest(boolean renewPrincipalAndPayInterest) {
        this.renewPrincipalAndPayInterest = renewPrincipalAndPayInterest;
    }

}
