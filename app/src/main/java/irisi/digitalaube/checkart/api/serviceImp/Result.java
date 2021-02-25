package irisi.digitalaube.checkart.api.serviceImp;

import irisi.digitalaube.checkart.api.model.User;

public class Result {
    private User user;
    private Integer status;

    public Result() {
    }

    public Result(User user, Integer status) {
        this.user = user;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}