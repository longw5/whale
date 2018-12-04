<html>
<head>
<script type="text/javascript" src="js/echarts.min.js"></script>
</head>
<body background="">
<!-- 折现图 -->
<div id="chartmain1" style="width:600px; height: 400px;" align="center"></div>
<!-- 饼状图 -->
<div id="chartmain2" style="width:600px; height: 400px;" align="center"></div>

<div id="chartmain3" style="width:600px; height: 400px;"></div>

<div id="chartmain4" style="width:600px; height: 400px;"></div>
</body>
<script type="text/javascript">
        //指定图标的配置和数据
        var option = {
            title:{
                text:'ECharts 数据统计1'
            },
            tooltip:{},
            legend:{
                data:['用户来源']
            },
            xAxis:{
                data:["Android222","IOS","PC","Ohter","aaa","bbb","ccc","ddd","eee","fff"]
            },
            yAxis:{
            },
            series:[{
                name:'访问量',
                type:'line',
                data:[1500,200,360,100,1000,200,50,800,1200,700]
            }]
        };
        //初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('chartmain1'));

        //使用制定的配置项和数据显示图表
        myChart1.setOption(option);
    </script>
    
    
    <script type="text/javascript">
        //指定图标的配置和数据
        var option = {
            title:{
                text:'ECharts 数据统计2'
            },            
            series:[{
                name:'访问量',
                type:'pie',    
                radius:'60%', 
                data:[
                    {value:500,name:'Android'},
                    {value:200,name:'IOS'},
                    {value:360,name:'PC'},
                    {value:100,name:'Ohter'}
                ]
            }]
        };
        //初始化echarts实例
        var myChart2 = echarts.init(document.getElementById('chartmain2'));

        //使用制定的配置项和数据显示图表
        myChart2.setOption(option);
    </script>
    
    <script type="text/javascript">
        //指定图标的配置和数据
        option = {
		    title : {
		        text: '动态数据3',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['最新成交价', '预购队列']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    dataZoom : {
		        show : false,
		        start : 0,
		        end : 100
		    },
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : true,
		            data : (function (){
		                var now = new Date();
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
		                    now = new Date(now - 2000);
		                }
		                return res;
		            })()
		        },
		        {
		            type : 'category',
		            boundaryGap : true,
		            data : (function (){
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.push(len + 1);
		                }
		                return res;
		            })()
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            scale: true,
		            name : '价格',
		            boundaryGap: [0.2, 0.2]
		        },
		        {
		            type : 'value',
		            scale: true,
		            name : '预购量',
		            boundaryGap: [0.2, 0.2]
		        }
		    ],
		    series : [
		        {
		            name:'预购队列',
		            type:'bar',
		            xAxisIndex: 1,
		            yAxisIndex: 1,
		            data:(function (){
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.push(Math.round(Math.random() * 1000));
		                }
		                return res;
		            })()
		        },
		        {
		            name:'最新成交价',
		            type:'line',
		            data:(function (){
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.push((Math.random()*10 + 5).toFixed(1) - 0);
		                }
		                return res;
		            })()
		        }
		    ]
		};
		var lastData = 11;
		var axisData;
		timeTicket = setInterval(function (){
		    lastData += Math.random() * ((Math.round(Math.random() * 10) % 2) == 0 ? 1 : -1);
		    lastData = lastData.toFixed(1) - 0;
		    axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');
		    
		    // 动态数据接口 addData
		    myChart.addData([
		        [
		            0,        // 系列索引
		            Math.round(Math.random() * 1000), // 新增数据
		            true,     // 新增数据是否从队列头部插入
		            false     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
		        ],
		        [
		            1,        // 系列索引
		            lastData, // 新增数据
		            false,    // 新增数据是否从队列头部插入
		            false,    // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
		            axisData  // 坐标轴标签
		        ]
		    ]);
		}, 2100);
		clearInterval(timeTicket);
                    
        //初始化echarts实例
        var myChart3 = echarts.init(document.getElementById('chartmain3'));
        //使用制定的配置项和数据显示图表
        myChart3.setOption(option);
    </script>
</html>
