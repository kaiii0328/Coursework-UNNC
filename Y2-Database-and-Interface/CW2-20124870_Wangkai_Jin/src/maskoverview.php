<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>Manage the System</title>
    <link rel="stylesheet" type="text/css" href="css/maskoverview.css"/>
    <script type="text/javascript" src="chart/js/jquery.min.js"></script>
	<script type="text/javascript" src="chart/js/Chart.min.js"></script>

</head>
<body>
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
                <a href="customerpage.php">HR Management</a>
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
        	<div class="navbutton">
			<button class="btn active">Total Mask Sold</button>
			<button class="btn">Different type of Mask</button>
			<button class="btn">Timeline</button>
		</div>
		<div class="rep show">
			<canvas id="total"></canvas>
			<script>
        $(document).ready(function () {
            showGraph();
        });


        function showGraph()
        {
            {
                $.post("totalmask.php",
                function (data)
                {
		    console.log(data.Total1);
 		    console.log(data.Total2);
                    var marks = [];
		    var marks2 = [];
		    var num=parseInt(data.Total1)+parseInt(data.Total2);
                    marks.push(data.Total1);
                    marks.push(data.Total2);
		    marks.push(num);
                    var chartdata = {
                        labels: ['Normal','Anomaly','Normal+Anomaly'],
                        datasets: [
                            {
                                label: 'Total',
                                backgroundColor: ['#3e647d','#008BBC','#014d64'],
                                borderColor: '#46d5f1',
                                hoverBackgroundColor: '#CCCCCC',
                                hoverBorderColor: '#666666',
				barThickness:8,
                                data: marks
				}		
			
                        ]
                    };
                    var graphTarget = $("#total");
		    Chart.defaults.global.defaultFontColor = 'black';
		    Chart.defaults.global.defaultFontSize =24;
                    var barGraph = new Chart(graphTarget, {
                        type: 'horizontalBar',
                        data: chartdata,
			options:{
					legend:{
						display:true,
					},
					scales:{
						 xAxes:[{
							ticks:{beginAtZero:true}
							}]
						},
					title:{
						display:true,
						text:'Amount of Maks Sold',
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

		<div class="rep">
			<canvas id="masktype"></canvas>
			<script>
        $(document).ready(function () {
            showGraph2();
        });


        function showGraph2()
        {
            {
                $.post("masktype.php",
                function (data)
                {
                    console.log(data);
                     var name = [];
                    var marks = [];

                    for (var i in data) {
                        name.push(data[i].Masktype);
                        marks.push(data[i].Total);
                    }

                    var chartdata = {
                        labels: name,
                        datasets: [
                            {
                                backgroundColor: ['#36478c','#29527e','#029bbb'],
                                borderColor: '#46d5f1',
                                hoverBackgroundColor: '#CCCCCC',
                                hoverBorderColor: '#666666',
                                data: marks
                            }
                        ]
                    };

                    var graphTarget = $("#masktype");
		    Chart.defaults.global.defaultFontColor = 'black';
		    Chart.defaults.global.defaultFontSize = 16;
                    var barGraph = new Chart(graphTarget, {
                        type: 'doughnut',
                        data: chartdata,
			options:{
				title:{
					display:true,
					text:"Amount sold in each type",
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

		<div class="rep">
			<canvas id="timeline"></canvas>
			<script>
        $(document).ready(function () {
            showGraph3();
        });


        function showGraph3()
        {
            {
                $.post("masktimeline.php",
                function (data)
                {
                    console.log(data);
                     var name = [];
                    var marks = [];

                    for (var i in data) {
                        name.push(data[i].Time);
                        marks.push(data[i].Quantity);
                    }

                    var chartdata = {
                        labels: name,
                        datasets: [
                            {
				label:'total mask per day',
                                backgroundColor: ['#014d64','#29527e','#029bbb'],
                                borderColor: '#014d64',
                                hoverBackgroundColor: '#CCCCCC',
                                hoverBorderColor: '#666666',
				fill:false,
                                data: marks
                            }
                        ]
                    };

                    var graphTarget = $("#timeline");
		    Chart.defaults.global.defaultFontColor = 'black';
		    Chart.defaults.global.defaultFontSize = 16;
                    var barGraph = new Chart(graphTarget, {
                        type: 'line',
                        data: chartdata,
			options:{
				title:{
					display:true,
					text:"Amount sold in past few days",
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
	</div>
	</div>
	<script>
			var btns=document.getElementsByClassName("btn");
			var contents = document.getElementsByClassName("rep");
			for (var i=0;i<btns.length;i++){
				btns[i].index = i;
				btns[i].onclick = function(){
					for (var j=0;j<btns.length;j++){
						btns[j].className = btns[j].className.replace(' active','').trim();
						contents[j].className = contents[j].className.replace(' show','').trim();
					}
					this.className = this.className+ ' active';
					contents[this.index].className = contents[this.index].className + ' show';
					
				}
			
			}
		</script>


</body>
</html>
