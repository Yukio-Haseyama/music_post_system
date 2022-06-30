<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actSong" value="${ForwardConst.ACT_SONG.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>楽曲一覧</h2>
        <table id="song_list">
            <tbody>
                <tr>
                    <th class="song_name">氏名</th>
                    <th class="song_date">日付</th>
                    <th class="song_title">タイトル</th>
                    <th class="song_action">操作</th>
                </tr>
                <c:forEach var="song" items="${songs}" varStatus="status">
                    <fmt:parseDate value="${song.song_date}" pattern="yyyy-MM-dd" var="songDay" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="song_name"><c:out value="${song.member.name}" /></td>
                        <td class="song_date"><fmt:formatDate value='${songDay}' pattern='yyyy-MM-dd' /></td>
                        <td class="song_title">${song.title}</td>
                        <td class="song_action"><a href="<c:url value='?action=${actSong}&command=${commShow}&id=${song.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${songs_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((songs_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actSong}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actSong}&command=${commNew}' />">新規楽曲の登録</a></p>

    </c:param>
</c:import>
