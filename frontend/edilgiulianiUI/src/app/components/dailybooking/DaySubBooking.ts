import {Mezzo} from "../../services/models/mezzo";
import {SubBooking} from "../../services/models/sub-booking";
import {IsoInterval} from "../../services/models/iso-interval";

export interface DaySubBooking{
  mezzo: Mezzo,
  giorni: Date[]
}

export function daysubTOsub(d : DaySubBooking ){
  let newd = new implSubBooking()
  newd.idmezzo=d.mezzo.id
  d.giorni.forEach(
    value => {
      let newInterval = new implISOInterval()
      value.setHours(7)
      newInterval.start=value.toISOString()
      value.setHours(16)
      newInterval.end=value.toISOString()
      newd.intervals.push(newInterval)
    }
  )
  return newd
}

class implSubBooking implements SubBooking {
  idmezzo: number | undefined = 0
  intervals: IsoInterval[] = []
}

class implISOInterval implements IsoInterval{
  start= ""
  end = ""
}
