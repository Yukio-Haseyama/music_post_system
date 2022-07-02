<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

         </div>
</c:if>
<fmt:parseDate value="${song.song_date}" pattern="yyyy-MM-dd" var="songDay" type="date" />
<label for="${AttributeConst.SONG_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.SONG_DATE.getValue()}" value="<fmt:formatDate value='${songDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_member.name}" />
<br /><br />

<label for="${AttributeConst.SONG_TITLE.getValue()}">タイトル</label><br />
<input type="text" name="${AttributeConst.SONG_TITLE.getValue()}" value="${song.title}" />
<br /><br />

<label for="${AttributeConst.SONG_URL.getValue()}">URL</label><br />
<textarea name="${AttributeConst.SONG_URL.getValue()}" rows="10" cols="50">${song.url}</textarea>
<br /><br />
<input type="hidden" name="${AttributeConst.SONG_ID.getValue()}" value="${song.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>