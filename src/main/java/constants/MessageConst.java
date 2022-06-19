package constants;

//各出力メッセージを定義する列挙型クラス

public enum MessageConst {

    //認証
    I_LOGINED("ログインしました"),
    E_LOGINED("ログインに失敗しました"),
    I_LOGOUT("ログアウトしました"),

    //DB更新
    I_REGISTERED("登録が完了しました"),
    I_UPDATED("更新が完了しました"),
    I_DELETED("削除が完了しました"),

    //バリデーション
    M_NONAME("名前を入力してください"),
    M_NOPASSWORD("パスワードを入力してください"),
    M_NOMEM_CODE("会員番号を入力してください"),
    M_MEM_CODE_EXIST("入力された会員番号の情報は既に存在しています"),
    M_NOTITLE("タイトルを入力してください"),
    M_NOURL("URLを入力してください");

    //文字列
    private final String text;

    //コンストラクタ
    private MessageConst(final String text) {
        this.text = text;
    }

    //値（文字列）取得
    public String getMessage() {
        return this.text;
    }
}
