import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as Highcharts from 'highcharts';
import { configs } from '../configs/configs';
import { QHourlyResponse } from '../model/qhourlyResponse';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  date?:Date;
  city?:string;
  categoriesArr:string[];
  valArr:number[];
  
  highcharts = Highcharts;

  chartOptions: Highcharts.Options = {
    title: {
      text: "Average Temprature"
      //text: `${this.date}`
    },
    xAxis: {
      title: {
        text: 'Tokyo'
      },
      categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
    },
    yAxis: {
      title: {
        text: "Temprature"
      }
    },
    series: [{
      data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 24.4, 19.3, 16.0, 18.4, 17.9],
      type: 'spline'
    }]
  };

  httpOptions ={
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  };  

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private http: HttpClient) { 
    console.log('inside chart component' + data.date)
    console.log('inside chart component' + data.city)

    this.date = data.date;
    this.city = data.city;

    console.log(this.date?.getTime())

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };  

    this.categoriesArr = [];
    this.valArr = [];

    this.http.get<QHourlyResponse[]>(configs.baseUrl + '/weather/detail/qhourly?city=' + this.city + '&date=' + this.date?.getTime() ,this.httpOptions).subscribe(
      val => {
        console.log(val)
        var index = 0;
        val.forEach(cValue => {
          //cValue.date;
          this.categoriesArr[index] = new Date(cValue.date) + "";
          this.valArr[index] = cValue.value;
          console.log(cValue + ' ' + cValue.date)
          index++;
        });

        this.chartOptions = {
          title: {
            //text: "Hourly Temprature"
            //text: `${this.date}`
          },
          xAxis: {
            title: {
              text: 'Time Range'
            },
            categories: this.categoriesArr
          },
          yAxis: {
            title: {
              text: "Temprature"
            }
          },
          series: [{
            name : "Temp Range",
            data: this.valArr,
            type: 'spline'
          }]
        };

        console.log(this.categoriesArr)
        console.log(this.valArr)
      }
    );


    

    console.log('Chartoptions : ' + this.chartOptions.xAxis)
  }

  
  ngOnInit(): void {
  }

  

}
