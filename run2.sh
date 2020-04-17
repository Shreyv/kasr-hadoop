hadoop dfs -mkdir /output2

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner1 /kasr-data/review1.csv /output
hadoop fs -mv /output/part-r-00000 /output2/output2.tsv 
hadoop dfs -rmr /output

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner2 /kasr-data/review1.csv /output
hadoop fs -mv /output/part-r-00000 /output2/output2.tsv 
hadoop dfs -rmr /output

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner3 /output2/output2.tsv /output /input/apk.csv /output2/output2.tsv
hadoop fs -mv /output/part-r-00000 /output2/output3.tsv
hadoop dfs -rmr /output 

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner4 /output2/output3.tsv /output
hadoop fs -mv /output/part-r-00000 /output2/output4.tsv
hadoop dfs -rmr /output 

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner5 /output2/output4.tsv /output /kasr-data/restaurant.csv
hadoop fs -mv /output/part-r-00000 /output2/final_output.tsv
hadoop dfs -rmr /output




