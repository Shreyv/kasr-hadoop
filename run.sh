hadoop dfs -rmr /output
hadoop dfs -rmr /output1
hadoop dfs -rmr /input

hadoop dfs -mkdir /output1
hadoop dfs -mkdir /input

hadoop fs -copyFromLocal  ./data/apk.csv /input/.
hadoop fs -copyFromLocal  ~/Desktop/review.csv /input/.
hadoop fs -copyFromLocal  ~/Desktop/restaurant.csv /input/.

hadoop fs -rm /output1/output2.tsv
hadoop fs -rm /output1/output3.tsv

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner1 /input/review.csv /output
hadoop fs -mv /output/part-r-00000 /output1/output1.tsv 
hadoop dfs -rmr /output

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner2 /input/review.csv /output
hadoop fs -mv /output/part-r-00000 /output1/output2.tsv 
hadoop dfs -rmr /output

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner3 /output1/output1.tsv /output /input/apk.csv /output1/output2.tsv
hadoop fs -mv /output/part-r-00000 /output1/output3.tsv
hadoop dfs -rmr /output 

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner4 /output1/output3.tsv /output
hadoop fs -mv /output/part-r-00000 /output1/output4.tsv
hadoop dfs -rmr /output 

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner5 /output1/output4.tsv /output /input/restaurant.csv
hadoop fs -copyToLocal  /output/part-r-00000 ~/Desktop/.




