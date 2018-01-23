package fr.denoria.client.space.models.dto;

import fr.denoria.client.space.models.Candidature;

public class CandidatureDto {

    private int id;

    private String name;

    private int age;

    private String personnalDescription;

    private String howLongPlayMinecraft;

    private String hoursPerWeek;

    private String why;

    private String desiredRole;

    private String userPseudo;

    private String status;

    public CandidatureDto(Candidature candidature) {
        this.id = candidature.getId();
        this.name = candidature.getName();
        this.age = candidature.getAge();
        this.personnalDescription = candidature.getPersonnalDescription();
        this.howLongPlayMinecraft = candidature.getHowLongPlayMinecraft();
        this.hoursPerWeek = candidature.getHoursPerWeek();
        this.why = candidature.getWhy();
        this.desiredRole = candidature.getDesiredRole();
        this.userPseudo = candidature.getUser().getPseudo();
        this.status = candidature.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPersonnalDescription() {
        return personnalDescription;
    }

    public void setPersonnalDescription(String personnalDescription) {
        this.personnalDescription = personnalDescription;
    }

    public String getHowLongPlayMinecraft() {
        return howLongPlayMinecraft;
    }

    public void setHowLongPlayMinecraft(String howLongPlayMinecraft) {
        this.howLongPlayMinecraft = howLongPlayMinecraft;
    }

    public String getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(String hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getDesiredRole() {
        return desiredRole;
    }

    public void setDesiredRole(String desiredRole) {
        this.desiredRole = desiredRole;
    }

    public String getUserPseudo() {
        return userPseudo;
    }

    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CandidatureDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", personnalDescription='" + personnalDescription + '\'' +
                ", howLongPlayMinecraft='" + howLongPlayMinecraft + '\'' +
                ", hoursPerWeek='" + hoursPerWeek + '\'' +
                ", why='" + why + '\'' +
                ", desiredRole='" + desiredRole + '\'' +
                ", userPseudo='" + userPseudo + '\'' +
                '}';
    }
}
