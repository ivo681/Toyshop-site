package is.job.shopping.service;


import is.job.shopping.model.service.UserRegisterServiceModel;

public interface UserService {
    void registerAndLoginUser(UserRegisterServiceModel model);

    boolean emailExists(String email);
}
