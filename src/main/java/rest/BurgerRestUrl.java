package rest;

public enum BurgerRestUrl {

    BASE_URL("https://stellarburgers.nomoreparties.site"),
    CREATE_USER("/api/auth/register"),
    AUTH_USER("/api/auth/login"),
    EDIT_DATA_USER("/api/auth/user"),
    ORDER("/api/orders");

    private final String id;

    BurgerRestUrl(String s) {
        this.id = s;
    }

    public String getId() {
        return id;
    }
}
