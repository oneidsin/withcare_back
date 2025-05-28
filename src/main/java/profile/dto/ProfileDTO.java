package profile.dto;

public class ProfileDTO {
    private String id;
    private String name;
    private String year;
    private String gender;
    private String email;
    private Integer cancer_idx;
    private Integer stage_idx;
    private boolean profile_yn;
    private String intro;
    private String profile_photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCancer_idx() {
        return cancer_idx;
    }

    public void setCancer_idx(Integer cancer_idx) {
        this.cancer_idx = cancer_idx;
    }

    public Integer getStage_idx() {
        return stage_idx;
    }

    public void setStage_idx(Integer stage_idx) {
        this.stage_idx = stage_idx;
    }

    public boolean isProfile_yn() {
        return profile_yn;
    }

    public void setProfile_yn(boolean profile_yn) {
        this.profile_yn = profile_yn;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }
} 