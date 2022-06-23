<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_MEM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<label for="${AttributeConst.MEM_CODE.getValue()}">会員番号</label><br />
<input type="text" name="${AttributeConst.MEM_CODE.getValue()}" value="${member.code}" />
<br /><br />

<label for="${AttributeConst.MEM_NAME.getValue()}">氏名</label><br />
<input type="text" name="${AttributeConst.MEM_NAME.getValue()}" value="${member.name}" />
<br /><br />

<label for="${AttributeConst.MEM_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.MEM_PASS.getValue()}" />
<br /><br />

<input type="hidden" name="${AttributeConst.MEM_ID.getValue()}" value="${member.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>