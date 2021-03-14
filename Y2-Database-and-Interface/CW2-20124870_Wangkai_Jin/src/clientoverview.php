<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>Manage the System</title>
    <link rel="stylesheet" type="text/css" href="css/clientoverview.css">
    
	<script type="text/javascript" src="chart/js/jquery.min.js"></script>
	<script type="text/javascript" src="chart/js/Chart.min.js"></script>
</head>
<body>
    <!--<?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/checkcookie.php"); ?>-->
    <header>
        <ul class="top">
            <li ><a href="managerpage.php">Home</a></li>
            <li>
                <a href="#">About</a>
                <ul class="top">
                    <li><a href="https://kaiii0328.github.io/">Author</a></li>
                </ul>
            </li>
            <li class="current">
                <a href="customerpage.html">HR Management</a>
                <ul>
                    <li><a href="repregister.html">New Sales Rep</a></li>
                    <li><a href="repoverview.php">Rep Overview</a></li>
                    <li><a href="clientoverview.php">Client Overview</a></li>
                </ul>
            </li>
            <li>
                <a href="">Mask Info</a>
                <ul>
                    <li><a href="maskoverview.php">Overview</a></li>
		    <li><a href="repkpi.php">Rep KPI</a></li>
                </ul>
            </li>
            <li>
                <a href="#">Account Info</a>
                <ul>
                    <li><a href="logout.php">Log out</a></li>
                </ul>
            </li>
        </ul>
    </header>
    
    <div class="main">
	<div class="chart1">
		<canvas id="region_cus"></canvas>
		<script>
        $(document).ready(function () {
            showGraph();
        });


        function showGraph()
        {
            {
                $.post("region_cus.php",
                function (data)
                {
                    console.log(data);
                     var name = [];
                    var marks = [];

                    for (var i in data) {
                        name.push(data[i].Region);
                        marks.push(data[i].Total);
                    }

                    var chartdata = {
                        labels: name,
                        datasets: [
                            {
                                backgroundColor: ['#6794a7','#014d64','#76c0c1',
'#01a2d9','#7ad2f6','#00887d','#adadad','#7c260b','#6794a7','#014d64','#76c0c1','#a18376'],
                                borderColor: '#46d5f1',
                                hoverBackgroundColor: '#CCCCCC',
                                hoverBorderColor: '#666666',
                                data: marks
                            }
                        ]
                    };

                    var graphTarget = $("#region_cus");
		    Chart.defaults.global.defaultFontColor = 'black';
		    Chart.defaults.global.defaultFontSize = 16;
                    var barGraph = new Chart(graphTarget, {
                        type: 'doughnut',
                        data: chartdata,
			options:{
				title:{
					display:true,
					text:"Customers in each region",
					fontSize:25,
					fontStyle:'bold'
				}
			}
                    });
                });
            }
        }
        </script>
			
	</div>
	<div class="chart2">
		<canvas id="region_cus2"></canvas>
			<script>
        $(document).ready(function () {
            showGraph2();
        });


        function showGraph2()
        {
            {
                $.post("region_cus2.php",
                function (data)
                {
                    console.log(data);
                     var name = [];
                    var marks = [];

                    for (var i in data) {
                        name.push(data[i].CusName);
                        marks.push(data[i].Quantity);
                    }

                    var chartdata = {
                        labels: name,
                        datasets: [
                            {
				label:'Amount of Purchase',
                                backgroundColor: ['#1A476F','#90353B','#9C8847','#01a2d9','#76c0c1','#7ad2f6'],
                                borderColor: '#46d5f1',
                                hoverBackgroundColor: '#CCCCCC',
                                hoverBorderColor: '#666666',
                                data: marks
                            }
                        ]
                    };

                    var graphTarget = $("#region_cus2");
		            Chart.defaults.global.defaultFontColor = 'black';
		            Chart.defaults.global.defaultFontSize = 16;
                    var barGraph = new Chart(graphTarget, {
                        type: 'bar',
                        data: chartdata,
			options:{
				scales:{
					title:{
						display:true,
						text:"Purchase per user",
						fontSize:25,
						fontStyle:'bold'
					},
					yAxes:[{
						ticks:{beginAtZero:true}
					}]
					}
			}
                    });
                });
            }
        }
        </script>
	</div>
    </div>


</body>
</html>
