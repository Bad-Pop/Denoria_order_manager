package fr.denoria.client.space.models.dto;

import fr.denoria.client.space.models.Admin;

public class AdminDto {

    private String pseudo;

    private String mail;

    private String avatarLink;

    private String token;

    private String skype;

    private String team;

    public AdminDto(Admin admin) {
        this.pseudo = admin.getPseudo();
        this.mail = admin.getMail();
        this.avatarLink = admin.getAvatarLink();
        this.token = admin.getToken();
        this.skype = admin.getSkype();
        this.team = admin.getTeam();
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "pseudo='" + pseudo + '\'' +
                ", mail='" + mail + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                ", token='" + token + '\'' +
                ", skype='" + skype + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
