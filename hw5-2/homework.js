use test;
db.grades.aggregate([
	{$unwind: "$scores"},
	{$match: 
		{$or: [{"scores.type": "homework"}, {"scores.type": "exam"}]}
	},
	{$project: {
		student_id: 1,
		class_id: 1,
		score: "$scores.score"
	}},
	{$group: {
		_id: {cid: "$class_id", sid: "$student_id"},
		avg_score: {$avg: "$score"}
	}},
	{$group: {
		_id: "$_id.cid",
		avg_total: {$avg: "$avg_score"}
	}},
	{$sort: {
		avg_total: -1
	}}
]);

