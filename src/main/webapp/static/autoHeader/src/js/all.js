/**
 * autoHeader 应用
 * created by Finira 2016-1-11
 */



$(function(){
    //返回列表按钮
    $('#backlist').on('click', function() {
        $('#listdiv').slideDown();
        $('#viewdiv').slideUp();
    });
    $('#compare_avg').on('click', function(e) {
        $.fn.autoHeader.anaLyz({anaJson:currentJson,baseJson:bodyjson,anatype:'avg',tableid:'tabled',digit:2,compare:{enable:true}});
    });
    $('#compare_max').on('click', function(e) {
        $.fn.autoHeader.anaLyz({anaJson:currentJson,baseJson:bodyjson,anatype:'max',tableid:'tabled',digit:2,compare:{enable:true}});
    });
    $('#compare_min').on('click', function(e) {
        $.fn.autoHeader.anaLyz({anaJson:currentJson,baseJson:bodyjson,anatype:'min',tableid:'tabled',digit:2,compare:{enable:true}});
    });
    $('#compare_locate').on('click', function(e) {
        window.location.href = '#156';
    });


    $('#compare_tran').on('click', function(e) {
        $.fn.autoHeader.transCompare({anaJson:currentJson,baseJson:bodyjson,tableid:'tabled'});
        $("#containdivcnt").show();
    });

    $.fn.zTree.init($("#treeDemo_org"), setting, treejson_);

    //筛选按钮
    $('#generator').on('click', function() {
        //获取表头筛选数据，重新生成table
        var treeObj = $.fn.zTree.getZTreeObj("header_tree");
        var nodes = treeObj.getCheckedNodes();
        var bodyjson_ = getSelectOrg();
//        console.log("head-------"+JSON.stringify(nodes) );
        initTable("tabled",nodes,bodyjson_);
        $("#containdivcnt").hide();
        //统计总表清空
        $('#tabledcnt').empty();
        //表头树默认缩回
        $('#headtre').slideUp();
        $('#headerchoose').html('<span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>&nbsp;选择表头');

    });
    Highcharts.setOptions({
        lang: {
            printChart:"打印图表",
            downloadJPEG: "下载JPEG 图片" ,
            downloadPDF: "下载PDF文档"  ,
            downloadPNG: "下载PNG 图片"  ,
            downloadSVG: "下载SVG 矢量图" ,
            exportButtonTitle: "导出图片"
        }
    });
    //饼状图
    $('#pie_chart').on('click', function() {
        $('#generator').click();

        var bodyjson_ = getSelectOrg();
        //获取表头筛选数据
        var treeObj = $.fn.zTree.getZTreeObj("header_tree");
        var nodes = treeObj.getCheckedNodes();
        var header_pie = [];
        for(var i=0;i<nodes.length;i++){
            var obj_value = nodes[i];
            if(obj_value.isleaf == 'Y'){
                var obj = new Object();
                obj.name = obj_value.name;
                obj.value = obj_value.reportColumnName;
                header_pie.push(obj);
            }
        }
        if(header_pie.length>1){
        	top.$.jBox.tip("该图表只能选择一个表头进行对比");
        	resetTip();
            return;
        }
        //var shuxing = [{name:'专题学习次数',value:'zzztxx'}];
        var shuju = [];
        var sx = header_pie[0].value;
        for(var m=0;m<bodyjson_.length;m++){
            var obj=[];
            obj[0] = bodyjson_[m].rep_org_name;
            var d_in_value = parseInt(bodyjson_[m][sx]);
            if(isNaN(d_in_value)){
                d_in_value = 0;
            }
            obj[1] = d_in_value;
            shuju.push(obj);
        }
        $('#container').slideDown();
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            }, title: {
                text: ''
            }, tooltip: {
                pointFormat: '{series.name}: <b>{point.y:.1f}</b>'
            }, plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    }
                }
            }, series: [{
                type: 'pie',
                name: header_pie[0].name,
                data: shuju
            }]
        });
    });

    //3D 饼状图
    $('#pie_chart_ddd').on('click', function() {
        $('#generator').click();

        var bodyjson_ = getSelectOrg();
        //获取表头筛选数据
        var treeObj = $.fn.zTree.getZTreeObj("header_tree");
        var nodes = treeObj.getCheckedNodes();
        var header_pie = [];
        for(var i=0;i<nodes.length;i++){
            var obj_value = nodes[i];
            if(obj_value.isleaf == 'Y'){
                var obj = new Object();
                obj.name = obj_value.name;
                obj.value = obj_value.reportColumnName;
                header_pie.push(obj);
            }
        }
        if(header_pie.length>1){
        	top.$.jBox.tip("该图表只能选择一个表头进行对比");
        	resetTip();
            return;
        }
        //var shuxing = [{name:'专题学习次数',value:'zzztxx'}];
        var shuju = [];
        var sx = header_pie[0].value;
        for(var m=0;m<bodyjson_.length;m++){
            var obj=[];
            obj[0] = bodyjson_[m].rep_org_name;
            var d_in_value = parseInt(bodyjson_[m][sx]);
            if(isNaN(d_in_value)){
                d_in_value = 0;
            }
            obj[1] = d_in_value;
            shuju.push(obj);
        }
        $('#container').slideDown();
        $('#container').highcharts({
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            }, title: {
                text: ''
            }, tooltip: {
                pointFormat: '{series.name} <b>百分比：{point.y:.1f}</b>'
            }, plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 35,
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name} </b>百分比: {point.percentage:.1f} %'
                    }
                }
            }, series: [{
                type: 'pie',
                name: header_pie[0].name,
                data: shuju
            }]
        });
    });


    //  折线图
    $('#line_chart').on('click', function() {
        $('#generator').click();
        var bodyjson_ = getSelectOrg();
        //获取表头筛选数据
        var treeObj = $.fn.zTree.getZTreeObj("header_tree");
        var nodes = treeObj.getCheckedNodes();
        var shuxing = [];
        for(var i=0;i<nodes.length;i++){
            var obj_value = nodes[i];
            if(obj_value.isleaf == 'Y'){
                //alert(JSON.stringify(obj_value));
                var obj = new Object();
                obj.name = obj_value.name;
                obj.value = obj_value.reportColumnName;
                shuxing.push(obj);
            }
        }
//        if(shuxing.length>10){
//            alert("请选择小于10个表头进行对比！");
//            return;
//        }
        var shuju = [];
        var org = [];
        var data = bodyjson_;
        for(var i=0;i<data.length;i++){
            org[i] = data[i].rep_org_name+"";
        }
        for(var i=0;i<shuxing.length;i++){
            var st = new Object();
            st.name = shuxing[i].name;
            var d = [];
            var sx = shuxing[i].value;
            for(var m=0;m<data.length;m++){
                var obj={};
                var d_in_value = parseInt(data[m][sx]);
                if(isNaN(d_in_value)){
                    d_in_value = 0;
                }
                d[m] = d_in_value;
            }
            st.data = d;
            shuju.push(st);
        }
        $('#container').slideDown();
        $('#container').highcharts({
            chart: {
                type: 'line'
            }, title: {
                text: ''
            }, xAxis: {
                categories: org
            },
            yAxis: {
                title: {
                    text: '数值（次数/百分比）'
                }
            }, tooltip: {
                enabled: false,
                formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y ;
                }
            }, plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            }, series: shuju
        });
    });


    //  柱状图
    $('#column_chart').on('click', function() {
        $('#generator').click();
        var bodyjson_ = getSelectOrg();
        //获取表头筛选数据
        var treeObj = $.fn.zTree.getZTreeObj("header_tree");
        var nodes = treeObj.getCheckedNodes();
        var shuxing = [];
        for(var i=0;i<nodes.length;i++){
            var obj_value = nodes[i];
            if(obj_value.isleaf == 'Y'){
                //alert(JSON.stringify(obj_value));
                var obj = new Object();
                obj.name = obj_value.name;
                obj.value = obj_value.reportColumnName;
                shuxing.push(obj);
            }
        }
//        if(shuxing.length>10){
//            alert("请选择小于10个表头进行对比！");
//            return;
//        }
        var data = bodyjson_;
        var shuju = [];
        var org = [];
        for(var i=0;i<data.length;i++){
            org[i] = data[i].rep_org_name+"";
        }
        for(var i=0;i<shuxing.length;i++){
            var st = new Object();
            st.name = shuxing[i].name;
            var d = [];
            var sx = shuxing[i].value;
            for(var m=0;m<data.length;m++){
                var obj={};
                var d_in_value = parseInt(data[m][sx]);
                if(isNaN(d_in_value)){
                    d_in_value = 0;
                }
                d[m] = d_in_value;
            }
            st.data = d;
            shuju.push(st);
        }
        $('#container').slideDown();
        $('#container').highcharts({
            chart: {
                type: 'column'
            }, title: {
                text: ''
            }, xAxis: {
                categories:org
            }, yAxis: {
                min: 0,
                title: {
                    text: '数值（次数/百分比）'
                }
            }, tooltip: {
                headerFormat: '<span style="font-size:20px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.0f} </b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            }, plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            }, series: shuju
        });
    });


    //  3D 柱状图
    $('#column_chart_ddd').on('click', function() {
        $('#generator').click();
        var bodyjson_ = getSelectOrg();
        //获取表头筛选数据
        var treeObj = $.fn.zTree.getZTreeObj("header_tree");
        var nodes = treeObj.getCheckedNodes();
        //重新组合 需要展现的属性列
        var shuxing = [];
        for(var i=0;i<nodes.length;i++){
            var obj_value = nodes[i];
            if(obj_value.isleaf == 'Y'){
                var obj = new Object();
                obj.name = obj_value.name;
                obj.value = obj_value.reportColumnName;
                shuxing.push(obj);
            }
        }
//        if(shuxing.length>10){
//            alert("请选择小于10个表头进行对比！");
//            return;
//        }
        // 编织数据 到shuju数组
        var data = bodyjson_;
        var shuju = [];
        // org为横坐标 部门数
        var org = [];
        for(var i=0;i<data.length;i++){
            org[i] = data[i].rep_org_name+"";
        }
        for(var i=0;i<shuxing.length;i++){
            var st = new Object();
            st.name = shuxing[i].name;
            var d = [];
            var sx = shuxing[i].value;
            for(var m=0;m<data.length;m++){
                var obj={};
                var d_in_value = parseInt(data[m][sx]);
                if(isNaN(d_in_value)){
                    d_in_value = 0;
                }
                d[m] = d_in_value;
            }
            st.data = d;
            shuju.push(st);
        }
        $('#container').slideDown();
        $('#container').highcharts({
            chart: {
                type: 'column',
                options3d: {
                    enabled: true,
                    alpha: 9,
                    beta: 6,
                    viewDistance: 25,
                    depth: 40
                },
                marginTop: 80,
                marginRight: 40
            }, title: {
                text: ''
            }, xAxis: {
                categories: org
            }, yAxis: {
                allowDecimals: false,
                min: 0,
                title: {
                    text: '数值（次数/百分比）'
                }
            }, tooltip: {
                headerFormat: '<b>{point.key}</b><br>',
                pointFormat: '<span style="color:{series.color}"></span> {series.name}: {point.y}'
            }, plotOptions: {
                column: {
                    depth: 40
                }
            }, series: shuju
        });
    });

    // 条形图
    $('#bar_chart').on('click', function() {
        $('#generator').click();
        var bodyjson_ = getSelectOrg();
        //获取表头筛选数据
        var treeObj = $.fn.zTree.getZTreeObj("header_tree");
        var nodes = treeObj.getCheckedNodes();
        var shuxing = [];
        for(var i=0;i<nodes.length;i++){
            var obj_value = nodes[i];
            if(obj_value.isleaf == 'Y'){
                var obj = new Object();
                obj.name = obj_value.name;
                obj.value = obj_value.reportColumnName;
                shuxing.push(obj);
            }
        }
//        if(shuxing.length>10){
//            alert("请选择小于10个表头进行对比！");
//            return;
//        }
        var data = bodyjson_;
        var shuju = [];
        var org = [];
        for(var i=0;i<data.length;i++){
            org[i] = data[i].rep_org_name+"";
        }
        for(var i=0;i<shuxing.length;i++){
            var st = new Object();
            st.name = shuxing[i].name;
            var d = [];
            var sx = shuxing[i].value;
            for(var m=0;m<data.length;m++){
                var obj={};
                var d_in_value = parseInt(data[m][sx]);
                if(isNaN(d_in_value)){
                    d_in_value = 0;
                }
                d[m] = d_in_value;
            }
            st.data = d;
            shuju.push(st);
        }

        $('#container').slideDown();
        $('#container').highcharts({
            chart: {
                type: 'bar'
            },  title: {
                text: ''
            },  xAxis: {
                categories: org
            },  yAxis: {
                min: 0,
                title: {
                    text: '  '
                },
                style : {
                    'fontSize' : '18px'
                }
            }, tooltip: {
                valueSuffix: ' '
            },  plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },  series: shuju
        });
    });


    //导出Excel
