<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="memory.*, java.util.*" %>
<%
ArrayList<Timeline> timeline = (ArrayList<Timeline>) request.getAttribute("timeline");
ArrayList<Reply> reply = (ArrayList<Reply>) request.getAttribute("reply");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板</title>
<link href="scroll.css" rel="stylesheet" type="text/css">
<link href="reply.css" rel="stylesheet" type="text/css">
<link href="button.css" rel="stylesheet" type="text/css">
<!-- jQueryの読み込み -->
<script src="jQuery.js"></script>
</head>
<body>
<div class="split">
    <div class="split-item split-left">
        <div class="split-left__inner">
        	<h1>掲示板</h1>
			<h3>掲示板へようこそ</h3>
			<div class="mainForm">
			<p>
			<b>&lt;名前&gt;</b>(スペースは使用不可)<br>
			<input type="text" name="name" size="20" maxlength="20" id="mainPostingName" required>
			</p>
			<p>
			<b>&lt;テキスト&gt;</b><br>
			<textarea name="text" rows="10" cols="30" id="mainPostingText" required></textarea>
			</p>
			<p>
			<b>&lt;パスワード&gt;</b>(8~20字でスペースは不可)<br>
			<input type="password" name="pass" size="20" minlength="6" maxlength="20" id="mainPostingPass" required>
			</p>
			<p>
			<button id="mainPosting">投稿</button>
			</p>
			</div><!-- mainForm -->
		</div><!--split-left__inner-->
    </div><!--split-item split-left-->

    <div class="split-item split-right">
    	<div class="split-right__inner">
			<h3>コメントログ</h3>
			<% if (timeline != null) { %>
				<div class="edit" id="edit">
				<% for (Timeline tl: timeline) {
						if(tl.isDeleted() == false) {%>
							<div class="test" id="test<%=tl.getId()%>">
							<span id="mainRemoveDeleteCheck<%=tl.getId()%>">&lt;<%=tl.getId() %>&gt; <span style="margin-right: 1em;"></span><%=tl.getName() %>: <span id= "mainText<%=tl.getId()%>"><%=tl.getText() %></span><!-- mainText --><span style="margin-right: 1em;"></span><br>

							<div class="hidden_box">
								<label for="edit<%=tl.getId()%>">編集</label>
								<input type="checkbox" id="edit<%=tl.getId()%>"/>
									<div class="hidden_show">
										<div class="mainEditForm" >
										<input type="hidden" name="mainEditId" value="<%=tl.getId()%>" class="editID" id="mainEditId<%=tl.getId()%>">
										<b>&lt;テキスト&gt;</b><br>
										<textarea name="mainEditText" rows="10" cols="30" id="mainEditText<%=tl.getId()%>"required><%=tl.getText()%></textarea><br>
										<b>&lt;パスワード確認&gt;</b><br>
										<input type="password" name="mainEditPass" size="20" minlength="6" maxlength="20" id="mainEditPass<%=tl.getId() %>" required>
										<br><br>
										<button class="mainEditPosting" id="mainEditPosting<%=tl.getId()%>">送信</button>
										</div><!-- mainEditForm -->
									</div><!-- hidden_show -->
							</div><!-- hidden_box -->

							<div class="hidden_box">
								<label for="mainRemoveDelete<%=tl.getId() %>">削除</label>
								<input type="checkbox" id="mainRemoveDelete<%=tl.getId()%>"/>
    							<div class="hidden_show">
      								<div class="mainRemoveDeleteForm">
      								<input type="hidden" name="mainRemoveDeleteId" value="<%=tl.getId()%>" id="mainRemoveDeleteId<%=tl.getId()%>">
      								<b>&lt;削除のタイプ&gt;</b>
      								<b>物理削除</b><input type="radio" name="mainRemoveDeleteKind" value="delete" required>
      								<b>論理削除</b><input type="radio" name="mainRemoveDeleteKind" value="remove" required>
      								<br><br>
      								<b>&lt;パスワード確認&gt;</b><br>
									<input type="password" name="mainRemoveDeletePass" size="20" minlength="6" maxlength="20" id="mainRemoveDeletePass<%=tl.getId() %>"required><br><br>
									<button class="mainRemoveDeleteSubmit" id="mainRemoveDeleteSubmit<%=tl.getId()%>">送信</button>
									</div><!-- mainRemoveDeleteForm -->
								</div><!-- hidden_show -->
							</div><!-- hidden_box -->

							<div class="hidden_box">
   			 					<label for="label<%=tl.getId() %>">返信</label>
    							<input type="checkbox" id="label<%=tl.getId()%>"/>
   								<div class="hidden_show">
									<div class="replyForm">
										<input type="hidden" name="replyId" value="<%=tl.getId()%>" id="replyId<%=tl.getId()%>"><br>
										<b>&lt;名前&gt;</b>(スペースは使用不可)<br>
										<input type="text" name="replyPostingName" size="20" maxlength="20" id="replyPostingName<%=tl.getId()%>" required><br>
										<b>&lt;テキスト&gt;</b><br>
										<textarea name="replyPostingText" rows="10" cols="30" id="replyPostingText<%=tl.getId()%>"required></textarea><br>
										<b>&lt;パスワード&gt;</b>(8~20字でスペースは不可)<br>
										<input type="password" name="replyPostingPass" size="20" minlength="6" maxlength="20" id="replyPostingPass<%=tl.getId()%>"required><br><br>
										<button class="replyPosting" id="replyPosting<%=tl.getId()%>">返信</button><br>
									</div><!-- replyForm -->
			    				</div><!-- hidden_show -->
							</div><!-- hidden_box -->
							</span>
							</div>
						<%} %>
					<br><% if (reply != null) {
							 for (Reply rp: reply) {
							 	if (rp.getReplyId() == tl.getId() && rp.isDeleted() == false) {%>
							 		 <span id="replyRemoveDeleteCheck<%=rp.getId()%>"><span style="margin-right: 3em;"></span><span class="replySign">&#8658;<span style="margin-right: 1em;"></span><%=rp.getName() %></span>: <span id="replyText<%=rp.getId()%>"><%=rp.getText()%></span><br>
							 		 <div class="hidden_box">
									 <label for="replyEdit<%=rp.getId()%>">編集</label>
									 <input type="checkbox" id="replyEdit<%=rp.getId()%>"/>
										<div class="hidden_show">
										 	<div class="replyEditForm">
												<input type="hidden" name="replyEditId" value="<%=rp.getId()%>" class="editId" id="replyEditId<%=rp.getId()%>">
												<span style="margin-right: 3em;"></span><b>&lt;テキスト&gt;</b><br>
											 	<span style="margin-right: 3em;"></span><textarea name="replyEditText" id="replyEditText<%=rp.getId() %>" rows="10" cols="30" required><%=rp.getText()%></textarea><br>
											 	<span style="margin-right: 3em;"></span><b>&lt;パスワード確認&gt;</b><br>
												<span style="margin-right: 3em;"></span><input type="password" name="replyEditPass" id="replyEditPass<%=rp.getId() %>" size="20" minlength="6" maxlength="20" required><br>
												<span style="margin-right: 3em;"></span><button class="replyEditPosting" id="replyEditPosting<%=rp.getId()%>">送信</button>
											</div><!-- replyEditForm -->
										</div><!-- hidden_show -->
									  </div><!-- hidden_box -->

									  <div class="hidden_box">
										<label for="replyRemoveDelete<%=rp.getId()%>">削除</label>
										<input type="checkbox" id="replyRemoveDelete<%=rp.getId()%>"/>
											<div class="hidden_show">
											 	<div class="replyRemoveDeleteForm">
					      							<input type="hidden" name="replyRemoveDeleteId" value="<%=rp.getId()%>" id="replyRemoveDeleteId<%=rp.getId()%>">
					      								<b>&lt;削除のタイプ&gt;</b>
					      								<b>物理削除</b><input type="radio" name="replyRemoveDeleteKind" value="delete" id="replyRemoveDeleteKind<%=rp.getId() %>" required>
					      								<b>論理削除</b><input type="radio" name="replyRemoveDeleteKind" value="remove" id="replyRemoveDeleteKind<%=rp.getId() %>" required>
					      								<br><br>
					      								<b>&lt;パスワード確認&gt;</b><br><br>
														<input type="password" name="replyRemoveDeletePass" size="20" minlength="6" maxlength="20" id="replyRemoveDeletePass<%=rp.getId() %>"required><br><br>
														<button class="replyRemoveDeleteSubmit" id="replyRemoveDeleteSubmit<%=rp.getId()%>">送信</button>
												</div><!-- replyRemoveDeleteForm -->
											</div><!-- hidden_show -->
									  </div><!-- hidden_box -->
									  </span>
								<%}
							 }
						}%>

					<% }
			%>
				</div><!--edit-->
		<% } %>
        </div><!--split-right__inner-->
    </div><!--split-item split-right-->
</div><!--split-->
<script type="text/javascript" src="TurnView.js" charset="utf-8"></script>
</body>
</html>