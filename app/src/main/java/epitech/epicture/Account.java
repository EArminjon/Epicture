package epitech.epicture;

import java.io.Serializable;

public class Account implements Serializable {
    public enum AccountState {
        CONNECTED,
        NOTCONNECTED,
        DISCONNECTED
    }

    private String musername = null;
    private String maccessToken = null;
    private String mrefreshToken = null;
    private AccountState maccountState = AccountState.NOTCONNECTED;

    public Account() {

    }

    public AccountState getAccountState() {
        return maccountState;
    }

    public void setAccountState(AccountState accountState) {
        this.maccountState = accountState;
    }

    public String getUsername() {
        return musername;
    }

    public void setUsername(String musername) {
        this.musername = musername;
    }

    public String getAccessToken() {
        return maccessToken;
    }

    public void setAccessToken(String maccessToken) {
        this.maccessToken = maccessToken;
    }

    public String getRefreshToken() {
        return mrefreshToken;
    }

    public void setRefreshToken(String mrefreshToken) {
        this.mrefreshToken = mrefreshToken;
    }
}
