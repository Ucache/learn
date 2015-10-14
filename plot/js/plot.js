var plotapp = angular.module('plotApp',["highcharts-ng"])

plotapp.controller('plotController', function($scope){
	$scope.addPoints = function () {
        var seriesArray = $scope.highchartsNG.series
        var rndIdx = Math.floor(Math.random() * seriesArray.length);
        seriesArray[rndIdx].data = seriesArray[rndIdx].data.concat([1, 10, 20])
    };

    $scope.addSeries = function () {
        var rnd = []
        for (var i = 0; i < 10; i++) {
            rnd.push(Math.floor(Math.random() * 20) + 1)
        }
        $scope.highchartsNG.series.push({
            data: rnd
        })
    }

    $scope.verifyData = function(){
    	var lines = $scope.chartData.split("\n")
    	if (lines.length<1){
    		alert("no input data")
    		return
    	}
    	var len = lines[0].split(",").length
    	if(len > 3){
    		alert("demition larger than 2 is not support")
    		this.error = "has-error"
    		return
    	}
    	var data = []
    	for(var i=0;i<lines.length;i++){
    		line = lines[i].split(",").map(function(item){
    			return parseFloat(item,10)
    		})

    		if(line.length != len){
    			alert("bad data format")
    			this.highchartsNG.errors = "has-error"
    			return
    		}

    		data.push(line)
    	}
    	$scope.highchartsNG.series=[]
    	$scope.highchartsNG.series.push({
    		type: "scatter",
    		data: data
    	})
    	this.highchartsNG.errors = "has-success"
    	this.calRegressLine()
    }

    $scope.calRegressLine = function(){

    	var multiply = function(a,b){
    		var xa = a.length
    		if (xa < 1){
    			return
    		}
    		var ya = a[0].length

    		var xb = b.length
    		if (xb < 1){
    			return
    		}
    		var yb = b[0].length

    		if (ya != xb){
    			return
    		}

    		ret = []
    		for (var i=0;i<xa;i++){
    			ret[i] = []
    			for(var j=0;j<yb;j++){
    				var sum = 0
    				for(var k=0;k<xb;k++){
    					sum += a[i][k] * b[k][j]
    				}
    				ret[i].push(parseFloat(sum))
    			}
    		}
    		return ret
    	}

    	var transpose = function(m){
    		var x = m.length
    		var y = x
    		if (x > 0){
    			y = m[0].length
    		}

    		var ret = []
    		for(var j=0;j<y;j++){
    			ret[j] = []
    			for(var i=0;i<x;i++){
    				ret[j].push(m[i][j])
    			}
    		}

    		return ret
    	}

    	//only inverse 2*2 matrix
    	var invert = function(m){
    		var x = m.length
    		if (x >2 || x < 1){
    			return
    		}

    		var y = m[0].length
    		if (x != y){
    			return
    		}

    		var det = m[0][0]*m[1][1] - m[0][1]*m[1][0]
    		if (det == 0){
    			return
    		}

    		ret=[]
    		ret[0] = [m[1][1]/det, -m[0][1]/det]
    		ret[1] = [-m[1][0]/det, m[0][0]/det]

    		return ret

    	}
    	var X = []
    	var y = []
    	var data = $scope.highchartsNG.series[0].data
    	for(var i=0;i<data.length;i++){
    		X[i] = [1,data[i][0]]
    		y[i] = [data[i][1]]
    	}
    	//alert(X)
    	var tx = transpose(X)
    	//alert(tx)
    	//alert(y)
    	var tmp = multiply(invert(multiply(tx,X)),tx)
    	var theta = multiply(multiply(invert(multiply(tx,X)),tx),y)
    	
    	var xzero = X[0][1]
    	var xone = X[X.length - 1][1]
    	$scope.highchartsNG.series.push({
    		type:"line",
    		data:[[xzero ,parseFloat(theta[0]) + parseFloat(xzero * theta[1])], [xone , parseFloat(theta[0]) + parseFloat(xone * theta[1])]]
    	})
    }

    $scope.removeRandomSeries = function () {
        var seriesArray = $scope.highchartsNG.series
        var rndIdx = Math.floor(Math.random() * seriesArray.length);
        seriesArray.splice(rndIdx, 1)
    }

    $scope.options = {
        type: 'line'
    }

    $scope.swapChartType = function () {
        if (this.highchartsNG.options.chart.type === 'line') {
            this.highchartsNG.options.chart.type = 'bar'
        } else {
            this.highchartsNG.options.chart.type = 'line'
        }
    }

    $scope.chartData = "0,1\n1,1.5\n2,2.8\n3,3.5\n4,3.9\n5,4.2"
    $scope.highchartsNG = {
        
        series: [{
        	type: 'scatter',
            data: [1, 1.5, 2.8, 3.5, 3.9, 4.2]
        }],
        title: {
            text: 'Hello'
        },
        loading: false
    }

});