//    $("#export_excel").on('click', function() {
//        $("#tabled").table2excel({
//            // 不被导出的表格行的CSS class类
//            //exclude: ".noExl",
//            // 导出的Excel文档的名称
//            name: "Excel Document Name",
//            // Excel文件的名称
//            filename: "MyExcelTable5555555"
//        });
//    });
    
    $("#export_excel").on('click', function() {
    	fnExcelReport("MyExcelTable.xls");     //导出文件名 MyExcelTable
    });
    //导出图片
    $("#export_png").on('click', function() {

    });

    //最大化按钮功能
    $("#maxbt").on('click', function() {
        if(!ismax){
            $('#refdiv').fadeOut();
            $('#navs').fadeOut();
            $('#alldiv').fadeIn();
            $(this).html('<span class="glyphicon glyphicon-resize-small" aria-hidden="true"></span>&nbsp;恢复原状');
            //复制按钮和table的内容到alldiv 中
            $('#buttons').insertBefore('#dtiyp');
            //增加导航css，滚动条滚动导航条不动
            $('#buttons').addClass('navbar-fixed-top');
            $('#analyzz').insertAfter('#dtiyp');
            $('#containdivcnt').insertAfter('#dtiyp');
            $('body').attr('class','bodyafter');
            //$('#tabled').insertAfter('#tabledcnt');
            //$('#container').insertAfter('#tabled');
            ismax = true;
        }else{
            //div隐藏关系改变，按钮和table内容重新复制回原来的位置
            $('#alldiv').fadeOut();
            $('#navs').fadeIn();
            $('#refdiv').fadeIn();
            $('#buttons').insertBefore('#datev');
            $('#analyzz').insertAfter('#tablepositonid');
            $('#containdivcnt').insertAfter('#datev');
            //$('#tabled').insertAfter('#tabledcnt');
            //$('#container').insertAfter('#tabled');
            $('#buttons').removeClass('navbar-fixed-top');
            $(this).html('<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>&nbsp;最大化');
            $('body').attr('class','bodypre');
            ismax = false;
        }

    });

    //表头树，按钮激发
    $('#headerchoose').on('click', function(e) {
        if($('#headtre').is(":hidden")){
            $('#headtre').slideDown();
            $(this).html('<span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>&nbsp;收回表头');
        }else{
            $('#headtre').slideUp();
            $(this).html('<span class="glyphicon glyphicon-tasks" aria-hidden="true"></span>&nbsp;选择表头');
        }
    });

});



