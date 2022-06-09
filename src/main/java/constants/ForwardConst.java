package constants;

//リクエストパラメータの変数名、変数値、jspファイルの名前等画面遷移に関わる値を定義する列挙型クラス

public enum ForwardConst {

    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_MEM("Member"),
    ACT_SONG("Song"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("toppage/index"),
    FW_LOGIN("login/login"),
    FW_MEM_INDEX("members/index"),
    FW_MEM_SHOW("members/show"),
    FW_MEM_NEW("members/new"),
    FW_MEM_EDIT("members/edit"),
    FW_SONGS_INDEX("songs/index"),
    FW_SONGS_SHOW("songs/show"),
    FW_SONGS_NEW("songs/new"),
    FW_SONGS_EDIT("songs/edit");

    //文字列
    private final String text;

    //コンストラクタ
    private ForwardConst (final String text) {
        this.text = text;
    }

    //値（文字列）の取得
    public String getValue() {
        return this.text;
    }


}
