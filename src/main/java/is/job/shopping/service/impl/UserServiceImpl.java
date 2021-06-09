package is.job.shopping.service.impl;

import is.job.shopping.model.User;
import is.job.shopping.model.UserRole;
import is.job.shopping.model.enums.RoleEnum;
import is.job.shopping.model.service.UserRegisterServiceModel;
import is.job.shopping.repository.UserRepository;
import is.job.shopping.repository.UserRoleRepository;
import is.job.shopping.service.UserDetailsService;
import is.job.shopping.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    @Override
    public void registerAndLoginUser(UserRegisterServiceModel model) {
        User user = this.modelMapper.map(model, User.class);
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        ArrayList<UserRole> roles = new ArrayList<>();
        if (userRepository.count() == 0) {
            roles.add(this.userRoleRepository.findByRole(RoleEnum.ADMIN).get());
        } else {
            roles.add(this.userRoleRepository.findByRole(RoleEnum.USER).get());
        }
        user.setRoles(roles);
        this.userRepository.save(user);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean emailExists(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
}
