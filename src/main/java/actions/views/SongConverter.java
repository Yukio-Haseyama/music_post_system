package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Song;

/**
 * 楽曲データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */

public class SongConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param sv SongViewのインスタンス
     * @return Songのインスタンス
     */
    public static Song toModel(SongView sv) {
        return new Song(
                sv.getId(),
                MemberConverter.toModel(sv.getMember()),
                sv.getSongDate(),
                sv.getTitle(),
                sv.getUrl(),
                sv.getCreatedAt(),
                sv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param s Songのインスタンス
     * @return SongViewのインスタンス
     */
    public static SongView toView(Song s) {

        if (s == null) {
            return null;
        }

        return new SongView(
                s.getId(),
                MemberConverter.toView(s.getMember()),
                s.getSongDate(),
                s.getTitle(),
                s.getUrl(),
                s.getCreatedAt(),
                s.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<SongView> toViewList(List<Song> list) {
        List<SongView> svs = new ArrayList<>();

        for (Song s : list) {
            svs.add(toView(s));
        }

        return svs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param s DTOモデル(コピー先)
     * @param sv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Song s, SongView sv) {
        s.setId(sv.getId());
        s.setMember(MemberConverter.toModel(sv.getMember()));
        s.setSongDate(sv.getSongDate());
        s.setTitle(sv.getTitle());
        s.setUrl(sv.getUrl());
        s.setCreatedAt(sv.getCreatedAt());
        s.setUpdatedAt(sv.getUpdatedAt());

    }

}


