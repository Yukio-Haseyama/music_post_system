package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.MemberConverter;
import actions.views.MemberView;
import constants.JpaConst;
import models.Member;
import models.validators.MemberValidator;
import utils.EncryptUtil;

/**
 *会員テーブルの操作に関わる処理を行うクラス
 */
public class MemberService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、MemberViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<MemberView> getPerPage(int page) {
        List<Member> members = em.createNamedQuery(JpaConst.Q_MEM_GET_ALL, Member.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return MemberConverter.toViewList(members);
    }

    /**
     * 会員テーブルのデータの件数を取得し、返却する
     * @return 会員テーブルのデータの件数
     */
    public long countAll() {
        long memCount = (long) em.createNamedQuery(JpaConst.Q_MEM_COUNT, Long.class)
                .getSingleResult();

        return memCount;
    }

    /**
     * 会員番号、パスワードを条件に取得したデータをMemberViewのインスタンスで返却する
     * @param code 会員番号
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得データのインスタンス 取得できない場合null
     */
    public MemberView findOne(String code, String plainPass, String pepper) {
        Member m = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //会員番号とハッシュ化済パスワードを条件に未削除の会員を1件取得する
            m = em.createNamedQuery(JpaConst.Q_MEM_GET_BY_CODE_AND_PASS, Member.class)
                    .setParameter(JpaConst.JPQL_PARM_CODE, code)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return MemberConverter.toView(m);

    }

    /**
     * idを条件に取得したデータをMemberViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MemberView findOne(int id) {
        Member m = findOneInternal(id);
        return MemberConverter.toView(m);
    }

    /**
     * 会員番号を条件に該当するデータの件数を取得し、返却する
     * @param code 会員番号
     * @return 該当するデータの件数
     */
    public long countByCode(String code) {

        //指定した会員番号を保持する会員の件数を取得する
        long members_count = (long) em.createNamedQuery(JpaConst.Q_MEM_COUNT_REGISTERED_BY_CODE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .getSingleResult();
        return members_count;
    }

    /**
     * 画面から入力された会員の登録内容を元にデータを1件作成し、会員テーブルに登録する
     * @param mv 画面から入力された会員の登録内容
     * @param pepper pepper文字列
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(MemberView mv, String pepper) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(mv.getPassword(), pepper);
        mv.setPassword(pass);

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        mv.setCreatedAt(now);
        mv.setUpdatedAt(now);

      //登録内容のバリデーションを行う
        List<String> errors = MemberValidator.validate(this, mv, true, true);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(mv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された会員の更新内容を元にデータを1件作成し、会員テーブルを更新する
     * @param mv 画面から入力された会員の登録内容
     * @param pepper pepper文字列
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(MemberView mv, String pepper) {

        //idを条件に登録済みの会員情報を取得する
        MemberView savedMem = findOne(mv.getId());

        boolean validateCode = false;
        if (!savedMem.getCode().equals(mv.getCode())) {
            //会員番号を更新する場合

            //会員番号についてのバリデーションを行う
            validateCode = true;
            //変更後の社員番号を設定する
            savedMem.setCode(mv.getCode());
        }

        boolean validatePass = false;
        if (mv.getPassword() != null && !mv.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedMem.setPassword(
                    EncryptUtil.getPasswordEncrypt(mv.getPassword(), pepper));
        }

        savedMem.setName(mv.getName()); //変更後の氏名を設定する

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedMem.setUpdatedAt(today);

        //更新内容についてバリデーションを行う
        List<String> errors = MemberValidator.validate(this, savedMem, validateCode, validatePass);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedMem);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 会員番号とパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param code 会員番号
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却する(成功:true 失敗:false)
     */
    public Boolean validateLogin(String code, String plainPass, String pepper) {

        Boolean isValidMember = false;
        if (code != null && !code.equals("") && plainPass != null && !plainPass.equals("")) {
            MemberView mv = findOne(code, plainPass, pepper);

            if (mv != null && mv.getId() != null) {

                //データが取得できた場合、認証成功
                isValidMember = true;
            }
        }

        //認証結果を返却する
        return isValidMember;
    }

    /**
     * idを条件にデータを1件取得し、Memberのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Member findOneInternal(int id) {
        Member m = em.find(Member.class, id);

        return m;
    }

    /**
     * 会員データを1件登録する
     * @param mv 会員データ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void create(MemberView mv) {

        em.getTransaction().begin();
        em.persist(MemberConverter.toModel(mv));
        em.getTransaction().commit();

    }

    /**
     * 会員データを更新する
     * @param mv 画面から入力された会員の登録内容
     */
    private void update(MemberView mv) {

        em.getTransaction().begin();
        Member m = findOneInternal(mv.getId());
        MemberConverter.copyViewToModel(m, mv);
        em.getTransaction().commit();

    }

    public void destroy(Integer id) {

        //idを条件に登録済みの会員情報を取得する
        Member m = findOneInternal(id);

      //更新処理を行う
        em.getTransaction().begin();
        em.remove(m); // データ削除
        em.getTransaction().commit();
        em.close();


    }
}
