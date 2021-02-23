package irisi.digitalaube.checkart.api.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private Long id;
    @SerializedName("nom")
    private String nom;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("tel")
    private String tel;
    @SerializedName("role")
    private Role role;


    public User() {
    }

    public User(Long id, String nom, String email, String password, String tel) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }

    public Role getRole() {
        return role;
    }
}
