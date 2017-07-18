# JSON API Design

> Races that are cancelled will only have the `cancelled`, `reason`, `raceDate.*`, `track.*`, and `raceNumber` properties.

| Field  | Type | Description | Notes | Sample Value(s) |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| cancelled  | boolean  | `true` if the race was cancelled  |  | `true`<br/>`false`  |
| reason  | string  | The reason behind the cancellation | Only present if `cancelled` has a value of `true` | "Track Conditions"  |
| raceDate  | object  | | `raceDate` and all child fields are always present |  |
| raceDate.text  | string  | The date of the race | Format: `"YYYY-MM-DD"` | "2016-07-24" |
| raceDate.year  | number  | The year of the race | A four-digit number | `2016` |
| raceDate.month  | number  | The month of the race | A value between 1-12 | `7` |
| raceDate.day  | number  | The day of the race | A value between 1-31, depending on the month | `24` |
| raceDate.dayOfWeek  | string  | The day of the week of this race | The full day name is used | "Sunday" |
| raceDate.dayOfYear  | number  | The day of the year of this race | A value between 1-365 (or 366 for leap years) | `206` |
| track  | object  | The details of the track where the race occurred | `track` and all child fields are always present |  |
| track.code  | string  | A short code for the track | A two- or three-character value; uppercase | "ARP" |
| track.country  | string  | A short country code | A two- or three-character value; uppercase | "USA" |
| track.name  | string  | The full track name | Uppercase | "ARAPAHOE PARK" |
| raceNumber  | number  | The official race number | Always present | `3` |
| conditions  | object  | | `raceDate` and all child fields always present |  |
| conditions.breed  | string  | The breed of horse this race was for | One of four possible values; uppercase | "THOROUGHBRED"<br/>"QUARTER_HORSE"<br/>"ARABIAN"<br/>"MIXED" |
| conditions.type  | string  | The classification of the race | Always present; uppercase | "ALLOWANCE"<br/>"MAIDEN SPECIAL WEIGHT"<br/>"STAKES"<br/>"CLAIMING" |
| conditions.name  | string  | The name of the race (if any) |  | "Mount Elbert S."<br/>"Kentucky Derby Presented by Yum! Brands" |
| conditions.grade  | number  | The grade of the race (if any) | A value between 1-3 | `1` |
| conditions.blackType  | string  | The black-type description of this race (if any) | Present when the race qualifies as a black-type race | "Grade 1"<br/>"Grade 3"<br/>"Listed"<br/>"Black Type" |
| conditions.text  | string  | The full race conditions text | | "FOR MAIDENS, TWO YEARS OLD. Weight, 120 lbs."<br/>"FOR THREE YEAR OLDS AND UPWARD WHICH HAVE NEVER WON TWO RACES. Weight, 124 lbs. (NW2 L)" |
| conditions.claimingPriceRange  | object  | The claim price range for the starters in the race  | Only present for claiming races |  |
| conditions.claimingPriceRange.min  | number  | The minimum claim price  |  | `5000` |
| conditions.claimingPriceRange.max  | number  | The maximum claim price  | If equal to `min`, it means claims are only at that price | `10000` |
| distance  | object  | | `distance` and all child fields are always present |  |
| distance.text  | string  | The distance of the race as described on the chart | | "One And One Sixteenth Miles"<br/>"Three Hundred And Fifty Yards"<br/>"Six Furlongs" |
| distance.compact  | string  | A compact description of the race distance |  | "1 1/16m"<br/>"350y"<br/>"6f" |
| distance.feet  | number  | The distance of the race in feet | 3 feet in a yard<br/>660 feet in a furlong<br/>5280 feet in a mile | `5610 `<br/>`1050 `<br/>`3960 ` |
| distance.furlongs  | number  | The distance of the race in furlongs | Up to two decimal places<br/>220 yards in a furlong<br/>8 furlongs in a mile | `8.5`<br/>`1.59`<br/>`6` |
| distance.exact  | boolean  | Whether the distance of the race is measured (`true`) or estimated (`false`) | `true` if the distance description starts with "About" | `true`<br/>`false` |
| distance.runUp  | number  | The distance between the starting stalls and the "start" electronic timer | Value is in feet | `30`<br/>`0` |
| surface  | string  | The surface the race was run on |  | "Dirt"<br/>"Turf"<br/>"Inner turf" |
| trackCondition  | string  | A description of the track/surface condition |  | "Fast"<br/>"Sloppy (Sealed)"<br/>"Muddy" |
| surface  | string  | The surface the race was supposed to be run on |  | "Turf" |
| offTurf  | boolean  | If the race was scheduled to be run on the turf, but was actually run on the main track |  | `true`<br/>`false` |
| trackRecord  | object  | The record time for this race distance and surface listed in the chart | |  |
| trackRecord.holder  | string  | The name of the track record holder | | "No It Ain't" |
| trackRecord.time  | string  | Text version of the track record time | Format: `M:SS:sss`<br/>Three decimal places | "1:08.190"<br/>"0:17.045" |
| trackRecord.millis  | number  | The track record time in milliseconds | | `68190`<br/>`17045` |
| trackRecord.raceDate  | object  | | When `trackRecord.raceDate` is present, all child fields are present |  |
| trackRecord.raceDate.text  | string  | The date of the track record race | Format: `"YYYY-MM-DD"` | "2011-08-12" |
| trackRecord.raceDate.year  | number  | The year of the track record race | A four-digit number | `2011` |
| trackRecord.raceDate.month  | number  | The month of the track record race | A value between 1-12 | `8` |
| trackRecord.raceDate.day  | number  | The day of the track record race | A value between 1-31, depending on the month | `12` |
| trackRecord.raceDate.dayOfWeek  | string  | The day of the week of the track record race | The full day name is used | "Friday" |
| trackRecord.raceDate.dayOfYear  | number  | The day of the year of the track record race | A value between 1-365 (or 366 for leap years) | `224` |
| purse  | object  | The prize money for this race | |  |
| purse.value  | number  | The value of the race | | `9700`<br/>`40000` |
| purse.text  | string  | Text version of the race prize money | | "$9,700"<br/>"$40,000 Guaranteed" |
| purse.availableMoney  | string  | Text of the "available money" description | | "$9,700"<br/>"$40,000" |
| purse.enhancements  | string  | A comma-separated list of purse enhancements that applied to this race | `null` if no enhancements | "Includes: $3,000 AQHA-American Quarter Horse Association, Plus: $500 Other Sources" |
| purse.valueOfRace  | string  | A detailed description of the prize money distribution (if any) | | "$9,700 1st $5,820, 2nd $1,940, 3rd $970, 4th $582, 5th $194, 6th $97, 7th $97" |
| weather  | object  | The weather conditions present during the race | |  |
| weather.text  | string  | A short description of the weather conditions | | "Clear" |
| weather.wind  | object  | The wind conditions present during the race | Only present for Quarter Horse or Mixed breed races |  |
| weather.wind.speed  | number  | The wind speed | In miles-per-hour | `2` |
| weather.wind.direction  | string  | The direction of the wind |  | "Head"  |
| postTime  | string  | The time listed in the chart as to when the race started | | "1:50" |
| startComments  | string  | The description listed in the chart about how well the field started | | "Good for all except 5" |
| timer  | string  | Description of how the race was timed | Only present for Quarter Horse or Mixed breed races | "Electronic" |
| deadHeat  | boolean  | `true` if the race resulted in a dead-heat  |  | `true`<br/>`false`  |
| numberOfRunners  | number  | The numbers of horses that officially ran in the race  |  | `9` |
| starters  | array  | The list of participants that started the race |  |  |
| starters[]  | object  | A participant in the race |  |  |
| starters[].lastRaced  | object  | The details of the last race this starter participated in (if any) |  |  |
| starters[].lastRaced.raceDate  | object  | The details of the last race date |  |  |
| starters[].lastRaced.raceDate.text  | string  | The date of the last race | Format: `"YYYY-MM-DD"` | "2016-09-19" |
| starters[].lastRaced.raceDate.year  | number  | The year of the last race | A four-digit number | `2016` |
| starters[].lastRaced.raceDate.month  | number  | The month of the last race | A value between 1-12 | `9` |
| starters[].lastRaced.raceDate.day  | number  | The day of the last race | A value between 1-31, depending on the month | `19` |
| starters[].lastRaced.raceDate.dayOfWeek  | string  | The day of the week of the last race | The full day name is used | "Sunday" |
| starters[].lastRaced.raceDate.dayOfYear  | number  | The day of the year of the last race | A value between 1-365 (or 366 for leap years) | `171` |
| starters[].lastRaced.daysSince  | number  | The number of days since the last race |  | `35` |
| starters[].lastRaced.track  | object  | | `starters[].lastRaced.track` and all child fields are present when a last race exists |  |
| starters[].lastRaced.track.code  | string  | A short code for the track | A two- or three-character value; uppercase | "ARP" |
| starters[].lastRaced.track.country  | string  | A short country code | A two- or three-character value; uppercase | "USA" |
| starters[].lastRaced.track.name  | string  | The full track name | Uppercase | "ARAPAHOE PARK" |
| starters[].lastRaced.raceNumber  | number  | The race number of the last race | May be `null` for foreign races | `3` |
| starters[].lastRaced.officialPosition  | number  | The official finishing position in the last race | May be `null` for foreign races | `6` |
| starters[].program  | string  | The starter's program number | | "6"<br/>"1A"<br/>"20" |
| starters[].horse  | object  | The details of the horse | |  |
| starters[].horse.name  | string  | The horse's name | Always present | "Back Stop"<br/>"Jila (IRE)" |
| starters[].horse.color  | string  | The horse's color | Only present for winners | "Bay" |
| starters[].horse.sex  | string  | The horse's gender/sex | Only present for winners | "Filly" |
| starters[].horse.sire | object | The horse's father | Only present for winners |  |
| starters[].horse.sire.name | object | The sire's name | Only present for winners | "Blame" |
| starters[].horse.dam | object | The horse's mother | Only present for winners |  |
| starters[].horse.dam.name | object | The dam's name | Only present for winners | "Freeroll" |
| starters[].horse.damSire | object | The horse's mother's father | Only present for winners |  |
| starters[].horse.damSire.name | object | The dam sire's name | Only present for winners | "Touch Gold" |
| starters[].horse.foalingDate  | object  | The details of the last race this starter participated in (if any) | Only present for winners |  |
| starters[].horse.foalingDate.text  | string  | The date when the horse was born | Only present for winners<br/>Format: `"YYYY-MM-DD"` | "2012-03-30" |
| starters[].horse.foalingDate.year  | number  | The year when the horse was born | Only present for winners<br/>A four-digit number | `2012` |
| starters[].horse.foalingDate.month  | number  | The month when the horse was born | Only present for winners<br/>A value between 1-12 | `3` |
| starters[].horse.foalingDate.day  | number  | The day when the horse was born | Only present for winners<br/>A value between 1-31, depending on the month | `30` |
| starters[].horse.foalingDate.dayOfWeek  | string  | The day of the week when the horse was born | Only present for winners<br/>The full day name is used | "Friday" |
| starters[].horse.foalingDate.dayOfYear  | number  | The day of the year when the horse was born | Only present for winners<br/>A value between 1-365 (or 366 for leap years) | `90` |
| starters[].horse.foalingLocation  | string  | The location of where the horse was born | Only present for winners<br/>The full day name is used | "Kentucky" |
| starters[].horse.breeder | object | The details of the horse's breeder | Only present for winners |  |
| starters[].horse.breeder.name | string | The breeder's name | Only present for winners | "Claiborne Farm" |
| starters[].jockey | object | The details of the horse's jockey |  |  |
| starters[].jockey.name | string | The jockey's full name |  | "Dennis Collins" |
| starters[].jockey.firstName | string | The jockey's first name |  | "Dennis" |
| starters[].jockey.lastName | string | The jockey's last name |  | "Collins" |
| starters[].trainer | object | The details of the horse's trainer |  |  |
| starters[].trainer.name | string | The trainer's full name |  | "Stetson Rushton" |
| starters[].trainer.firstName | string | The trainer's first name |  | "Stetson" |
| starters[].trainer.lastName | string | The trainer's last name |  | "Rushton" |
| starters[].owner | object | The details of the horse's owner | |  |
| starters[].owner.name | string | The owner's full name |  | "Rockin R Racing Stable" |
| starters[].weight | object | The details of the weight carried by the starter | |  |
| starters[].weight.carried | number | The number of pounds (lbs) carried |  | `124` |
| starters[].weight.jockeyAllowance | number | The number of pounds (lbs) not carried due to the jockey's allowance | One of five values | `0`<br/>`3`<br/>`5`<br/>`7`<br/>`10` |
| starters[].medicationEquipment | object | The details of the medications and equipment for the starter | |  |
| starters[].medicationEquipment.text | string | The chart's short code text of the starter's medication and equipment | | `BL f` |
| starters[].medicationEquipment.medications | array | The details of the medications applied to the starter | |  |
| starters[].medicationEquipment.medications[] | object | A medication applied to the starter | |  |
| starters[].medicationEquipment.medications[].code | string | The short code for the medication | | "B"<br/>"L" |
| starters[].medicationEquipment.medications[].text | string | The description of the medication | | "Bute"<br/>"Lasix" |
| starters[].medicationEquipment.equipment | array | The details of the equipment carried by the starter | |  |
| starters[].medicationEquipment.equipment[] | object | A piece of equipment carried by the starter | |  |
| starters[].medicationEquipment.equipment[].code | string | The short code for the equipment | | "f" |
| starters[].medicationEquipment.equipment[].text | string | The description of the equipment | | "Front Bandages" |
| starters[].claim | object | The claim details of the starter | Only present when the starter is available to be claimed |  |
| starters[].claim.price | number | The price the starter could be claimed at | Only present when the starter was available to be claimed | `5000` |
| starters[].claim.claimed | boolean | Whether the starter was claimed or not | Only present when the starter was available to be claimed | `true`<br/>`false` |
| starters[].claim.newTrainerName | string | The name of the new trainer post-claim | Only present when `claimed` is `true` | "R. Mike Scudder" |
| starters[].claim.newOwnerName | string | The name of the new owner post-claim | Only present when `claimed` is `true` | "R. Mike Scudder" |
| starters[].postPosition | number | The starter's post position | | `6` |
| starters[].finishPosition | number | The starter's unofficial finishing position | Can be different to `officialPosition` after disqualifications | `1` |
| starters[].officialPosition | number | The starter's official finishing position | Should be used the final finishing position | `1` |
| starters[].winner | boolean | Whether the starter officially won the race |  | `true`<br/>`false` |
| starters[].disqualified | boolean | Whether the starter was disqualified from their finishing position |  | `true`<br/>`false` |
| starters[].odds | number | The starter's odds for the race | Odds are expressed as "to one (unit)" e.g. 3.4/1 | `3.4` |
| starters[].choice | number | The starter's position in the betting order | Favorites are first choice (`1`), third favorites are `3` etc. | `2` |
| starters[].favorite | boolean | Whether the starter was mutuel favorite for the race |  | `true`<br/>`false` |
| starters[].wagering | object | The win-place-show details (if any) for the starter | Only present if the starter had a mutuel win, and/or place, and/or show payoff |  |
| starters[].wagering.win | object | The mutuel win details for the starter | Only present if the starter had a mutuel win  payoff, otherwise `null` |  |
| starters[].wagering.win.unit | number | The wagering unit the payoff is based on | $2 is the default for win-place-show | `2` |
| starters[].wagering.win.payoff | number | The mutuel payoff for the above wagering unit |  | `2.6` |
| starters[].wagering.win.odds | number | The odds equivalent for this unit and payoff | Odds are expressed as "to one (unit)" | `0.3` |
| starters[].wagering.place | object | The mutuel place details for the starter | Only present if the starter had a mutuel place payoff, otherwise `null` |  |
| starters[].wagering.place.unit | number | The wagering unit the payoff is based on | $2 is the default for win-place-show | `2` |
| starters[].wagering.place.payoff | number | The mutuel payoff for the above wagering unit |  | `2.2` |
| starters[].wagering.place.odds | number | The odds equivalent for this unit and payoff | Odds are expressed as "to one (unit)" | `0.1` |
| starters[].wagering.show | object | The mutuel show details for the starter | Only present if the starter had a mutuel show payoff, otherwise `null` |  |
| starters[].wagering.show.unit | number | The wagering unit the payoff is based on | $2 is the default for win-place-show | `2` |
| starters[].wagering.show.payoff | number | The mutuel payoff for the above wagering unit |  | `2.2` |
| starters[].wagering.show.odds | number | The odds equivalent for this unit and payoff | Odds are expressed as "to one (unit)" | `0.1` |
| starters[].pointsOfCall | array | The points of call listed in the chart for the race<br/>Each point of call describes the race position of the starter, the number of lengths ahead of the next starter, and total lengths behind the leader |  |  |
| starters[].pointsOfCall[] | object | A call at a particular point of the race |  |  |
| starters[].pointsOfCall[].point | number | The point number<br/>`1` is the first point of call, `6` is the finish | Shorter races have fewer points of call | `3` |
| starters[].pointsOfCall[].text | string | The chart's description of the point of call |  | "Start"<br/>"1/4"<br/>"1m" |
| starters[].pointsOfCall[].feet | number | The number of feet traveled in the race to this point of call |  | `2640` |
| starters[].pointsOfCall[].furlongs  | number  | The number of furlongs traveled in the race to this point of call | Up to two decimal places | `4` |
| starters[].pointsOfCall[].relativePosition | object | Tracks the position of the starter at the point of call relative to the other starters |  |  |
| starters[].pointsOfCall[].relativePosition.position | number | The position in the field at the point of call |  | `6` |
| starters[].pointsOfCall[].relativePosition.lengthsAhead | object | The details about how far ahead the starter was at the point of call from the next starter | `null` if either no value available or in last position |  |
| starters[].pointsOfCall[].relativePosition.lengthsAhead.text | string | The lengths ahead as text from the chart |  | "1/2"<br/>"Neck" |
| starters[].pointsOfCall[].relativePosition.lengthsAhead.lengths | number | The numeric total lengths behind |  | `0.5`<br/>`0.25` |
| starters[].pointsOfCall[].relativePosition.totalLengthsBehind | object | The details about how far behind the starter was at the point of call from the leader | `null` if either no value available or in first position |  |
| starters[].pointsOfCall[].relativePosition.totalLengthsBehind.text | string | The total lengths behind as text from the chart |  | "4 1/2" |
| starters[].pointsOfCall[].relativePosition.totalLengthsBehind.lengths | number | The numeric total lengths behind the leader |  | `4.5` |
| starters[].fractionals | array | The list of calculated individual fractionals for the starter |  |  |
| starters[].fractionals[] | object | The details of an individual fractional |  |  |
| starters[].fractionals[].point | number | The fractional point in question | Values range from `1` to `6` | `1` |
| starters[].fractionals[].text | string | The chart text description of where the fractional was measured |  | "1/4" |
| starters[].fractionals[].feet | number | The number of feet traveled in the race to this fractional point  |  | `1320` |
| starters[].fractionals[].furlongs  | number  | The number of furlongs traveled in the race to this fractional point | Up to two decimal places | `2` |
| starters[].fractionals[].time | string | A text version of the time taken by the starter to reach this fractional point | Format: "M:SS.sss" | "0:22.880" |
| starters[].fractionals[].millis | number | The time taken (in milliseconds) for the starter to reach this fractional point |  | `22880` |
| starters[].splits | array | The list of calculated individual split times between fractionals for the starter |  |  |
| starters[].splits[] | object | The details of an individual split |  |  |
| starters[].splits[].point | number | The split point in question | Values range from `1` to `6` | `1` |
| starters[].splits[].text | string | Describes the in-race distance the split covers  |  | "1/4 to 1/2"<br/>"5/8 to Fin"<br/>"Start to 1/4" |
| starters[].splits[].feet | number | The number of feet traveled in the race for the split  |  | `1320` |
| starters[].splits[].furlongs  | number  | The number of furlongs traveled in the race for the split | Up to two decimal places | `2` |
| starters[].splits[].time | string | A text version of the time taken by the starter for the split | Format: "M:SS.sss" | "0:23.620" |
| starters[].splits[].millis | number | The time taken (in milliseconds) for the starter for the split |  | `23620` |
| starters[].splits[].from | object | The fractional point the split started at | `null` if it is the start of the race |  |
| starters[].splits[].from.point | number | The fractional point in question | Values range from `1` to `6` | `1` |
| starters[].splits[].from.text | string | The chart text description of where the fractional was measured |  | "1/4" |
| starters[].splits[].from.feet | number | The number of feet traveled in the race to this fractional point  |  | `1320` |
| starters[].splits[].from.furlongs  | number  | The number of furlongs traveled in the race to this fractional point | Up to two decimal places | `2` |
| starters[].splits[].from.time | string | A text version of the time taken by the starter to reach this fractional point | Format: "M:SS.sss" | "0:22.880" |
| starters[].splits[].from.millis | number | The time taken (in milliseconds) for the starter to reach this fractional point |  | `22880` |
| starters[].splits[].to | object | The fractional point the split ended at |  |  |
| starters[].splits[].to.point | number | The fractional point in question | Values range from `1` to `6` | `2` |
| starters[].splits[].to.text | string | The chart text description of where the fractional was measured |  | "1/2" |
| starters[].splits[].to.feet | number | The number of feet traveled in the race to this fractional point  |  | `2640` |
| starters[].splits[].to.furlongs | number | The number of furlongs traveled in the race to this fractional point  | Up to two decimal places | `4` |
| starters[].splits[].to.time | string | A text version of the time taken by the starter to reach this fractional point | Format: "M:SS.sss" | "0:46.500" |
| starters[].splits[].to.millis | number | The time taken (in milliseconds) for the starter to reach this fractional point |  | `46500` |
| starters[].ratings | array | The list of associated ratings for the starter | Always present; empty if no ratings |  |
| starters[].ratings[] | object | The details of a rating |  |  |
| starters[].ratings[].name | string | The rating name |  | "AQHA Speed Index" |
| starters[].ratings[].text | string | The text description of the rating value  |  | "78p" |
| starters[].ratings[].value | number | The numeric value for this rating  |  | `78.0` |
| starters[].ratings[].extra  | string  | Any additional text that affects the rating e.g. "p" for "improvement likely" | Only present if not `null` | "p" |
| starters[].comments | string | The brief race comments listed in the chart for the starter's performance |  | "speed off rail 3wd tr" |
| wagering| object | The wagering details for the race (win-place-show and exotics) | Only present if the race had wagering |  |
| wagering.winPlaceShow | object | The win-place-show details for the race | Only present if the race had a mutuel win, and/or place, and/or show payoffs |  |
| wagering.winPlaceShow.totalWPSPool | number | The combined pool total for win, place, and show betting |  |  |
| wagering.winPlaceShow.payoffs | array | The win/place/show payoffs for the race |  |  |
| wagering.winPlaceShow.payoffs[] | object | The win-place-show details for the race for a starter |  |  |
| wagering.winPlaceShow.payoffs[].program | string | The program number of the mutuel starter |  | "2" |
| wagering.winPlaceShow.payoffs[].horse | object | The details of the mutuel starter |  | "2" |
| wagering.winPlaceShow.payoffs[].horse.name | string | The name of the mutuel starter |  | "2" |
| wagering.winPlaceShow.payoffs[].win | object | The mutuel win details for the starter | Only present if the starter had a mutuel win  payoff, otherwise `null` |  |
| wagering.winPlaceShow.payoffs[].win.unit | number | The wagering unit the payoff is based on | $2 is the default for win-place-show | `2` |
| wagering.winPlaceShow.payoffs[].win.payoff | number | The mutuel payoff for the above wagering unit |  | `2.6` |
| wagering.winPlaceShow.payoffs[].win.odds | number | The odds equivalent for this unit and payoff | Odds are expressed as "to one (unit)" | `0.3` |
| wagering.winPlaceShow.payoffs[].place | object | The mutuel place details for the starter | Only present if the starter had a mutuel place payoff, otherwise `null` |  |
| wagering.winPlaceShow.payoffs[].place.unit | number | The wagering unit the payoff is based on | $2 is the default for win-place-show | `2` |
| wagering.winPlaceShow.payoffs[].place.payoff | number | The mutuel payoff for the above wagering unit |  | `2.2` |
| wagering.winPlaceShow.payoffs[].place.odds | number | The odds equivalent for this unit and payoff | Odds are expressed as "to one (unit)" | `0.1` |
| wagering.winPlaceShow.payoffs[].show | object | The mutuel show details for the starter | Only present if the starter had a mutuel show payoff, otherwise `null` |  |
| wagering.winPlaceShow.payoffs[].show.unit | number | The wagering unit the payoff is based on | $2 is the default for win-place-show | `2` |
| wagering.winPlaceShow.payoffs[].show.payoff | number | The mutuel payoff for the above wagering unit |  | `2.2` |
| wagering.winPlaceShow.payoffs[].show.odds | number | The odds equivalent for this unit and payoff | Odds are expressed as "to one (unit)" | `0.1` |
| wagering.exotics | array | The exotic wagering details for the race | Only present if valid exotics were found |  |
| wagering.exotics[] | object | The details of an exotic wager for the race |  |  |
| wagering.exotics[].unit | number | The wagering unit the payoff is based on |  | `2`<br/>`1`<br/>`0.1` |
| wagering.exotics[].name | string | The name of the exotic wager |  | "Exacta"<br/>"Daily Double"<br/>"Trifecta" |
| wagering.exotics[].winningNumbers | string | The text description of the winning combination of program numbers |  | "2-1"<br/>"2-1-4-ALL"<br/>"5/13/14/15/16-1-4" |
| wagering.exotics[].numberCorrect | number | The number of correct picks required for the exotic wager payoff | For example, `5` (consolation) or `6` in a Pick 6; `null` when not applicable | `3`<br/>`null` |
| wagering.exotics[].payoff | number | The mutuel payoff for the above wagering unit  |  | `36` |
| wagering.exotics[].odds | number | The odds equivalent for this unit and payoff | Odds are expressed as "to one (unit)" | `17` |
| wagering.exotics[].pool | number | The gross amount bet into the pool |  | `1688` |
| wagering.exotics[].carryover | number | The amount to be carried over into the next applicable pool | `null` if no carryover | `1335505`<br/>`null` |
| scratches | array  | The horses scratched from the race and, if available, the reason why | Empty array if there were no scratches |  |
| scratches[]  | object  | The details of a horse scratched from the race |  |  |
| scratches[].horse  | object  | A horse scratched from the race |  |  |
| scratches[].horse.name  | string  | The name of the horse scratched |  | "Cat With a Twist" |
| scratches[].reason  | string  | The reason the horse was scratched | `null` if not available | "Trainer"<br/>"Veterinarian" |
| fractionals | array | The list of fractional times registered by the leader at each fractional point |  |  |
| fractionals[] | object | The details of an individual fractional |  |  |
| fractionals[].point | number | The fractional point in question | Values range from `1` to `6` | `1` |
| fractionals[].text | string | The chart text description of where the fractional was measured |  | "1/4" |
| fractionals[].feet | number | The number of feet traveled in the race to this fractional point  |  | `1320` |
| fractionals[].furlongs  | number  | The number of furlongs traveled in the race to this fractional point | Up to two decimal places | `2` |
| fractionals[].time | string | A text version of the time taken by the leader to reach this fractional point | Format: "M:SS.sss" | "0:22.880" |
| fractionals[].millis | number | The time taken (in milliseconds) for the leader to reach this fractional point |  | `22880` |
| splits | array | The list of split times registered by the leader between two fractional points |  |  |
| splits[] | object | The details of the split times  |  |  |
| splits[].point | number | The split point in question | Values range from `1` to `6` | `1` |
| splits[].text | string | Describes the in-race distance the split covers  |  | "1/4 to 1/2"<br/>"5/8 to Fin"<br/>"Start to 1/4" |
| splits[].feet | number | The number of feet traveled in the race for the split  |  | `1320` |
| splits[].furlongs  | number  | The number of furlongs traveled in the race for the split | Up to two decimal places | `2` |
| splits[].time | string | A text version of the time taken by the leader for the split | Format: "M:SS.sss" | "0:23.620" |
| splits[].millis | number | The time taken (in milliseconds) for the leader for the split |  | `23620` |
| splits[].from | object | The fractional point the split started at | `null` if it is the start of the race |  |
| splits[].from.point | number | The fractional point in question | Values range from `1` to `6` | `1` |
| splits[].from.text | string | The chart text description of where the fractional was measured |  | "1/4" |
| splits[].from.feet | number | The number of feet traveled in the race to this fractional point  |  | `1320` |
| splits[].from.furlongs  | number  | The number of furlongs traveled in the race to this fractional point | Up to two decimal places | `2` |
| splits[].from.time | string | A text version of the time taken by the leader to reach this fractional point | Format: "M:SS.sss" | "0:22.880" |
| splits[].from.millis | number | The time taken (in milliseconds) for the leader to reach this fractional point |  | `22880` |
| splits[].to | object | The fractional point the split ended at |  |  |
| splits[].to.point | number | The fractional point in question | Values range from `1` to `6` | `2` |
| splits[].to.text | string | The chart text description of where the fractional was measured |  | "1/2" |
| splits[].to.feet | number | The number of feet traveled in the race to this fractional point  |  | `2640` |
| splits[].to.furlongs | number | The number of furlongs traveled in the race to this fractional point  | Up to two decimal places | `4` |
| splits[].to.time | string | A text version of the time taken by the leader to reach this fractional point | Format: "M:SS.sss" | "0:46.500" |
| splits[].to.millis | number | The time taken (in milliseconds) for the leader to reach this fractional point |  | `46500` |
| ratings | array | The list of associated ratings for the race | Always present; empty if no ratings |  |
| ratings[] | object | The details of a rating |  |  |
| ratings[].name | string | The rating name |  | "Power Rating" |
| ratings[].text | string | The text description of the rating value  |  | "165.0 (1st)" |
| ratings[].value | number | The numeric value for this rating  |  | `165.0` |
| ratings[].extra  | string  | Any additional text that affects the rating | Only present if not `null` | "1st" |
| footnotes  | string  | The full race comments as provided in the chart | | "BACK STOP showed good early speed but did not get to the rail raced three wide on the turn and was best late. REGAL SUNSET came to join the..." (truncated) |
