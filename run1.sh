hadoop dfs -mkdir /output1
hadoop dfs -mkdir /input

hadoop fs -copyFromLocal  ./data/apk.csv /input/.

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner1 /kasr-data/review.csv /output
hadoop fs -mv /output/part-r-00000 /output1/output1.tsv 
hadoop dfs -rmr /output

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner2 /kasr-data/review.csv /output
hadoop fs -mv /output/part-r-00000 /output1/output2.tsv 
hadoop dfs -rmr /output

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner3 /output1/output1.tsv /output /input/apk.csv /output1/output2.tsv
hadoop fs -mv /output/part-r-00000 /output1/output3.tsv
hadoop dfs -rmr /output 

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner4 /output1/output3.tsv /output
hadoop fs -mv /output/part-r-00000 /output1/output4.tsv
hadoop dfs -rmr /output 

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner5 /output1/output4.tsv /output /kasr-data/restaurant.csv
hadoop fs -mv /output/part-r-00000 /output1/final_output.tsv
hadoop dfs -rmr /output




