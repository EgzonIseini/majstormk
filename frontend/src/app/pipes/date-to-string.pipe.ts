import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateToString'
})
export class DateToStringPipe implements PipeTransform {

    transform(date: Date) {
        if (!(typeof date === typeof Date)) { date = new Date(date); }
        return `${date.getUTCDate()}/${date.getUTCMonth()}/${date.getUTCFullYear()}`;
    }

}
