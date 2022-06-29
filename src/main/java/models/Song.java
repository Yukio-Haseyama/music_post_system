package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 日報データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_SONGS)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_SONG_GET_ALL,
            query = JpaConst.Q_SONG_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_SONG_COUNT,
            query = JpaConst.Q_SONG_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_SONG_GET_ALL_MINE,
            query = JpaConst.Q_SONG_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_SONG_COUNT_ALL_MINE,
            query = JpaConst.Q_SONG_COUNT_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Song {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.SONGS_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 楽曲を登録した会員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.SONGS_COL_MEM, nullable = false)
    private Member member;

    /**
     * いつの楽曲かを示す日付
     */
    @Column(name = JpaConst.SONGS_COL_SONGS_DATE, nullable = false)
    private LocalDate songDate;

    /**
     * 楽曲のタイトル
     */
    @Column(name = JpaConst.SONGS_COL_TITLE, length = 255, nullable = false)
    private String title;

    /**
     * 楽曲のURL
     */
    @Lob
    @Column(name = JpaConst.SONGS_COL_URL, nullable = false)
    private String url;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.SONGS_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.SONGS_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

}