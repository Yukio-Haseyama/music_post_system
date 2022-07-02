package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MemberView;
import actions.views.SongView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.SongService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private SongService service;

    /**
     * indexメソッドを実行する
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
     */
    public void index() throws ServletException, IOException {


        //セッションからログイン中の会員情報を取得
        MemberView loginMember = (MemberView) getSessionScope(AttributeConst.LOGIN_MEM);

        //ログイン中の会員が作成した楽曲データを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<SongView> songs = service.getMinePerPage(loginMember, page);

        //ログイン中の会員が作成した楽曲データの件数を取得
        long mySongsCount = service.countAllMine(loginMember);

        putRequestScope(AttributeConst.SONGS, songs); //取得した楽曲データ
        putRequestScope(AttributeConst.SONG_COUNT, mySongsCount); //ログイン中の会員が作成した楽曲の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数


        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}