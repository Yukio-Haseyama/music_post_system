package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Member;

/**
 * 会員データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class MemberConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param mv MemberViewのインスタンス
     * @return Memberのインスタンス
     */
    public static Member toModel(MemberView mv) {

        return new Member(
                mv.getId(),
                mv.getCode(),
                mv.getName(),
                mv.getPassword(),
                mv.getCreatedAt(),
                mv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param m Memberのインスタンス
     * @return MemberViewのインスタンス
     */
    public static MemberView toView(Member m) {

        if(m == null) {
            return null;
        }

        return new MemberView(
                m.getId(),
                m.getCode(),
                m.getName(),
                m.getPassword(),
                m.getCreatedAt(),
                m.getUpdatedAt());

    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<MemberView> toViewList(List<Member> list) {
        List<MemberView> mvs = new ArrayList<>();

        for (Member m : list) {
            mvs.add(toView(m));
        }

        return mvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Member m, MemberView mv) {
        m.setId(mv.getId());
        m.setName(mv.getName());
        m.setPassword(mv.getPassword());
        m.setCreatedAt(mv.getCreatedAt());
        m.setUpdatedAt(mv.getUpdatedAt());

    }
}