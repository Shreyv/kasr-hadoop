hadoop dfs -rmr /temp_output
hadoop dfs -rmr /input

hadoop dfs -mkdir /temp_output
hadoop dfs -mkdir /input

hadoop fs -copyFromLocal  ./data/apk.csv /input/.
hadoop fs -copyFromLocal  ./data/restaurant.csv /kasr-data/.

echo "======================> Step 1 <====================="

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner3 /kasr-data/output1.tsv /output /input/apk.csv /kasr-data/output2.tsv
hadoop fs -mv /output/part-r-00000 /temp_output/output3.tsv
hadoop dfs -rmr /output 

echo "======================> Step 2 <====================="


hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner4 /temp_output/output3.tsv /output
hadoop fs -mv /output/part-r-00000 /temp_output/output4.tsv
hadoop dfs -rmr /output 

echo "======================> Step 3 <====================="


hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner5 /temp_output/output4.tsv /output /kasr-data/restaurant.csv
hadoop fs -mv /output/part-r-00000 /temp_output/final_output.tsv
hadoop dfs -rmr /output 


hadoop fs -copyToLocal /temp_output/final_output.tsv ./result/
