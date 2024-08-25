package vutran.my_first_project_spring_boot.management_student.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // muốn giữ lại cấu trúc tuần tự hóa nhưng tránh vòng lặp bằng cách sử dụng định danh
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "identity")
    private String identity;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "email")
    private String email;
    @Column(name = "position")
    private String position;
    @Lob
    @Column(name = "avatar")
    private Blob avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_authority", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @JsonManagedReference
    @JsonIgnore
    private Collection<Authority> authority;

    public User() {
    }

    // khong bao gồm pass và enabled vì ảnh hưởng tới Repository khi dùng @query
    public User(String firstName, String lastName,String identity, String address, String phoneNumber, String username, String email, String position, Blob avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identity = identity;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.email = email;
        this.position = position;
        this.avatar = avatar;
    }

    // đầy đủ tham số
    public User(String firstName, String lastName, String address, String phoneNumber, String identity, String username, String password, Boolean enabled, String email, String position, Blob avatar, Collection<Authority> authority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.identity = identity;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.position = position;
        this.avatar = avatar;
        this.authority = authority;
    }

    // không bao gồm authority
    public User(Blob avatar, String position, String email, Boolean enabled, String password, String username, String identity, String phoneNumber, String address, String lastName, String firstName) {
        this.avatar = avatar;
        this.position = position;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.username = username;
        this.identity = identity;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Authority> getCollectionAuthority() {
        return this.authority;
    }

    public void setCollectionAuthority(Collection<Authority> authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", identity='" + identity + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", avatar=" + avatar +
                ", authority=" + authority +
                '}';
    }
}
