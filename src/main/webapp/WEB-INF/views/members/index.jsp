<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actMem" value="${ForwardConst.ACT_MEM.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>トップページ</h2>
        <table id="member_list">
            <tbody>
                <tr>
                    <th>会員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="member" items="${members}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${member.code}" /></td>
                        <td><c:out value="${member.name}" /></td>
                        <td>
                                    <a href="<c:url value='?action=${actMem}&command=${commShow}&id=${member.id}' />">詳細を見る</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

         <div id="pagination">
            （全 ${members_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((members_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actMem}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actMem}&command=${commNew}' />">新規会員の登録</a></p>

    </c:param>
</c:import>