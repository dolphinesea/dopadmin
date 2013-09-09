<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
    <title>党政专网话务管理系统</title>

    <%@ include file="/commons/taglibs.jsp"%>

    <script src="scripts/jquery.js" type="text/javascript"></script>
    <script src="scripts/jquery.slidingmessage.js" type="text/javascript"></script>

    <script language="javascript">
    $(document).ready(function()
    {
        $('.default-text').focus(function () {
            if ($(this).val() == $(this).attr("title")) {
                $(this).val("");
            }
        }).blur(function () {
            if ($(this).val() == "") {
                $(this).val($(this).attr("title"));
            }
        });
    });
    </script>

    <style>
        body {
            height: 100%;
            overflow: hidden;
            background-image: -moz-linear-gradient(top, #69b, #123);
        }

        .dialog {
            overflow: hidden;
            border: 3px solid #888;
            border-radius: 11px;
            width: 500px;
            height: 430px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 150px;
            background: url(images/texture/lightgray.png);
            box-shadow: 0 0 15px black;
        }

        .dialog .content {
            padding: 10px 50px 20px 50px;
            border-bottom: 1px solid #aaa;
        }

        .dialog h1 {
            display:inline-block;
            position: relative;
            line-height: 2.0em;
            width:64%;
            top: -20px;
            font-size:16pt;
            letter-spacing: 0.1em;
            color: #444;
            text-align: center;
            margin: 30px 10px;
            padding-left: 20px;
            background-image: -moz-linear-gradient(left,
                                                   rgba(32, 32, 80, 0),
                                                   rgba(32, 32, 80, 0.13),
                                                   rgba(32, 32, 50, 0));
        }

        .dialog .icon {
            position: relative;
            top: 10px;
            display: inline-block;
            width: 80px;
            margin-left: 10px;
        }

        .dialog .footer {
            padding: 10px 30px;
            border-top: 1px solid #ccc;
            background: -moz-linear-gradient(center top, rgba(0,0,0,0.4) 0%, rgba(0,0,0,0.39) 30%, rgba(0,0,0,0.3) );
        }

        .dialog input {
            display: block;
            border: 2px solid #777;
            border-radius: 15px 7px 7px 15px;
            width:  100%;
            height: 45px;
            margin-bottom: 15px;
        }

        .dialog input[type="text"],
        .dialog input[type="password"] {
            box-shadow: 0 0 2px hsla(30, 40,50, 0.1) ;
            padding-left: 65px;
		    color: #7A7A7A;
		    font: bold italic 16pt "Bookman Old Style","Consolas"
		}

        .dialog input[type="text"]:hover,
        .dialog input[type="password"]:hover {
            box-shadow: 0 0 3px #175E7C;
            width: 101%;
			border-color:#175E7C;
			color:#5A7A9A;
		}

		.dialog input[type="submit"] {
            color: #eee;
		    font: normal 16pt "微软雅黑","Bookman Old Style","Consolas";
		    margin-top:4px;
		    border-radius: 7px;
		    box-shadow: 0 0 2px #B2B2B2;
            background: url(images/icon/unlock.png) no-repeat 96% 5px,
                        -moz-linear-gradient(top, #cc6020 0%, #c06000 50%, #c50 51%, orange 100%);
            letter-spacing: 0.5em;
	    }

        .dialog input[type="submit"]:hover { 
            color: #fff;
            box-shadow: 0 0 7px #888;
        }

        input[name="account"] {
            background: url(images/icon/user.png) no-repeat 12px 7px,
                        -moz-linear-gradient(left top, orange 0%, orange 50px, gray 51px, rgba(255,255,255,0.5) 52px);
            background-size: 27px 27px, 100%;
        }

        input[name="password"] {
            background: url(images/icon/key.png) no-repeat 13px 9px,
                        -moz-linear-gradient(left top, orange 0%, orange 50px, gray 51px, rgba(255,255,255,0.6) 52px);
            background-size: 25px 25px, 100%;
        }

        .product-name {
            text-align: center;
            font: bold normal 22pt "宋体";
            letter-spacing: 0.2em;
            line-height: 25px;
            margin-top: 10px;
            text-shadow: 0px 1px 1px #ccc;
            color: #222;
        }
        .copyright {
            text-align: center;
            font: italic 10pt "宋体";
            text-shadow: 0px 1px 1px #eee;
            color: #333;
        }
        .en-char {
            font-family: "Book Antiqua","Times New Roman";
            font-weight: bold;
        }
        #sliding_message_box {
            font: normal 22pt "宋体";
            background: rgba(170, 170, 0, 0.2);
            color: orange;
            opacity: 0.8;
        }
    </style>

</head>

<body>
    <div class='dialog'>
        <div class='content'>
            <h1>请登录</h1>
            <img class="icon" src="images/icon/system.png"/>
            <form action="${ctxPath}/login/userLogin.do" method="post">
                <input type="text" name="account" class="default-text"
                    value="请输入话务员工号" title="请输入话务员工号" />
                <input type="password" name="password" class="default-text"
                    value="请输入登录密码" title="请输入登录密码" />
                <input type="submit" name="btnLogin" value="登录"/>
            </form>
        </div>
        <div class='footer'>
            <p class="product-name">党政专网话务管理系统</p>
            <p class="copyright"><span class="en-char">&copy;2012</span>，上海欣国信息技术有限公司</p>
        </div>
    </div>

    <script language="javascript">
        var error = <% if (request.getParameter("error") != null)
                            out.print(request.getParameter("error").toString());
                    %>;
        if (error == 1) {
            var options = {position: 'top',
                           size: 50,
                           delay: 5000,
                           speed: 500,
                          };
                           
            $.showMessage("输入的工号或登录密码错误，请重新输入", options);
        }
    </script>
</body>
</html>
