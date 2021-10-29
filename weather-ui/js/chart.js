var chartOp = {
    chart: {
      type: 'spline'
    },
    title: {
      text: 'Temperature Change'
    },
    subtitle: {
      text: ''
    },
    xAxis: {
      type: 'datetime',
      dateTimeLabelFormats: { // don't display the dummy year
        month: '%e. %b',
        year: '%b'
      },
      title: {
        text: 'Date'
      }
    },
    yAxis: {
      title: {
        text: 'Snow Temp (Kl)'
      },
      min: 0
    },
    tooltip: {
      headerFormat: '<b>{series.name}</b><br>',
      pointFormat: '{point.x:%e. %b}: {point.y:.2f} kl'
    },
  
    plotOptions: {
      series: {
        marker: {
          enabled: true
        }
      }
    },
  
    colors: ['#6CF', '#39F', '#06C', '#036', '#000'],
  
    // Define the data points. All series have a dummy year
    // of 1970/71 in order to be compared on the same x axis. Note
    // that in JavaScript, months start at 0 for January, 1 for February etc.
    series: [{
      name: "",
      data: [
        [Date.UTC(1970, 10, 25, 0), 100],
        [Date.UTC(1970, 10, 25, 3), 200],
        [Date.UTC(1970, 10, 25, 6), 150],
        [Date.UTC(1970, 10, 25, 9), 300],
        [Date.UTC(1970, 10, 25, 12), 275],
        [Date.UTC(1970, 10, 25, 15), 300],
        [Date.UTC(1970, 10, 25, 18), 300],
        [Date.UTC(1970, 10, 25, 21), 250]
      ]
    }],
  
    responsive: {
      rules: [{
        condition: {
          maxWidth: 500
        },
        chartOptions: {
          plotOptions: {
            series: {
              marker: {
                radius: 2.5
              }
            }
          }
        }
      }]
    }
  };

  function updateChart(data){
      console.log(data);
      var chartDataToUpdate = [];
      var index = 0;
      data.forEach(function(cValue){
        var record = [cValue.date, cValue.value];
        chartDataToUpdate[index] = record;
        index++;
      });
     console.log(chartDataToUpdate);

     chartOp.series[0].data = chartDataToUpdate;

     console.log(chartOp.series[0].data);
    Highcharts.chart('container', chartOp);
  }

  
