layui.define(function (e) {
    var obj = {
        ajax: function (url, type, dataType, data, callback) {
            layui.$.ajax({
                url: url,
                type: type,
                dataType: dataType,
                data: data,
                success: callback
            });
        },
        post: function (url, data, callback) {
            layui.$.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                data: data,
                success: callback
            });
        },
        postFromSubmitParamArray: function (url, paramArray, target) {
            var postUrl = url;//提交地址
            var ExportForm = document.createElement("FORM");
            document.body.appendChild(ExportForm);
            ExportForm.method = "POST";
            if (target && (target == "_blank" || target == "_blank" || target == "_parent" || target == "_self" || target == "_top")) {
                ExportForm.target = target;
            }
            for (var i = 0; i < paramArray.length; i++) {
                var newElement = document.createElement("input");
                newElement.setAttribute("name", paramArray[i].key);
                newElement.setAttribute("type", "hidden");
                ExportForm.appendChild(newElement);
                newElement.value = paramArray[i].value;
            }
            ExportForm.action = postUrl;
            ExportForm.submit();
        },
        postFromSubmitParamMap: function (url, paramMap, target) {
            var postUrl = url;
            var ExportForm = document.createElement("FORM");
            document.body.appendChild(ExportForm);
            ExportForm.method = "POST";
            if (target && (target == "_blank" || target == "_blank" || target == "_parent" || target == "_self" || target == "_top")) {
                ExportForm.target = target;
            }
            for (var key in paramMap) {
                var newElement = document.createElement("input");
                newElement.setAttribute("name", key);
                newElement.setAttribute("type", "hidden");
                ExportForm.appendChild(newElement);
                newElement.value = paramMap[key];
            }
            ExportForm.action = postUrl;
            ExportForm.submit();
        },
        form: function (url, paramArray, target) {
            var postUrl = url;
            if (url.indexOf("?") != -1) {
                var paramArray = new Array();
                postUrl = url.substring(0, url.indexOf("?"))
                var str = url.substring(url.indexOf("?") + 1, url.length);
                var strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    var input = {};
                    input.key = strs[i].split("=")[0];
                    input.value = strs[i].split("=")[1];
                    paramArray.push(input)
                }
                obj.postFromSubmitParamArray(postUrl, paramArray, target);
            } else {
                obj.postFromSubmitParamMap(postUrl, paramArray, target);
            }
        },
        isEmpty: function (_obj) {
            if (typeof _obj == "undefined" || _obj == null || _obj == "") {
                return true;
            } else {
                return false;
            }
        },
        downFile: function (url) {
            // 定义一个form表单
            var form = layui.$("<form>");
            // 在form表单中添加查询参数
            form.attr('style', 'display:none');
            form.attr('target', '');
            // 使用POST方式提交
            form.attr('method', 'post');
            form.attr('action', url);
            //将表单放置在web中
            layui.$('body').append(form);
            form.submit();
        }
    };
    e("site", obj)
});