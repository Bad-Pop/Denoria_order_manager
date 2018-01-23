package fr.denoria.client.space.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @Column(columnDefinition = "varchar(16)")
    private String pseudo;

    @NotNull
    private String mail;

    @NotNull
    @Column(columnDefinition = "char(64)")
    private String password;

    @NotNull
    @Column(columnDefinition = "varchar(255) default 'http://cravatar.eu/avatar/steve/64.png'")
    private String avatarLink;

    @NotNull
    @Column(unique = true, columnDefinition = "char(36)")
    @GeneratedValue(generator = "system-uuid")
    private String token;

    @NotNull
    @Column(unique = true, columnDefinition = "varchar(32)")
    private String skype;

    @NotNull
    @Column(columnDefinition = "varchar(32)")
    private String team;

    public Admin() {
    }

    public Admin(String pseudo, String mail, String password,
                 String avatarLink, String token, String skype,
                 String team) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.password = password;
        this.avatarLink = avatarLink;
        this.token = token;
        this.skype = skype;
        this.team = team;
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

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "pseudo='" + pseudo + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", skype='" + skype + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
