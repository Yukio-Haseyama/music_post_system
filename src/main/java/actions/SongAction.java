package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.SongView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
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
        sv.setSongDate(LocalDate.now());
        putRequestScope(AttributeConst.SONG, sv); //日付のみ設定済みの楽曲インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_SONG_NEW);

}

}
