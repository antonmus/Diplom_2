public class PrepareData {
    private String tempName;
    private String tempPass;
    private String tempEmail;

    public PrepareData() {
    }
    public String getTempName() {
        return tempName;
    }
    public String getTempEmail() {
        return tempEmail;
    }
    public String getTempPass() {
        return tempPass;
    }
    public void preparLogin(User user){
        tempName = user.getName();
        user.setName(null);
    }
    public void rePreparLogin(User user){
        user.setName(tempName);
    }
    public void saveDataUser(User user){
        this.tempName = user.getName();
        this.tempPass = user.getPassword();
        this.tempEmail = user.getEmail();
    }
}
