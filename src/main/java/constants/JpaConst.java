package constants;

//DB関連の項目値を定義するインターフェース
//インターフェースに定義した変数はpublic static final 修飾子がついているとみなされる

public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "music_post_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15;//1ページに表示するレコードの数

    //会員テーブル
    String TABLE_MEM = "members";//テーブル名

    //会員テーブルカラム
    String MEM_COL_ID = "id";//id
    String MEM_COL_CODE = "code";//会員番号
    String MEM_COL_NAME = "name";//名前
    String MEM_COL_PASS = "password";//パスワード
    String MEM_COL_COM = "comment";//簡単な自己紹介欄
    String MEM_COL_CREATED_AT = "created_at";//登録日時
    String MEM_COL_UPDATED_AT = "up_dated_at";//更新日時


    //楽曲テーブル
    String Table_SONGS = "songs";

    //楽曲テーブルカラム
    String SONGS_COL_ID = "id";//id
    String SONGS_COL_MEM = "member_id";//楽曲を投稿した会員のID
    String SONGS_COL_SONG_DATE = "song_date";//いつの楽曲かを示す日付
    String SONGS_COL_TITLE ="title";//楽曲のタイトル
    String SONGS_COL_URL = "url";//楽曲のURL
    String SONGS_COL_CREATED_AT ="created_at";//登録日時
    String SONGS_COL_UPDATED_AT = "updated_at";//更新日時

    //Entity名
    String ENTITY_MEM = "member";//会員
    String ENTITY_SONG ="song";//楽曲

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code";//会員番号
    String JPQL_PARM_PASSWORD = "password";//パスワード
    String JPQL_PARM_MEMBER = "member";//会員

    //NamedQueryのnameとquery
    //全ての会員をIDの降順に取得する
    String Q_MEM_GET_ALL = ENTITY_MEM + ".getAll";//name
    String Q_MEM_GET_ALL_DEF = "SELECT m FROM Member AS m ORDER BY m.id DESC";//query

    //全ての会員の件数を取得する
    String Q_MEM_COUNT = ENTITY_MEM + ".count";
    String Q_MEM_COUNT_DEF = "SELECT COUNT(m) FROM Member AS m";

    //会員番号とハッシュ化済パスワードを条件に未削除の会員を取得する
    String Q_MEM_GET_BY_CODE_AND_PASS = ENTITY_MEM + "getByCodeAndPass";
    String Q_MEM_GET_BY_CODE_AND_PASS_DEF = "SELECT m FROM Member AS m WHERE m.deleteFlagf = 0 AND m.code = :" + JPQL_PARM_CODE + "AND m.password = :" + JPQL_PARM_PASSWORD;

    //指定した会員番号を保持する会員の件数を取得する
    String Q_MEM_COUNT_REGISTERED_BY_CODE = ENTITY_MEM + ".countRegisteredByCode";
    String Q_MEM_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(m) FROM Member AS m WHERE m.code = :" + JPQL_PARM_CODE;;

    //全ての楽曲をIDの降順に取得する
    String Q_SONG_GET_ALL = ENTITY_SONG + "getAll";
    String Q_SONG_GET_ALL_DEF = "SELECT s FROM Song AS s ORDER BY s.id DESC";

    //全ての楽曲の件数を取得する
    String Q_SONG_COUNT =  ENTITY_SONG + "count";
    String Q_SONG_COUNT_DEF = "SELECT COUNT(s) FROM Song AS s";

    //指定した会員が作成した楽曲を全件IDの降順で取得する
    String Q_SONG_GET_ALL_MINE = ENTITY_SONG + "getAllMine";
    String Q_SONG_GET_ALL_MINE_DEF = "SELECT s FROM Song AS s WHERE s.member = :" + JPQL_PARM_MEMBER + "ORDER BY s.id DESC";

    //指定した会員が作成した楽曲の件数を取得する
    String Q_SONG_COUNT_ALL_MINE = ENTITY_SONG + "countAllMine";
    String Q_SONG_COUNT_ALLMINE_DEF = "SELECT COUNT(s) FROM Song AS s WHERE s.member = :" + JPQL_PARM_MEMBER;
}
