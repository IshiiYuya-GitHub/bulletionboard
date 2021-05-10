/**
 * http://usejsdoc.org/
 */
$(function() {
	var flag = 0;

	// 投稿後、ページの一番底を表示
	function bottom() {
  		var a = document.documentElement;
  		var y = a.scrollHeight - a.clientHeight;
  		window.scroll(0, y);
	}

	// スペースを埋める
	function checkSpace(target) {
		return target.replace(/[\s　]/g, '');
	}

	// スペースを許さない
	function checkSpacePass(target) {
		return target.search(/[\s　]/);
	}

	// パスワードを英数字8~20に制限
	function checkPassLength(target) {
		return target.match(/^([a-z0-9]{8,20})$/);
	}


	// 本投稿
	$(document).on('click', '#mainPosting', function() {
		var mainData = {
						mainPostingName : checkSpace(document.getElementById('mainPostingName').value),
						mainPostingText : document.getElementById('mainPostingText').value,
						mainPostingPass : document.getElementById('mainPostingPass').value
						}

		if (mainData['mainPostingName'] == null || mainData['mainPostingName'] == '') {
			flag = 1;
		}
		if (mainData['mainPostingText'] == null || checkSpace(mainData['mainPostingText']) == '') {
			flag = 1;
		}
		if (mainData['mainPostingPass'] == null || checkSpacePass(mainData['mainPostingPass']) != -1 || checkPassLength(mainData['mainPostingPass']) == null) {
			flag = 1;
		}
		if (flag == 1) {
			alert('入力が完全でない箇所があります。');
			flag = 0;
			return;
		}

		$.ajax({
			url: 'Posting',
			type: 'POST',
			cache: false,
			data: mainData
		}).done (function(result) {
			result = JSON.parse(result);
			console.log(result);
			if (result.check == true) {
				let element = document.getElementById('edit');
				element.insertAdjacentHTML('beforeend', `
								<div class="test" id="test${result.id}">
								<span id="mainRemoveDeleteCheck${result.id}">&lt;${result.id}&gt; <span style="margin-right: 1em;"></span>${result.postingName}: <span id= "mainText${result[1]}">${result.postingText}</span><!-- mainText --><span style="margin-right: 1em;"></span><br><div class="hidden_box">
								<label for="edit${result.id}">編集</label>
								<input type="checkbox" id="edit${result.id}"/>
									<div class="hidden_show">
										<div class="mainEditForm" >
										<input type="hidden" name="mainEditId" value="${result.id}" class="editID" id="mainEditId${result.id}">
										<b>&lt;テキスト&gt;</b><br>
										<textarea name="mainEditText" rows="10" cols="30" id="mainEditText${result.id}" required>${result.postingText}</textarea><br>
										<b>&lt;パスワード確認&gt;</b><br>
										<input type="password" name="mainEditPass" size="20" minlength="6" maxlength="20" id="mainEditPass${result.id}" required>
										<br><br>
										<button class="mainEditPosting" id="mainEditPosting${result.id}">送信</button>
										</div><!-- mainEditForm -->
									</div><!-- hidden_show -->
							</div><!-- hidden_box -->

							<div class="hidden_box">
								<label for="mainRemoveDelete${result.id}">削除</label>
								<input type="checkbox" id="mainRemoveDelete${result.id}"/>
    							<div class="hidden_show">
      								<div class="mainRemoveDeleteForm">
      								<input type="hidden" name="mainRemoveDeleteId" value="${result.id}" id="mainRemoveDeleteId${result.id}">
      								<b>&lt;削除のタイプ&gt;</b>
      								<b>物理削除</b><input type="radio" name="mainRemoveDeleteKind" value="delete" required>
      								<b>論理削除</b><input type="radio" name="mainRemoveDeleteKind" value="remove" required>
      								<br><br>
      								<b>&lt;パスワード確認&gt;</b><br>
									<input type="password" name="mainRemoveDeletePass" size="20" minlength="6" maxlength="20" id="mainRemoveDeletePass${result.id}" required><br><br>
									<button class="mainRemoveDeleteSubmit" id="mainRemoveDeleteSubmit${result.id}">送信</button>
									</div><!-- mainRemoveDeleteForm -->
								</div><!-- hidden_show -->
							</div><!-- hidden_box -->

							<div class="hidden_box">
   			 					<label for="label${result.id}">返信</label>
    							<input type="checkbox" id="label${result.id}"/>
   								<div class="hidden_show">
									<div class="replyForm">
										<input type="hidden" name="replyId" value="${result.id}" id="replyId${result.id}"><br>
										<b>&lt;名前&gt;</b>(スペースは使用不可)<br>
										<input type="text" name="replyPostingName" size="20" maxlength="20" id="replyPostingName${result.id}" required><br>
										<b>&lt;テキスト&gt;</b><br>
										<textarea name="replyPostingText" rows="10" cols="30" id="replyPostingText${result.id}"required></textarea><br>
										<b>&lt;パスワード&gt;</b>(8~20字でスペースは不可)<br>
										<input type="password" name="replyPostingPass" size="20" minlength="6" maxlength="20" id="replyPostingPass${result.id}"required><br><br>
										<button class="replyPosting" id="replyPosting${result.id}">返信</button><br>
									</div><!-- replyForm -->
			    				</div><!-- hidden_show -->
							</div><!-- hidden_box -->
							</span>
							</div>

					<br>`);
				bottom();
				document.getElementById('mainPostingName').value = "";
				document.getElementById('mainPostingText').value = "";
				document.getElementById('mainPostingPass').value = "";
			}
		}).fail(function() {
			alert('投稿に失敗しました。');
		});
	});

	// リプライ
	$(document).on('click', '.replyPosting', function(){
		var buttonId = $(this).attr('id').replace('replyPosting', '');
		var replyData = {
						 replyPostingName : document.getElementById('replyPostingName' + buttonId).value,
						 replyPostingText :  document.getElementById('replyPostingText' + buttonId).value,
						 replyPostingPass :  document.getElementById('replyPostingPass' + buttonId).value,
						 replyId :  document.getElementById('replyId' + buttonId).value
						 }



		if (replyData['replyPostingName'] == null || replyData['replyPostingName'] == '') {
			flag = 1;
		}
		if (replyData['replyPostingText'] == null || checkSpace(replyData['replyPostingText']) == '') {
			flag = 1;
		}
		if (replyData['replyPostingPass'] == null || checkSpacePass(replyData['replyPostingPass']) != -1 || checkPassLength(replyData['replyPostingPass']) == null) {
			flag = 1;
		}
		if (flag == 1) {
			alert('入力が完全でない箇所があります。');
			flag = 0;
			return;
		}

		$.ajax({
			url: 'Posting',
			type: 'POST',
			cache: false,
			data: replyData
		}).done(function(result){
			result = JSON.parse(result);
			if (result.check == true) {
				let element = document.getElementById('test' + result.replyId);
				element.insertAdjacentHTML('beforeend',
									`<span id="replyRemoveDeleteCheck${result.id}"><span style="margin-right: 3em;"></span><span class="replySign">&#8658;<span style="margin-right: 1em;"></span>${result.postingName}</span>: <span id="replyText${result.id}">${result.postingText}</span><br>
							 		 <div class="hidden_box">
									 <label for="replyEdit${result.id}">編集</label>
									 <input type="checkbox" id="replyEdit${result.id}"/>
										<div class="hidden_show">
										 	<div class="replyEditForm">
												<input type="hidden" name="replyEditId" value="${result.id}" class="editId" id="replyEditId${result.id}">
												<span style="margin-right: 3em;"></span><b>&lt;テキスト&gt;</b><br>
											 	<span style="margin-right: 3em;"></span><textarea name="replyEditText" id="replyEditText${result.id}" rows="10" cols="30" required>${result.postingText}</textarea><br>
											 	<span style="margin-right: 3em;"></span><b>&lt;パスワード確認&gt;</b><br>
												<span style="margin-right: 3em;"></span><input type="password" name="replyEditPass" id="replyEditPass${result.id}" size="20" minlength="6" maxlength="20" required><br>
												<span style="margin-right: 3em;"></span><button class="replyEditPosting" id="replyEditPosting${result.id}">送信</button>
											</div><!-- replyEditForm -->
										</div><!-- hidden_show -->
									  </div><!-- hidden_box -->

									  <div class="hidden_box">
										<label for="replyRemoveDelete${result.id}">削除</label>
										<input type="checkbox" id="replyRemoveDelete${result.id}"/>
											<div class="hidden_show">
											 	<div class="replyRemoveDeleteForm">
					      							<input type="hidden" name="replyRemoveDeleteId" value="${result.id}" id="replyRemoveDeleteId${result.id}">
					      								<b>&lt;削除のタイプ&gt;</b>
					      								<b>物理削除</b><input type="radio" name="replyRemoveDeleteKind" value="delete" id="replyRemoveDeleteKind${result.id}" required>
					      								<b>論理削除</b><input type="radio" name="replyRemoveDeleteKind" value="remove" id="replyRemoveDeleteKind${result.id}" required>
					      								<br><br>
					      								<b>&lt;パスワード確認&gt;</b><br><br>
														<input type="password" name="replyRemoveDeletePass" size="20" minlength="6" maxlength="20" id="replyRemoveDeletePass${result.id}"required><br><br>
														<button class="replyRemoveDeleteSubmit" id="replyRemoveDeleteSubmit${result.id}">送信</button>
												</div><!-- replyRemoveDeleteForm -->
											</div><!-- hidden_show -->
									  </div><!-- hidden_box -->
									  </span>`);
				document.getElementById('replyPostingName' + result.replyId).value = "";
				document.getElementById('replyPostingText' + result.replyId).value = "";
				document.getElementById('replyPostingPass' + result.replyId).value = "";
			}
		}).fail(function(){
			alert('投稿に失敗しました。(リプライ)');
		});
	});


	// 編集非同期処理メイン
    $(document).on('click', '.mainEditPosting', function (){
		// ボタンのIDによってそのクラス内の要素を取得
		var buttonId = $(this).attr('id').replace('mainEditPosting', '');
		var mainEditData = {
							mainEditId : $('.mainEditForm').children('#mainEditId'+ buttonId).val(),
			   				mainEditText : document.getElementById('mainEditText' + buttonId).value,
			   				mainEditPass : $('.mainEditForm').children('#mainEditPass' + buttonId).val()
							}

		if (mainEditData['mainEditText'] == null || checkSpace(mainEditData['mainEditText']) == '') {
			alert('テキストボックスに文字を入力してください。');
			return
		}


		$.ajax({
			url: 'Edit',
			type: 'POST',
			cache: false,
			data: mainEditData

		}).done (function(result) {
			result = JSON.parse(result);
			if (result.check == true) {
				$('#mainText' + buttonId).text(result.editText);
				document.getElementById('mainEditPass' + buttonId).value = "";
			} else {
				alert('パスワードの不一致');
			}

		}).fail(function() {
			alert('通信失敗');
		});
	});

	// 編集非同期処理リプライ
	$(document).on('click', '.replyEditPosting', function(){
		var buttonId = $(this).attr('id').replace('replyEditPosting', '');
		var replyEditData = {
							 replyEditId : $('.replyEditForm').children('#replyEditId' + buttonId).val(),
			   				 replyEditText : document.getElementById('replyEditText' + buttonId).value,
			   			 	 replyEditPass : $('.replyEditForm').children('#replyEditPass' + buttonId).val()
							 }


		if (replyEditData['replyEditText'] == null || checkSpace(replyEditData['replyEditText']) == "") {
			alert('テキストボックスに文字を入力してください。');
			return
		}



		$.ajax({
			url: 'Edit',
			type : 'POST',
			cache: false,
			data : replyEditData
		}).done (function(result) {
			result = JSON.parse(result);
			if (result.check == true) {
				$('#replyText' + buttonId).text(result.editText);
				document.getElementById('replyEditPass' + buttonId).value = "";
			} else {
				alert('パスワードの不一致');
			}

		}).fail(function() {
			alert('通信失敗');
		});
	});

	// メイン削除
	$(document).on('click', '.mainRemoveDeleteSubmit', function(){
		var buttonId = $(this).attr('id').replace('mainRemoveDeleteSubmit', '');

		var mainRemoveDeleteData = {
									mainRemoveDeleteId : $('.mainRemoveDeleteForm').children('#mainRemoveDeleteId' + buttonId).val(),
									mainRemoveDeleteKind : $('input[name="mainRemoveDeleteKind"]:checked').val(),
									mainRemoveDeletePass : $('.mainRemoveDeleteForm').children('#mainRemoveDeletePass' + buttonId).val()
									}

		if (mainRemoveDeleteData['mainRemoveDeleteKind'] == null) {
			alert('削除の種類を選んでください。');
			return;
		}

		$.ajax({
			url: 'RemoveDelete',
			type: 'POST',
			cache: false,
			data: mainRemoveDeleteData
		}).done (function(result) {
			if (result == '1') {
				alert('物理削除');
			} else if (result == '2') {
				alert('論理削除');
			} else {
				alert('パスワードの不一致');
				return;
			}
			$('#mainRemoveDeleteCheck' + buttonId).remove();
		}).fail (function(){
			alert('通信失敗');
		});
	});

	// リプライ削除
	$(document).on('click', '.replyRemoveDeleteSubmit', function(){
		var buttonId = $(this).attr('id').replace('replyRemoveDeleteSubmit', '');
		var replyRemoveDeleteData = {
									 replyRemoveDeleteId : $('.replyRemoveDeleteForm').children('#replyRemoveDeleteId' + buttonId).val(),
									 replyRemoveDeleteKind : $('input[name="replyRemoveDeleteKind"]:checked').val(),
									 replyRemoveDeletePass : $('.replyRemoveDeleteForm').children('#replyRemoveDeletePass' + buttonId).val()
									 }

		if (replyRemoveDeleteData['replyRemoveDeleteKind'] == null) {
			alert('削除の種類を選んでください。');
			return;
		}


		$.ajax({
			url: 'RemoveDelete',
			type: 'POST',
			cache: false,
			data: replyRemoveDeleteData
		}).done (function(result) {
			if (result == '1') {
				alert('物理削除');
			} else if (result == '2') {
				alert('論理削除');
			} else {
				alert('パスワードの不一致');
			}
			$('#replyRemoveDeleteCheck' + buttonId).remove();
		}).fail (function(){
			alert('通信失敗');
		});
	});
});