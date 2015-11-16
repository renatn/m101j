db.zips.aggregate([
    {$project: {
    	pop: 1,
		firstChar: {$substr : ["$city",0,1]},
    }},
    {$match: {
    	firstChar: {$in: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]}
    }},
    {$group: {
    	_id: null,
    	total: {$sum: "$pop"}
    }}
]);
