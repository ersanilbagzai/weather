import { Coord } from './coord';

export interface City{
    id:number;
    name:string;
    state:string;
    country:string;
    coord:Coord;
}