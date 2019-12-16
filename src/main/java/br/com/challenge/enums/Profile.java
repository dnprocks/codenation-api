package br.com.challenge.enums;


public enum Profile {

    ADMIN(0, "ROLE_ADMIN"),
    USER(1, "ROLE_USER");

    private int cod;
    private String description;

    Profile(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static Profile toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (Profile profile : Profile.values()) {
            if (cod.equals(profile.getCod())) {
                return profile;
            }
        }

        throw new IllegalArgumentException("Invalid id: " + cod);
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }
}
