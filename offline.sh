hadoop dfs -mkdir /kasr-data

hadoop fs -copyFromLocal  ./data/reduced_reviews.csv /kasr-data/.

echo "===================> Step 1 <===================="

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner1 /kasr-data/reduced_reviews.csv /output
hadoop fs -mv /output/part-r-00000 /kasr-data/output1.tsv 
hadoop dfs -rmr /output

echo "===================> Step 2 <===================="

hadoop jar ./target/KASR-Recommendation-1.0-SNAPSHOT.jar runner.JobRunner2 /kasr-data/reduced_reviews.csv /output
hadoop fs -mv /output/part-r-00000 /kasr-data/output2.tsv 
hadoop dfs -rmr /output

