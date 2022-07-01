<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actSong" value="${ForwardConst.ACT_SONG.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>楽曲詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${song.member.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${song.song_date}" pattern="yyyy-MM-dd" var="songDay" type="date" />
                    <td><fmt:formatDate value='${songDay}' pattern='yyyy-MM-dd' /></td>
                </tr>
                <tr>
                    <th>タイトル</th>
                    <td><c:out value="${song.title}" /></td>
                </tr>
                <tr>
                    <th>URL</th>
                    <td><pre><c:out value="${song.url}" /></pre></td>
                   <iframe width="420" height="315"
                        src="${song.url}"
                        allowfullscreen>
                   </iframe>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${song.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${song.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <c:if test="${sessionScope.login_member.id == song.member.id}">
            <p>
                <a href="<c:url value='?action=${actSong}&command=${commEdt}&id=${song.id}' />">この楽曲を編集する</a>
            </p>
        </c:if>

        <p>
            <a href="<c:url value='?action=${actSong}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>