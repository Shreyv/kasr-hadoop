import json,re
res_id = set()
with open("reviews.csv") as fp:
    line = fp.readline()
    while line:
        res_id.add(line.split(",")[0])
        line = fp.readline()

res_file = open("restaurant1.csv",'w')
res_file.write("business_id,name,address,rating\n")
with open("yelp_academic_dataset_business.json") as fp:
    line = fp.readline()
    while line:
        data = json.loads(line)
        id = data['business_id']
        if id in res_id:
                res_file.write(id+','+data['name']+','+str(data['address']).replace(',',';')+','+str(data['stars'])+'\n')
        line = fp.readline()        
