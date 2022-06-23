package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.MemberView;
import constants.MessageConst;
import services.MemberService;

/**
 * 会員インスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class MemberValidator {

    /**
     * 会員インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param mv MemberViewのインスタンス
     * @param codeDuplicateCheckFlag 会員番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            MemberService service, MemberView mv, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();



        //氏名のチェック
        String nameError = validateName(mv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードのチェック
        String passError = validatePassword(mv.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }




    /**
     * 氏名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.M_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.M_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}

