package fr.denoria.client.space.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Candidature")
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(columnDefinition = "varchar(32)")
    private String name;

    @NotNull
    private int age;

    @NotNull
    @Column(columnDefinition = "text", name = "personnal_description")
    private String personnalDescription;

    @NotNull
    @Column(columnDefinition = "varchar(6)", name = "how_long_play_minecraft")
    private String howLongPlayMinecraft;

    @NotNull
    @Column(columnDefinition = "varchar(64)", name = "hours_per_week")
    private String hoursPerWeek;

    @NotNull
    @Column(columnDefinition = "text")
    private String why;

    @NotNull
    @Column(columnDefinition = "varchar(64)", name = "desired_role")
    private String desiredRole;

    @OneToOne
    private User user;

    @NotNull
    @Column(columnDefinition = "varchar(32)")
    private String status;

    public Candidature() {
    }

    public Candidature(String name, int age, String personnalDescription,
                       String howLongPlayMinecraft, String hoursPerWeek,
                       String why, String desiredRole, User user, String status) {
        this.name = name;
        this.age = age;
        this.personnalDescription = personnalDescription;
        this.howLongPlayMinecraft = howLongPlayMinecraft;
        this.hoursPerWeek = hoursPerWeek;
        this.why = why;
        this.desiredRole = desiredRole;
        this.user = user;
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Candidature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", personnalDescription='" + personnalDescription + '\'' +
                ", howLongPlayMinecraft='" + howLongPlayMinecraft + '\'' +
                ", hoursPerWeek='" + hoursPerWeek + '\'' +
                ", why='" + why + '\'' +
                ", desiredRole='" + desiredRole + '\'' +
                ", user=" + user +
                '}';
    }
}
