<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
<h2>Hello World!</h2>
<audio id="audio" controls autoplay src="/audio.wav"></audio>
<form action="/login" method="post" >
    <%--<input name="answer" />--%>
    <button id="change">看不清?换一张</button>

    <%--<input   type="submit" value="验证"/>--%>

</form>

<img id="img" src="/stickyImg" />
<form action="/login" method="post" >
    <input name="answer"/>
</form>

</body>
<script>
    $(document).ready(function () {

        // $('#change').on('click',(function (e) {
        //     console.log(23)
        // }))
        $('#change').click(function (e) {
            e.preventDefault();
            // alert(32423)


            $('#audio').attr('src','<%=request.getContextPath()%>/audio.wav?'+Math.floor(Math.random()*100));
            $('#img').attr('src','<%=request.getContextPath()%>/stickyImg?'+Math.floor(Math.random()*100));

        })

    })

</script>
</html>
