<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
<script type="text/javascript">


	//var ws = new WebSocket(encodeURI('ws://localhost:8080/WebSocket/message'));
	var ws ="";
	function startWebSocket() {
		if ('WebSocket' in window)
// 			ws = new WebSocket("ws://localhost:8080/WebSocket/message");
			ws = new WebSocket("ws://localhost:8080/deviceService/message");
		else if ('MozWebSocket' in window)
			ws = new MozWebSocket("ws://localhost:8080/deviceService/message");
		else
			alert("not support");

		ws.onmessage = function(message) {
			alert("onmessage : "+message.data);
		};

		ws.onclose = function(evt) {
			alert("close");
		};

		ws.onopen = function(evt) {
			alert("open");
		};
	}

// 	function cc() {
// 			alert("close");
// 			ws.onclose('');
// 	}
	function sendMsg() {
				var message = document.getElementById('writeMsg').value;
				if (ws != null) {
					ws.send(JSON.stringify(message));
				} else {
					Ext.Msg.alert('提示', '您已经掉线，无法发送消息!');
				}
			
		//ws.send(document.getElementById('writeMsg').value);
	} 
</script>
</head>
<body onload="startWebSocket();">
	<input type="text" id="writeMsg"></input>
	<input type="button" value="send" onclick="sendMsg()"></input>
<!-- 	<input type="button" value="close" onclick="cc()"></input> -->
</body>
</html>