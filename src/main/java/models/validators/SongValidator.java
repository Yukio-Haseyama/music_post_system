package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.SongView;
import constants.MessageConst;

/**
 * 楽曲インスタンスに設定されている値のバリデーションを行うクラス
 */

public class SongValidator {

    /**
     * 楽曲インスタンスの各項目についてバリデーションを行う
     * @param sv 楽曲インスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(SongView sv) {
        List<String> errors = new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateTitle(sv.getTitle());
        if (!titleError.equals("")) {
            errors.add(titleError);
        }

      //URLのチェック
        String urlError = validateUrl(sv.getUrl());
        if (!urlError.equals("")) {
            errors.add(urlError);
        }

        return errors;
    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title タイトル
     * @return エラーメッセージ
     */
    private static String validateTitle(String title) {
        if (title == null || title.equals("")) {
            return MessageConst.M_NOTITLE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * URLに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param url URL
     * @return エラーメッセージ
     */
    private static String validateUrl(String url) {
        if (url == null || url.equals("")) {
            return MessageConst.M_NOURL.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

}
