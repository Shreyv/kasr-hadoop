hadoop dfs -mkdir /output3


hadoop fs -copyFromLocal  ./data/apk.csv /input/.


echo "====================> Starting Step 1 <======================"

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner1 /kasr-data/review2.csv /output
hadoop fs -mv /output/part-r-00000 /output3/output3.tsv 
hadoop dfs -rmr /output

echo "====================> Starting Step 2 <======================"

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner2 /kasr-data/review2.csv /output
hadoop fs -mv /output/part-r-00000 /output3/output2.tsv 
hadoop dfs -rmr /output

echo "====================> Starting Step 3 <======================"

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner3 /output3/output3.tsv /output /input/apk.csv /output3/output2.tsv
hadoop fs -mv /output/part-r-00000 /output3/output3.tsv
hadoop dfs -rmr /output 

echo "====================> Starting Step 4 <======================"

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner4 /output3/output3.tsv /output
hadoop fs -mv /output/part-r-00000 /output3/output4.tsv
hadoop dfs -rmr /output 

echo "====================> Starting Step 5 <======================"

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner5 /output3/output4.tsv /output /kasr-data/restaurant.csv
hadoop fs -mv /output/part-r-00000 /output3/final_output.tsv
hadoop dfs -rmr /output




