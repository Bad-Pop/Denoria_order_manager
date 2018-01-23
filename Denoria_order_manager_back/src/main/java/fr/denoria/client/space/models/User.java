package fr.denoria.client.space.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(columnDefinition = "varchar(16)", unique = true)
    private String pseudo;

    @NotNull
    @Column(unique = true)
    private String mail;

    @NotNull
    @Column(columnDefinition = "char(64)")
    private String password;

    @NotNull
    @Column(columnDefinition = "varchar(255) default 'https://denoria.fr/minecraft-build-team/images/logo.png'")
    private String avatarLink;

    @Column(unique = true)
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.AUTO)
    private String token;

    @NotNull
    @Column(unique = true, columnDefinition = "varchar(32)")
    private String skype;

    private boolean active = true;

    public User() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                ", token='" + token + '\'' +
                ", skype='" + skype + '\'' +
                '}';
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
