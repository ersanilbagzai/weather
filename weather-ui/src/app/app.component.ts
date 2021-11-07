import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormControl } from '@angular/forms';
import {never, Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { ColDef } from 'ag-grid-community';
import { WeatherDayData } from './model/weatherDayData';
import { OptionsComponent } from './options/options.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ChartComponent } from './chart/chart.component';
import { configs } from './configs/configs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'sample-ui';

  myControl = new FormControl();
  options: string[] = [];
  filteredOptions?: Observable<string[]>;

  ngOnInit(): void {

    this.http.get<string[]>(configs.baseUrl + '/city/detail',this.httpOptions).subscribe(
      val => {
        this.options = val
      }
    );
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value)),
    );

    this.populateGrid('indore');
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    this.http.get<string[]>(configs.baseUrl + '/city/detail',this.httpOptions).subscribe(
      val => {
        this.options = val
      }
    );
    
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  httpOptions ={
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  };  

  constructor(private http: HttpClient, private dialog: MatDialog) { 
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };  

    this.http.get<string[]>(configs.baseUrl + '/city/detail',this.httpOptions).subscribe(
      val => {
        this.options = val
      }
    );
  }

  // can take any variable here and this variable can be accessed in the app.component.html file using {{ varName.varInside}}
  data = {
    courseTitle : 'My title'
  };

  onLogoClick(){
    alert('hii')
  }

  onKeyUp(value:string){
    this.data.courseTitle = value;
  }

  // data from the data file
  //cityDetails = CITYDATA[0];


  /// ag grid 

  frameworkComponents = {
    options: OptionsComponent
  }

  columnDefs: ColDef[] = [
    { field: 'Date', sortable: true, sort: 'desc' , filter: 'agTextColumnFilter'},
    { field: 'Day_Min_Temp' , filter: 'agTextColumnFilter'},
    { field: 'Day_Max_Temp', filter: 'agTextColumnFilter'},
    { field: 'Wind_Speed', filter: 'agTextColumnFilter'},
    { field: 'Description', filter: 'agTextColumnFilter'},
    { field: 'Suggestion', filter: 'agTextColumnFilter'},
    { field: 'City', hide: true},
    {headerName: '', field: 'icon', cellRenderer: 'options', pinned: 'right'}
];

rowData = [
  { Date: 'Toyota', Day_Min_Temp: 'Celica', Day_Max_Temp: 35000 },
  { Date: 'Ford', Day_Min_Temp: 'Mondeo', Day_Max_Temp: 32000 },
  { Date: 'Porsche', Day_Min_Temp: 'Boxter', Day_Max_Temp: 72000 }
];

dRowData=[
  { Date: 'Toyota', Day_Min_Temp: 'Celica', Day_Max_Temp: 35000 }
];

weatherArr?:WeatherDayData[];
record:any;

populateGrid(select:any){
  this.http.get<any>(configs.baseUrl + '/weather/detail?city=' + select,this.httpOptions).subscribe(
    val => {
      console.log(val)
      this.weatherArr = val.weatherDayData;

      this.dRowData = [];

      var index = 0;
      this.weatherArr?.forEach(cValue => {

        var dat = new Date(cValue.date);
        this.record = { Date: dat , 
          Day_Min_Temp: cValue.weatherInfo.tempMin, 
          Day_Max_Temp: cValue.weatherInfo.tempMax,
          Wind_Speed: cValue.wind.speed,
          Description: cValue.weather[0].description,
          Suggestion: cValue.weatherInfo.suggestion != null ? cValue.weatherInfo.suggestion : 'Have a nice day', 
          City: select};

        this.dRowData[index] = this.record;
        index++;
      });
      console.log('ddd')
      console.log(this.rowData)
      this.rowData = this.dRowData;
  //     this.rowData = [
  //       { Date: 'Toyota', Day_Min_Temp: 'Celica', Day_Max_Temp: 35000 },
  // { Date: 'Ford', Day_Min_Temp: 'Mondeo', Day_Max_Temp: 32000 }
  //   ];
    
    }
  );
}
// pagination

// enables pagination in the grid
pagination:boolean = true;

// sets 10 rows per page (default is 100)
paginationPageSize = 3;

  
// chart dialog 
openDialog() {

  const dialogConfig = new MatDialogConfig();

  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;

  this.dialog.open(ChartComponent, dialogConfig);
}

}
