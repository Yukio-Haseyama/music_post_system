package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.MemberConverter;
import actions.views.MemberView;
import actions.views.SongConverter;
import actions.views.SongView;
import constants.JpaConst;
import models.Song;
import models.validators.SongValidator;

/**
 * 楽曲テーブルの操作に関わる処理を行うクラス
 */

public class SongService extends ServiceBase {

    /**
     * 指定した会員が作成した楽曲データを、指定されたページ数の一覧画面に表示する分取得しMemberViewのリストで返却する
     * @param member 会員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<SongView> getMinePerPage(MemberView member, int page) {

        List<Song> songs = em.createNamedQuery(JpaConst.Q_SONG_GET_ALL_MINE, Song.class)
                .setParameter(JpaConst.JPQL_PARM_MEMBER, MemberConverter.toModel(member))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return SongConverter.toViewList(songs);
    }

    /**
     * 指定した会員が作成した楽曲データの件数を取得し、返却する
     * @param member
     * @return 楽曲データの件数
     */
    public long countAllMine(MemberView member) {

        long count = (long) em.createNamedQuery(JpaConst.Q_SONG_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_MEMBER, MemberConverter.toModel(member))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する楽曲データを取得し、SongViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<SongView> getAllPerPage(int page) {

        List<Song> songs = em.createNamedQuery(JpaConst.Q_SONG_GET_ALL, Song.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return SongConverter.toViewList(songs);
    }

        /**
         * 楽曲テーブルのデータの件数を取得し、返却する
         * @return データの件数
         */
        public long countAll() {
            long songs_count = (long) em.createNamedQuery(JpaConst.Q_SONG_COUNT, Long.class)
                    .getSingleResult();
            return songs_count;
        }

        /**
         * idを条件に取得したデータをSongViewのインスタンスで返却する
         * @param id
         * @return 取得データのインスタンス
         */
        public SongView findOne(int id) {
            return SongConverter.toView(findOneInternal(id));
        }

        /**
         * 画面から入力された楽曲の登録内容を元にデータを1件作成し、楽曲テーブルに登録する
         * @param sv 楽曲の登録内容
         * @return バリデーションで発生したエラーのリスト
         */
        public List<String> create(SongView sv) {
            List<String> errors = SongValidator.validate(sv);
            if (errors.size() == 0) {
                LocalDateTime ldt = LocalDateTime.now();
                sv.setCreatedAt(ldt);
                sv.setUpdatedAt(ldt);
                createInternal(sv);
            }

          //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
            return errors;
        }

        /**
         * 画面から入力された楽曲の登録内容を元に、楽曲データを更新する
         * @param sv 楽曲の更新内容
         * @return バリデーションで発生したエラーのリスト
         */
        public List<String> update(SongView sv) {

            //バリデーションを行う
            List<String> errors = SongValidator.validate(sv);

            if (errors.size() == 0) {

                //更新日時を現在時刻に設定
                LocalDateTime ldt = LocalDateTime.now();
                sv.setUpdatedAt(ldt);

                updateInternal(sv);
            }

            //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
            return errors;
        }

        /**
         * idを条件にデータを1件取得する
         * @param id
         * @return 取得データのインスタンス
         */
        private Song findOneInternal(int id) {
            return em.find(Song.class, id);
        }

        /**
         * 楽曲データを1件登録する
         * @param rv 日報データ
         */
        private void createInternal(SongView sv) {

            em.getTransaction().begin();
            em.persist(SongConverter.toModel(sv));
            em.getTransaction().commit();

        }

        /**
         * 楽曲データを更新する
         * @param sv 楽曲データ
         */
        private void updateInternal(SongView sv) {

            em.getTransaction().begin();
            Song s = findOneInternal(sv.getId());
            SongConverter.copyViewToModel(s, sv);
            em.getTransaction().commit();

        }
    }


