use test;

db.zips.aggregate([
 	{$match:  {$or: [{state: "CA"}, {state: "NY"}]} }, 
 	{$group: {_id: {city: "$city", state: "$state"}, total: {$sum: "$pop"} } },
 	{$match: {total: {$gt: 25000} } }, 
 	{$group: {_id: null, avg_pop: {$avg: "$total"} } } 
]);

