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

    $scope.highchartsNG = {
        options: {
	        yAxis: {
	            min: 0,
	            max: 5
	        },
            xAxis: {
	            min: -0.5,
	            max: 5.5
        	}
        },
        series: [{
        	type:'line',
        	data:[[0, 1.11], [5, 4.51]]
        },{
        	type: 'scatter',
            data: [1, 1.5, 2.8, 3.5, 3.9, 4.2]
        }],
        title: {
            text: 'Hello'
        },
        loading: false
    }

});