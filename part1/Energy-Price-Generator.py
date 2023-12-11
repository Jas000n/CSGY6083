from datetime import datetime, timedelta
import random


zipcodes = ["11201", "02115", "10027", "10163", "15213", "94720", "60208"]


start_date = datetime(2023, 11, 1, 0, 0, 0)
end_date = datetime(2023, 11, 30, 23, 0, 0)
delta = timedelta(hours=1)


insert_statements = []


current_date = start_date
while current_date <= end_date:
    for zipcode in zipcodes:
        # random price
        price = round(random.uniform(0.05, 0.20), 2)
        statement = f"INSERT INTO EnergyPrice (zipcode, price, time, isDeleted) VALUES ('{zipcode}', {price}, '{current_date.strftime('%Y-%m-%d %H:%M:%S')}', 0);"
        insert_statements.append(statement)
    current_date += delta

print(insert_statements[:10])
file_path = './price3.sql'


with open(file_path, 'w') as file:
    for statement in insert_statements:
        file.write(statement + "\n")