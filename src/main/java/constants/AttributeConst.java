package constants;

//画面の項目値等を定義するEnum(列挙型）クラス

public enum AttributeConst {

    Entity("entity"),

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の会員
    LOGIN_MEM("login_member"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //会員管理
    MEMBER("member"),
    MEMBERS("members"),
    MEM_COUNT("members_count"),
    MEM_ID("id"),
    MEM_CODE("code"),
    MEM_PASS("password"),
    MEM_NAME("name"),

    //楽曲投稿
    SONG("song"),
    SONGS("songs"),
    SONGS_COUNT("songs_count"),
    SONGS_ID("id"),
    SONG_DATE("song_date"),
    SONG_TITLE("title"),
    SONG_CONTENT("content"),
    SONG_URL("url");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }


}
