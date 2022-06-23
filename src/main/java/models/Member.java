package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 会員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_MEM)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_MEM_GET_ALL,
            query = JpaConst.Q_MEM_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_MEM_COUNT,
            query = JpaConst.Q_MEM_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_MEM_COUNT_REGISTERED_BY_CODE,
            query = JpaConst.Q_MEM_COUNT_REGISTERED_BY_CODE_DEF),

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Member {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.MEM_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会員番号
     */
    @Column(name = JpaConst.MEM_COL_CODE, nullable = false, unique = true)
    private String code;

    /**
     * 氏名
     */
    @Column(name = JpaConst.MEM_COL_NAME, nullable = false)
    private String name;

    /**
     * パスワード
     */
    @Column(name = JpaConst.MEM_COL_PASS, length = 64, nullable = false)
    private String password;

    /**
     *登録日時
     */
    @Column(name = JpaConst.MEM_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.MEM_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;
}