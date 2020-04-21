import json

test_list = [
    'Restaurant',
    'restaurant',
    'food',
    'Food'
]

bid = set()



filepath = 'yelp_academic_dataset_business.json'
csv_file = open("restaurant.csv",'w')
cnt = 1
with open(filepath) as fp:
   line = fp.readline()
   reviews = 0
   restaurant_count = 1
   while line:
       flag = False
       data = json.loads(line)
       id = data['business_id']
       name =  data['name']
       city =   data['city']
       category = data['categories']
       if category is not None : 
            for ele in test_list:
                if ele in category:
                    flag = True
            if flag:
                bid.add(id)        
                csv_file.write(
                        id+','+name+','+city+','+category.replace(",","|")+'\n'
                    )
                reviews += int(data['review_count'])
                restaurant_count += 1
       line = fp.readline()
   print(str(reviews) + ', ' + str(restaurant_count))     

print("\n##########################\n")
   

rev_file = open("reviews.csv",'w')
rev_file.write("business_id\tuid\trid\tstars\tcomment\n")

with open("yelp_academic_dataset_review.json") as fp:
   line = fp.readline()
   while line:
    data = json.loads(line)
    id = data['business_id']
    if id in bid:
        u_id = data['user_id']
        r_id = data['review_id']
        stars = data['stars']
        text = data['text']
        rev_file.write(id+','+u_id+','+r_id+','+str(stars)+','+text.replace('\n','.').replace(',',';')+'\n')
    line = fp.readline()
