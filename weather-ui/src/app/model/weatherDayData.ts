import { Weather } from './weather';
import { WeatherInfo } from './weatherInfo';
import { Wind } from './wind';

export interface WeatherDayData{
    date:number;
    weatherInfo:WeatherInfo;
    wind:Wind;
    weather:Weather[];
}