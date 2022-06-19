package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.MemberView;
import constants.MessageConst;
import services.MemberService;

//会員インスタンスに設定されている値のバリデーションを行うクラス

public class MemberValidator {

    //会員インスタンスの各項目についてバリデーションを行う

    public static List<String> validate(
            MemberService service, MemberView mv, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag){
        List<String> errors = new ArrayList<String>();

        //会員番号のチェック
        String codeError = validateCode(service, mv.getCode(), codeDuplicateCheckFlag);
        if(!codeError.equals("")) {
            errors.add(codeError);
        }

        //氏名のチェック
        String nameError = validateName(mv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードのチェック
        String passError = validatePassword(mv.getPassword(), passwordCheckFlag);
        if(!passError.equals("")) {
            errors.add(passError);
        }

        return errors;

        }

    //会員番号の入力チェックを行い、エラーメッセージを返却
    private static String validateCode(MemberService service, String code, Boolean codeDuplicateCheckFlag) {

        //入力値が無ければエラーメッセージを返却
        if(code == null || code.equals("")) {
            return MessageConst.M_NOMEM_CODE.getMessage();
        }

        if(codeDuplicateCheckFlag) {
            //会員番号の重複チェックを実施

            long membersCount = isDuplicateMember(service, code);

            //同一会員番号が既に登録されている場合はエラーメッセージを返却
        if(membersCount > 0) {
            return MessageConst.M_MEM_CODE_EXIST.getMessage();
        }
        }
    return "";
    }

    private static long isDuplicateMember(MemberService service, String code) {

        long membersCount = service.countByCode(code);
        return membersCount;
    }

    //氏名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
    private static String validateName(String name) {
        if(name == null || name.equals("")) {
            return MessageConst.M_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    //パスワードの入力チェックを行い、エラーメッセージを返却
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施かつ、入力値が無ければエラーメッセージを返却
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.M_NOPASSWORD.getMessage();
        }

        //エラーが無い場合は空文字を返却
        return "";

        }
    }

