/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package course.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static java.util.stream.Collectors.toList;

public class Homework3 {

    public static void main(String[] args) {

        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");

        MongoCursor<Document> iterator = collection.find().iterator();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            List<Document> scores = (List<Document>) doc.get("scores");

            Optional<Document> bestHomeWork = scores.stream().max((a, b) -> a.getString("type").equals("homework") && b.getString("type").equals("homework") ? Double.compare(a.getDouble("score"), b.getDouble("score")) : -1);
            Double bestScore = bestHomeWork.get().getDouble("score");

            List<Document> result = scores.stream()
                    .filter(x -> !x.getString("type").equals("homework") || x.getDouble("score").equals(bestScore))
                    .collect(Collectors.toList());

            collection.updateOne(eq("_id", doc.get("_id")),
                    new Document("$set", new Document("scores", result)));

        }
        iterator.close();
        client.close();


    }
}
