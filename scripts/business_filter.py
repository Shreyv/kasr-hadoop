import json

cat = set()
test_list = [
    'Restaurant',
    'restaurant',
    'food',
    'Food'
]

filepath = 'yelp_academic_dataset_business.json'
csv_file = open("categories.csv",'w')
cnt = 1
with open(filepath) as fp:
   line = fp.readline()
   restaurant_count = 1
   while line:
       flag = True
       data = json.loads(line)
       category = data['categories']
       if category is not None:
          for ele in test_list:
              if ele in category:
                  flag = False
          if flag:
            for c in category.split(","):
                if c.strip() not in cat:
                    cat.add(c.strip())
                    csv_file.write(str(restaurant_count)+','+c.strip()+','+data['categories'].replace(',','|')+'\n')
                    restaurant_count += 1
    

       line = fp.readline()
       