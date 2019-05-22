<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
<h2>Hello World!</h2>
<div id="wrapper">
</div>
    <form action="/login" method="post" >
        <%--<input name="answer" />--%>
        <button id="change">看不清?换一张</button>
        <button id="getAudio">语音</button>

    </form>

    <img id="img" src="/stickyImg" />
    <form action="/login" method="post" >
        <input name="answer"/>
    </form>
</div>

</body>
<script>
    $(document).ready(function () {

        $('#getAudio').click(function(e){
            e.preventDefault();
            // $('#wrapper').html('<audio id="audio" controls autoplay src="/audio.wav"></audio>')
            // var randNum=Math.floor(Math.random()*100)
            $('#wrapper').html('<audio id="audio" controls="controls" autoplay="autoplay" src="/audio.wav"></audio>');
            $('#audio').attr('src','<%=request.getContextPath()%>/audio.wav?'+Math.floor(Math.random()*100));
        });
        $('#change').click(function (e) {
            e.preventDefault();
            $.get('/refresh',function (res) {
                alert("refresh success");
                $('#audio').css("display","none");
                $('#img').attr('src','<%=request.getContextPath()%>/stickyImg?'+Math.floor(Math.random()*100));

            });
            <%--$('#img').attr('src','<%=request.getContextPath()%>/stickyImg?'+Math.floor(Math.random()*100));--%>

            <%--$('#audio').css("display","none");--%>

        })

    })

</script>
</html>
