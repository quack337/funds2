package fund.dto;

public class Todo {

    int id;
    int userId;
    String userName;
    String createDate;
    String dueDate;
    String dueDate2;
    String message;
    int repeat;
    int alertBefore;
    String confirmDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate2() {
        return dueDate2;
    }

    public void setDueDate2(String dueDate2) {
        this.dueDate2 = dueDate2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getAlertBefore() {
        return alertBefore;
    }

    public void setAlertBefore(int alertBefore) {
        this.alertBefore = alertBefore;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

}