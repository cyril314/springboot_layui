<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8"/>
<title><@block name="popTitle">通用后台管理模板系统</@block></title>
<meta name="renderer" content="webkit"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<link rel="stylesheet" href="${webPath}/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${webPath}/css/admin.css" media="all"/>
<link rel="stylesheet" href="${webPath}/css/font-awesome.css" media="all"/>
<@block name="csslibs"></@block>
<style>
    .layui-icon {font-family: layui-icon, FontAwesome!important; }
    <@block name="CSS"><#--通用样式--></@block>
</style>
</head>
<body>
<div><@block name="body">通用主体</@block></div>

<div><@block name="foot"><#--通用页脚--></@block></div>
</body>
<script src="${webPath}/layui/layui.js"></script>
<@block name="jslibs"><#--通用JS插件--></@block>
<script>
    <@block name="JS"><#--通用JS--></@block>
</script>
</html>