import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AgRendererComponent } from 'ag-grid-angular';
import { ICellRendererParams } from 'ag-grid-community';
import { ChartComponent } from '../chart/chart.component';
import { ChartInput } from '../model/chartInput';

@Component({
  selector: 'app-options',
  templateUrl: './options.component.html',
  styleUrls: ['./options.component.css']
})
export class OptionsComponent implements OnInit,AgRendererComponent {

  private displayValue: ChartInput;

  constructor(private dialog: MatDialog) {
    this.displayValue = {date: 1636267244, city:'indore'}
   }
  refresh(params: ICellRendererParams): boolean {
    throw new Error('Method not implemented.');
  }

  ngOnInit(): void {
  }

  agInit(params: ICellRendererParams): void {
    this.displayValue.date = params.data.Date;
    this.displayValue.city = params.data.City;
    // this.displayValue = new Array(params.data.Date).fill('#').join('');
    console.log('HIiiiiiiiiiiiiiiiiiiiiiiiii')
    console.log(params)
    // console.log(this.displayValue);
  }

  titl = 'as';

  // tempOptionClicked(){
  //   alert(`${this.displayValue} medals won!`);
  // }
  
  tempOptionClicked() {

    const dialogConfig = new MatDialogConfig();
  
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;
    dialogConfig.data = this.displayValue;
    //dialogConfig.minWidth = '1000px';
  
    //ChartComponent.arguments.date = `${this.displayValue}`;

    this.dialog.open(ChartComponent, dialogConfig);
  }

}
