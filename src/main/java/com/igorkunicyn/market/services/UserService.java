package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Role;
import com.igorkunicyn.market.entities.User;
import com.igorkunicyn.market.repositories.RoleRepository;
import com.igorkunicyn.market.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private RoleRepository roleRepo;

    @Autowired
    public void setRoleRepo(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

//    public void setEm(EntityManager em) {
//        this.em = em;
//    }

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    @Autowired
//    private EntityManager em;


    public Page<User> findPaginated(int pageNum) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return userRepo.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");

        }
        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = Optional.ofNullable(userRepo.findById(userId));
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepo.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
//        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role(1L, "ROLE_USER"));
        user.addRoles(roleRepo.findByName("ROLE_USER"));
//        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try{
//            userRepo.save(user);
            userRepo.saveAndFlush(user);

        }catch (ConstraintViolationException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean deleteUser(Long userId) {
        User user = userRepo.findById(userId);
        if (user != null) {
            userRepo.delete(user);
            return true;
        }
        return false;
    }

    public boolean editUser(Long userId) {
        User user = userRepo.findById(userId);
        if (user != null) {
//            Set<Role> roles = user.getRoles();
//            roles.add(new Role(2L,"ROLE_ADMIN"));
            user.addRoles(roleRepo.findByName("ROLE_ADMIN"));
//            user.setRoles(roles);
            userRepo.save(user);
//            for (Role role: roles){
//                System.out.println(role.getName());
//            }
//            Role role = roleRepo.findByName("ROLE_ADMIN");
//            role.getUsers().add(user);
            return true;
        }
        return false;
    }

}
