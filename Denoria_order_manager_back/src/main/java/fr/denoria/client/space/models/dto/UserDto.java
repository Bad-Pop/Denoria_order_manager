package fr.denoria.client.space.models.dto;

import fr.denoria.client.space.models.User;

public class UserDto {

    private String pseudo;

    private String mail;

    private String avatarLink;

    private String token;

    private String skype;

    private Boolean active;

    public UserDto(User user) {
        this.pseudo = user.getPseudo();
        this.mail = user.getMail();
        this.skype = user.getSkype();
        this.avatarLink = user.getAvatarLink();
        this.token = user.getToken();
        this.skype = user.getSkype();
        this.active = user.isActive();
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
