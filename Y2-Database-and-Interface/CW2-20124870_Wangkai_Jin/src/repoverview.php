<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>Manage the System</title>
    <link rel="stylesheet" type="text/css" href="css/repoverview.css"/>
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
                <a href="customerpage.php">HR Management</a>
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
	<div class="table">
        	<div class="navbutton">
			<button class="btn active">Basic Info</button>
			<button class="btn">Region Distribution</button>
		</div>
		<div class="rep show">
			<table>
			<thead>
			<tr>
			<th>UserName</th>
			<th>RealName</th>
			<th>EmployeeID</th>
			<th>Telephone</th>
			<th>Quota</th>
			<th>Region</th>
			</tr>
			</thead>
	
			<tbody>
				<?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/reptable.php"); ?>
			</tbody>
			</table>
		</div>
		<div class="rep">
			<canvas id="region_rep"></canvas>
			<script>
        $(document).ready(function () {
            showGraph();
        });


        function showGraph()
        {
            {
                $.post("region_rep.php",
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
                                label: 'Total Reps in each region',
                                backgroundColor: ['#00887d','#014d64','#ee8f71','#01a2d9','#76c0c1','#a18376','#ee8f71','#00887d','#014d64','#ee8f71','#01a2d9'],
                                borderColor: '#46d5f1',
                                hoverBackgroundColor: '#CCCCCC',
                                hoverBorderColor: '#666666',
                                data: marks
                            }
                        ]
                    };

                    var graphTarget = $("#region_rep");
		    Chart.defaults.global.defaultFontColor = 'black';
		    Chart.defaults.global.defaultFontSize = 16;
                    var barGraph = new Chart(graphTarget, {
                        type: 'bar',
                        data: chartdata,
			options:{
					scales:{
						 yAxes:[{
							ticks:{beginAtZero:true}
							}]
						},
					title:{
						display:true,
						text:'Representative wordwide'
						}
				}
                    });
                });
            }
        }
        </script>
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
	</div>
	
	<div class="assign">
		<h2>Assign</h2>
		<form class="assign" action="assign.php" method="post">
                <ul>
                    <li>
                        <label> Rep Name: </label>
                        <?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/getrepsformanager.php");?>
	   	    <li>
			<label> Region:</label>
			<input type="text" name="Region" placeholder="Enter region"/>
		    </li>
		    <li>
			<label> Quota:</label>
			<input type="text" name="Quota" placeholder="Enter quota"/>
		    </li>
                    <li>
                        <input class="buy"  type="submit" value="Assign"/>
                    </li>
                </ul>

            </form>
	</div>
    </div>


</body>
</html>