/**
 * 根据选择的部门信息过滤数据包，将符合条件的留下
 */
function validateBodyJson(orgstr){
    if(orgstr){
        var newJson =[];
        //循环json，获取上报部门ID字段
        $.each(bodyjson, function (j,itemj) {
            var orgcode = "_"+itemj.rep_org_code+"_";
            //如果不在选中的部门ID范围内 则删除数据
            if(orgstr.indexOf(orgcode)>=0){
                newJson.push(itemj);
            }
        });
        return newJson;
    }
}


//定义树
var setting = {
    check: {
        enable: true,
        chkboxType:{ "Y":"ps", "N":"ps"}

    },
    data: {
        simpleData: {
            enable: true
        }
    }
};

var code;
var ismax=false;
var treejson=null;
var bodyjson = null;
var currentJson =null;


//表格初始化
function showView(){
    $('#listdiv').slideUp();
    $('#viewdiv').slideDown();
    var headjson_ = header_;
    var bodyjson_= dataer_;
    var treejson_ = headjson_;
    currentJson = bodyjson = bodyjson_ ;
    initTable("tabled",headjson_,bodyjson_);
    //console.log("head-------"+JSON.stringify(headjson_) );
    $.fn.zTree.init($("#header_tree"), setting, treejson_);

}

function getSelectOrg(){
    var treeOrg = $.fn.zTree.getZTreeObj("treeDemo_org");
    var orgnodes = treeOrg.getCheckedNodes();
    var orgstr='_';
    //循环遍历已选部门 打包部门ID
    $.each(orgnodes, function (i, item) {
        orgstr += item.id+"_";
    });
    var bodyjson_ = bodyjson;
    //如果有选择部门，就去筛选body数据
    if(orgstr!='_' ){
        bodyjson_ = validateBodyJson(orgstr);
    }
    currentJson = bodyjson_;
   // console.log(JSON.stringify(currentJson) );
    return bodyjson_;
}


// 导出excel
function fnExcelReport(fileName) {
    var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
    alert(234234234)
    tab_text = tab_text + '<head><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>';

    tab_text = tab_text + '<x:Name>Test Sheet</x:Name>';

    tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
    tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';

    tab_text = tab_text + "<table border='1px'>";
    tab_text = tab_text + $('#tabled').html();
    tab_text = tab_text + '</table></body></html>';
    tab_text = tab_text.replace('rowspan="3"','rowspan="2"');

    var data_type = 'data:application/vnd.ms-excel';

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        if (window.navigator.msSaveBlob) {
            var blob = new Blob([tab_text], {
                type: "application/csv;charset=utf-8;"
            });
            navigator.msSaveBlob(blob, fileName);
        }
        console.log(tab_text)
        
    } else {
        $('#export_excel').attr('href', data_type + ', ' + encodeURIComponent(tab_text));
        $('#export_excel').attr('download', fileName);
    }
}
