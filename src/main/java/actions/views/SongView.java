package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 楽曲情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class SongView {

    /**
     * id
     */
    private Integer id;

    /**
     * 楽曲を登録した会員
     */
    private MemberView member;

    /**
     * いつの楽曲かを示す日付
     */
    private LocalDate songDate;

    /**
     * 楽曲のタイトル
     */
    private String title;

    /**
     * 楽曲のURL
     */
    private String url;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;


}
