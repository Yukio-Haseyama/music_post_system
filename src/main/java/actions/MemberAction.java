package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MemberView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.MemberService;

/**
 * 会員に関わる処理を行うActionクラス
 *
 */

public class MemberAction extends ActionBase {

    private MemberService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new MemberService();

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

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<MemberView> members = service.getPerPage(page);

        //全ての会員データの件数を取得
        long memberCount = service.countAll();

        putRequestScope(AttributeConst.MEMBERS, members); //取得した会員データ
        putRequestScope(AttributeConst.MEM_COUNT, memberCount); //全ての会員データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

      //一覧画面を表示
        forward(ForwardConst.FW_MEM_INDEX);



    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.MEMBER, new MemberView()); //空の会員インスタンス


        //新規登録画面を表示
        forward(ForwardConst.FW_MEM_NEW);
    }


/**
 * 新規登録を行う
 * @throws ServletException
 * @throws IOException
 */
public void create() throws ServletException, IOException {

    //CSRF対策 tokenのチェック
    if (checkToken()) {

        //パラメータの値を元に会員情報のインスタンスを作成する
        MemberView mv = new MemberView(
                null,
                getRequestParam(AttributeConst.MEM_CODE),
                getRequestParam(AttributeConst.MEM_NAME),
                getRequestParam(AttributeConst.MEM_PASS),
                null,
                null,
                null);



        //アプリケーションスコープからpepper文字列を取得
        String pepper = getContextScope(PropertyConst.PEPPER);

        //従業員情報登録
        List<String> errors = service.create(mv, pepper);

        if (errors.size() > 0) {
            //登録中にエラーがあった場合

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.MEMBER, mv); //入力された会員情報
            putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

            //新規登録画面を再表示
            forward(ForwardConst.FW_MEM_NEW);

        } else {
            //登録中にエラーがなかった場合

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_MEM, ForwardConst.CMD_INDEX);
        }

    }
}



/**
 * 詳細画面を表示する
 * @throws ServletException
 * @throws IOException
 */

public void show() throws ServletException, IOException {

  //idを条件に従業員データを取得する
    MemberView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MEM_ID)));{

  //データが取得できなかった、または物理削除されている場合はエラー画面を表示
    if(mv == null) {
       forward(ForwardConst.FW_ERR_UNKNOWN) ;
    return;
}

    putRequestScope(AttributeConst.MEMBER, mv); //取得した会員情報

//詳細画面を表示
    forward(ForwardConst.FW_MEM_SHOW);
}
}

/**
 * 編集画面を表示する
 * @throws ServletException
 * @throws IOException
 */
public void edit() throws ServletException,IOException{

    //idを条件に従業員データを取得する
    MemberView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MEM_ID)));

  //データが取得できなかった、または物理削除されている場合はエラー画面を表示
    if(mv == null) {
       forward(ForwardConst.FW_ERR_UNKNOWN) ;
    return;
}

    putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
    putRequestScope(AttributeConst.MEMBER, mv);//取得した会員情報

  //編集画面を表示する
    forward(ForwardConst.FW_MEM_EDIT);
}

/**
 * 更新を行う
 * @throws ServletException
 * @throws IOException
 */
public void update() throws ServletException, IOException{

  //CSRF対策 tokenのチェック
    if(checkToken()) {
        //パラメータの値を元に会員情報のインスタンスを作成する
        MemberView mv = new MemberView(
                toNumber(getRequestParam(AttributeConst.MEM_ID)),
                getRequestParam(AttributeConst.MEM_CODE),
                getRequestParam(AttributeConst.MEM_NAME),
                getRequestParam(AttributeConst.MEM_PASS),
                null,
                null,
                null);

      //アプリケーションスコープからpepper文字列を取得
        String pepper = getContextScope(PropertyConst.PEPPER);

      //会員情報更新
        List<String> errors = service.update(mv, pepper);

        if(errors.size() > 0) {
          //更新中にエラーが発生した場合

            putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
            putRequestScope(AttributeConst.MEMBER, mv);//入力された会員情報
            putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

          //編集画面を再表示
            forward(ForwardConst.FW_MEM_EDIT);
        }else {
          //更新中にエラーがなかった場合

          //セッションに更新完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

          //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_MEM, ForwardConst.CMD_INDEX);
        }
    }
}

/**
 * 物理削除を行う
 * @throws ServletException
 * @throws IOException
 */

    public void destroy() throws ServletException, IOException{

   //CSRF対策 tokenのチェック
      if(checkToken()) {
      }

          String _token = request.getParameter("_token");
          if(_token != null && _token.equals(request.getSession().getId())) {

        // 該当のIDの会員1件のみをデータベースから取得
        service.destroy(toNumber(getRequestParam(AttributeConst.MEM_ID)));

      //セッションに削除完了のフラッシュメッセージを設定
          putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_MEM, ForwardConst.CMD_INDEX);

}
}
}


