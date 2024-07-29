package vutran.my_first_project_spring_boot.management_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vutran.my_first_project_spring_boot.management_student.Dao.UserRepository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;
import vutran.my_first_project_spring_boot.management_student.Entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImple implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImple(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserByIdentity(String identity) {
        return userRepository.findUserByIdentity(identity);
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByUserName(username);
    }

    @Override
    public List<User> getListUserByPosition(String position) {
        return userRepository.findAllUserByPosition(position);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userExist = userRepository.findUserByUserName(username);
        if(userExist == null){
            throw new UsernameNotFoundException("Invalid Username Or Password");
        }
         return new org.springframework.security.core.userdetails.User(userExist.getUsername(), userExist.getPassword(), roleToAuthorities(userExist.getCollectionAuthority()));
    }

    private Collection<? extends GrantedAuthority> roleToAuthorities(Collection<Authority> authorities){
        return authorities.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
