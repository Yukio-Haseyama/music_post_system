package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MemberView;
import actions.views.SongView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.SongService;

/**
 * 楽曲に関する処理を行うActionクラス
 *
 */

public class SongAction extends ActionBase {

    private SongService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new SongService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する楽曲データを取得
        int page = getPage();
        List<SongView> songs = service.getAllPerPage(page);

        //全楽曲データの件数を取得
        long song_count = service.countAll();

        putRequestScope(AttributeConst.SONGS, songs); //取得した楽曲データ
        putRequestScope(AttributeConst.SONG_COUNT, song_count); //全ての楽曲データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

      //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_SONG_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //楽曲情報の空インスタンスに、楽曲の日付＝今日の日付を設定する
        SongView sv = new SongView();
        sv.setSong_date(LocalDate.now());
        putRequestScope(AttributeConst.SONG, sv); //日付のみ設定済みの楽曲インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_SONG_NEW);

}

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //楽曲の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.SONG_DATE) == null
                    || getRequestParam(AttributeConst.SONG_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.SONG_DATE));
            }

            //セッションからログイン中の会員情報を取得
            MemberView mv = (MemberView) getSessionScope(AttributeConst.LOGIN_MEM);

            //パラメータの値をもとに楽曲情報のインスタンスを作成する
            SongView sv = new SongView(
                    null,
                    mv, //ログインしている会員を、楽曲作成者として登録する
                    day,
                    getRequestParam(AttributeConst.SONG_TITLE),
                    getRequestParam(AttributeConst.SONG_URL),
                    null,
                    null);

            //楽曲情報登録
            List<String> errors = service.create(sv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.SONG, sv);//入力された楽曲情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_SONG_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_SONG, ForwardConst.CMD_INDEX);
            }
        }
    }


    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に楽曲データを取得する
        SongView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SONG_ID)));

        if (sv == null) {
            //該当の楽曲データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.SONG, sv); //取得した楽曲データ

            //詳細画面を表示
            forward(ForwardConst.FW_SONG_SHOW);
        }
    }

        /**
         * 編集画面を表示する
         * @throws ServletException
         * @throws IOException
         */
        public void edit() throws ServletException, IOException {

            //idを条件に楽曲データを取得する
            SongView sv = service.findOne(toNumber(getRequestParam(AttributeConst.SONG_ID)));

            //セッションからログイン中の会員情報を取得
            MemberView mv = (MemberView) getSessionScope(AttributeConst.LOGIN_MEM);

            if (sv == null || mv.getId() != sv.getMember().getId()) {
                //該当の楽曲データが存在しない、または
                //ログインしている会員が楽曲の作成者でない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);

            } else {

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.SONG, sv); //取得した楽曲データ

                //編集画面を表示
                forward(ForwardConst.FW_SONG_EDIT);
            }

        }

    }


