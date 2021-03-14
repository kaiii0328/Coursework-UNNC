<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>Manage the System</title>
    <link rel="stylesheet" type="text/css" href="css/repkpi.css"/>
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
            <li >
                <a href="#">HR Management</a>
                <ul>
                    <li><a href="repregister.html">New Sales Rep</a></li>
                    <li><a href="repoverview.php">Rep Overview</a></li>
                    <li><a href="clientoverview.php">Client Overview</a></li>
                </ul>
            </li>
            <li class="current">
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
	<div class="table">
		<h2>Mask Sold by Each Representative</h2>
		<canvas id="repkpi"></canvas>
		<script>
        $(document).ready(function () {
            showGraph();
        });


        function showGraph()
        {
            {
                $.post("rep_kpi.php",
                function (data)
                {
                    console.log(data);
                     var name = [];
                    var marks = [];

                    for (var i in data) {
                        name.push(data[i].RepName);
                        marks.push(data[i].Quantity);
                    }

                    var chartdata = {
                        labels: name,
                        datasets: [
                            {
				label:'Total masks sold',
                                backgroundColor: ['#6794a7','#014d64','#76c0c1',
'#01a2d9','#7ad2f6','#00887d','#adadad','#7c260b','#6794a7','#014d64','#76c0c1','#a18376'],
                                borderColor: '#46d5f1',
                                hoverBackgroundColor: '#CCCCCC',
                                hoverBorderColor: '#666666',
                                data: marks
                            }
                        ]
                    };

                    var graphTarget = $("#repkpi");
		    Chart.defaults.global.defaultFontColor = 'black';
		    Chart.defaults.global.defaultFontSize = 20;
                    var barGraph = new Chart(graphTarget, {
                        type: 'horizontalBar',
                        data: chartdata,
			options:{
				scales:{
					xAxes:[{
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
