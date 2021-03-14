function changepic1() {
            var obj = document.getElementById("maskpic");
            //obj.setAttribute("style", "background:url('img/n95respirator3.png') no-repeat;background-position: 50% 20%;background-color:rgba(206, 200, 200, 0.39);box-shadow: 0 5px 15px rgba(0,0,0,.5);");
		obj.style="background:url('img/n95respirator3.png') no-repeat;background-position: 50% 20%;background-color:rgba(206, 200, 200, 0.39);box-shadow: 0 5px 15px rgba(0,0,0,.5);";
        }
        function changepic2() {
            var obj = document.getElementById("maskpic");
           //obj.setAttribute("style", "background:url('img/surgicalmask2.png') no-repeat;background-position:50% 20%;background-color:rgba(206, 200, 200, 0.39);box-shadow: 0 5px 15px rgba(0,0,0,.5);");
		obj.style="background:url('img/surgicalmask2.png') no-repeat;background-position:50% 20%;background-color:rgba(206, 200, 200, 0.39);box-shadow: 0 5px 15px rgba(0,0,0,.5);";
        }
        function changepic3() {
            var obj = document.getElementById("maskpic");
            //obj.setAttribute("style", "background:url('img/surgicaln95.png') no-repeat;background-position: 50% 20%;background-color:rgba(206, 200, 200, 0.39);box-shadow: 0 5px 15px rgba(0,0,0,.5);");
		obj.style="background:url('img/surgicaln95.png') no-repeat;background-position: 50% 20%;background-color:rgba(206, 200, 200, 0.39);box-shadow: 0 5px 15px rgba(0,0,0,.5);";
        }
        function sub() {
            var num=parseInt(document.getElementById("amount").value);
            if (num>0)
                document.getElementById("amount").value = parseInt(document.getElementById("amount").value) - 1;
            else{
                document.getElementById("amount").value =0;
            }
        }
        function plus() {
            document.getElementById("amount").value = parseInt(document.getElementById("amount").value) + 1;            
        }